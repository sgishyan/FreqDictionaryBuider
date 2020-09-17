package plumber.engine;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by suren on 2/14/20.
 */
public class  WaterPipesRotateLevelFire {
    public static final String INPUT_DIRECTORY = "/home/suren/Documents/plumber_new_levels/fire_all/f_additional";
    public static final String OUTPUT_DIRECTORY = "/home/suren/Documents/plumber_new_levels/fire_all/f_additional_rotate";


    public static void main(String[] args) throws IOException {
        File root = new File(INPUT_DIRECTORY);
        for (File file : root.listFiles()) {
            ArrayList<String> levelArray = readFile(file.getAbsolutePath());
            System.out.println(file.getName());
            Level level = getLevel(levelArray);
            remake(level);
            solve(level);
            writeToFile(OUTPUT_DIRECTORY + "/" + file.getName(), level);

        }
    }

    public static void solve(Level level) {
        int[][] board = level.board;
        int heigth = level.height;
        int width = level.width;

//        PlumberPressureModel model = new PlumberPressureModel(board, heigth, width);
//        ArrayList<ArrayList<WaterPathSegment>> solutions = model.allSolutions();
//        if (solutions == null) {
//            System.out.println("________ALERT______   0 solutions");
//            return;
//        }
//        if (solutions.size() > 1) {
//            System.out.println("Solutions count = " + solutions.size());
//            for (int i = 0 ; i < solutions.size();i++) {
//               // System.out.println("--Solution number : " + (i + 1));
//                for (int j = 0; j < solutions.get(i).size();j++) {
//                    System.out.println(solutions.get(i).get(j).x + " " + solutions.get(i).get(j).y + " " + solutions.get(i).get(j).rotation);
//                }
//            }
//        }

    }

