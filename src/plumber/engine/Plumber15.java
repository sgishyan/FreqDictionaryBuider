package plumber.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 4/1/20.
 */
public class Plumber15 {

    Set<State> allStates = new TreeSet<>();
    Queue<State> queue = new LinkedList<>();



    private static class State implements Comparable<State>{
        public int size;
        public int[][] board;
        public boolean[][] frozen;
        public int depth;

        public State(int[][] board, boolean[][] frozen, int size) {
            this.size = size;
            this.board = new int[size][size];
            this.frozen = new boolean[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    this.board[i][j] = board[i][j];
                    this.frozen[i][j] = frozen[i][j];

                }
            }

        }

        public State(State state, int x, int y, int dx, int dy) {
            frozen = state.frozen;
            size = state.size;
            board = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board[i][j] = state.board[i][j];
                }
            }
            depth = state.depth + 1;
            int temp = board[x][y];
            board[x][y] = board[x + dx][y + dy];
            board[x + dx][y + dy] = temp;
        }

        public State() {

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
            StringBuilder str1 = new StringBuilder();
            StringBuilder str2 = new StringBuilder();

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    str1.append(board[i][j]);
                    str2.append(o.board[i][j]);

                }
            }
            return str1.toString().compareTo(str2.toString());

        }
    }

    public Plumber15(int[][] board, boolean[][] frozen, int size) {
        State state = new State(board, frozen, size);
        perform(state);

    }

    private void perform(State state) {
        allStates.add(state);
        queue.add(state);
        getAllStates();
        System.out.println("total states : " + allStates.size() );
        //Finding deepest state
        int max = 0;
        for (State s : allStates) {
            if (s.depth > max) {
                max = s.depth;
            }
        }
        System.out.println("max depth : " + max);
    }

    public Plumber15(String filename) throws IOException {
        ArrayList<String> stringLevel = readFile(filename);
        State start = getLevel(stringLevel);
        perform((start));
    }



    private void getAllStates() {
        while (!queue.isEmpty()) {
            State state = queue.remove();

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
                        State newState = new State(state, i, j, 0, -1);
                        if (allStates.contains(newState)) {
                           // System.out.println("Same state " + state.depth);
                            continue;
                        }
                        allStates.add(newState);
                        queue.add(newState);
                        System.out.println(state.depth + " " + allStates.size());
                    }

                    //Trying right move
                    if (j < state.size - 1 && state.board[i][j + 1] == 70) {
                        State newState = new State(state, i, j, 0, 1);
                        if (allStates.contains(newState)) {
                           // System.out.println("Same state " + state.depth);
                            continue;
                        }
                        allStates.add(newState);
                        //System.out.println(state.depth + " " + allStates.size());
                        queue.add(newState);
                    }


                    //Trying down move
                    if (i < state.size - 1 && state.board[i + 1][j] == 70) {
                        State newState = new State(state, i, j, 1, 0);
                        if (allStates.contains(newState)) {
                           // System.out.println("Same state " + state.depth);

                            continue;
                        }
                        allStates.add(newState);
                        //System.out.println(state.depth + " " + allStates.size());
                        queue.add(newState);
                    }

                    //Trying right move
                    if (i > 0 && state.board[i - 1][j] == 70) {
                        State newState = new State(state, i, j, -1, 0);
                        if (allStates.contains(newState)) {
                           // System.out.println("Same state " + state.depth);

                            continue;
                        }
                        allStates.add(newState);
                        System.out.println(state.depth + " " + allStates.size());
                        queue.add(newState);
                    }
                }
            }
        }

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
        Plumber15 plumber15 = new Plumber15("/home/suren/Documents/plumber_new_levels/original/1.txt");
    }


}
