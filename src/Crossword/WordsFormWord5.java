package Crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 9/29/18.
 */
public class WordsFormWord5 {
    public static final String DICTIONARY_FILENAME_RUS_EASY = "/home/suren/Documents/languages/Russian/rusRes.txt";
    public static final String DICTIONARY_FILENAME_RUS_HARD = "/home/suren/Documents/languages/Russian/hard_final.txt";
    public static final String DICTIONARY_FILENAME_RUS_WM = "/home/suren/Documents/languages/Russian/russian_wm_cleared.txt";
    public static final String DICTIONARY_FILENAME_SPA = "/home/suren/Documents/languages/Spanish/spanishDictionary_38.txt";
    private static final int SHUFFLE_ITERATIONS = 20;
    private static final int SEED_SHUFFLES = 5;
    private ArrayList<String> dictionary;
    private ArrayList<String> seedDictionary;
    private ArrayList<String> availableWords;
    private ArrayList<String> usedPrevWords;
    private ArrayList<String> usedWords;
    private ArrayList<Crossword> crosswords = new ArrayList<>();
    private ArrayList<Crossword> temp = new ArrayList<>();
    private ArrayList<Crossword> sequence = new ArrayList<>();
    private ArrayList<Crossword> bestSequence = new ArrayList<>();
    private ArrayList<Crossword> globalBestSequence = new ArrayList<>();

    public static final int SEED_LENGTH = 5;
    public static final int MIN_COUNT = 5;
    public static final int COUNT = 60;


    public static void main(String[] args) throws IOException {
        WordsFormWord5 w = new WordsFormWord5();

    }

