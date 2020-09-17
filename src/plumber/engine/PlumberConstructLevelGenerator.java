package plumber.engine;

import mobiloids.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 6/12/17.
 */
public class PlumberConstructLevelGenerator {

    private int removedCount;
    private Random rand;
    private PlumberConstructModel model;
    int HEIGHT = 7;
    int WIDTH = 6;
    int TOTAL = HEIGHT * WIDTH;
    int TOTAL_ANGLES = 8;
    int TOTAL_TUBES = 8;
    int TOTAL_EMPTY = 0;
     int difficulty = 0;
//
//    final int TOTAL = 24;
//    final int TOTAL_ANGLES = 6;
//    final int TOTAL_TUBES = 6;
//    final int TOTAL_EMPTY = 10;
    int [][] level;
    public PlumberConstructLevelGenerator(int count, int minComplexity, int maxComplexity) {

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

        int [] indexes = {
                102, 103, 104, 112, 117, 118, 119, 122, 129, 131, 137, 139, 144, 145, 152, 153, 161, 165, 167, 170, 171, 174, 177, 179, 183, 192, 193, 198, 204, 205, 206, 212, 214, 218, 222, 225, 234, 238, 248, 257, 259, 271, 281, 297, 300, 302, 307, 308, 317, 320, 322, 323, 324, 326, 331, 336, 344, 347, 349, 364, 365, 366, 368, 370, 373, 374, 378, 380, 386, 387, 389, 393, 400, 401, 402, 406, 409, 420, 423, 425, 427, 429, 431, 446, 447, 450, 452, 455, 458, 462, 465, 469, 470, 473, 477, 479, 482, 483, 484, 485, 486, 492, 496, 498 };

        int [] indexes2 = {
                513, 523, 529, 534, 540, 541, 555, 569, 571, 572, 573, 578, 584, 587, 588, 592, 593, 597, 598, 605, 615, 619, 623, 632, 636, 641, 646, 647, 650, 651, 659, 665, 676, 677, 680, 682, 683, 684, 693, 695, 696, 710, 716, 727, 750, 756, 757, 759, 760, 763, 770, 775, 776, 779, 785, 791, 793, 801, 804, 805, 829, 832, 841, 842, 858, 859, 860, 861, 875, 877, 881, 884, 885, 889, 892, 894, 897, 898, 902, 906, 907, 912, 914, 915, 925, 926, 927, 928, 931, 932, 950, 954, 962, 974, 981, 982, 986, 994, 995, 997, 1001, 1003, 1014, 1017 };


        int [] indexes3 = {
                2, 14, 23, 24, 35, 39, 40, 43, 46, 51, 52, 53, 54, 55, 56, 57, 60, 65, 68, 76, 78, 85, 86, 88, 92, 97, 98, 99, 100, 116, 120, 123, 128};


        int [] indexes4 = {
                136, 139, 142, 144, 146, 153, 164, 171, 174, 180, 182, 198, 210, 214, 220, 222, 224, 228, 229, 233, 234, 235, 252, 254};

        int[] difficulties =    {
                12, 12, 12, 16, 12, 12, 18, 12,
                12, 12, 12, 20, 14, 19, 20, 22,//old values

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

        };


        int eps = 6;

        rand = new Random();
        level = new int [HEIGHT][WIDTH];
        int levelsGenerated = 0;
        int levelNumber = 513;
        removedCount = 4;
        int step = 1;
        count = 412;
        int levelStartNumber = 200;
        int distributionIndex = 0;


       // while(levelsGenerated < count) {
        while(levelsGenerated < indexes3.length) {
            int level = indexes3[levelsGenerated];


            //Todo open after 1 level
          //  TOTAL_ANGLES = anglesCountPerPackage[(levelStartNumber + levelsGenerated) /16] + rand.nextInt(anglesCountPerPackageRandComponent[(levelStartNumber + levelsGenerated) /16]) ;

           //TOTAL_ANGLES = 10 + rand.nextInt(4);
            TOTAL_ANGLES =  15 + rand.nextInt(10);
          // int randInt = level < 300 ? 13 : 21;
          //  int randInt = level < 800 ? 22 : 30;
       //    TOTAL_ANGLES = 25 + rand.nextInt(randInt);
         //  TOTAL_ANGLES = 15 + rand.nextInt(randInt);

           // TOTAL_ANGLES = 22 + rand.nextInt(22);

            // System.out.println("Total_angles" + TOTAL_ANGLES + " " + levelsGenerated);

            TOTAL_TUBES = TOTAL - TOTAL_ANGLES - 2;
            PlumberConstructModel model = generateLevel();
            //checkConnectivity(model);
            if (model == null) {
                continue;
            }
            removedCount = rand.nextInt(3) +2;
            ArrayList<WaterPath> solution = model.shortestPath(removedCount);
            ArrayList<WaterPath> removedPipes = model.getRemovedPipes();



            if (solution != null) {
                difficulty = (TOTAL_ANGLES + 2 * solution.size()) / 2;
             //   System.out.println(difficulty);
//                if (difficulty > 70) {
//                    System.out.println(solution.size() + " " + difficulty);
//                }

                if (difficulty < 48) {
                    continue;
                }

//                 if (Math.abs(difficulty - difficulties[levelStartNumber +(level- levelStartNumber)/3]) > eps ) {
//                    continue;
//                }
//                if (Math.abs(difficulty - difficulties[levelStartNumber + (level- 512) / 4]) > eps ) {
//                    continue;
//                }

                //Todo open after 1 levels
              //  System.out.println("Target difficulty " + difficulties[levelStartNumber + levelsGenerated / 4 - 1] + " " + levelStartNumber +" " + levelsGenerated);
//                if (Math.abs(difficulty - difficulties[levelStartNumber + levelsGenerated - 1]) > eps ) {
//                    continue;
//                }

//                if (Math.abs(difficulty - difficulties[(level - 150) / 2]) > eps ) {
//                    continue;
//                }

//                if (Math.abs(difficulty - difficultiesQuiz[2]) > eps ) {
//                    continue;
//                }

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
                System.out.println("Level " + (levelNumber + levelsGenerated) + "  " + solution.size() + " Diff = " + difficulty);
                System.out.println(model.toString());

                writeToFile("/home/suren/Documents/plumber_new_levels/extra_hard/" + (level) + ".txt", solution, removedPipes);

               // writeToFile("/home/suren/Documents/plumber_new_levels/construct_classic/" + (levelNumber + levelsGenerated) + ".txt", solution, removedPipes);
                //levelsGenerated+=step;
                //ToDo
               // levelsGenerated+= 1;
                levelsGenerated++;
            }else {
               // System.out.println("No solution");
            }

        }


    }

