package Crossword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 9/29/18.
 */
public class DictionaryCompare {
    public static final String DICTIONARY_FILENAME_1 = "/home/suren/Documents/languages/French/frenchWords_cleaned.txt";
    public static final String DICTIONARY_FILENAME_2 = "/home/suren/Documents/languages/French/let4_for_start.txt";



    private Set<String> dictionaryWords1 = new TreeSet<>();
    private Set<String> dictionaryWords2 = new TreeSet<>();





            ;

    public static void main(String[] args) throws IOException {
        DictionaryCompare w = new DictionaryCompare();
    }

    public DictionaryCompare() throws IOException {

        dictionaryWords1 = readFile(DICTIONARY_FILENAME_1);
        dictionaryWords2 = readFile(DICTIONARY_FILENAME_2);


        System.out.println("Missing in dictionary 2: ");

        for (String word : dictionaryWords1) {
            if (word.length() != 4) continue;
           if (!dictionaryWords2.contains(word)) {
               System.out.println(word);
           }
        }
        System.out.println("Missing in dictionary 1: ");
        for (String word : dictionaryWords2) {
            if (word.length() != 4) continue;
            if (!dictionaryWords1.contains(word)) {

                System.out.println(word);
            }
        }

        System.out.println("-----------------------Intersection------------");

        for (String word : dictionaryWords1) {
            if (word.length() != 4) continue;
            if (dictionaryWords2.contains(word)) {
                System.out.println(word);
            }
        }
    }

    private static ArrayList<String> readBadFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();

                if (word.length() > 8 || word.contains(".") ) {
                    words.add((word.split("\t"))[0]);
                    line = dic1.readLine();
                   // System.out.println((word.split("\t"))[0]);
                    continue;
                }
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static ArrayList<String> readDictionary(String englishFile) throws IOException {
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


    private static int getDistance(String currentString, String s) {
        int sameCharacters = 0;
        boolean[] used1 = new boolean[currentString.length()];
        boolean[] used2 = new boolean[currentString.length()];
        for (int i = 0; i < currentString.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (currentString.charAt(i) == s.charAt(j) && used1[i] == false && used2[j] == false) {
                    sameCharacters++;
                    used1[i] = true;
                    used2[j] = true;
                    break;
                }
            }
        }
        return currentString.length() - sameCharacters;
    }

    public static boolean isAnagram(String a, String b) {
        Map<Character, Integer> appear = new HashMap<>();
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            char symbol = a.charAt(i);
            if (appear.containsKey(symbol)) {
                appear.put(symbol, appear.get(symbol) + 1);
            }else {
                appear.put(symbol, 1);
            }
        }

        for (int i = 0; i < b.length(); i++) {
            char symbol = b.charAt(i);
            if (appear.containsKey(symbol)) {
                appear.put(symbol, appear.get(symbol) - 1);
            }else {
                return false;
            }
        }

        for (Character str : appear.keySet()) {
            if (appear.get(str) != 0) {
                return false;
            }
        }
        return true;

    }

    private  Set<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        Set<String> words = new TreeSet<>();

        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim().toLowerCase();
               if (word.length() < 3 || word.length() > 8 || word.contains("/") || word.contains("-") || words.contains(word)) {
                    line = dic1.readLine().toLowerCase();
                    continue;
                }
                else {
                    words.add(word.toLowerCase());
                }

                line = dic1.readLine();
            }
            System.out.println("Total words " + words.size());
//            for(String w : t3) {
//                ArrayList<String > wordsFrom3 = checkLetters(w.toCharArray());
//                System.out.println(w  + " " + wordsFrom3.size());
//                if (wordsFrom3.size() > 1) {
//
//                    for (String word : wordsFrom3) {
//                        System.out.println(word);
//                        dictionary.remove(word);
//                    }
//                }
//
//            }
        } finally {
            dic1.close();
            return words;
        }
    }



}



