package ger;

import rus.Word;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Babun
 * Date: 19.01.14
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class VariationTester {
    public long global_counter = 0;

    public static final int DESIRED_COUNT = 36;
    public HashMap<String, Integer> wordUsageFrequency;

    public static void main(String[] args) throws IOException {
        VariationTester parser = new VariationTester();
        parser.parseFile();


    }

    public void parseFile() throws IOException {

        String goodWordsPath = "/home/suren/Documents/temp/ger/finalout.txt";
        String badWordsPath = "/home/suren/Documents/temp/ger/maybenotaword.txt";
        String notSureWordsPath = "/home/suren/Documents/temp/ger/undef.txt";
        String allWordsPath = "/home/suren/Documents/temp/ger/all.txt";
        String variationPath = "/home/suren/Documents/temp/ger/variations1.txt";

        Set<Word> allWords = readFile(allWordsPath);
        Set<Word> notSureWords = readFile(notSureWordsPath);
        Set<Word> badWords = readFile(badWordsPath);
        Set<Word> goodWords = readFile(goodWordsPath);
        ArrayList<String> variations = new ArrayList<String>();


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(variationPath));
            String variation = br.readLine().trim();


            while (variation != null) {
                variation =variation.trim();
                variations.add(variation);
                variation = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String variation : variations) {
            ArrayList<String> variationWords = getVariationWords(allWords, variation.toCharArray());
            int badWordsCount = 0;
            int goodWordsCount = 0;
            int notSureWordsCount = 0;
            int shortWords = 0;
            int longWords = 0;
            for (String word : variationWords) {
                if (word.length() >= 5) {
                    longWords++;
                }else if (word.length() < 3){
                    shortWords++;
                }

                Word dummyWord = new Word(word, "");
                if (badWords.contains(dummyWord)) {
                    badWordsCount++;
                }

                if (goodWords.contains(dummyWord)) {
                    goodWordsCount++;
                }

                if (notSureWords.contains(dummyWord)) {
                    notSureWordsCount++;
                }
            }
            if (shortWords > longWords) {
                continue;
            }
            if(notSureWordsCount< 15 && badWordsCount < 3) {
                System.out.println(variation);
            }

        }



//        for (String let : desiredLetters)  {
//            fw.write(let+ "\n");
//        }
//        fw.close();
//
//        FrequencyWord[] words = new FrequencyWord[wordUsageFrequency.size()];
//        int wIndex = 0;
//        for (String str : wordUsageFrequency.keySet())  {
//            words[wIndex++] = new FrequencyWord(str, wordUsageFrequency.get(str), descriptions.get(str).getDescription());
//        }
//        Arrays.sort(words);
//


    }

    private Set<Word> readFile(String path) {

        Set<Word> dictionary = new TreeSet<Word>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String word = br.readLine();
            word = word.trim().toLowerCase();


            while (word != null) {
                //System.out.println(word);
                String description =  br.readLine().trim();
                dictionary.add(new Word(word, description));
                word = br.readLine();
                if(word != null) {
                    word = word.trim().toLowerCase();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dictionary;
    }
    private ArrayList<String> getVariationWords(Set<Word> dictionary, char[] letters) {

        ArrayList<String> currentWords = new ArrayList<String>();
          int counter = 0;
        for(Word wordDes : dictionary )    {
            String word = wordDes.getWord();
            boolean[] usedLetters = {false, false, false, false, false, false, false};
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

    private class FrequencyWord implements Comparable<FrequencyWord> {

        private String word;
        private int frequency;
        private String description;

        public FrequencyWord(String word, int frequency, String description) {

            this.word = word;
            this.frequency = frequency;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }


        @Override
        public int compareTo(FrequencyWord o) {
            return -(frequency - o.frequency);
        }

        @Override
        public boolean equals(Object obj) {
            return word.equals(((FrequencyWord)obj).word);
        }
    }

    private class Word implements Comparable<Word>{
        private String word;
        private String description;

        public Word(String word, String description) {
            this.word = word;
            this.description = description;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj instanceof Word) {
                Word w = (Word)obj;
                return w.word.equals(word);
            }
            return false;
        }

        @Override
        public int compareTo(Word o) {
            return word.compareTo(o.word);
        }
    }
}
