package Crossword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 9/29/18.
 */
public class Word7_list {
    public static final String DICTIONARY_FILENAME_RUS_EASY = "/home/suren/Documents/languages/Russian/rusRes.txt";
    public static final String DICTIONARY_FILENAME_RUS_HARD = "/home/suren/Documents/languages/Russian/hard_final.txt";
    public static final String DICTIONARY_FILENAME_RUS_WM = "/home/suren/Documents/languages/Russian/russian_wm_cleared.txt";
    public static final String DICTIONARY_FILENAME_SPA = "/home/suren/Documents/languages/Spanish/spanishDictionary_38.txt";
    private static final int SHUFFLE_ITERATIONS = 100;
    private static final int SEED_SHUFFLES = 4;
    private ArrayList<String> dictionaryEasy;
    private ArrayList<String> dictionaryHard;
    private ArrayList<String> dictionaryWM;

    private ArrayList<String> dictionary;
    private ArrayList<String> seedDictionary;
    private ArrayList<String> availableWords;
    private Queue<String> usedWords;
    private ArrayList<Crossword> crosswords = new ArrayList<>();
    private ArrayList<Crossword> temp = new ArrayList<>();
    private ArrayList<Crossword> sequence = new ArrayList<>();
    private ArrayList<Crossword> bestSequence = new ArrayList<>();
    private ArrayList<Crossword> globalBestSequence = new ArrayList<>();

    public static final int SEED_LENGTH = 7;
    public static final int MIN_COUNT = 7;
    public static final int COUNT = 1000;
    public static final int QUEUE_SIZE = 280;

    public static void main(String[] args) throws IOException {
        Word7_list w = new Word7_list();

    }

    public Word7_list() throws IOException {

        dictionaryEasy = readFile(DICTIONARY_FILENAME_RUS_EASY);
        dictionaryHard = readFile(DICTIONARY_FILENAME_RUS_HARD);
        dictionaryWM = readFile(DICTIONARY_FILENAME_RUS_WM);

//        for (String a : dictionaryEasy) {
//            if (a.length() == 7) {
//                System.out.println(a);
//            }
//        }

//        for (String a : dictionaryHard) {
//            if (a.length() == 6 && !dictionaryEasy.contains(a)) {
//                System.out.println(a);
//            }
//        }

        for (String a : dictionaryWM) {
            if (!dictionaryHard.contains(a)) {
                System.out.println(a);
            }
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


    private ArrayList<String> checkLetters(char[] letters) {

        ArrayList<String> currentWords = new ArrayList<String>();

        int counter = 0;
        for (String word : dictionary) {
            boolean[] usedLetters = {false, false, false, false, false, false, false,false};
            counter = 0;
            for (int i = 0; i < word.length(); i++)
                innerLoop:
                        for (int j = 0; j < letters.length; j++) {
                            if (word.charAt(i) == letters[j] && usedLetters[j] == false) {
                                usedLetters[j] = true;
                                counter++;
                                break innerLoop;
                            }
                        }
            if (counter == word.length()) {
                currentWords.add(word);
            }
        }

        return currentWords;
    }
}



