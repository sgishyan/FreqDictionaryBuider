package rus;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/29/15.
 */
public class ShortWords {
    public static void main(String[] args) throws IOException {



        String cleaned = "/home/suren/Documents/temp/rus/russian_words1.txt";

        String old = "/home/suren/Documents/temp/rus/russian_words.txt";
        Set<String> cleanedWords= new TreeSet<String>();





        //Reading old dic
        //
        BufferedReader dic1 = new BufferedReader(new FileReader(old));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();


                cleanedWords.add(word);
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }


        for (String word : cleanedWords) {
            if (word.length() < 3 ) {
                System.out.println(word);
            }
        }

        writeToFile(cleanedWords, cleaned);
    }

    private static void writeToFile(Set<String> words, String filename) {
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