    public WordsFormWord5() throws IOException {

        dictionary = readFile(DICTIONARY_FILENAME_RUS_HARD);
        seedDictionary = readFile(DICTIONARY_FILENAME_RUS_HARD);
        usedPrevWords = usedWords("/home/suren/Documents/temp/crossword/levels_rus/Pack1");
        usedWords = new ArrayList<>();
        for (String str : usedPrevWords) {
            System.out.println("Used : " + str);
        }
        int globalBestDistance = 0;
        ArrayList<String> seedWords = new ArrayList<>();
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (int shuffleIteration = 0; shuffleIteration < SEED_SHUFFLES; shuffleIteration++) {
            System.out.println("SEED SHUFFLE " + shuffleIteration);
            Collections.shuffle(seedDictionary);
            seedWords.clear();
            wordFrequency.clear();
            temp.clear();
            usedWords.clear();
            usedWords.addAll(usedPrevWords);
            crosswords.clear();
            for (String seed : seedDictionary) {
                if (seed.length() == SEED_LENGTH) {

                     for (String exist : seedWords) {
                        if (isAnagram(exist, seed)) {
                           // System.out.println("Anagram " + exist + " " + seed);
                            continue;
                        }
                    }

                    availableWords = checkLetters(seed.trim().toCharArray());
                    if (!availableWords.contains(seed)) {
                        availableWords.add(seed);
                    }

                    // System.out.println(seed + "  " + availableWords.size());

                    //Removing used words
                    Iterator<String> iterator = availableWords.iterator();

                    while (iterator.hasNext()) {
                        String available = iterator.next();
                        if (usedWords.contains(available)) {
                            iterator.remove();
                        }
                    }

                    if (availableWords.size() >= MIN_COUNT) {
                        for (String str : availableWords) {
                           // System.out.println(str);
                        }
                        Collections.sort(availableWords, new Comparator<String>() {
                            @Override
                            public int compare(String s1, String s2) {
                                return s2.length() - s1.length();// comparision
                            }
                        });

                        Crossword best = null;
                        Iterations:
                        for (int i = 0; i < 100; i++) {
                            Crossword crossword = new Crossword(dictionary, availableWords, seed);

                            for (String a : availableWords) {
                                crossword.placeWord(a);
                            }
                            if (crossword.getPlacedWordsCount() < MIN_COUNT) {
                                continue;
                            }
                            //Checking used seedWords
//                        for (FixedWord fixedWord : crossword.getPlacedWords()) {
//                            if (usedPrevWords.contains(fixedWord.getWord())) {
//                              //  System.out.println("Repeated word " + fixedWord.getWord());
//                                continue Iterations;
//                            }
//                        }

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
                        //best.print();
                        for (FixedWord fixedWord : best.getPlacedWords()) {
                            usedWords.add(fixedWord.getWord());
                        }
                        crosswords.add(best);
                        // best.writePuzzle("/home/suren/Documents/temp/crossword/levels_rus/5", levelNum );
                        availableWords.clear();

                    }

                }
            }
            if (crosswords.size() < COUNT) {
                System.out.println("Count = " + crosswords.size());
                shuffleIteration--;
                continue;
            }
          //  System.out.println("Crosswords Count : " + crosswords.size());
            int bestDistance = 0;
            for (Crossword crossword : crosswords) {
                temp.add(crossword);
            }

            for (int i = 0; i < SHUFFLE_ITERATIONS; i++) {
                boolean wordFound = false;
                boolean allOK = true;
                int currentDistance = 0;
                sequence.clear();
                crosswords.clear();
                for (Crossword crossword : temp) {
                    crosswords.add(crossword);
                }
                Collections.shuffle(crosswords);
                sequence.add(crosswords.get(0));
                //System.out.println(crosswords.get(0));
                int wordsToArrange = COUNT;
                crosswords.remove(0);
                tryingCrosswords:
                while (crosswords.size() > 0 && wordsToArrange > 0) {
                    wordFound = false;
                    for (int index = 0; index < crosswords.size(); index++) {
                        String candidate = crosswords.get(index).getSeedWord();
                        boolean tooClose = false;

                        //System.out.println("Candidate: " + candidate);

                        int localDistance = 0;
                        for (int seqIndex = sequence.size() - 1; seqIndex >= ((sequence.size() > 4) ? sequence.size() - 4 : 0); seqIndex--) {
                            String seqWord = sequence.get(seqIndex).getSeedWord();
                            //System.out.println(sequence.size() - 3 + " " + seqIndex);
                            if (getDistance(candidate, seqWord) < seqWord.length() - 2) {
                                tooClose = true;
                                // System.out.println("Too Close: " + seqWord);
                                break;
                            } else {
                                localDistance += getDistance(candidate, seqWord);
                            }
                        }

                        if (!tooClose) {
                            //System.out.println(candidate);
                          //  currentDistance += localDistance;
                            sequence.add(crosswords.get(index));
                            crosswords.remove(index);
                            wordFound = true;
                            wordsToArrange--;
                            break;
                        }

                    }

                    if (!wordFound) {
                        // System.out.println("!!!!!!!!!!!!.........No word left...");
//                    for (Crossword cr : crosswords) {
//                        System.out.println(cr.getSeedWord());
//                    }
                        allOK = false;
                        break;
                    }
                }
                if (allOK) {


                    wordFrequency.clear();
                    for (Crossword cr : sequence) {
                        for (FixedWord fixed : cr.getPlacedWords()) {
                            String str = fixed.getWord();
                            if (wordFrequency.containsKey(str)) {
                                wordFrequency.put(str, wordFrequency.get(str) + 1);
                            } else {
                                wordFrequency.put(str, 1);
                            }
                        }
                    }
                    int usedWordsCount = wordFrequency.values().size();
                   // System.out.println("Distance = " + usedWordsCount);
                    if (bestDistance < usedWordsCount) {
                        bestDistance = usedWordsCount;
                      //  System.out.println("----Best---" + usedWordsCount);
                        bestSequence.clear();
                        for (Crossword crossword : sequence) {
                            bestSequence.add(crossword);
                        }
                    }
                    //TODO:Trying word count metric
//                if (bestDistance < currentDistance) {
//                    bestDistance = currentDistance;
//                    System.out.println("----Best---" + bestDistance);
//                    bestSequence.clear();
//                    System.out.println("Size Before: " +  bestSequence.size());
//
//                    for (Crossword crossword : sequence) {
//                        bestSequence.add(crossword);
//                    }
//                    System.out.println("Size After: " +  bestSequence.size());
//                }
                }

            }
            System.out.println("Local words " + bestDistance);
            System.out.println("Global words " + globalBestDistance);
            if (bestDistance > globalBestDistance) {
                globalBestDistance = bestDistance;
                globalBestSequence.clear();
                for (Crossword crossword : bestSequence) {
                    globalBestSequence.add(crossword);
                }
            }
            System.out.println("After: " + globalBestDistance);
        }



        System.out.println("----Best---" + globalBestSequence);
        int counter = 1;
        int total = 1;
        int level = 1;

        wordFrequency.clear();
        int[]  levelsPerPack = {0, 5, 10, 15, 15, 15, 20, 20, 20, 20};
        int countryIterator = 1;
        int packLevels = 1;
        int pack = 1;
        for (Crossword cr : globalBestSequence) {


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






            cr.writePuzzle("/home/suren/Documents/temp/crossword/levels_rus/" + (countryIterator + 1) + "." + pack + "/" , packLevels );
            System.out.println((countryIterator + 1) + "" + pack + "/" + packLevels);
            packLevels++;
            System.out.println("Pack Levels " + packLevels);
            System.out.println("Pack iterator " +levelsPerPack[countryIterator] );
            if (packLevels > levelsPerPack[countryIterator]) {
                pack++;
                packLevels = 1;
                if (pack > 4) {
                    pack = 1;
                    countryIterator++;
                }
            }



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
        System.out.println("Max Entry : " + maxEntry.getKey() + " " + maxEntry.getValue());
        System.out.println("Total Words used : " + wordFrequency.values().size());
        



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

    private static ArrayList<String> readLevelWords(String level) throws IOException {
        System.out.println(level);
        BufferedReader dic1 = new BufferedReader(new FileReader(level));
        ArrayList<String> words = new ArrayList<String>();
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

    private  ArrayList<String> usedWords(String root) {
        ArrayList<String> used = new ArrayList<>();
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



