package plumber.engine;

import mobiloids.Pair;
import plumber.engine.models.PlumberModelPortal;
import plumber.engine.models.PlumberModelSwitch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 6/12/17.
 */
public class PlumberSwitchlLevelGenerator {

    private Random rand;
    private PlumberModelSwitch model;
    int HEIGHT = 8;
    int WIDTH = 6;
    int TOTAL = HEIGHT * WIDTH;
    int TOTAL_ANGLES = 8;
    int TOTAL_TUBES = 8;
    int TOTAL_EMPTY = 0;
     int difficulty = 0;
    int [] indexes = {
            101, 105, 111, 124, 134, 140, 141, 142, 148, 155, 158, 160, 166, 169, 173, 175, 182, 185, 190, 191, 203, 207, 210, 224, 228, 229, 230, 232, 233, 237, 247, 250, 255, 261, 265, 284, 288, 290, 291, 294, 295, 303, 311, 312, 314, 315, 328, 330, 332, 339, 340, 342, 343, 345, 348, 350, 353, 354, 357, 371, 372, 376, 384, 388, 390, 399, 408, 411, 413, 418, 426, 432, 433, 435, 436, 441, 444, 453, 466, 468, 474, 476, 478, 480, 487, 489, 500, 502};

    int [] indexes2 = {
            514, 516, 518, 521, 526, 530, 531, 532, 537, 544, 551, 552, 554, 563, 565, 567, 570, 576, 577, 583, 591, 595, 600, 602, 606, 608, 610, 612, 617, 620, 624, 625, 626, 630, 631, 633, 638, 645, 648, 649, 653, 654, 661, 663, 667, 669, 670, 672, 674, 685, 689, 690, 700, 704, 705, 707, 708, 712, 713, 719, 720, 721, 723, 725, 728, 729, 730, 731, 732, 735, 740, 742, 744, 746, 748, 749, 755, 761, 762, 766, 771, 773, 778, 781, 783, 787, 789, 794, 796, 797, 800, 808, 809, 811, 815, 818, 820, 835, 840, 845, 850, 853, 855, 864, 866, 871, 873, 878, 880, 882, 887, 888, 895, 896, 899, 909, 910, 913, 917, 920, 921, 935, 939, 940, 945, 947, 949, 953, 955, 957, 959, 965, 966, 967, 970, 975, 978, 979, 983, 985, 987, 988, 989, 991, 998, 1004, 1015, 1016, 1018, 1019, 1020, 1021, 1024 };

    int [] indexes3 = {
            1, 4, 10, 12, 13, 15, 19, 26, 32, 37, 45, 48, 50, 58, 64, 67, 69, 70, 72, 79, 81, 82, 83, 87, 91, 93, 96, 104, 108, 110, 111, 113, 115, 121, 122, 124, 126, 127};
    int [] indexes4 = {
            130, 131, 134, 135, 137, 140, 141, 147, 152, 157, 161, 162, 166, 169, 172, 175, 176, 184, 190, 192, 193, 195, 197, 199, 202, 211, 212, 213, 215, 219, 230, 237, 240, 243, 248, 255 };

