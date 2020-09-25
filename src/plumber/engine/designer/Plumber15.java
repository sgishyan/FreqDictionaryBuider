package plumber.engine.designer;

import carparking.Move;
import mobiloids.Pair;
import plumber.engine.Tubes;
import plumber.engine.WaterPathSegment;
import plumber.engine.models.PlumberModel15;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.*;

/**
 * Created by suren on 4/1/20.
 */
public class Plumber15 {



    Set<String> allStates = new TreeSet<>();
    Queue<State> queue = new LinkedList<>();
    State startState;
    Pair<Integer, Integer> valve;

    public Plumber15() {


//        int[][] b = {
//                {70, 70, 21, 22},
//                {51, 11, 30, 23},
//                {70, 70, 10, 70},
//                {70, 70, 60, 70}};
//
//        boolean[][] f = {
//                {false, false, false, false},
//                {true, false, true, true},
//                {false, false, true, false},
//                {false, false, true, false}};

        int count = 100;
        String base = "C:\\Users\\Suren\\Documents\\Plumber15\\templates\\classic\\generated\\4_4\\ordinary_new\\";
        mainLoop:
        while(count > 0) {
            //System.out.println("New state");
            //startState = new State(b, f, 4);
            startState = new State(4);
            boolean [][] inSolution = new boolean[4][4];

            PlumberModel15 model = new PlumberModel15(startState.board, startState.size, startState.size);
        //    System.out.println("Model created");
            int[][] solutionBoard = model.shortestPath();
            if (solutionBoard == null) {
                //System.out.println("No solution");
                continue;
            }
            ArrayList<WaterPathSegment> singleSolution = model.getSingleSolution();
            if (singleSolution.size() < 5) {
                System.out.println("Too short");
                continue ;
            }
           
            int [][] multiEnterElements = new int [startState.size][startState.size];

            startState.board = solutionBoard;
            boolean hasDoubleTube = false;
         //   System.out.println("Path found");
            for (WaterPathSegment segment : singleSolution) {
                inSolution[segment.x][segment.y] = true;
                if (segment.tube == Tubes.DOUBLE_TUBE) {
                    hasDoubleTube = true;
                    multiEnterElements[segment.x][segment.y] ^= 1;
                }
                if (segment.tube == Tubes.DOUBLE_ANGLE) {
                    hasDoubleTube = true;
                    multiEnterElements[segment.x][segment.y] ^= 2;
                }
            }

            //Forcing Double Angle/Cross
//            if (!hasDoubleTube) {
//                System.out.println("No double pipe");
//                continue mainLoop;
//            }
            for (int i = 0; i < startState.size; i++)
                for (int j = 0; j < startState.size; j++) {
                    if (startState.board[i][j] != 70 && !inSolution[i][j]){
                        if (startState.frozen[i][j]) {
                           // System.out.println("Make 90");
                            startState.board[i][j] = 90;
                        }else {
                           // System.out.println("Make 70");
                            startState.board[i][j] = 70;
                        }
                    }
                }
            for (int i = 0; i < startState.size; i++)
                for (int j = 0; j < startState.size; j++) {
                    if (multiEnterElements[i][j] != 0) {
                        System.out.println("Single usage of double tube");
                        continue mainLoop;
                    }
                }
            State potentialState = analizeState(startState);
            //System.out.println("Model analized");

            if (potentialState != null) {
               // System.out.println("Potential state found");
                //printState(startState);
                Plumber15 plumber15 = new Plumber15(startState.board, startState.frozen, startState.rotatable, startState.size);
                int depth = plumber15.fullInfo();
                System.out.println("Depth = " + depth);
                if (depth > 25) {
                    System.out.println("----------------Solution Found " + depth);

                    potentialState.printLevel();

                    writeToFile(base + (100 - count ) + "_" + depth + "cg.txt", potentialState);
                    count--;
                }
            }
        }
    }


     static  class State implements Comparable<State> {

        public int size;
        public int[][] board;
        public static boolean[][] frozen;
        public static boolean[][] rotatable;
        public ArrayList<Move15> moves;
        public int depth;
        public int hashCode = 0;
        public String trace;

         public static final int[] elements_unique_rotations = {2, 4, 1, 2, 4, 4, 1, 1, 1};
         public static final int[] elements_unique_rotations2 = {4, 4, 4, 4, 4, 4, 4};
         public static final int[] elements_unique_rotations3 = {4, 4, 4, 1, 1, 2, 4};

