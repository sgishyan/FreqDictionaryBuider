package dictionary;

import mobiloids.Pair;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by suren on 10/26/18.
 */
public class RussianDictionaryCreator {


    public static void main(String[] args) throws IOException {
        String pathAll = "/home/suren/Documents/languages/Russian/russian_words.txt";
        String pathEasy = "/home/suren/Documents/creative_soft_new_server/spiderswordsgenerator/rusRes.txt";
        String pathHard = "/home/suren/Documents/languages/Russian/russian_all_cleared.txt";
        String pathWM = "/home/suren/Documents/languages/Russian/russian_wm_cleared.txt";
        String pathChalk = "/home/suren/Documents/languages/Russian/hard_final_chalk.txt";


        ArrayList<String> allWords = readFile(pathAll);
        ArrayList<String> easyWords = readFile(pathEasy);
        ArrayList<String> hardWords = readFile(pathHard);
        ArrayList<String> wmWords = readFile(pathWM);
        ArrayList<String> chalkWords = readFile(pathChalk);
        ArrayList<String> result = new ArrayList<>();


//
        for (String word : wmWords) {
//            if (!allWords.contains(easy)) {
//                System.out.println("All words missing :" + easy);
//            }
//
//            if (!hardWords.contains(easy)) {
//                System.out.println("Hard words missing :" + easy);
//            }

            if ( word.length() == 5  && !chalkWords.contains(word)) {
                System.out.println(word);
            }

        }


//
//        for (String hard : hardWords) {
////            if (!allWords.contains(easy)) {
////                System.out.println("All words missing :" + easy);
////            }
////
////            if (!hardWords.contains(easy)) {
////                System.out.println("Hard words missing :" + easy);
////            }
//
//            if ( hard.length() < 8 &&  !allWords.contains(hard)) {
//                System.out.println("ALL words  missing :" + hard);
//            }
//        }


    }

    private static ArrayList<String> readFile(String path1) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(path1));
        ArrayList<String> words = new ArrayList<>();
        try {

            String lineRus= dic1.readLine().trim();
            while (lineRus != null) {

                words.add(lineRus);

                lineRus = dic1.readLine();

            }
        } finally {
            dic1.close();
            return words;
        }
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