    //
//    final int TOTAL = 24;
//    final int TOTAL_ANGLES = 6;
//    final int TOTAL_TUBES = 6;
//    final int TOTAL_EMPTY = 10;
    int [][] level;
    public PlumberSwitchlLevelGenerator(int count, int minComplexity, int maxComplexity) {

        int[] anglesCountPerPackage =
                {1, 16, 16, 16, 16, 16, 15, 15,
                15, 15, 15, 15, 15, 15, 15, 15,
                25, 25, 25, 25, 25, 25, 25, 25,
                25, 25, 25, 25, 25, 25, 25, 25,
                25, 25, 25, 25, 25, 25, 25, 25
                            };

        int[] anglesCountPerPackageRandComponent =
                {1, 20, 20, 20, 20, 20, 22, 20,
                20, 16, 15, 14, 20, 20, 20, 20,
                20, 22, 22, 24, 22, 22, 24, 24,
                25, 25, 25, 25, 25, 25, 25, 25,
                25, 25, 25, 25, 25, 25, 25, 25};

        int[] difficultiesQuiz =    {
                10, 15, 25, 30, 40, 50, 60, 65};

        int[] difficulties =    {
                12, 12, 12, 16, 12, 12, 18, 12,
                12, 12, 12, 20, 14, 19, 34, 22,//old values

                25, 25, 30, 25, 25, 32, 25, 20,
                25, 30, 25, 25, 25, 20, 35, 25, // second pack easy 6x8 with some hard levels

                27, 27, 32, 27, 27, 27, 27, 27,
                27, 32, 27, 27, 27, 27, 37, 27, // 3-rd pack

                28, 28, 33, 28, 28, 28, 28, 28,
                28, 28, 33, 28, 28, 28, 28, 38,   //4-th pack

                35, 30, 30, 30, 30, 35, 30, 30,
                30, 30, 30, 40, 30, 30, 30, 30,   //5-th pack

                32, 37, 32, 32, 32, 32, 32, 32,
                32, 32, 37, 32, 42, 32, 32, 32,   //6-th pack

                34, 34, 39, 34, 34, 34, 34, 34,
                34, 39, 34, 44, 34, 34, 34, 34,   //7-th pack

                36, 41, 36, 36, 36, 36, 36, 36,
                36, 41, 36, 36, 36, 36, 46, 36,   //8-th pack

                43, 38, 38, 38, 38, 38, 38, 38,
                38, 38, 38, 43, 38, 38, 38, 48,   //9-th pack

                40, 40, 40, 45, 40, 45, 40, 40,
                40, 40, 40, 50, 40, 40, 40, 40,   //10-th pack

                42, 42, 47, 42, 42, 42, 42, 42,
                42, 47, 42, 52, 42, 42, 42, 42,   //11-th pack

                44, 49, 44, 44, 44, 44, 44, 44,
                44, 44, 44, 49, 44, 44, 44, 54,   //12-th pack

                46, 46, 51, 46, 46, 46, 51, 46,
                46, 46, 46, 46, 56, 46, 46, 46,   //13-th pack

                48, 53, 48, 48, 48, 48, 48, 48,
                48, 56, 48, 48, 53, 48, 48, 48,   //14-th pack

                50, 55, 50, 50, 55, 50, 50, 50,
                50, 50, 50, 50, 55, 50, 50, 50,   //15-th pack

                55, 52, 52, 52, 52, 52, 52, 52,
                55, 52, 52, 52, 55, 52, 52, 56,   //16-th pack

                55, 55, 55, 58, 55, 55, 55, 50,
                55, 58, 55, 55, 55, 55, 60, 55,   //17-th pack

                57, 57, 57, 57, 60, 57, 57, 57,
                57, 60, 57, 57, 57, 57, 60, 57,    //18-th pack

                59, 59, 62, 59, 59, 59, 59, 62,
                59, 59, 59, 59, 59, 59, 59, 63,    //19-th pack

                60, 60, 63, 60, 60, 60, 60, 63,
                60, 60, 60, 60, 63, 60, 60, 60,    //20-th pack

                61, 61, 61, 61, 63, 61, 61, 63,
                61, 63, 61, 61, 61, 63, 61, 61,    //21-th pack

                62, 62, 64, 62, 62, 62, 62, 64,
                64, 62, 62, 62, 62, 62, 62, 64,    //22-th pack

                63, 65, 65, 63, 63, 63, 65, 63,
                63, 66, 63, 63, 63, 63, 63, 65,    //23-th pack

                64, 66, 66, 64, 64, 67, 64, 64,
                66, 64, 64, 64, 66, 63, 63, 67,    //24-th pack


                65, 65, 65, 67, 67, 65, 70, 65,
                65, 65, 67, 65, 65, 65, 65, 70,    //25-th pack

                66, 66, 66, 68, 66, 66, 66, 69,
                69, 66, 66, 70, 66, 66, 66, 71,    //26-th pack

                67, 67, 69, 67, 67, 67, 67, 69,
                67, 69, 67, 67, 69, 69, 72, 69,    //27-th pack

                68, 70, 68, 68, 68, 70, 68, 68,
                68, 68, 68, 68, 70, 68, 68, 73,    //28-th pack

                69, 71, 69, 69, 69, 69, 71, 69,
                69, 69, 71, 69, 69, 69, 74, 69,    //29-th pack

                70, 70, 72, 70, 70, 70, 70, 70,
                70, 72, 70, 70, 72, 75, 70, 70,    //30-th pack

                71, 71, 73, 71, 71, 71, 71, 73,
                73, 71, 73, 71, 76, 71, 71, 71,    //31-th pack

                72, 74, 72, 74, 72, 72, 72, 72,
                72, 74, 72, 74, 72, 79, 72, 72,    //32-th pack

                73, 75, 73, 73, 73, 73, 73, 73,
                75, 73, 73, 75, 73, 73, 73, 78,    //33-th pack

                74, 74, 76, 74, 74, 76, 74, 74,
                74, 74, 74, 76, 74, 79, 74, 74,    //34-th pack

                75, 77, 75, 75, 75, 75, 77, 75,
                75, 75, 77, 75, 75, 75, 75, 80,    //35-th pack

                78, 76, 76, 76, 78, 76, 76, 76,
                76, 78, 76, 76, 76, 76, 81, 76,    //36-th pack

                77, 77, 77, 79, 77, 77, 77, 77,
                77, 77, 82, 77, 77, 77, 79, 77,    //37-th pack

                78, 78, 78, 80, 78, 78, 78, 78,
                78, 78, 80, 78, 78, 83, 78, 78,    //38-th pack


                79, 79, 79, 79, 79, 79, 79, 81,
                79, 79, 79, 81, 79, 79, 79, 84,    //39-th pack


        };


        int eps = 5;

        rand = new Random();
        level = new int [HEIGHT][WIDTH];
        int levelsGenerated = 0;
        int step = 1;
        count = 412;
        int levelStartNumber = 352;

        int difStartNumber = 121;
//        while(levelsGenerated < count) {


        while(levelsGenerated < indexes3.length) {
            int level = indexes3[levelsGenerated];


            //Todo open after 1 level
           // TOTAL_ANGLES = anglesCountPerPackage[(levelStartNumber + levelsGenerated) /16] + rand.nextInt(anglesCountPerPackageRandComponent[(levelStartNumber + levelsGenerated) /16]) ;

            //System.out.println(TOTAL_ANGLES);

            int switches  = rand.nextInt(3) + 2;
            //int randInt = level < 350 ? 15 : 25;
            //int randInt = level < 800 ? 25 : 35;
            //TOTAL_ANGLES = 25 + rand.nextInt(randInt);
            TOTAL_ANGLES = 15 + rand.nextInt(25);
            TOTAL_TUBES = TOTAL - TOTAL_ANGLES - 2 - switches;
            PlumberModelSwitch model = generateLevel(switches);
            //checkConnectivity(model);
            if (model == null) {
                continue;
            }
            ArrayList<WaterPathSegment> solution = model.shortestPath();


            if (solution != null) {
                difficulty = (TOTAL_ANGLES + 2 * solution.size()) / 2;
               // System.out.println(difficulty);
//                if (difficulty > 65) {
//                    //System.out.println(solution.size() + " " + difficulty);
//                    System.out.println(difficulty);
//                }


////Todo open after 1 levels
//                if (Math.abs(difficulty - difficulties[difStartNumber + levelsGenerated / 2 - 1]) > eps ) {
//                    continue;
//                }
//                if (Math.abs(difficulty - difficulties[40 +levelStartNumber +(level- levelStartNumber)/2]) > eps ) {
//                    continue;
//                }
//                if (Math.abs(difficulty - difficulties[levelStartNumber + (level- 512) / 2]) > eps ) {
//                    continue;
//                }
                if (difficulty  < 60 ) {
                    continue;
                }

                if (solution.size() < (2 * difficulty / 3)) {
                    continue;
                }


//                if (solution.size() < 2) {
//                    if (solution.size() >= 30) {
//                        System.out.println(solution.size() + " " + TOTAL_ANGLES);
//                    }
//
//                    continue;
//                }
                boolean isGood = false;
                //Checking if there is a double used double tube
//                for (WaterPath path : solution) {
//                    if (path.isFirstTime == false && path.tube == Tubes.DOUBLE_ANGLE) {
//                        isGood = true;
//                    }
//                }

                //Double angle double used
//                if (!isGood) {
//                    continue;
//                }
                System.out.println("Level " + (levelStartNumber + levelsGenerated) + "  " + solution.size() + " Diff = " + difficulty);
                System.out.println(model.toString());
              //  writeToFile("/home/suren/Documents/plumber_new_levels/classic_switch/" + (levelStartNumber + levelsGenerated) + ".txt", solution);
               // writeToFile("/home/suren/Documents/plumber_new_levels/classic_switch/" + (level) + ".txt", solution);
                writeToFile("/home/suren/Documents/plumber_new_levels/extra_hard/" + (level) + ".txt", solution);

                levelsGenerated++;
            }else {
               // System.out.println("No solution");
            }

        }


    }