        public void rotateClockwise(int count) {
            int [][] boardNew = new int[size][size];

            boolean [][] frozenNew = new boolean[size][size];
            boolean [][] rotatableNew = new boolean[size][size];
            ArrayList<Move15> movesNew = new ArrayList<>();


            for(int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    frozenNew[j] [size - i - 1] = frozen[i][j];
                    rotatableNew[j] [size - i - 1] = rotatable[i][j];
                    int element = board[i][j] / 10;
                    int rotation = board[i][j] % 10;
                    if (element < 10) {
                        rotation = (rotation + 1) % elements_unique_rotations[(element - 1)];
                    }else {
                        if (element == 10){
                            rotation = (rotation + 1) % elements_unique_rotations2[(0)];
                        }
                        if (element/ 100 == 11) {
                            rotation = (rotation + 1) % elements_unique_rotations2[(1)];
                            //   System.out.println(rotation);
                        }
                        if (element == 51 || element == 52 || element == 53) {
                            rotation = (rotation + 1) % elements_unique_rotations2[(4)];
                            // System.out.println(rotation);
                        }
                        if (element == 61 || element == 62 || element == 63) {
                            rotation = (rotation + 1) % elements_unique_rotations3[(0)];
                            //  System.out.println(rotation);
                        }
                    }
                    boardNew[j][size - i - 1] = (10 * element + rotation);
                }
            }

            for (int i = 0; i < moves.size(); i++) {
                Move15 move = moves.get(i);
                int newDx = 0;
                int newDy = 0;

                if (move.dx == 0 && move.dy == 1 ) {
                    newDx = 1;
                    newDy = 0;
                }
                if (move.dx == 1 && move.dy == 0 ) {
                    newDx = 0;
                    newDy = -1;
                }
                if (move.dx == 0 && move.dy == -1 ) {
                    newDx = -1;
                    newDy = 0;
                }
                if (move.dx ==-1 && move.dy == 0 ) {
                    newDx = 0;
                    newDy = 1;
                }


                Move15 newMove = new Move15(move.y, size - move.x - 1, newDx, newDy );
            }
            board = boardNew;
            rotatable = rotatableNew;
            frozen = frozenNew;
            moves = movesNew;

        }

        public State(int[][] board, boolean[][] f, boolean[][] r, int size) {
            this.size = size;
            this.depth = 0;
            frozen = f;
            rotatable = r;
            moves = new ArrayList<>();
            this.board = new int[size][size];
            StringBuilder tr = new StringBuilder();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    this.board[i][j] = board[i][j];
                   // hashCode += (i * 31 + j* 13) * board[i][j];
                    tr.append(board[i][j]);
                }
            }
            trace = tr.toString();
        }

        //Rotate Constructor
         public State(State state, int x, int y, boolean needMoves) {
             size = state.size;

             //     board = state.board;
             board = new int[size][size];
             for (int i = 0; i < size; i++) {
                 for (int j = 0; j < size; j++) {
                     board[i][j] = state.board[i][j];
                     // hashCode += (i * 31 + j* 13) * board[i][j];
                 }
             }

             //If we need solution moves
             if (needMoves) {
                 moves = new ArrayList<>();
                 for (int i = 0; i < state.moves.size(); i++) {
                     moves.add(state.moves.get(i));
                 }
                 moves.add(new Move15(x , y , 0, 0));
             }


             depth = state.depth + 1;
             int type = board[x][y] / 10;
             int rotation = board[x][y] % 10;
             int newValue = 0;
             rotation--;
             if (type == 1) {
                 if (rotation < 0)
                     rotation = 1;
             }
             if (type == 2) {
                 if (rotation < 0)
                     rotation = 3;
             }
             newValue = 10 * type + rotation;

             board[x][y] = newValue;
             StringBuilder tr = new StringBuilder();
             for (int i = 0; i < size; i++) {
                 for (int j = 0; j < size; j++) {
                     tr.append(board[i][j]);
                 }
             }
             trace = tr.toString();
         }

         public void printLevel() {
            System.out.println("--------------------------------------");
            System.out.println(size);
            System.out.println(size);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.println(board[i][j]);
                }
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.println(frozen[i][j]);
                }
            }


        }


        public State(int size) {
            this.size = size;
            this.depth = 0;
            moves = new ArrayList<>();
            this.board = new int[size][size];
            frozen = new boolean[size][size];
            rotatable = new boolean[size][size];
            //Building cells array
            ArrayList<Pair<Integer, Integer>> cells = new ArrayList<>();
            int cellIndex = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells.add(new Pair<>(i, j));
                }
            }
            Collections.shuffle(cells);
            Random random = new Random();

            //Getting random values for empty and frozen cells(beside valve and container)
            int emptyCount = random.nextInt(size) ;
            int frozenCount = random.nextInt(2 * size - 1) ;
            int frozenEmptyCount = random.nextInt((int)     (size + 2)) ;;
