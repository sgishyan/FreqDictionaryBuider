import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 7/30/15.
 */
public class HugeDictionaryFiltration {
    public static void main(String[] args) throws IOException {


        String originalHugePath = "/home/suren/Documents/temp/freq_huge.txt";
        String originalHugeLemmaPath = "/home/suren/Documents/temp/freq_huge_lemma.txt";
        String filteredHuge = "/home/suren/Documents/temp/filtered_huge.txt";

        Set<Word> dictionary = new TreeSet<Word>();


        //Reading frequency



        //Reading part 1
        BufferedReader dic1 = new BufferedReader(new FileReader(originalHugePath));
        BufferedReader dic2 = new BufferedReader(new FileReader(originalHugeLemmaPath));
        try {

            String line = dic1.readLine();
            String lemma = dic2.readLine();
            while (line != null) {

                line = line.trim();
                lemma = lemma.trim();
                System.out.println(line + " " + lemma);
                if(line.length() >= 3 && line.length() <= 7 && line.matches("[a-z]+")) {

                    dictionary.add(new Word(line, lemma, 0));
                }
                line = dic1.readLine();
                lemma = dic2.readLine();

            }

        } finally {
            dic1.close();
            dic2.close();
        }

        writeToFile(dictionary, filteredHuge);
    }

    private static void writeToFile(Set<Word> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (Word word : words) {
                output.write(word.getWord() + "\n");
                //output.write(word.getDescription() + "\n");
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
