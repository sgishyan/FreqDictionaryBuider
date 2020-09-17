import java.io.*;
import java.util.ArrayList;

/**
 * Created by suren on 7/30/15.
 */
public class FindingNewWordsFromHuge {

    public static void main(String[] args) throws IOException {


        String oldDictionary = "/home/suren/Documents/temp/Testing/dictionary.txt";
        String filteredHuge = "/home/suren/Documents/temp/filtered_huge.txt";
        String newWords = "/home/suren/Documents/temp/new_words.txt";
        String strangeWords = "/home/suren/Documents/temp/strange_words.txt";
        ArrayList<String> filtered = new ArrayList<String>();
        ArrayList<String> oldList = new ArrayList<String>();



        //Reading old
        BufferedReader old = new BufferedReader(new FileReader(oldDictionary));
        try {

            String line = old.readLine();
            while (line != null) {
                String word = line.trim().toLowerCase();
                oldList.add(word);
                old.readLine();
                line = old.readLine();

            }

        } finally {
            old.close();
        }



        //Reading filtered
        BufferedReader dic1 = new BufferedReader(new FileReader(filteredHuge));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                filtered.add(word);
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }


        writeNewWords(oldList, filtered, newWords);
        writeStrangeWords(oldList, filtered, strangeWords);
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

    private static void writeNewWords(ArrayList<String> old, ArrayList<String> huge, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < huge.size(); i++) {
                String string = huge.get(i);
                if (!old.contains(string)) {
                    //output.write(i + " " + string + "\n");
                    output.write(string + "\n");

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

    private static void writeStrangeWords(ArrayList<String> old, ArrayList<String> huge, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < old.size(); i++) {
                String string = old.get(i);
                if (!huge.contains(string)) {
                    output.write(string + "\n");
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