//            int emptyCount = random.nextInt((int) (1.5 * size)) + 2 ;
//            int frozenCount = random.nextInt((int) (1.5 * size ))  ;
//            int frozenEmptyCount = random.nextInt((int) (1.5 * size)) + 2 ;;

            //Setting valve
            Pair<Integer, Integer> valve = cells.get(cellIndex++);
            int x = valve.first;
            int y = valve.second;
            if (x == 0 && y == 0) {
                board[valve.first][valve.second] = 50 + random.nextInt(2) + 1;
            }else
            if (x == size - 1 && y == 0) {
                board[valve.first][valve.second] = 50 + random.nextInt(2) ;
            }else
            if (x == 0 && y == size - 1) {
                board[valve.first][valve.second] = 50 + random.nextInt(2) + 2;
            }else
            if (x == size - 1 && y == size - 1) {
                board[valve.first][valve.second] = 50 + (random.nextInt(2) + 3) % 4;
            }else
            if (x == 0) {
                board[valve.first][valve.second] = 50 + random.nextInt(3) + 1;
            }else
            if (x == size - 1) {
                board[valve.first][valve.second] = 50 + (random.nextInt(3) + 3) % 4;
            }else
            if (y == 0) {
                board[valve.first][valve.second] = 50 + random.nextInt(3) ;
            } else
            if (y == size - 1) {
                board[valve.first][valve.second] = 50 + (random.nextInt(3) + 2) % 4;
            } else {
                board[valve.first][valve.second] = 50 + random.nextInt(4);
            }
            frozen[valve.first][valve.second] = true;

            //Setting container
            Pair<Integer, Integer> container = cells.get(cellIndex++);
            x = container.first;
            y = container.second;
            if (x == 0 && y == 0) {
                board[container.first][container.second] = 60 + random.nextInt(2) + 1;
            }else
            if (x == size - 1 && y == 0) {
                board[container.first][container.second] = 60 + random.nextInt(2) ;
            }else
            if (x == 0 && y == size - 1) {
                board[container.first][container.second] = 60 + random.nextInt(2) + 2;
            }else
            if (x == size - 1 && y == size - 1) {
                board[container.first][container.second] = 60 + (random.nextInt(2) + 3) % 4;
            }else
            if (x == 0) {
                board[container.first][container.second] = 60 + random.nextInt(3) + 1;
            }else
            if (x == size - 1) {
                board[container.first][container.second] = 60 + (random.nextInt(3) + 3) % 4;
            }else
            if (y == 0) {
                board[container.first][container.second] = 60 + random.nextInt(3) ;
            } else
            if (y == size - 1) {
                board[container.first][container.second] = 60 + (random.nextInt(3) + 2) % 4;
            } else {
                board[container.first][container.second] = 60 + random.nextInt(4);
            }
            frozen[container.first][container.second] = true;


            //Setting empty
            for (int i = 0; i < emptyCount; i++) {
                Pair<Integer, Integer> cell = cells.get(cellIndex++);
                board[cell.first][cell.second] = 70;
            }

            //Setting frozen empty
            for (int i = 0; i < frozenEmptyCount; i++) {
                Pair<Integer, Integer> cell = cells.get(cellIndex++);
                board[cell.first][cell.second] = 90;
                frozen[cell.first][cell.second] = true;
            }

            //Setting pipes empty
            int frozenSetCount = 0;
            int remainingCells = size * size - 2 - emptyCount - frozenEmptyCount;
         //   System.out.println("Empty = " + emptyCount);
          //  System.out.println("Frozen = " + frozenEmptyCount);
          //  System.out.println("Remaining = " + remainingCells);
         //   System.out.println("CellIndex = " + cellIndex);