    private static void remake(Level level) {
        Random random = new Random();


        //Counting potentilally turnable pipes count
        int minMoves = 0;
        int turnables = 0;
        ArrayList<WaterPathSegment> turnablePipes = new ArrayList<>();


        //Random rotating board
        for(int i=0; i< level.height; i++) {
            for (int j= 0;j <level.width;j++) {

                int pipe = level.board[i][j];
                int availableRotations = 0;

                if (pipe <100) {
                    int pipeType = pipe/10;
                    switch (pipeType) {
                        case 1:
                            availableRotations = 2;
                            break;
                        case 2:
                            availableRotations = 4;
                            break;
                        case 3:
                            availableRotations = 2;
                            break;
                        case 4:
                            availableRotations = 2;
                            break;
                        case 5:
                            availableRotations = 0;
                            break;
                        case 6:
                            availableRotations = 0;
                            break;
                        case 8:
                            availableRotations = 0;
                            break;

                    }

                }
                else  if (pipe <= 1000) {
                    availableRotations = 0;

                }
                else if (pipe >1000) {
                    availableRotations = 4;
                }
                int newRotation = 0;
               // System.out.println(i + " " + j + " " + level.board[i][j] + "   AR = " + availableRotations);
                if(availableRotations > 0)
                switch (availableRotations) {

                    case 2:
                        newRotation = random.nextInt(2);
                        level.board[i][j] = (level.board[i][j] / 10) * 10 + newRotation;
                        break;
                    case 4:
                        newRotation = random.nextInt(4);
                        level.board[i][j] = (level.board[i][j] / 10) * 10 + newRotation;
                        break;
                }

            }
        }


        boolean [][] solutionMap = new boolean[level.height][level.width];

        //Counting solution rotations
        for (int s = 0; s < level.solution.size(); s++) {

            for (int i = 0; i < level.solution.get(s).size(); i++) {
                int x = level.solution.get(s).get(i).x;
                int y = level.solution.get(s).get(i).y;
                int rotation = level.solution.get(s).get(i).rotation;
                int pipe = level.board[x][y];
                if (solutionMap[x][y]) {
                    continue;
                }
                solutionMap[x][y] = true;


                int availableRotations = 0;
                //old pipe


                if (pipe < 100) {
                    int pipeType = pipe / 10;
                    switch (pipeType) {
                        case 1:
                            availableRotations = 2;
                            break;
                        case 2:
                            availableRotations = 4;
                            break;
                        case 3:
                            availableRotations = 2;
                            break;
                        case 4:
                            availableRotations = 2;
                            break;
                        case 5:
                            availableRotations = 0;
                            break;
                        case 6:
                            availableRotations = 0;
                            break;
                        case 8:
                            availableRotations = 0;
                            break;

                    }

                } else if (pipe <= 1000) {
                    availableRotations = 0;

                } else if (pipe > 1000) {
                    availableRotations = 4;

                }
                // System.out.println(x + " " + y + " " + level.board[x][y] + "   AR = " + availableRotations);

                if (availableRotations > 0) {
                    turnables++;
                    turnablePipes.add(level.solution.get(s).get(i));
                }
                if (availableRotations == 0) {
                    if (rotation != level.board[x][y] % 10) {
                        System.out.println("Rotation problem " + x + " " + y + " " + level.board[x][y] + "   Rotation = " + rotation);
                    }

                    level.board[x][y] = (level.board[x][y] / 10) * 10 + rotation;
                }
            }
        }
        //The percent of pipes in solution with wrong rotation
        int wrongStartRotationNumber = (int) (turnables * 0.5);
        for (int i = 0; i < wrongStartRotationNumber;i++) {
            int index = random.nextInt(turnablePipes.size());
            int x = turnablePipes.get(index).x;
            int y = turnablePipes.get(index).y;
            int rotation = turnablePipes.get(index).rotation;
            int pipe = level.board[x][y];
            int availableRotations = 0;

            //old pipe

            if (pipe < 100) {
                int pipeType = pipe / 10;
                switch (pipeType) {
                    case 1:
                        availableRotations = 2;
                        break;
                    case 2:
                        availableRotations = 4;
                        break;
                    case 3:
                        availableRotations = 2;
                        break;
                    case 4:
                        availableRotations = 2;
                        break;
                    case 5:
                        availableRotations = 0;
                        break;
                    case 6:
                        availableRotations = 0;
                        break;
                    case 8:
                        availableRotations = 0;
                        break;

                }

            } else if (pipe <= 1000) {
                availableRotations = 0;

            } else if (pipe > 1000) {
                availableRotations = 4;
            }

            switch (availableRotations) {
                case 2:
                    if (rotation == 0) {
                        level.board[x][y] = (level.board[x][y] / 10) * 10 + 1;
                    }else {
                        level.board[x][y] = (level.board[x][y] / 10) * 10;
                    }
                    minMoves++;
                    break;
                case 4:
                    ArrayList<Integer> rotationsList = new ArrayList<>();
                    rotationsList.add(0);
                    rotationsList.add(1);
                    rotationsList.add(2);
                    rotationsList.add(3);
                    rotationsList.remove(rotation);
                    int newRotation = rotationsList.get(random.nextInt(3));
                    level.board[x][y] = (level.board[x][y] / 10) * 10 + newRotation;
                    int extraMoves = newRotation <= rotation ? rotation - newRotation : rotation + 4 - newRotation;
                    minMoves += extraMoves;
                    break;
            }


            turnablePipes.remove(index);
        }


        //Setting random rotations to other 30% of tubes
        for (int i = 0; i < turnablePipes.size();i++) {

            int x = turnablePipes.get(i).x;
            int y = turnablePipes.get(i).y;
            int pipe = level.board[x][y];
            int availableRotations = 0;
            int rotation = turnablePipes.get(i).rotation;
            //old pipe

            if (pipe < 100) {
                int pipeType = pipe / 10;
                switch (pipeType) {
                    case 1:
                        availableRotations = 2;
                        break;
                    case 2:
                        availableRotations = 4;
                        break;
                    case 3:
                        availableRotations = 2;
                        break;
                    case 4:
                        availableRotations = 2;
                        break;
                    case 5:
                        availableRotations = 0;
                        break;
                    case 6:
                        availableRotations = 0;
                        break;
                    case 8:
                        availableRotations = 0;
                        break;

                }

            } else if (pipe <= 1000) {
                availableRotations = 0;

            } else if (pipe > 1000) {
                availableRotations = 4;
            }

            int newRotation = 0;
            switch (availableRotations) {

                case 2:
                    newRotation = random.nextInt(2);
                    level.board[x][y] = (level.board[x][y] / 10) * 10 + newRotation;
                    if (newRotation != rotation) {
                        minMoves++;
                    }
                    break;
                case 4:
                    newRotation = random.nextInt(4);
                    level.board[x][y] = (level.board[x][y] / 10) * 10 + newRotation;
                    int extraMoves = newRotation <= rotation ? rotation - newRotation : rotation + 4 - newRotation;
                    minMoves += extraMoves;
                    break;
            }
        }
        level.movesToSolve = minMoves;
        int difficulty = level.difficulty;
        if (difficulty < 20) {
            level.levelMoves = (int) (minMoves * 2.0);
        }else
        if (difficulty < 30) {
            level.levelMoves = (int) (minMoves * 2.0);
        }else
        if (difficulty < 40) {
            level.levelMoves = (int) (minMoves * 2.0);
        }else
        if (difficulty < 50) {
            level.levelMoves = (int) (minMoves * 1.5);
        }else
        if (difficulty < 60) {
            level.levelMoves = (int) (minMoves * 1.6);
        }else
        if (difficulty < 70) {
            level.levelMoves = (int) (minMoves * 2.2);
        }else {
            level.levelMoves = (int) (minMoves * 2.5);
        }



    }


