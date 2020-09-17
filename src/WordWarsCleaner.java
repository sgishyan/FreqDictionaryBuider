import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 3/31/16.
 */
public class WordWarsCleaner {

    public static void main(String[] args) throws IOException {


        String words = "/home/suren/Documents/temp/eng/wordwars/words.txt";
        String newWords =  "/home/suren/Documents/temp/eng/wordwars/eng_words.txt";


        Set<String> wordList = new TreeSet<String>();



        //Reading frequency



        //Reading part 1
        BufferedReader dic1 = new BufferedReader(new FileReader(words));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                if(word.length()<2 || word.contains(" ") || word.contains("'") || word.contains("-") || word.contains("/") || word.contains("é")) {
                    //System.out.println(word);
                    line = dic1.readLine();
                    continue;
                }
                word = word.toLowerCase();
                if(wordList.contains(word)) {
                    System.out.println(word);
                } else {
                    wordList.add(word);
                }
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }


        writeToFile(wordList, newWords);
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
