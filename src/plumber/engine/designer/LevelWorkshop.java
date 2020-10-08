package plumber.engine.designer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelWorkshop {
    public static void main(String[] args) throws IOException {
        generateFromTemplates();
    }
    public static void rotateLevels() {

    }
    public static void generateFromTemplates() throws IOException {
//        String tasksPath = "C:\\Users\\sgish\\OneDrive\\Documents\\rotate\\5x5\\tasks.txt";
//        System.out.println("Task Reading...");
//
//        ArrayList<Task> tasks = readTasks(tasksPath);
//        System.out.println("Read " + tasks.size() + " tasks");
//        makeTasks(tasks);
        String input = "C:\\Users\\Suren\\Downloads\\Levels\\easy";
        String output = "C:\\Users\\Suren\\Documents\\Plumber15\\levels_october_8\\easy";
        addRotatablePartInLevels(input, output);


    }


    public static void makeTasks(ArrayList<Task> tasks) {
        for(Task task : tasks) {
            System.out.println("Performing task " + task.templatePath);

            String templatePath = task.templatePath;


            //Rename task
            if (templatePath.equals("-1")) {
                String oldLevelName = task.levelOutputEasy;
                String newLevelName = task.levelOutputHard;

            }


            String outputEasyPath = task.levelOutputEasy;
            String outputHardPath = task.levelOutputHard;
            int easyDif = task.difficultyEasy;
            int hardDif = task.getDifficultyHard;

            Plumber15.State stateEasy = getStateFromTemplate(templatePath);
            Plumber15 plumber15 = new Plumber15(stateEasy);
            plumber15.printStateAtDepth(easyDif, outputEasyPath, task.needRotation);

            Plumber15.State stateHard= getStateFromTemplate(templatePath);
            Plumber15 plumber15Hard = new Plumber15(stateHard);
            plumber15Hard.printStateAtDepth(hardDif, outputHardPath,task.needRotation);

        }
    }

    public static Plumber15.State getStateFromTemplate(String templatePath) {

        ArrayList <String> level = new ArrayList<>();

            try {
                java.io.FileReader fw = new java.io.FileReader(templatePath);
                String line;
                try {
                    BufferedReader bufferreader = new BufferedReader(fw);
                    while ((line = bufferreader.readLine()) != null) {
                        level.add(line);
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fw.close();


            } catch (IOException iox) {
                iox.printStackTrace();
            }
        int height = Integer.parseInt(level.get(0));
        int width = Integer.parseInt(level.get(1));
        int [][] board = new int[height][width];
        boolean [][] isFrozen = new boolean[height][width];
        boolean [][] isRotatable = new boolean[height][width];
        System.out.println("height : " + height + "  width : " + width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[j][i] = Integer.parseInt(level.get((i * width + j + 2)));
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                isFrozen[j][i] = Boolean.parseBoolean( level.get((height* width + i * width + j + 2)));
            }
        }
        if (level.size() >=  3 * height* width ) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    isRotatable[j][i] = Boolean.parseBoolean(level.get((2 * height * width + i * width + j + 2)));
                }
            }
        }

        Plumber15.State state = new Plumber15.State(board,isFrozen, isRotatable, height);
        return state;
    }

    public static Plumber15.State getStateFromLevel(String levelPath) {

        ArrayList <String> level = new ArrayList<>();

        try {
            java.io.FileReader fw = new java.io.FileReader(levelPath);
            String line;
            try {
                BufferedReader bufferreader = new BufferedReader(fw);
                while ((line = bufferreader.readLine()) != null) {
                    level.add(line);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            fw.close();


        } catch (IOException iox) {
            iox.printStackTrace();
        }
        int index = 0;
        int difficulty = Integer.parseInt(level.get(index++));
        ArrayList<Plumber15.State.Move15> moves = new ArrayList<>();

        int height = Integer.parseInt(level.get(index++));
        int width = Integer.parseInt(level.get(index++));
        int [][] board = new int[height][width];
        boolean [][] isFrozen = new boolean[height][width];
        boolean [][] isRotatable = new boolean[height][width];
        int movesCount = 0;
        System.out.println("height : " + height + "  width : " + width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[j][i] = Integer.parseInt(level.get((index++)));

            }
        }

        int frozenCount = Integer.parseInt(level.get((index++)));
        for (int i = 0; i < frozenCount; i++) {
                String frozenString = level.get(index++);
                String[] frozenTokens = frozenString.split(" ");
                isFrozen[Integer.parseInt(frozenTokens[1])][Integer.parseInt(frozenTokens[0])] = true;
        }

        int rotatableCount = Integer.parseInt(level.get((index++)));
        System.out.println("Rotatable " + rotatableCount);
        System.out.println("Index = " + (level.size() - index));
        if(level.size() - index == rotatableCount) {
            movesCount = rotatableCount;
            rotatableCount = 0;
        }else {
            for (int i = 0; i < rotatableCount; i++) {
                String rotateString = level.get(index++);
                String[] rotateTokens = rotateString.split(" ");
                isRotatable[Integer.parseInt(rotateTokens[1])][Integer.parseInt(rotateTokens[0])] = true;
            }
        }

        if (rotatableCount != 0) {
            movesCount = Integer.parseInt(level.get((index++)));
        }

        for (int i = 0; i < movesCount; i++) {
            String move = level.get(index++);
            String[] moveTokens = move.split(" ");
            int x = Integer.parseInt(moveTokens[0]);
            int y = Integer.parseInt(moveTokens[1]);
            int dx = Integer.parseInt(moveTokens[2]);
            int dy = Integer.parseInt(moveTokens[3]);
            moves.add(new Plumber15.State.Move15(x, y, dx, dy));

        }

        Plumber15.State state = new Plumber15.State(board,isFrozen, isRotatable, moves,height);
        return state;
    }

    public static void addRotatablePartInLevels(String input, String output) {
        File inputFolder = new File(input);
        for (File level : inputFolder.listFiles()) {
            String fileShortName = level.getName();
            if (fileShortName.endsWith("meta")) {
                continue;
            }
            Plumber15.State state = getStateFromLevel(level.getAbsolutePath());
            writeState(state, output + "/" + fileShortName);
        }
    }

    public static void writeState(Plumber15.State state, String path) {
        File file = new File(path);
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
    public static ArrayList<Task> readTasks(String path) throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        BufferedReader dic1 = new BufferedReader(new FileReader(path));
        try {

            String line = dic1.readLine();
            while (line != null) {

                line = line.trim();
                String[] tokens = line.split(" ");

                String template = tokens[0];
                String outputEasy = tokens[1];
                String outputHard = tokens[2];

                int easy = Integer.parseInt(tokens[3]);
                int hard = Integer.parseInt((tokens[4]));



                Task task = new Task(template, outputEasy, outputHard, easy, hard, true);
                tasks.add(task);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return tasks;
        }
    }
    static class Task {
        String templatePath;
        String levelOutputEasy;
        String levelOutputHard;

        int difficultyEasy;
        int getDifficultyHard;
        boolean needRotation;

        public Task(String templatePath, String levelOutputEasy, String levelOutputHard, int difficultyEasy, int getDifficultyHard, boolean needRotation) {
            this.templatePath = templatePath;
            this.levelOutputEasy = levelOutputEasy;
            this.levelOutputHard = levelOutputHard;
            this.difficultyEasy = difficultyEasy;
            this.getDifficultyHard = getDifficultyHard;
            this.needRotation = needRotation;
        }
    }


}
