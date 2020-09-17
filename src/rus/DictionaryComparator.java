package rus;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/23/15.
 */
public class DictionaryComparator {
    public static void main(String[] args) throws IOException {


        String oldDic = "/home/suren/Downloads/russian_words.txt";
        String newDic = "/home/suren/Documents/temp/rus/wordwars/commonWords.txt";
        String foundWords = "/home/suren/Documents/temp/rus/newDictionary.txt";
        String loosedWords = "/home/suren/Documents/temp/rus/newWords2.txt";


        ArrayList<String> loosed = new ArrayList<String>();
        ArrayList<String> found = new ArrayList<String>();
        Set<String> oldWords = new TreeSet<String>();
        Set<String> newWords = new TreeSet<String>();

        Set<String> commonWords = new TreeSet<String>();


        //Reading frequency



        //Reading old dic
        //
        BufferedReader dic1 = new BufferedReader(new FileReader(oldDic));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                //System.out.println(word);
               // String description = dic1.readLine().trim();
                oldWords.add(word);
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }

        //Reading part 2
        BufferedReader dic2 = new BufferedReader(new FileReader(newDic));
        try {

            String line = dic2.readLine();

            while (line != null) {
                String word = line.trim();
                if(!word.contains("-")) {

                    word = word.replaceAll("ё", "е");

                    newWords.add(word);
                }
                line = dic2.readLine();
            }

        } finally {
            dic2.close();
        }

        for(String word : oldWords) {
            if (!newWords.contains(word)) {
                System.out.println(word);

            }
            commonWords.add(word);
        }
//
//        for(String word : newWords) {
//            if (word.length() > 3 ) {
//                commonWords.add(word);
//            }
//        }



//        for(String word : oldWords) {
//            if (word.length()> 3 && !newWords.contains(word)) {
//                if (word.endsWith("ек") || word.endsWith("ёк") || word.endsWith("ок") ||
//                        word.endsWith("ик") ||
//                        word.contains("очек") || word.contains("ёчек") ||
//                        word.contains("ечк") || word.contains("еньк") ||
//                        word.contains("очк") || word.contains("оньк") ||
//                        word.contains("очк") || word.contains("оньк")
//
//
//                ){
//                    System.out.println(word);
//                }else{
//                    commonWords.add(word);
//                }
//            }
//
//        }





        writeToFile(newWords, foundWords);
        //writeToFile(loosed, loosedWords);
    }

    private static void writeToFile(Set<String> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (String word : words) {

               // output.write(word.getDescription() + "\n");

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
