package esp;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Removes all long words and replace "strange" letters
 * Created by suren on 12/9/15.
 */
public class DictionaryCleaner {





        public static void main(String[] args) throws IOException {


            String full = "/home/suren/Documents/temp/spanish/full_dictionary.txt";
            String cleared = "/home/suren/Documents/temp/spanish/cleared_dictionary.txt";

            ArrayList<String> clearedArray = new ArrayList<String>();



            //Reading part 1
            BufferedReader dic1 = new BufferedReader(new FileReader(full));
            try {

                String line = dic1.readLine();

                while (line != null) {

                    String word = line.trim();
                    String temp = Normalizer.normalize(word, Normalizer.Form.NFD);
                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                    String result = pattern.matcher(temp).replaceAll("");

                    if (result.length() >= 2 && result.length() < 8 && !clearedArray.contains(result))
                    {
                        clearedArray.add(result);
                    }
                    line = dic1.readLine();
                }

            } finally {
                dic1.close();
            }


            writeToFile(clearedArray, cleared );
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