    private static void writeToFile(String fileText, String filename) {
        System.out.println("Writing File : " + filename);
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(fileText + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    private static int calculateDifficulty(Level level) {
        int angles = 0;
        for (int i = 0 ; i < level.height; i++) {
            for (int j = 0 ; j < level.width; j++) {
                if (level.board[i][j] / 10 == 2 || level.board[i][j] / 10 == 4) {
                    angles++;
                }
            }
        }
        int solutionsLength = 0;
        for (ArrayList<WaterPathSegment> sol: level.solution) {
            solutionsLength += sol.size();
        }
        return (angles + 2 * solutionsLength) / 2;
    }

    public static Level getLevel(ArrayList<String> level) {

        StringBuilder lowerPart = new StringBuilder();
        Level levelObject = new Level();
        int line = 1;

        //System.out.println(level.get(0));
        levelObject.difficulty = Integer.parseInt(level.get(line++));
       // System.out.println("dif = " + levelObject.difficulty);
        levelObject.height = Integer.parseInt(level.get(line++));
        levelObject.width = Integer.parseInt(level.get(line++));
        levelObject.board = new int[levelObject.height][levelObject.width];
        levelObject.frozenCells = new boolean[levelObject.height][levelObject.width];
       // System.out.println("height : " + levelObject.height + "  width : " + levelObject.width);

        for (int i = 0; i < levelObject.height; i++) {
            for (int j = 0; j < levelObject.width; j++) {
                levelObject.board[i][j] = Integer.parseInt(level.get((line)));
                line++;
            }
        }
        int solutionsNumber = Integer.parseInt(level.get((line++)));
        levelObject.solution = new ArrayList<>();
        for (int s = 0; s < solutionsNumber;s++) {
            levelObject.solution.add(new ArrayList<>());

            int solutionLength = Integer.parseInt(level.get((line++)));
            for (int i = 0; i < solutionLength;i++) {
                String[] tokenStrings = level.get(line).split(" ");
                //System.out.println(Integer.parseInt(tokenStrings[0]) + " " + Integer.parseInt(tokenStrings[1]) + " " + Integer.parseInt(tokenStrings[2]) + " ");
                levelObject.solution.get(s).add(new WaterPathSegment(Integer.parseInt(tokenStrings[0]), Integer.parseInt(tokenStrings[1]), Integer.parseInt(tokenStrings[2])));
                line++;
            }
        }

        levelObject.difficulty = calculateDifficulty(levelObject);
        return levelObject;
    }

    public static void writeToFile(String filename, Level level) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(level.upperPart +"\n");
            output.write(level.difficulty +"\n");
            output.write(level.levelMoves +"\n");

            output.write(level.height +"\n");
            output.write(level.width +"\n");
            for (int i = 0; i < level.height; i++) {
                for (int j = 0; j < level.width; j++) {
                    output.write(level.board[i][j] +"\n");
                }
            }

            //Writing solution
            output.write(level.solution.size() + "\n");
            for (int s = 0; s < level.solution.size(); s++) {
                output.write(level.solution.get(s).size() + "\n");
                for (int i = 0; i< level.solution.get(s).size(); i++) {
                    output.write(level.solution.get(s).get(i).x + " " + level.solution.get(s).get(i).y + " " + level.solution.get(s).get(i).rotation + "\n");
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

    static class Level{
        public int difficulty;
        public int movesToSolve;
        public int levelMoves;
        public int height;
        public int width;
        public int [][] board;
        public boolean [][] frozenCells;
        public ArrayList<ArrayList<WaterPathSegment>> solution;
        public String upperPart = "Fire";
    }
}
