import rus.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by suren on 9/29/15.
 */
public class LongWords {
    public static void main(String[] args) throws IOException {



        String huge = "/home/suren/Documents/temp/eng/huge.txt";
        String all = "/home/suren/Documents/temp/eng/all.txt";
        String eng_all = "/home/suren/Documents/temp/eng/eng_all.txt";


        ArrayList<String> cleanedWords= new ArrayList<String>();





        //Reading old dic
        //
        BufferedReader dic1 = new BufferedReader(new FileReader(all));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                if (word.length() == 8) {
                    cleanedWords.add(word);
                    System.out.println(word);
                }line = dic1.readLine();

            }

        } finally {
            dic1.close();
        }




    BufferedReader dic2 = new BufferedReader(new FileReader(huge));
    try {

        String line = dic2.readLine();
        String lemma = dic2.readLine();
        while (line != null) {

            line = line.trim();
            lemma = lemma.trim();
            if(line.length() >= 3 && line.length() <= 8 && line.matches("[a-z]+")) {

                cleanedWords.add(line);
            }
            line = dic2.readLine();
            lemma = dic2.readLine();

        }

    } finally {
        dic1.close();
        dic2.close();
    }

    writeToFile(cleanedWords, eng_all);
}

    private static void writeToFile(ArrayList<String> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (String word : words) {
                output.write(word + "\n");
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
