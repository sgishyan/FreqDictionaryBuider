package Crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 9/29/18.
 */
public class WordsFormWord6_rus_separate_custom {
    public static final String DICTIONARY_FILENAME_RUS_EASY = "/home/suren/Documents/languages/Russian/rusRes.txt";
    public static final String DICTIONARY_FILENAME_RUS_HARD = "/home/suren/Documents/languages/Russian/hard_final_chalk.txt";
    public static final String DICTIONARY_FILENAME_RUS_WM = "/home/suren/Documents/languages/Russian/russian_wm_cleared.txt";
    public static final String DICTIONARY_FILENAME_SPA = "/home/suren/Documents/languages/Spanish/spanishDictionary_38.txt";
    public static final String DICTIONARY_FILENAME_RUS_7medium = "/home/suren/Documents/languages/Russian/medium7.txt";
    public static final String CUSTOM_WORD_LIST_6 = "/home/suren/Documents/languages/Russian/custom6.txt";

    private static final int SHUFFLE_ITERATIONS = 1;
    private static final int SEED_SHUFFLES = 1;
    private ArrayList<String> dictionary;
    private ArrayList<String> custom6;
    private ArrayList<String> seedDictionary;
    private ArrayList<String> availableWords;
    private LinkedList<String> usedSeeds;
    private Queue<String> usedWords;
    private ArrayList<CrosswordWithSeparateWords> crosswords = new ArrayList<>();
    private ArrayList<CrosswordWithSeparateWords> temp = new ArrayList<>();
    private ArrayList<CrosswordWithSeparateWords> sequence = new ArrayList<>();
    private ArrayList<CrosswordWithSeparateWords> bestSequence = new ArrayList<>();
    private ArrayList<CrosswordWithSeparateWords> globalBestSequence = new ArrayList<>();


    public static final int SEED_LENGTH = 6;
    public static final int MIN_COUNT = 7;
    public static final int COUNT = 1000;
    public static final int QUEUE_SIZE = 300;

    public static void main(String[] args) throws IOException {
       WordsFormWord6_rus_separate_custom w = new WordsFormWord6_rus_separate_custom();

    }