    private PlumberConstructModel generateLevel() {
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

        //System.out.println("Angles " + TOTAL_ANGLES);
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
        level[valve.getKey()][valve.getValue()] = 50 + rand.nextInt(4);

        //Adding end tube with no rotation
        level[end.getKey()][end.getValue()] = 60;
//

//        //Adding valve with some rotation
//        level[0][0] = 50 + rand.nextInt(4);;
//
//        //Adding end tube with some rotation
//        level[HEIGHT - 1][WIDTH - 1] = 60 + rand.nextInt(4);


      //  System.out.println(angles + " " + doubleAngles + " " + tubes + " " + doubleTubes);
        int cellsIndex = 2;
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

        //Checking game board connectivity
        if (TOTAL_EMPTY != 0) {
            ConnectionState state = checkBoardConnectivity(level);
            // System.out.println(state);
            if (state != ConnectionState.CONNECTED){
                return null;
            }
        }

        model = new PlumberConstructModel(level, HEIGHT, WIDTH);

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

    public void writeToFile(String filename, ArrayList<WaterPath> solution, ArrayList<WaterPath> removedPipes) {

        System.out.println("Writing to file...");
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            output.write(difficulty +"\n");
            output.write(HEIGHT +"\n");
            output.write(WIDTH +"\n");
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    //Searching in removed pipes
                    boolean foundRemovedPipe = false;
                    for(WaterPath wp : removedPipes) {
                        if (wp.x == i && wp.y == j) {
                            int rotation = 0;
                            output.write((level[i][j] + wp.rotation) + "\n");
                            if (level[i][j] + wp.rotation >=12 && level[i][j] + wp.rotation <=19) {
                                System.out.println("AXTUNG");
                            }
                            System.out.println("Found " + wp.rotation  + " " + wp.tube);
                            foundRemovedPipe = true;
                            break;
                        }
                    }
                    if (!foundRemovedPipe) {
                        output.write(level[i][j] + "\n");
                    }
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
            //Writing removed count
            output.write("Construct" +"\n");
            output.write(removedPipes.size() + "\n");
            //Writing removed solution
            for (int i = 0; i< removedPipes.size(); i++) {
                output.write(removedPipes.get(i).x + " " + removedPipes.get(i).y + "\n");// " " + removedPipes.get(i).tube + " " + removedPipes.get(i).rotation + "\n");
            }


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

        PlumberConstructLevelGenerator generator = new PlumberConstructLevelGenerator(365, 0, 100);

    }
}
