import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by suren on 8/7/15.
 */
public class FrequencyCleaner {


    public static void main(String[] args) throws IOException {


        String frequencyDirty = "/home/suren/Documents/temp/dictionaries/new/frequencyRare3.txt";
        String frequencyClean = "/home/suren/Documents/temp/dictionaries/new/rare_dic.txt";



        HashMap<String, Integer> frequency = new HashMap<String, Integer>(460000);
        ArrayList<Word> dictionary = new ArrayList<Word>();


        //Reading frequency



        //Reading part 1
        BufferedReader dic1 = new BufferedReader(new FileReader(frequencyDirty));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                String[] lemmas = word.split(" ");
                word = lemmas[0];
                String description = dic1.readLine();
                System.out.println(word + "  :" + description);
                Integer freq = frequency.get(word);
                int f = 0;
                if (freq != null) {
                    f = freq;
                }
                dictionary.add(new Word(word, description, 0));
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }


        writeToFile(dictionary, frequencyClean);
    }

    private static void writeToFile(ArrayList<Word> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (Word word : words) {
                output.write(word.getWord() + "\n");
                output.write(word.getDescription() + "\n");
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