    private PlumberModelSwitch generateLevel(int switches) {
        int totalTubes = HEIGHT * WIDTH;



        ArrayList<Pair<Integer,Integer>> cells = new ArrayList<>(48);

//        //Generating connected graph
//        int startX = rand.nextInt(HEIGHT);
//        int startY = rand.nextInt(WIDTH);
//
//        //Adding some cell
//        cells.add(new mobiloids.Pair<>(startX,startY));
//        for(int i = 0; i < TOTAL - TOTAL_EMPTY - 1; i++) {
//
//            //Generating connected graph
//            int x = rand.nextInt(HEIGHT);
//            int y = rand.nextInt(WIDTH);
//
//        }

        //Filling all cells
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {

//                //Tempo
//                if ((i == 0 && j == 0) || (i == HEIGHT - 1 && j == WIDTH - 1))
       //             continue;
                cells.add(new Pair<>(i, j));
            }

        Collections.shuffle(cells);


//        while(!checkConnectivity(cells)) {
//            Collections.shuffle(cells);
//        }
      //  System.out.println("connected...");


        //Randomising tubes counts
        int totalAngleTubes = TOTAL_ANGLES;
        int angles = totalAngleTubes / 3  + rand.nextInt(totalAngleTubes -  totalAngleTubes / 3);
 //       int angles = (2 * totalAngleTubes)  / 3;
        int doubleAngles = totalAngleTubes - angles;



        int remainingTubes = TOTAL_TUBES;

        int tubes;
        if (TOTAL_TUBES == 0) {
            tubes = 0;
        } else {
            tubes = (remainingTubes) / 4 + rand.nextInt(remainingTubes * 3 / 4);
        }
        //tubes = (2 * remainingTubes) / 3;
        //tubes = (remainingTubes) / 3 + rand.nextInt(remainingTubes * 2 / 3);
        int doubleTubes = remainingTubes - tubes;

       // System.out.println(angles + " " + doubleAngles + " " + tubes + " " + doubleTubes);


        //Getting positions for Valve and End tubes
        Pair<Integer, Integer> valve = cells.get(0);
        Pair<Integer, Integer> end = cells.get(1);

        //For hard levels, placing valve and end far from each other
//        if (Math.abs(valve.getKey() - end.getKey()) + Math.abs(valve.getValue() - end.getValue()) < 3 * (WIDTH + HEIGHT) / 4) {
//            return null;
//        }

        //Adding valve with some rotation
        level[valve.getKey()][valve.getValue()] = 500 + 10 * (rand.nextInt(3) + 1) + rand.nextInt(4);

        //Adding end tube with no rotation
        level[end.getKey()][end.getValue()] = 600 + 10 * (rand.nextInt(3) + 1);




        //Adding switches rotation
        for (int i = 0; i< switches; i++) {
            Pair<Integer, Integer> switcher = cells.get(2 + i);
            int switcherColor1 = rand.nextInt(2) + 1;
            int switcherColor2 = rand.nextInt(3 - switcherColor1) + switcherColor1 + 1;
            level[switcher.getKey()][switcher.getValue()] = 11000 + 100 * switcherColor1 + 10 * switcherColor2;
        }



//        //Adding valve with some rotation
//        level[0][0] = 50 + rand.nextInt(4);;
//
//        //Adding end tube with some rotation
//        level[HEIGHT - 1][WIDTH - 1] = 60 + rand.nextInt(4);


      //  System.out.println(angles + " " + doubleAngles + " " + tubes + " " + doubleTubes);
        int cellsIndex = 2 + switches;
        //Placing angle tubes
        for (int i = 0; i < angles; i++) {
            Pair<Integer, Integer> angle = cells.get(cellsIndex++);
            //Placing with maximal rotation for better backtracking performance
            level[angle.getKey()][angle.getValue()] = 20;
        }

        //Placing double angle tubes
        for (int i = 0; i < doubleAngles; i++) {
            Pair<Integer, Integer> doubleAngle = cells.get(cellsIndex++);
            //Placing with maximal rotation for better backtracking performance
            level[doubleAngle.getKey()][doubleAngle.getValue()] = 40;
        }

        //Placing tubes
        for (int i = 0; i < tubes; i++) {
            Pair<Integer, Integer> tube = cells.get(cellsIndex++);

            //Checking corner cells. Placing angle tubes in corners
            if (isCornerCell(tube)) {
                level[tube.getKey()][tube.getValue()] = 20;

            } else {
                //Placing with maximal rotation for better backtracking performance
                level[tube.getKey()][tube.getValue()] = 10;
            }
        }

        //Placing double tubes
        for (int i = 0; i < doubleTubes; i++) {
            Pair<Integer, Integer> doubleTube = cells.get(cellsIndex++);

            //Checking corner cells. Placing angle tubes in corners
            if (isCornerCell(doubleTube)) {
                level[doubleTube.getKey()][doubleTube.getValue()] = 20;

            } else {
                //Placing with maximal rotation for better backtracking performance
                level[doubleTube.getKey()][doubleTube.getValue()] = 30;
            }
        }

        //Placing empty tubes
        for (int i = 0; i < TOTAL_EMPTY; i++) {
            Pair<Integer, Integer> empty = cells.get(cellsIndex++);

            //Placing with maximal rotation for better backtracking performance
            level[empty.getKey()][empty.getValue()] = 70;

        }



        //Checking border items
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if ((i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1 ) && level[i][j] == 30) {
                    level[i][j] = 10;
                }

                if ((i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1 ) && level[i][j] == 40) {
                    level[i][j] = 20;
                }
            }
        }

        int removedSwitch = 0;
        //Checking corner items
        if(level[0][0] / 1000 == 11) {
            level[0][0] = 20;
            removedSwitch++;
        }
        //Checking corner items
        if(level[0][WIDTH - 1] / 1000 == 11) {
            level[0][WIDTH - 1] = 20;
            removedSwitch++;
        }

        //Checking corner items
        if(level[HEIGHT - 1][0] / 1000 == 11) {
            level[HEIGHT - 1][0] = 20;
            removedSwitch++;

        }

        //Checking corner items
        if(level[HEIGHT - 1][WIDTH - 1] / 1000 == 11) {
            level[HEIGHT - 1][WIDTH - 1] = 20;
            removedSwitch++;

        }

        if (switches - removedSwitch < 3) {
            return null;
        }

        //Checking game board connectivity
        if (TOTAL_EMPTY != 0) {
            ConnectionState state = checkBoardConnectivity(level);
            // System.out.println(state);
            if (state != ConnectionState.CONNECTED){
                return null;
            }
        }

        model = new PlumberModelSwitch(level, HEIGHT, WIDTH);

