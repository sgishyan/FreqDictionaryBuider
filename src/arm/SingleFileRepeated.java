package arm;

import mobiloids.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/16/15.
 */
public class SingleFileRepeated {

    public static void main(String[] args) throws IOException {

        String path = "/home/suren/Documents/temp/arm/sport/words.txt";


        ArrayList<String> words = readFile(path);


        Set<String> selfRepeating = new TreeSet<String>();

        for(String str : words ) {
            if (selfRepeating.add(str) != true) {
                System.out.println( str);
            }
        }
        selfRepeating.clear();


    }

    private static void addToList(ArrayList<Pair<String, String>> yesno, String filename, String category) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(filename));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                yesno.add(new Pair<String, String>(word.toLowerCase(), category));
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return;
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

    private static ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        int counter = 0;

        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.toLowerCase().trim();
                if (word.contains("---------") || word.startsWith("Card")) {
                    line = dic1.readLine();
                    continue;
                }
                words.add(word.toLowerCase());
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }
}
