package rus;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/24/15.
 */
public class SmallerRemovaer {
    public static void main(String[] args) throws IOException {


        String oldDic = "/home/suren/Documents/temp/rus/old_russian.txt";
        String newDic = "/home/suren/Documents/temp/rus/russian_output.txt";
        String foundWords = "/home/suren/Documents/temp/rus/old_russian.txt";
        String cleaned = "/home/suren/Documents/temp/rus/cleanedWords.txt";


        ArrayList<Word> cleanedWords= new ArrayList<Word>();
        ArrayList<Word> found = new ArrayList<Word>();
        Set<Word> oldWords = new TreeSet<Word>();
        Set<Word> newWords = new TreeSet<Word>();


        ArrayList<Word> dictionary = new ArrayList<Word>();


        //Reading frequency



        //Reading old dic
        //
        BufferedReader dic1 = new BufferedReader(new FileReader(foundWords));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();

                String description = dic1.readLine().trim();
                oldWords.add(new Word(word, description, 0));
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }



        for (Word word : oldWords) {
            if (!word.getDescription().contains("уменьш.")) {

                cleanedWords.add(word);
            }
            else
            {
                System.out.println(word.getWord());
            }

        }



        writeToFile(cleanedWords, cleaned);
        //writeToFile(loosed, loosedWords);
    }

    private static void writeToFile(ArrayList<Word> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (Word word : words) {

                output.write(word.getWord() + "\n");
                String d = word.getDescription();
                int index  = d.indexOf("◆");
                if (index > 0) {
                    output.write(d.substring(0, index - 1) + "\n");
                } else {
                    output.write(word.getDescription() + "\n");
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
