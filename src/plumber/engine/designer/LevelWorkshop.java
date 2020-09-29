package plumber.engine.designer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelWorkshop {
    public static void main(String[] args) throws IOException {
        generateFromTemplates();
    }
    public static void rotateLevels() {

    }
    public static void generateFromTemplates() throws IOException {
        String tasksPath = "C:\\Users\\sgish\\OneDrive\\Documents\\rotate\\5x5\\tasks.txt";
        System.out.println("Task Reading...");

        ArrayList<Task> tasks = readTasks(tasksPath);
        System.out.println("Read " + tasks.size() + " tasks");
        makeTasks(tasks);
    }

    public static void fileRenamer() throws IOException {
        String renameTasksPath = "C:\\Users\\sgish\\OneDrive\\Documents\\rotate\\5x5\\tasks.txt";
        System.out.println("Rename Task Reading...");

        ArrayList<Task> tasks = readRenameTasks(renameTasksPath);
        System.out.println("Read " + tasks.size() + " tasks");
        makeTasks(tasks);
    }

    private static ArrayList<Task> readRenameTasks(String renameTasksPath) throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        BufferedReader dic1 = new BufferedReader(new FileReader(renameTasksPath));
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

    public static void makeTasks(ArrayList<Task> tasks) {
        for(Task task : tasks) {
            System.out.println("Performing task " + task.templatePath);
            String templatePath = task.templatePath;
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
        char[] charLevel = new char['2'];
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
