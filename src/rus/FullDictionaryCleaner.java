package rus;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/16/15.
 */
public class FullDictionaryCleaner {

    public static void main(String[] args) throws IOException {

        String originalNounsPath = "/home/suren/Documents/temp/rus/nouns.txt";
        String filteredNounsBySize = "/home/suren/Documents/temp/rus/filtered_nouns_by_size.txt";

        ArrayList<String> words = new ArrayList<String>();


        BufferedReader dic1 = null;


        try {


            dic1 = new BufferedReader(new FileReader(originalNounsPath));

            String line = dic1.readLine();
            while (line != null) {

                line = line.trim();
                if(line.length() >= 2 && line.length() <= 7 && line.matches("[а-я]+")) {

                    words.add(line);
                }
                line = dic1.readLine();
            }

        } finally {
            dic1.close();

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
                if(word.endsWith("ек") || word.endsWith("ёк") || word.endsWith("ок") ||
                   word.endsWith("ик") ||
                   word.contains("очек") ||  word.contains("ёчек") ||
                   word.contains("ечк") ||  word.contains("еньк")   ||
                   word.contains("очк") ||  word.contains("оньк")   ||
                   word.contains("очк") ||  word.contains("оньк")


                        ) {
                    System.out.println(word);
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
