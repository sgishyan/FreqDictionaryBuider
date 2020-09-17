package ita;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Removes all long words and replace "strange" letters
 * Created by suren on 12/9/15.
 */
public class DictionaryCleaner {





        public static void main(String[] args) throws IOException {


            String full = "/home/suren/Documents/temp/italian/italian.txt";
            String cleared = "/home/suren/Documents/temp/italian/cleared_dictionary2.txt";

            ArrayList<String> clearedArray = new ArrayList<String>();
            Set<String> words = new TreeSet<String>();


            //Reading part 1
            BufferedReader dic1 = new BufferedReader(new FileReader(full));
            try {

                String line = dic1.readLine();

                while (line != null) {

                    String word = line.trim();
                    StringBuilder s = new StringBuilder(word);
//                 for (int i = 0; i < s.length(); i++) {
//                     if (s.charAt(i) > 'z' || s.charAt(i) < 'a' ) {
//                         s = s.replace(i, i, "");
//                     }
//                 }

                    String result = s.toString();


                    if (result.startsWith("abaco")) {
                        System.out.println(result);
                    }

                    //result = result.replace("œ", "oe");
                    // result = result.replace("æ", "ae");

                    if (result.length() > 2 && result.length() < 8 && !clearedArray.contains(result))
                    {

                        clearedArray.add(result);
                        words.add(result);

                    }
                    line = dic1.readLine();
                }

            } finally {
                dic1.close();
            }


            writeToFile(words, cleared );
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