//        for (int i= 0; i< HEIGHT; i++) {
//            for(int j = 0; j< WIDTH; j++) {
//                System.out.print(level[i][j] + " ");
//            }
//            System.out.println();
//        }
       // System.out.println(model.toString());
     //   System.out.println("-------------------------------------------------------------");
        return model;

    }

    private ConnectionState checkBoardConnectivity(int[][] level) {

        boolean [][] wasHere = new boolean[HEIGHT][WIDTH];
        int foundX = 0;
        int foundY = 0;


        outer:
        for(int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (level[i][j] != 70) {
                    foundX = i;
                    foundY = j;
                    break outer;
                }
            }
        }
        Queue <Pair<Integer, Integer>> queue = new ArrayDeque<>();
        queue.add(new Pair<> (foundX, foundY));
        wasHere[foundX][foundY] = true;
        int connectedCount = 1;
        while(!queue.isEmpty()) {

            Pair<Integer, Integer> tube = queue.remove();
            int x = tube.getKey();
            int y = tube.getValue();


            if(x - 1 >= 0 && level[x - 1][y] != 70 && wasHere[x - 1][y] == false ) {
                queue.add(new Pair<>(x - 1, y));
                wasHere[x - 1][y] = true;
                connectedCount++;
            }
            if(x + 1 < HEIGHT && level[x + 1][y] != 70 && wasHere[x + 1][y] == false ) {
                queue.add(new Pair<>(x + 1, y));
                wasHere[x + 1][y] = true;
                connectedCount++;
            }
            if(y - 1 >= 0 && level[x][y - 1] != 70 && wasHere[x][y - 1] == false ) {
                queue.add(new Pair<>(x, y - 1));
                wasHere[x][y - 1] = true;
                connectedCount++;
            }
            if(y + 1 < WIDTH && level[x][y + 1] != 70 && wasHere[x][y + 1] == false ) {
                queue.add(new Pair<>(x, y + 1));
                wasHere[x][y + 1] = true;
                connectedCount++;
            }
        }

        if (connectedCount == TOTAL - TOTAL_EMPTY) {

            //Checking good graph. Each node must have at leas 2 neighbours(except valve and endTube)
        int weeks = 0;
        for(int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if (level[i][j] != 70) {
                    foundX = i;
                    foundY = j;

                    if (level[i][j] / 10 > 4 && level[i][j] / 10 != 7)
                        continue;

                    int connections = 0;
                  //  System.out.println( i + " " + j);
                    if (i > 0 && level[i-1][j] != 70) connections++;
                    if (j > 0 && level[i][j - 1] != 70) connections++;
                    if (i < HEIGHT - 1 && level[i+1][j] != 70) connections++;
                    if (j < WIDTH - 1 && level[i][j + 1] != 70) connections++;

                    if (connections < 2) {
                        weeks++;
                    }

                }
            }
        }

        if (weeks > 1)
            return ConnectionState.WEAK_CONNECTED;
        return ConnectionState.CONNECTED;

        }
      //  System.out.println(connectedCount);
        return ConnectionState.NOT_CONNECTED;

    }


    private boolean checkConnectivity(PlumberModelOld model) {

//        model.
//        int [][] graph = new int[HEIGHT][WIDTH];
//        boolean [][] haveBeen = new boolean[HEIGHT][WIDTH];
//
//        for (int i = 0; i < TOTAL - TOTAL_EMPTY; i++) {
//            mobiloids.Pair<Integer, Integer> pair = cells.get(i);
//            graph[pair.getKey()][pair.getValue()] = 1;
//        }
//
//        Stack<mobiloids.Pair<Integer, Integer>> stack = new Stack<>();
//        stack.add(cells.get(0));
//        haveBeen[cells.get(0).getKey()][cells.get(0).getValue()] = true;
//        int connectedCount = 1;
//        while(!stack.empty()) {
//            mobiloids.Pair<Integer, Integer> item = stack.pop();
//            int x = item.getKey();
//            int y = item.getValue();
//
//            if(x - 1 >= 0 && graph[x - 1][y] == 1 && haveBeen[x - 1][y] == false ) {
//                stack.push(new mobiloids.Pair<>(x - 1, y));
//                haveBeen[x - 1][y] = true;
//                connectedCount++;
//            }
//            if(x + 1 < HEIGHT && graph[x + 1][y] == 1 && haveBeen[x + 1][y] == false ) {
//                stack.push(new mobiloids.Pair<>(x + 1, y));
//                haveBeen[x + 1][y] = true;
//                connectedCount++;
//            }
//            if(y - 1 >= 0 && graph[x][y - 1] == 1 && haveBeen[x][y - 1] == false ) {
//                stack.push(new mobiloids.Pair<>(x, y - 1));
//                haveBeen[x][y - 1] = true;
//                connectedCount++;
//            }
//            if(y + 1 < WIDTH && graph[x][y + 1] == 1 && haveBeen[x][y + 1] == false ) {
//                stack.push(new mobiloids.Pair<>(x, y + 1));
//                haveBeen[x][y + 1] = true;
//                connectedCount++;
//            }
//        }
//        if (connectedCount == TOTAL - TOTAL_EMPTY) {
//            return true;
//        }
        return false;
    }

    private boolean isCornerCell(Pair<Integer, Integer> cell) {
        if (cell.getKey() == 0  || cell.getKey() == HEIGHT - 1) {
            if (cell.getValue() == 0 || cell.getValue() == WIDTH - 1)
                return true;
            return  false;
        }
        if (cell.getValue() == 0  || cell.getValue() == WIDTH - 1) {
            if (cell.getKey() == 0 || cell.getKey() == HEIGHT - 1)
                return true;
            return  false;
        }
        return false;
    }

    public void writeToFile(String filename, ArrayList<WaterPathSegment> solution) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(difficulty +"\n");
            output.write(HEIGHT +"\n");
            output.write(WIDTH +"\n");
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    output.write(level[i][j] +"\n");
                }
            }

            //Writing solution
            output.write(solution.size() + "\n");
            output.write(solution.get(0).x + " " + solution.get(0).y + " " + solution.get(0).rotation + "\n");
            for (int i = 1; i< solution.size() - 1; i++) {
                output.write(solution.get(i).x + " " + solution.get(i).y + " " + solution.get(i+1).rotation + "\n");
            }
            int endX = solution.get(solution.size() - 1).x;
            int endY = solution.get(solution.size() - 1).y;
            output.write(endX + " " + endY + " " + level[endX][endY] % 10 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backtrack(Pair<Integer, Integer> valve, Direction noWay) {

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int count;
        int minComplexity;
        int maxComplexity;

        //Reading generator parameters
//        count = sc.nextInt();
//        minComplexity = sc.nextInt();
//        maxComplexity = sc.nextInt();

        PlumberSwitchlLevelGenerator generator = new PlumberSwitchlLevelGenerator(365, 0, 100);

    }
}
