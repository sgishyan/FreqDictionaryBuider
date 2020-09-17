package carparking;

import mobiloids.Pair;
import rus.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by suren on 4/22/19.
 */
public class LevelRefactor {
    public static void main(String[] args) throws IOException {

        int levelsCount[] = {1, 1, 2, 2, 2, 2, 2, 2, 3, 3,            // Dif 1 -10     20
                             4, 4, 4, 4, 4, 5, 5, 5, 5, 5,            // Dif 11-20     45 Tot: 65
                             6, 7, 8, 9, 10, 11, 12, 13, 14, 15,      // Dif 21-30    105 Tot: 170
                             25, 30, 35, 40, 40, 45, 45, 50, 50, 50 , // Dif 31-40    410 Tot: 580
                             60, 52, 45, 40, 20, 15, 10, 10, 5, 5,    // Dif 41-50    265 Tot:
                             2, 2, 3, 4, 2, 0, 0, 1, 0, 1             // Dif 51-60    15   Tot:
        };


        String levelsFile = "/home/suren/Downloads/rush.txt";
        String alternative = "/home/suren/Downloads/alternative.txt";
        String outputLevelsFile = "/home/suren/Downloads/levels.txt";
        String outputAlternativeFile = "/home/suren/Downloads/alternative_levels.txt";
        ArrayList<String> levels = readFile(levelsFile);
        ArrayList<Pair<Integer, Integer>> alters = readAlternativeFile(alternative);

        ArrayList<String> leveldAlternativeLevels = new ArrayList<>();
        ArrayList<String> levelStrings = new ArrayList<>();
        ArrayList<LinkedList<String>> levelsByDifficulty = new ArrayList<>();
        for (int i = 0; i < 70; i++) {
            levelsByDifficulty.add(new LinkedList<>());
        }
        int counter = 0;
        int totalLevels = 2000;
        int dif[] = new int[100];
        int interval = levels.size() / totalLevels;
        for (String level : levels) {
            C:
            for (char c = 'A'; c <= 'Z'; c++) {
                if (level.contains(c + "")) {
                    continue;
                }
                for (char r = (char)(c + 1); r <= 'Z'; r++) {
                    if (level.contains(r +"")) {
                        level = level.replaceAll(r + "", c + "");
                        continue C;
                    }
                }

            }
//            if (counter % interval == 0) {
//                System.out.println(level);
//                counter = 1;
//            }else {
//                counter++;
//            }
            int d = Integer.parseInt(level.trim().substring(0, 2));
            dif[d]++;
            levelsByDifficulty.get(d).add(level);



        }

        System.out.println("Reading ended");

        for(LinkedList<String> l : levelsByDifficulty) {
            Collections.shuffle(l);
        }

        System.out.println("Shuffling ended");


        for(int i = 1; i < 70; i++) {
            System.out.println("Dif : " + i + "   " + dif[i]);
        }


        System.out.println("Printing ended");

        for (int i = 0; i < levelsCount.length; i++) {
            for (int j = 0; j < levelsCount[i]; j++) {
                if (levelsByDifficulty.get(i + 1).size() > 0) {
                    levelStrings.add(levelsByDifficulty.get(i + 1).get(0));
                    levelsByDifficulty.get(i + 1).remove(0);
                } else {
                    System.out.println("No levels left " + i + " " + j);
                }
            }
        }

        System.out.println("Main Levels ended");
        //r.exec("cmd /c pdftk C:\\tmp\\trashhtml_to_pdf\\b.pdf C:\\tmp\\trashhtml_to_pdf\\a.pdf cat output C:\\tmp\\trashhtml_to_pdf\\d.pdf");

        for (int i = alters.size() - 1; i >= 0; i--) {
            int startLevel = Integer.parseInt(levelStrings.get(alters.get(i).getValue()).trim().substring(0, 2));;
            System.out.println("Start = " + startLevel);
            int count = alters.get(i).getKey();
            int delta = 10 / count;
            for (int j = 0; j < count; j++) {
                int target = startLevel + (j + 1) * delta;
                System.out.println("Target = " + target);
                while (target >= 60 || levelsByDifficulty.get(target).size() <= 0) {
                    target = target - 1;
                }
                if (levelsByDifficulty.get(target).size() > 0) {
                    leveldAlternativeLevels.add(levelsByDifficulty.get(target).get(0));
                    System.out.println("Real target" + target);
                    levelsByDifficulty.get(target).remove(0);
                } else {
                    System.out.println("No alternative left " + target);
                }
            }

        }
        System.out.println("Alter Levels ended");
//
//        for(int i = levelStrings.size() - 1; i >= 0; i--) {
//            System.out.println(levelStrings.get(i));
//        }

       // System.out.println("------------------------------------------------------");
//        for(int i = leveldAlternativeLevels.size() - 1; i >= 0; i--) {
//            System.out.println(leveldAlternativeLevels.get(i));
//        }

        writeToFile(levelStrings, outputLevelsFile);

        writeToFile(leveldAlternativeLevels, outputAlternativeFile);


    }

    private static ArrayList<String> readFile(String levelFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(levelFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                words.add(word);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static void writeToFile(ArrayList<String> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (String word : words) {
                output.write(word + "\n");

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
    private static ArrayList<Pair<Integer, Integer>> readAlternativeFile(String levelFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(levelFile));
        ArrayList<Pair<Integer, Integer>> alter = new ArrayList<>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                String[] tokens = word.split("\t");
                alter.add(new Pair<Integer, Integer>(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])));

                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return alter;
        }
    }
}