//            //Double Cross and Double Angle
//            Pair<Integer, Integer> cellCross = cells.get(cellIndex++);
//            int crossOrAngle = random.nextInt(2);
//            if (crossOrAngle == 0) {
//                board[cellCross.first][cellCross.second] = 30;
//            }else {
//                int rotation = random.nextInt(2);
//                board[cellCross.first][cellCross.second] = 40 + rotation;
//            }


            //Don't forget to +/-  1 in remainingCells
            for (int i = 0; i < remainingCells ; i++) {
                Pair<Integer, Integer> cell = cells.get(cellIndex++);
                //Choosing pipe
                int pipeTypeIndex = random.nextInt(10);

                if (pipeTypeIndex < 5) {
//                    if (pipeTypeIndex < 1) {
//                        board[cell.first][cell.second] = 30;
//                    }else {
//                        board[cell.first][cell.second] = 10 + random.nextInt(2);
//                    }
                    board[cell.first][cell.second] = 10 + random.nextInt(2);

                }else {
//                    if (pipeTypeIndex > 8) {
//                        board[cell.first][cell.second] = 40 + random.nextInt(2);
//
//                    }else {
//                        board[cell.first][cell.second] = 20 + random.nextInt(4);
//                    }
                    board[cell.first][cell.second] = 20 + random.nextInt(4);

                }
                if (frozenSetCount < frozenCount) {
                    frozen[cell.first][cell.second] = true;
                    frozenSetCount++;
                }

            }

            StringBuilder tr = new StringBuilder();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    tr.append(board[i][j]);
                }
            }
            trace = tr.toString();



        }

        private static class Move15 {
            public int dx;
            public int dy;
            int x;
            int y;

            public Move15(int x, int y, int dx, int dy) {
                this.x = x;
                this.y = y;
                this.dx = dx;
                this.dy = dy;
            }
        }



        public State(State state, int x, int y, int dx, int dy, boolean needMoves) {

            size = state.size;

            //     board = state.board;
            board = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board[i][j] = state.board[i][j];
                    // hashCode += (i * 31 + j* 13) * board[i][j];
                }
            }

            //If we need solution moves
            if (needMoves) {
                moves = new ArrayList<>();
                for (int i = 0; i < state.moves.size(); i++) {
                    moves.add(state.moves.get(i));
                }
               moves.add(new Move15(x + dx, y + dy, -dx, -dy));
            }


            depth = state.depth + 1;
            int temp = board[x][y];
            board[x][y] = board[x + dx][y + dy];
            board[x + dx][y + dy] = temp;
            StringBuilder tr = new StringBuilder();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    tr.append(board[i][j]);
                }
            }
            trace = tr.toString();
        }


        public State() {

        }

        @Override
        public int hashCode() {

            return  hashCode;
        }

        @Override
       public boolean equals(Object a) {
           State state = (State)a;
           if (size != state.size) {
               return false;
           }
           for (int i = 0; i < size; i++) {
               for (int j = 0; j < size; j++) {
                   if (board[i][j] != state.board[i][j]) {
                       return false;
                   }
               }
           }
           return true;
       }

        @Override
        public int compareTo(State o) {
            //StringBuilder str1 = new StringBuilder();
            //StringBuilder str2 = new StringBuilder();

//            for (int i = 0; i < size; i++) {
//                for (int j = 0; j < size; j++) {
//                    if(board[i][j]<o.board[i][j]) {
//                        return -1;
//                    }
//                    if (board[i][j] > o.board[i][j]) {
//                        return 1;
//                    }
//                   // str1.append(board[i][j]);
//                   // str2.append(o.board[i][j]);
//
//                }
//            }
            //return str1.toString().compareTo(str2.toString());
            return trace.compareTo(o.trace);

        }

        public int solve() {
            PlumberModel15 model = new PlumberModel15(board, size, size);
            return model.makeValvePath();
//            for (int i = 0; i < 100; i++) {
//
//            }
           // System.out.println("Solve");

        }
    }

    public Plumber15(State state) {
        startState = state;
        for (int i = 0; i< state.size;i++) {
            for (int j = 0; j < state.size; j++) {
                int v = state.board[i][j];
                if ( (v >= 50 && v < 60) || (v >=500 && v < 600)) {
                    valve = new Pair<>(i, j);
                }

            }
        }

    }

    public Plumber15(int[][] board, boolean[][] frozen, boolean[][] rotatable, int size) {
        startState = new State(board, frozen, rotatable, size);
        for (int i = 0; i< size;i++) {
            for (int j = 0; j < size; j++) {
                int v = board[i][j];
                if ( (v >= 50 && v < 60) || (v >=500 && v < 600)) {
                    valve = new Pair<>(i, j);
                }

            }
        }

        //fullInfo(state);
      //getStateAtDepth(state, 10);

    }

    public void getStateAtDepth(int depth) {
        allStates.clear();
        allStates.add(startState.trace);
        queue.clear();
        queue.add(startState);
        getAllStates(depth, null);
    }

    public void printStateAtDepth(int depth, String path,boolean needRotation) {
        allStates.clear();
        allStates.add(startState.trace);
        queue.clear();
        if (needRotation) {
            startState.rotateClockwise(1);
        }
        queue.add(startState);
        getAllStates(depth, path);
    }

    public int fullInfo() {
        allStates.clear();
        queue.clear();
        allStates.add(startState.trace);
        queue.add(startState);
        getAllStates(-1, null);
        //System.out.println("total states : " + allStates.size() );
        //Finding deepest state
        int max = 0;
        State maxState = null;
//        for (State s : allStates) {
//            if (s.depth > max) {
//                max = s.depth;
//                maxState = s;
//            }
//        }
//        if (maxState != null)
//        for (State.Move15 move : maxState.moves) {
//            System.out.println(move.x + " " + move.y + " " + move.dx + " " + move.dy);
//        }
//        System.out.println("max depth : " + max);

        return  max;
    }

    public Plumber15(String filename) throws IOException {
        ArrayList<String> stringLevel = readFile(filename);
        startState = getLevel(stringLevel);
        fullInfo();
    }


    private State getStateAtDepth(State state, int depth) {
        if (depth == 0) {
            return state;
        }
        ArrayList<State> candidates = new ArrayList<>();
        Random random = new Random();


        for (int i = 0; i < state.size; i++) {
            for (int j = 0; j < state.size; j++) {

                if (state.frozen[i][j]) {
                    continue;
                }
                if (state.board[i][j] == 70) {
                    continue;
                }

                //Trying left move
                if (j > 0 && state.board[i][j - 1] == 70) {
                    State newState = new State(state, i, j, 0, -1, true);
                    if (!allStates.contains(newState.trace)) {
                        candidates.add(newState);

                    }
                }

                //Trying right move
                if (j < state.size - 1 && state.board[i][j + 1] == 70) {
                    State newState = new State(state, i, j, 0, 1, true);
                    if (!allStates.contains(newState.trace)) {
                        candidates.add(newState);
                    }
                }

                //Trying down move
                if (i < state.size - 1 && state.board[i + 1][j] == 70) {
                    State newState = new State(state, i, j, 1, 0, true);
                    if (!allStates.contains(newState.trace)) {
                        candidates.add(newState);
                    }
                }

                //Trying right move
                if (i > 0 && state.board[i - 1][j] == 70) {
                    State newState = new State(state, i, j, -1, 0, true);
                    if (!allStates.contains(newState.trace)) {
                        candidates.add(newState);
                    }
                }
            }
        }

        int randIndex = random.nextInt(candidates.size());
        State nextState = candidates.get(randIndex);
        allStates.add(nextState.trace);
        return getStateAtDepth(nextState, depth - 1);
    }

    private void getAllStates(int depth, String outputPath) {

        State state = null;
        boolean needMoves = depth != -1;
        while (!queue.isEmpty()) {

            state = queue.remove();
            //System.out.println(state.depth + " " + queue.size());

            // System.out.println("----State---- ");
            //printState(state);
            int solutionLength = state.solve();
           // System.out.println("SL = " + solutionLength);
            if (solutionLength > 0 ) {
                System.out.println("New Solution : Length = " + solutionLength + " Depth = " + state.depth);
            }

            if (needMoves && state.depth == depth) {
                ArrayList<State> candidates = new ArrayList<>();
                candidates.add(state);
                while (!queue.isEmpty()) {
                    State s = queue.remove();
                    if (s.depth == depth) {
                        candidates.add(s);

                    } else {
                        break;
                    }
                }
                System.out.println("Total candidates " + candidates.size());
//                for (State s : candidates) {
//                    printSolution(s);
//                }
                Random random = new Random();
                int index = random.nextInt(candidates.size());
                State solution = candidates.get(index);
                if (outputPath == null) {
                    printSolution(solution);
                }else {
                    writeSolution(outputPath, solution);
                }
                return;
            }
//
//            for (int i = 0; i < state.moves.size(); i++) {
//                int dx = state.moves.get(i).dx;
//                int dy = state.moves.get(i).dy;
//                String dir = "";
//                if (dx == 0 && dy == 1) {
//                    dir = "right";
//                }
//                if (dx == 0 && dy == -1) {
//                    dir = "left";
//                }
//                if (dx == -1 && dy == 0) {
//                    dir = "up";
//                }
//                if (dx == 1 && dy == 0) {
//                    dir = "down";
//                }
//
//                System.out.println(state.moves.get(i).x + " " + state.moves.get(i).y + " " + dir);
//
//            }



            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {

                    if (state.rotatable[i][j] ) {
                        State newState;
                        if(needMoves) {
                            newState = new State(state, i, j, true);
                        }else {
                            newState = new State(state, i, j, false);
                        }
                        if (allStates.add(newState.trace)) {
                            queue.add(newState);
                        }
                    }

                    if (state.frozen[i][j]) {
                        continue;
                    }
                    if (state.board[i][j] == 70) {
                        continue;
                    }

                    //Trying left move
                  //  System.out.println("Cell " + i + " " + j);
                   // System.out.println("trying Left");

                    if (j > 0 && state.board[i][j - 1] == 70) {
                        State newState;
                        if(needMoves) {
                            newState = new State(state, i, j, 0, -1, true);
                        }else {
                            newState = new State(state, i, j, 0, -1, false);

                        }
                        if (allStates.add(newState.trace)) {
                            queue.add(newState);
                        }
//                        if (!allStates.contains(newState)) {
//                            allStates.add(newState);
//                            queue.add(newState);
//                         //   System.out.println("Good " + state.depth + " " + allStates.size());
//                        }else {
//                            //System.out.println("old State");
//                        }

                    }

                    //Trying right move
                   // System.out.println("trying right");
                    if (j < state.size - 1 && state.board[i][j + 1] == 70) {

                        State newState;
                        if(needMoves) {
                            newState = new State(state, i, j, 0, 1, true);
                        }else {
                            newState = new State(state, i, j, 0, 1, false);
                        }

                        if (allStates.add(newState.trace)) {
                            queue.add(newState);
                        }
//
//
//                        if (!allStates.contains(newState)) {
//                            allStates.add(newState);
//                            //System.out.println("Good " + state.depth + " " + allStates.size());
//                            queue.add(newState);
//                        }else {
//                            //System.out.println("Old State");
//                        }
                    }



                    //Trying down move
                    //System.out.println("trying down");
                    if (i < state.size - 1 && state.board[i + 1][j] == 70) {
                        State newState;
                        if(needMoves) {
                            newState = new State(state, i, j, 1, 0, true);
                        }else {
                            newState = new State(state, i, j, 1, 0, false);
                        }


                        if (allStates.add(newState.trace)) {
                            queue.add(newState);
                        }
//                        if (!allStates.contains(newState)) {
//                            allStates.add(newState);
//                          //  System.out.println("Good " + state.depth + " " + allStates.size());
//                            queue.add(newState);
//                        }else {t.println("Old state");
////
//                           // System.ou}

                    }

                    //Trying right move
                    //System.out.println("trying up");
                    if (i > 0 && state.board[i - 1][j] == 70) {


                        State newState;
                        if(needMoves) {
                            newState = new State(state, i, j, -1, 0, true);
                        }else {
                            newState = new State(state, i, j, -1, 0, false);
                        }
                        if (allStates.add(newState.trace)) {
                            queue.add(newState);
                        }
//                        if (!allStates.contains(newState)) {
//                            allStates.add(newState);
//                           // System.out.println("Good " +state.depth + " " + allStates.size());
//                            queue.add(newState);
//                        }else {
//                           // System.out.println("Old State");
//                        }


                       // System.out.println("Not empty or out of board");
                    }
                }
            }
        }
        System.out.println(state.depth + " " + queue.size());

    }


    private State analizeState(State s) {
        queue.clear();
        queue.add(s);
        allStates.clear();
        allStates.add(s.trace);
        State solution = null;
        int solutionsCount = 0;
        int length = 0;
        State state = null;
        while (!queue.isEmpty()) {
           // System.out.println(queue.size());
            state = queue.remove();
            // System.out.println("----State---- ");
            //printState(state);
            int solutionLength = state.solve();
            if (solutionLength > 0) {
                solutionsCount++;
              //  System.out.println("New Solution : Length = " + solutionLength + " Depth = " + state.depth);
               // state.printLevel();
//                for (State.Move15 m : state.moves) {
//                    System.out.println("Move: " + m.x + " " + m.y + " " + m.dx + " " + m.dy);
//                }
                if (solutionLength < 5) {
                    System.out.println("Too short solution " + solutionLength);
                    return null;
                }
                if (solutionsCount > 1) {
                    System.out.println("Multiple Solutions");
                    return null;
                }
                solution = new State(state.board, state.frozen, state.rotatable, state.size);
                length = solutionLength;
            }


            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {
                    if (state.frozen[i][j]) {
                        continue;
                    }
                    if (state.board[i][j] == 70) {
                        continue;
                    }

                    //Trying left move
                    //  System.out.println("Cell " + i + " " + j);
                    // System.out.println("trying Left");

                    if (j > 0 && state.board[i][j - 1] == 70) {
                        State newState = new State(state, i, j, 0, -1, false);
                        if (!allStates. contains(newState.trace)) {
                            allStates.add(newState.trace);
                            queue.add(newState);
                            //   System.out.println("Good " + state.depth + " " + allStates.size());
                        }else {
                            //   System.out.println("old State");
                        }

                    }else {
                        //System.out.println("Not empty or out of board");
                    }

                    //Trying right move
                    // System.out.println("trying right");
                    if (j < state.size - 1 && state.board[i][j + 1] == 70) {
                        State newState = new State(state, i, j, 0, 1, false);
                        if (!allStates.contains(newState.trace)) {
                            allStates.add(newState.trace);
                            //System.out.println("Good " + state.depth + " " + allStates.size());
                            queue.add(newState);
                        }else {
                            //System.out.println("Old State");
                        }

                    }else {
                        //System.out.println("Not empty or out of board");
                    }


                    //Trying down move
                    //System.out.println("trying down");
                    if (i < state.size - 1 && state.board[i + 1][j] == 70) {
                        State newState = new State(state, i, j, 1, 0, false);
                        if (!allStates.contains(newState.trace)) {
                            allStates.add(newState.trace);
                            //  System.out.println("Good " + state.depth + " " + allStates.size());
                            queue.add(newState);
                        }else {
                            // System.out.println("Old state");
                        }

                    }else {
                        //System.out.println("Not empty or out of board");
                    }

                    //Trying right move
                    //System.out.println("trying up");
                    if (i > 0 && state.board[i - 1][j] == 70) {
                        State newState = new State(state, i, j, -1, 0, false);
                        if (!allStates.contains(newState.trace)) {
                            allStates.add(newState.trace);
                            // System.out.println("Good " +state.depth + " " + allStates.size());
                            queue.add(newState);
                        }else {
                            // System.out.println("Old State");
                        }

                    }else {
                        // System.out.println("Not empty or out of board");
                    }
                }
            }
        }
        if (solutionsCount == 0) {
           // System.out.println("No solution");
            return null;
        }
       // state.printLevel();
        return solution;
    }

    private int analizeStateMaxDepth(State s) {
        queue.clear();
        queue.add(s);
        State solution ;
        int solutionsCount = 0;
        int length = 0;
        State state = null;
        while (!queue.isEmpty()) {
            state = queue.remove();
            // System.out.println("----State---- ");
            //printState(state);
            int solutionLength = state.solve();
            if (solutionLength > 0) {
                solutionsCount++;
                // System.out.println("New Solution : Length = " + solutionLength + " Depth = " + state.depth);
                if (solutionLength < 8) {
                    //System.out.println("Too short solution " + solutionLength);
                    return -3;
                }
                if (solutionsCount > 1) {
                  //  System.out.println("Multiple Solutions");
                    return -2;
                }
                solution = new State(state.board, state.frozen, state.rotatable, state.size);
                length = solutionLength;
            }


            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {
                    if (state.frozen[i][j]) {
                        continue;
                    }
                    if (state.board[i][j] == 70) {
                        continue;
                    }

                    //Trying left move
                    //  System.out.println("Cell " + i + " " + j);
                    // System.out.println("trying Left");

                    if (j > 0 && state.board[i][j - 1] == 70) {
                        State newState = new State(state, i, j, 0, -1, false);
                        if (!allStates.contains(newState)) {
                            allStates.add(newState.trace);
                            queue.add(newState);
                            //   System.out.println("Good " + state.depth + " " + allStates.size());
                        }else {
                            //   System.out.println("old State");
                        }

                    }else {
                        //System.out.println("Not empty or out of board");
                    }

                    //Trying right move
                    // System.out.println("trying right");
                    if (j < state.size - 1 && state.board[i][j + 1] == 70) {
                        State newState = new State(state, i, j, 0, 1, false);
                        if (!allStates.contains(newState)) {
                            allStates.add(newState.trace);
                            //System.out.println("Good " + state.depth + " " + allStates.size());
                            queue.add(newState);
                        }else {
                            //System.out.println("Old State");
                        }

                    }else {
                        //System.out.println("Not empty or out of board");
                    }


                    //Trying down move
                    //System.out.println("trying down");
                    if (i < state.size - 1 && state.board[i + 1][j] == 70) {
                        State newState = new State(state, i, j, 1, 0, false);
                        if (!allStates.contains(newState)) {
                            allStates.add(newState.trace);
                            //  System.out.println("Good " + state.depth + " " + allStates.size());
                            queue.add(newState);
                        }else {
                            // System.out.println("Old state");
                        }

                    }else {
                        //System.out.println("Not empty or out of board");
                    }

                    //Trying right move
                    //System.out.println("trying up");
                    if (i > 0 && state.board[i - 1][j] == 70) {
                        State newState = new State(state, i, j, -1, 0, false);
                        if (!allStates.contains(newState)) {
                            allStates.add(newState.trace);
                            // System.out.println("Good " +state.depth + " " + allStates.size());
                            queue.add(newState);
                        }else {
                            // System.out.println("Old State");
                        }

                    }else {
                        // System.out.println("Not empty or out of board");
                    }
                }
            }
        }
        if (solutionsCount == 0) {
            // System.out.println("No solution");
            return -1;
        }
        state.printLevel();
        return length;
    }

    public void printSolution(State state) {
        System.out.println("------------------------------------------");
        System.out.println(state.moves.size());
        System.out.println(state.size);
        System.out.println(state.size);
        int frozenCount = 0;
        int rotatableCount = 0;
        for (int i = 0; i < state.size; i++) {
            for (int j = 0; j < state.size; j++) {
                if (state.frozen[i][j]) {
                    frozenCount++;
                }
                if (state.rotatable[i][j]) {
                    rotatableCount++;
                }
                System.out.println(state.board[i][j]);
            }
        }
        System.out.println(frozenCount);
        for (int i = 0; i < state.size; i++) {
            for (int j = 0; j < state.size; j++) {
                if (state.frozen[i][j]) {
                    System.out.println(i + " " + j);
                }
            }
        }
        System.out.println(rotatableCount);
        for (int i = 0; i < state.size; i++) {
            for (int j = 0; j < state.size; j++) {
                if (state.rotatable[i][j]) {
                    System.out.println(i + " " + j);
                }
            }
        }


        System.out.println(state.moves.size()); {
            for (int i = state.moves.size() - 1; i >=0; i--) {
                System.out.println(state.moves.get(i).x + " " +
                        state.moves.get(i).y + " " +
                        state.moves.get(i).dx + " " +
                        state.moves.get(i).dy);
            }
        }



    }

    private void printState(State state) {
        System.out.println("----------------State--------------------------");

        for (int i = 0; i < state.size; i++) {
            for (int j = 0; j < state.size; j++) {
                System.out.print(state.board[i][j] + " ");


            }
            System.out.println();
        }
        System.out.println();


    }

    private static ArrayList<String> readFile(String englishFile) throws IOException {
        ArrayList<String> level = new ArrayList<>();
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                level.add(word);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return level;
        }
    }

    public static State getLevel(ArrayList<String> level) {

        StringBuilder lowerPart = new StringBuilder();
        State levelObject = new State();
        int line = 0;

        //System.out.println(level.get(0));
        Integer.parseInt(level.get(line++));
        // System.out.println("dif = " + levelObject.difficulty);
        levelObject.size = Integer.parseInt(level.get(line++));
        Integer.parseInt(level.get(line++));
        levelObject.board = new int[levelObject.size][levelObject.size];
        levelObject.frozen = new boolean[levelObject.size][levelObject.size];

        // System.out.println("height : " + levelObject.height + "  width : " + levelObject.width);

        for (int i = 0; i < levelObject.size; i++) {
            for (int j = 0; j < levelObject.size; j++) {
                levelObject.board[i][j] = Integer.parseInt(level.get((line)));
                line++;
            }
        }

        int solutionLength = Integer.parseInt(level.get((line++)));
        for (int i = 0; i < solutionLength; i++) {
            String[] tokenStrings = level.get(line).split(" ");
            //System.out.println(Integer.parseInt(tokenStrings[0]) + " " + Integer.parseInt(tokenStrings[1]) + " " + Integer.parseInt(tokenStrings[2]) + " ");
            // levelObject.solution.get(s).add(new WaterPathSegment(Integer.parseInt(tokenStrings[0]), Integer.parseInt(tokenStrings[1]), Integer.parseInt(tokenStrings[2])));
            line++;
        }

        int frozenCells = Integer.parseInt(level.get((line++)));
        for(int i = 0; i < frozenCells; i++) {
            String[] tokenStrings = level.get(line).split(" ");
            levelObject.frozen[Integer.parseInt(tokenStrings[0])][Integer.parseInt(tokenStrings[1])] = true;
            System.out.println(Integer.parseInt(tokenStrings[0]) + " " + Integer.parseInt(tokenStrings[1]) + " " +levelObject.frozen[Integer.parseInt(tokenStrings[0])][Integer.parseInt(tokenStrings[1])]);
            line++;
        }

        //levelObject.difficulty = calculateDifficulty(levelObject);
        levelObject.depth = 0;
        return levelObject;
    }

    public static void main(String[] args) throws IOException {
      //  Plumber15 plumber15 = new Plumber15("/home/suren/Documents/plumber_new_levels/original/2.txt");
        Plumber15 gen = new Plumber15();

    }

    public void writeSolution(String filename, State state) {

        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write((state.moves.size() + "\n"));
            output.write(state.size +"\n");
            output.write(state.size +"\n");
            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {
                    output.write(state.board[i][j] +"\n");
                }
            }
            int frozenCount = 0;
            int rotatableCount = 0;
            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {
                    if (state.frozen[i][j]) {
                        frozenCount++;
                    }
                    if (state.rotatable[i][j]) {
                        rotatableCount++;
                    }
                }
            }
            output.write(frozenCount+ "\n");
            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {
                    if (state.frozen[i][j]) {
                        output.write(i + " " + j + "\n");
                    }
                }
            }
            output.write(rotatableCount+ "\n");
            for (int i = 0; i < state.size; i++) {
                for (int j = 0; j < state.size; j++) {
                    if (state.rotatable[i][j]) {
                        output.write(i + " " + j + "\n");
                    }
                }
            }


            output.write(state.moves.size()+ "\n");
                for (int i = state.moves.size() - 1; i >=0; i--) {
                    output.write(state.moves.get(i).x + " " +
                            state.moves.get(i).y + " " +
                            state.moves.get(i).dx + " " +
                            state.moves.get(i).dy + "\n");
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



    public void writeToFile(String filename, State solution) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(solution.size +"\n");
            output.write(solution.size +"\n");
            for (int i = 0; i < solution.size; i++) {
                for (int j = 0; j < solution.size; j++) {
                    output.write(solution.board[i][j] +"\n");
                }
            }
            for (int i = 0; i < solution.size; i++) {
                for (int j = 0; j < solution.size; j++) {
                    output.write(solution.frozen[i][j] +"\n");
                }
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




}
