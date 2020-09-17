package guessPicutre;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by suren on 1/16/17.
 */
public class MultilanguageFilter {
    public static void main(String[] args) throws IOException {


        ArrayList<String> english;
        ArrayList<String> translator;
        ArrayList<String> google;

        ArrayList<String> acceptedWords = new ArrayList<String>();

        final String englishFile = "/home/suren/Documents/temp/pictures/multilanguage/english.txt";
        final String translatorFile = "/home/suren/Documents/temp/pictures/multilanguage/frenchTranslator.txt";
        final String googleFile = "/home/suren/Documents/temp/pictures/multilanguage/frenchGoogle.txt";
        final String acceptedFile = "/home/suren/Documents/temp/pictures/multilanguage/frenchWordList.txt";
        english = readFile(englishFile);
        translator = readFile(translatorFile);
        google = readFile(googleFile);


        for(int i= 0; i < english.size(); i++) {

            if(( translator.get(i).length() < 3 || translator.get(i).length() > 10 ) && (google.get(i).length() < 3 || google.get(i).length() > 10) ) {
                //System.out.println(english.get(i) + " : " + translator.get(i) + " | " + "INVALID LENGTH");
                continue;
            }

            if(!translator.get(i).equals(google.get(i))) {
                System.out.println(english.get(i) + " : " + translator.get(i) + " | " + google.get(i));
                acceptedWords.add("!!!ATTENTION!!!");
                acceptedWords.add(new String(english.get(i) + "\t" + translator.get(i) + "\t" + "-\t-\t-\t"));
                acceptedWords.add(new String(english.get(i) + "\t" + google.get(i) + "\t" + "-\t-\t-\t"));
                continue;
            }
            if(translator.get(i).length() < 3 || translator.get(i).length() > 10) {
               // System.out.println(english.get(i) + " : " + translator.get(i) + " | " + "INVALID LENGTH");
                continue;
            }

            if(translator.get(i).contains(" ")) {
                System.out.println(english.get(i) + " : " + translator.get(i) + " | " + "INVALID CHARACTER");
                continue;
            }
            acceptedWords.add(new String(english.get(i) + "\t" + translator.get(i) + "\t" + "-\t-\t-\t"));
            writeToFilesEng(acceptedWords, acceptedFile);
        }
    }

    private static ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                words.add(word.toLowerCase());
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static void writeToFilesEng(ArrayList<String> words, String filename) throws IOException {

        File file = new File(filename);
        BufferedWriter output = null;
        output = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < words.size(); i++) {
            output.write(words.get(i) + "\n");
        }

        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

