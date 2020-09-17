package arm;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/16/15.
 */
public class ArmCleaner {

    public static void main(String[] args) throws IOException {

        String originalNounsPath = "/home/suren/Documents/temp/arm/tot/barer.txt";
        String filteredNounsBySize = "/home/suren/Documents/temp/arm/tot/barer_unique.txt";

        ArrayList<String> words = new ArrayList<String>();

        Set<String> repeatedWords = new TreeSet<String>();
        BufferedReader dic1 = null;


        try {


            dic1 = new BufferedReader(new FileReader(originalNounsPath));

            String line = dic1.readLine();
            while (line != null) {

                line = line.trim();
                if (words.contains(line)) {
                    repeatedWords.add(line);
                } else {
                     words.add(line);
                }

                line = dic1.readLine();
            }

        } finally {
            dic1.close();

        }
        for(String s : repeatedWords) {
            System.out.println(s);
        }
        writeToFile(words, filteredNounsBySize);
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
}
