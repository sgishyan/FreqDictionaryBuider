package Crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 9/29/18.
 */
public class GeorgianBadWordsUsage {
    public static final String DICTIONARY_FILENAME_RUS_EASY = "/home/suren/Documents/languages/Russian/rusRes.txt";
    public static final String DICTIONARY_FILENAME_RUS_HARD = "/home/suren/Documents/languages/Russian/hard_final.txt";
    public static final String DICTIONARY_FILENAME_RUS_WM = "/home/suren/Documents/languages/Russian/russian_wm_cleared.txt";
    public static final String DICTIONARY_FILENAME_SPA = "/home/suren/Documents/languages/Spanish/spanishDictionary_38.txt";
    public static final String DICTIONARY_FILENAME_RUS_7medium = "/home/suren/Documents/languages/Russian/medium7.txt";
    public static final String DICTIONARY_FILENAME_GEO = "/home/suren/Documents/languages/Georgian/georgianAll.txt";

    private static final int SHUFFLE_ITERATIONS = 1;
    private static final int SEED_SHUFFLES = 1;
    private ArrayList<String> dictionary;
    private ArrayList<String> seedDictionary;
    private ArrayList<String> dictionaryWords = new ArrayList<>();
    private ArrayList<String> badWords;
    private LinkedList<String> usedSeeds;
    private Queue<String> usedWords;
    private ArrayList<Crossword> crosswords = new ArrayList<>();
    private ArrayList<Crossword> temp = new ArrayList<>();
    private ArrayList<Crossword> sequence = new ArrayList<>();
    private ArrayList<Crossword> bestSequence = new ArrayList<>();
    private ArrayList<Crossword> globalBestSequence = new ArrayList<>();

    public static final int SEED_LENGTH = 5;
    public static final int MIN_COUNT = 4;
    public static final int COUNT = 60;
    public static final int QUEUE_SIZE = 250



            ;

    public static void main(String[] args) throws IOException {
        GeorgianBadWordsUsage w = new GeorgianBadWordsUsage();
    }

    public GeorgianBadWordsUsage() throws IOException {

        dictionaryWords = readFile("/home/suren/Documents/languages/English/engAllRes.txt");
//        usedWords = usedSeedWords("/home/suren/Documents/temp/crossword/levels_geo/new");
//        badWords = readBadFile("/home/suren/Documents/languages/Georgian/georgianFiver2.txt");
        Set<String> seed = new TreeSet<>();
        for (String string : dictionaryWords) {
            //System.out.println(string);
            if (seed.contains(string)) {
                System.out.println("Alarm ... " + string);
            }
            seed.add(string);
        }
        System.out.println(seed.size());

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

    private  ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<String> t3 = new ArrayList<>();
        int three = 0;
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                if (word.length() < 3) {
                   // System.out.println("Too short : " + word);
                    line = dic1.readLine();
                    continue;
                }
                if (words.contains(word)) {
                   // System.out.println("Duplicate : " + word);
                }else {
                    words.add(word.toLowerCase());
                }
                if (word.length() == 3) {
                    System.out.println(word);
                    t3.add(word);
                    three++;
                }
                line = dic1.readLine();
            }
            System.out.println("Total 3 letters " + three);
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

    private static LinkedList<String> readLevelWords(String level) throws IOException {
        // System.out.println(level);
        BufferedReader dic1 = new BufferedReader(new FileReader(level));
        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            int boardSize = Integer.parseInt(line);

            //Reading crossword
            for(int i = 0; i < boardSize; i++) {
                line = dic1.readLine();
            }

            //Reading seed word
            line = dic1.readLine();

            //Reading level words count
            line = dic1.readLine();
            int levelWordsCount = Integer.parseInt(line);

            for(int i = 0; i < levelWordsCount; i++) {

                line = dic1.readLine();
                words.add(line);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private  LinkedList<String> usedWords(String root) {
        LinkedList<String> used = new LinkedList<>();
        File rootFolder = new File (root);
        for (File inner : rootFolder.listFiles()) {
            if (!inner.isDirectory()) {
                try {
                    if (!inner.getName().contains("~")) {
                        used.addAll(readLevelWords(inner.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                used.addAll(usedWords(inner.getAbsolutePath()));
            }
        }
        return used;
    }

    private static String readLevelSeedWords(String level) throws IOException {
        // System.out.println(level);
        BufferedReader dic1 = new BufferedReader(new FileReader(level));
        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            int boardSize = Integer.parseInt(line);

            //Reading crossword
            for(int i = 0; i < boardSize; i++) {
                line = dic1.readLine();
            }

            //Reading seed word
            line = dic1.readLine();
            return line;

        } finally {
            dic1.close();
        }
    }

    private  LinkedList<String> usedSeedWords(String root) {
        LinkedList<String> used = new LinkedList<>();
        File rootFolder = new File (root);
        for (File inner : rootFolder.listFiles()) {
            if (!inner.isDirectory()) {
                try {
                    if (!inner.getName().contains("~")) {
                        used.add(readLevelSeedWords(inner.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                used.addAll(usedSeedWords(inner.getAbsolutePath()));
            }
        }
        return used;
    }
}



