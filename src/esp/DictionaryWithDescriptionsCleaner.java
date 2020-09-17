package esp;

import rus.Word;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Removes all long words and replace "strange" letters
 * Created by suren on 12/9/15.
 */
public class DictionaryWithDescriptionsCleaner {





        public static void main(String[] args) throws IOException {


            String full = "/home/suren/Documents/temp/spanish/full_dictionary.txt";
            String cleared = "/home/suren/Documents/temp/spanish/dictionary_description_cleared_short.txt";

            ArrayList<Word> clearedArray = new ArrayList<Word>();



            //Reading part 1
            BufferedReader dic1 = new BufferedReader(new FileReader(full));
            try {

                String line = dic1.readLine();

                while (line != null) {

                    boolean haveNinyo = false;
                    String ninyoWord = "";
                    String word = line.trim();
                    String description = dic1.readLine();
                    description = description.trim();
                    int dotIndex = description.indexOf('.');
                    if(description.startsWith("[1]")) {
                        System.out.println(word);
                    }
                    if (dotIndex != -1) {
                        description = description.substring(0, description.indexOf('.') + 1);
                    }
                    if (word.contains("ñ")) {
                        haveNinyo = true;
                        ninyoWord = word;
                    }


                    String temp = Normalizer.normalize(word, Normalizer.Form.NFD);
                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                    String result = pattern.matcher(temp).replaceAll("");

                    if (haveNinyo) {
                        StringBuilder builder = new StringBuilder(result);
                        for(int i = 0; i < result.length(); i++ ) {
                            if(ninyoWord.charAt(i)=='ñ') {
                                builder.setCharAt(i, 'ñ');
                            }
                        }
                        result = builder.toString();
                    }

                    Word wholeWord = new Word(result, description, 0 );


                    if (result.length() >= 2 && result.length() < 8 && !clearedArray.contains(wholeWord))
                    {
                        clearedArray.add(wholeWord);
                    }

                    line = dic1.readLine();
                }

            } finally {
                dic1.close();
            }


            writeToFile(clearedArray, cleared );
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

