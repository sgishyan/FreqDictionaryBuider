package plumber.engine.designer;

import mobiloids.Pair;
import plumber.engine.ConnectionState;
import plumber.engine.Direction;
import plumber.engine.PlumberModelOld;
import plumber.engine.WaterPathSegment;
import plumber.engine.models.PlumberModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 6/12/17.
 */
public class PlumberLevelGenerator {

    private Random rand;
    private PlumberModel model;
    int HEIGHT = 4;
    int WIDTH = 4;
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
    public PlumberLevelGenerator(int count, int minComplexity, int maxComplexity) {

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
        int [] indexes4 = {
                132, 133, 149, 151, 156, 158, 163, 167, 170, 173, 177, 179, 181, 187, 189, 191, 194, 196, 200, 201, 204, 205, 206, 208, 209, 221, 226, 236, 238, 246, 247, 249, 250, 251};


        int [] indexes3 = {
                6, 8, 9, 17, 18, 20, 21, 22, 25, 27, 29, 30, 31, 33, 38, 47, 59, 61, 66, 71, 74, 77, 90, 102, 105, 109, 112, 118, 119 };
        int eps = 5;

        rand = new Random();
        level = new int [HEIGHT][WIDTH];
        int levelsGenerated = 0;
        int step = 1;
        count = 512;
        int levelStartNumber = 513;

        int difStartNumber = 320;
//        while(levelsGenerated < count) {

        while(levelsGenerated < indexes3.length) {
            int level = indexes3[levelsGenerated];

            //Todo open after 1 level
           // TOTAL_ANGLES = anglesCountPerPackage[(levelStartNumber + levelsGenerated) /16] + rand.nextInt(anglesCountPerPackageRandComponent[(levelStartNumber + levelsGenerated) /16]) ;
           TOTAL_ANGLES = 4 + rand.nextInt(4);
          //  TOTAL_ANGLES = 25 + rand.nextInt(25);
            //System.out.println(TOTAL_ANGLES);
            //TOTAL_ANGLES = 25 + rand.nextInt(25);


            TOTAL_TUBES = TOTAL - TOTAL_ANGLES - 2;
            PlumberModel model = generateLevel();
            //checkConnectivity(model);
            if (model == null) {
                continue;
            }
            ArrayList<WaterPathSegment> solution = model.shortestPath();


            if (solution != null) {
                difficulty = (TOTAL_ANGLES + 2 * solution.size()) / 2;
               //System.out.println(difficulty);

               // System.out.println("Level " + (levelStartNumber + levelsGenerated) + "  " + solution.size() + " Diff = " + difficulty);
                System.out.println(level + "  " + model.toString());
               // writeToFile("/home/suren/Documents/plumber_new_levels/classic_february_2020/" + (levelStartNumber + levelsGenerated) + ".txt", solution);
                //writeToFile("/home/suren/Documents/plumber_new_levels/extra_hard/" + (level) + ".txt", solution);

                levelsGenerated++;
            }else {
               // System.out.println("No solution");
            }

        }


    }

    private PlumberModel generateLevel() {
        int TOTAL_TUBES = HEIGHT * WIDTH;
        ArrayList<Pair<Integer,Integer>> cells = new ArrayList<>(48);

        //Filling all cells
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {

                cells.add(new Pair<>(i, j));
            }

        Collections.shuffle(cells);


//        while(!checkConnectivity(cells)) {
//            Collections.shuffle(cells);
//        }
      //  System.out.println("connected...");
        int emptyCount = rand.nextInt(HEIGHT) + 2;
        int frozenCount = rand.nextInt(HEIGHT) + 2;
        int frozenEmptyCount = rand.nextInt(HEIGHT ) ;


        //Randomising tubes counts
        TOTAL_TUBES = HEIGHT * WIDTH - 2 - emptyCount;




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
        level[end.getKey()][end.getValue()] = 60 + + rand.nextInt(4);;
//

//        //Adding valve with some rotation
//        level[0][0] = 50 + rand.nextInt(4);;
//
//        //Adding end tube with some rotation
//        level[HEIGHT - 1][WIDTH - 1] = 60 + rand.nextInt(4);

        //Placing empty tubes
        int cellsIndex = 2;
        for (int i = 0; i < emptyCount; i++) {
            Pair<Integer, Integer> empty = cells.get(cellsIndex++);
            //Placing with maximal rotation for better backtracking performance
            level[empty.getKey()][empty.getValue()] = 70;
        }
      //  System.out.println(angles + " " + doubleAngles + " " + tubes + " " + doubleTubes);

        //Placing angle tubes
        for (int i = 0; i < TOTAL_TUBES; i++) {
            Pair<Integer, Integer> angle = cells.get(cellsIndex++);
            int element = rand.nextInt(30);
            if (element < 13) {
                level[angle.getKey()][angle.getValue()] = 10;
            } else if (element < 26) {
                level[angle.getKey()][angle.getValue()] = 20;
            } else if (element < 28) {
                level[angle.getKey()][angle.getValue()] = 30;
            } else {
                level[angle.getKey()][angle.getValue()] = 40;
            }
        }

            for (int i = 2; i < cells.size(); i++) {
            Pair<Integer, Integer> tube = cells.get(i);

            //Checking corner cells. Placing angle tubes in corners
            if (isCornerCell(tube)) {
                level[tube.getKey()][tube.getValue()] = 20;

            } else {
                //Placing with maximal rotation for better backtracking performance
                level[tube.getKey()][tube.getValue()] = 10;
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

        model = new PlumberModel(level, HEIGHT, WIDTH);

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

        PlumberLevelGenerator generator = new PlumberLevelGenerator(365, 0, 100);

    }
}