    public WordsFormWord6_rus_separate_custom() throws IOException {
        int customUsedSeeds = 0;

        Random random = new Random();
        custom6 =  readFile(CUSTOM_WORD_LIST_6);
        usedSeeds = usedSeedWords("/home/suren/Documents/temp/crossword/chalk_rus/last5");
        System.out.println("---------------------------------------------------");
       // System.out.println("---------USED SEEDS");
//        for (String s : usedSeeds) {
//            System.out.println(s);
//        }
        System.out.println("---------------------------------------------------");

        dictionary = readFile(DICTIONARY_FILENAME_RUS_HARD);
        seedDictionary = readFile(DICTIONARY_FILENAME_RUS_HARD);
        usedWords = usedWordsList("/home/suren/Documents/temp/crossword/chalk_rus/last5");
        int globalBestDistance = 0;
        ArrayList<String> seedWords = new ArrayList<>();
        Map<String, Integer> wordFrequency = new HashMap<>();


        for (int shuffleIteration = 0; shuffleIteration < SEED_SHUFFLES; shuffleIteration++) {

           // System.out.println("SEED SHUFFLE " + shuffleIteration);

            Collections.shuffle(custom6);
            Set<String> repeat = new HashSet<>();

            for (String a : custom6) {
                String seed = a.substring(0, a.length() - 1);
                if (repeat.contains(seed)) {
                  //  System.out.println("Reapeat : " + seed);
                    continue;
                }
                for (String exist : repeat) {
                    if (isAnagram(exist, seed)) {
                        continue;
                    }
                }

                repeat.add(seed);
                seedDictionary.add(a);
            }
            System.out.println("Customs " + repeat.size());
            Collections.shuffle(seedDictionary);
            seedWords.clear();
            wordFrequency.clear();
            temp.clear();
            usedWords = usedWordsList("/home/suren/Documents/temp/crossword/chalk_rus/last5");
            crosswords.clear();
            customUsedSeeds = 0;


            for (int index = 0; index < seedDictionary.size(); index++) {
                System.out.println(index);

                boolean tooClose = true ;
                boolean isFound = false ;

                for (int element = index; element < seedDictionary.size(); element++) {

                    String seed = seedDictionary.get(element);
                   // System.out.println(seed);
                    if (seed.length() != SEED_LENGTH) {
                        continue;
                    }

                    if (usedSeeds.contains(seed)) {
                        continue;
                    }
                    //System.out.println(seed);
                    for (String exist : seedWords) {
                        if (isAnagram(exist, seed)) {
                            // System.out.println("Anagram " + exist + " " + seed);
                            continue;
                        }
                    }

                    tooClose = false;
                    for (int seqIndex = seedWords.size() - 1; seqIndex >= ((seedWords.size() > 3) ? seedWords.size() - 3 : 0); seqIndex--) {
                        String seqWord = seedWords.get(seqIndex);
                        //System.out.println(sequence.size() - 3 + " " + seqIndex);
                        if (getDistance(seed, seqWord) < seqWord.length() - 2) {
                            tooClose = true;
                            // System.out.println("Too Close: " + seqWord);
                            break;
                        }
                    }

                    if (tooClose) {
                        continue;
                    }

                    availableWords = checkLetters(seed.trim().toCharArray());
                    if (!availableWords.contains(seed) && !repeat.contains(seed.substring(0, seed.length() -1))) {
                        availableWords.add(seed);
                    }
                    Iterator<String> iterator = availableWords.iterator();

                    while (iterator.hasNext()) {
                        String available = iterator.next();

                        //System.out.println(available);
                        if (usedWords.contains(available)) {
                            iterator.remove();
                        }
                    }

                    if (availableWords.size() < MIN_COUNT) {
                        continue;
                    }



                    CrosswordWithSeparateWords best = null;
                    int randChance = random.nextInt(7);
                    Iterations:
                    for (int i = 0; i < 100; i++) {
                        CrosswordWithSeparateWords crossword = null;


                        if (randChance  < 2) {
                            crossword = new CrosswordWithSeparateWords(dictionary, availableWords, seed, Difficulty.NO_SEPARATION);

                        }else
                        if (randChance  == 2 || randChance == 3 || randChance == 4) {
                            crossword = new CrosswordWithSeparateWords(dictionary, availableWords, seed, Difficulty.EASY);

                        }else
                        if (randChance  == 5) {
                            crossword = new CrosswordWithSeparateWords(dictionary, availableWords, seed, Difficulty.MEDIUM);
                        }
                        if (randChance  == 6) {
                            crossword = new CrosswordWithSeparateWords(dictionary, availableWords, seed, Difficulty.HARD);
                        }

                        if (crossword.getPlacedWordsCount() < MIN_COUNT) {
                            continue;
                        }
                        if (crossword.hasInvisablCells()) {
                            continue;
                        }


                        if (best == null || best.getPlacedWordsCount() < crossword.getPlacedWordsCount()) {
                                best = crossword;
                            } else if (best.getPlacedWordsCount() == crossword.getPlacedWordsCount() && best.getTableSize() > crossword.getTableSize()) {
                                best = crossword;
                        }
                    }

                    if (best == null) {
                        continue;
                    }


                    seedWords.add(seed);
                    if (repeat.contains(seed.substring(0, seed.length() - 1))) {
                        customUsedSeeds++;
                    }
                    //best.print();
                    for (FixedWord fixedWord : best.getPlacedWords()) {
                        usedWords.add(fixedWord.getWord());
                    }
                    //System.out.println("Used Size " + usedWords.size() );
                    if (usedWords.size() > QUEUE_SIZE) {
                        int numberToDelete = usedWords.size() - QUEUE_SIZE;
                        for (int i = 0; i < numberToDelete; i++) {
                            usedWords.remove();
                        }
                    }
                    crosswords.add(best);
                    // best.writePuzzle("/home/suren/Documents/temp/crossword/levels_rus/5", levelNum );
                    availableWords.clear();
                    Collections.swap(seedDictionary, index, element);
                    isFound = true;
                    break;
                }
                if (!isFound) {
                    break;
                }
                if (crosswords.size() >= COUNT) {
                    break;
                }


            }

            if (crosswords.size() < COUNT) {
                System.out.println("Size " + crosswords.size());
                shuffleIteration--;
                continue;
            }
            System.out.println("Size " + crosswords.size());
          //  System.out.println("Crosswords Count : " + crosswords.size());

            wordFrequency.clear();
            for (CrosswordWithSeparateWords cr : sequence) {
                for (FixedWord fixed : cr.getPlacedWords()) {
                    String str = fixed.getWord();
                    if (wordFrequency.containsKey(str)) {
                        wordFrequency.put(str, wordFrequency.get(str) + 1);
                    } else {
                        wordFrequency.put(str, 1);
                    }
                }
            }
        }



        int counter = 1;
        int total = 1;
        int level = 1;
        System.out.println("Custom seeds used = " + customUsedSeeds);
        wordFrequency.clear();
        int[]  levelsPerPack = {3, 5, 10, 15, 15, 15, 20, 20, 20, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
        int countryIterator = 22;
        int packLevels = 1;
        int pack = 1;
        int number = 121;
        for (CrosswordWithSeparateWords cr : crosswords) {


            for(FixedWord fixed : cr.getPlacedWords()) {
                String str = fixed.getWord();
                System.out.println(str);
                if (wordFrequency.containsKey(str)) {
                    wordFrequency.put(str, wordFrequency.get(str) + 1);
                } else {
                    wordFrequency.put(str, 1);
                }
            }
            cr.print();






            cr.writePuzzle("/home/suren/Documents/temp/crossword/chalk_rus/6", number);
            System.out.println(pack + "/" + packLevels);
            number++;
            System.out.println(pack + "/" + packLevels);
            packLevels++;
           // System.out.println("Pack Levels " + packLevels);
////            System.out.println("Pack iterator " +levelsPerPack[countryIterator] );
//            if (packLevels > levelsPerPack[countryIterator]) {
//                pack++;
//                packLevels = 1;
//                if (pack > 5) {
//                    pack = 1;
//                    countryIterator++;
//                }
//            }





//            if (total <= 20) {
//                total++;
//                cr.writePuzzle("/home/suren/Documents/temp/crossword/levels_rus/Pack2/Dish" + counter + "/" , level );
//                level++;
//                if ((level - 1) % 5 == 0) {
//                    counter++;
//                    if (counter == 5) {
//                        counter = 1;
//                    }
//                    level = 1;
//                }
//            } else {
//
//                cr.writePuzzle("/home/suren/Documents/temp/crossword/levels_rus/Pack3/Dish" + counter + "/", level);
//                level++;
//                if ((level - 1) % 10 == 0) {
//                    level = 1;
//                    counter++;
//                    if (counter == 5) {
//                        counter = 1;
//                    }
//                }
//
//            }
//            System.out.println(cr.getSeedWord());

        }

        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
//        System.out.println("Max Entry : " + maxEntry.getKey() + " " + maxEntry.getValue());
     //   System.out.println("Total Words used : " + wordFrequency.values().size());
        



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
                if (words.contains(word)) {
                    System.out.println("Duplicate : " + word);
                }else {
                    words.add(word.toLowerCase());
                }
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

    private  static LinkedList<String> usedWordsList(String root) {
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
                used.addAll(usedWordsList(inner.getAbsolutePath()));
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

    private static LinkedList<String> usedSeedWords(String root) {
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



