package guessPicutre;

import mobiloids.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by suren on 6/1/17.
 */
public class DuplicateDetector {
    public static void main(String[] args) throws IOException {

        String newLevelsFile = "/home/suren/Documents/temp/pictures/newItalianWords.txt";
        String oldLevelsFolderPath = "/home/suren/Documents/creative_soft_new_server/picturesNew/app/src/main/assets/levels/ita";

        ArrayList<String> newLevels = readFile(newLevelsFile);
        ArrayList<Pair<String, String>> oldLevels = readFolder(oldLevelsFolderPath);

        outerLoop:
        for(String level : newLevels) {
            for (int i = 0; i < oldLevels.size(); i++) {
                if (oldLevels.get(i).getKey().equals(level)) {
                    System.out.println(oldLevels.get(i).getValue());
                    continue outerLoop;
                }
            }
            System.out.println("-----------");

        }

    }

    private static ArrayList<Pair<String,String>> readFolder(String englishLevelsDir) throws IOException {
        File folder = new File(englishLevelsDir);
        ArrayList<Pair<String,String>> words = new ArrayList<Pair<String,String>>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory())
                continue;
            BufferedReader dic1 = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
            try {

                String line = dic1.readLine().trim().toLowerCase();
                String word = line.substring(0, line.length() - 4);
                String translation = dic1.readLine().trim().toLowerCase();
                words.add(new Pair<String, String>(word, translation));
                // System.out.println(line);

            } finally {
                dic1.close();
            }

        }
        System.out.println("Words in folder : " + words.size());
        return words;
    }

    private static ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                words.add(word.toLowerCase());
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }
}

