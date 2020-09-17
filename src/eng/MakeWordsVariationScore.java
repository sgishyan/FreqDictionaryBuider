package eng;

import protector.ValuesEncryption;
import wordwars.MakeWordsWithLengthNChallenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by suren on 3/28/19.
 */
public class MakeWordsVariationScore {

    private ArrayList<String> dictionaryBasic;
    private ArrayList<String> dictionaryMain;
    private ArrayList<String> dictionaryRare;
    private ArrayList<String> dictionaryAll;
    private ArrayList<String> variationsRare;

    private class VariationScore implements Comparable<VariationScore>{
        String word;
        int basic;
        int main;
        int rare;
        int all;

        public VariationScore(String word, int basic, int main, int rare, int all) {
            this.word = word;
            this.basic = basic;
            this.main = main;
            this.rare = rare;
            this.all = all;
        }


        @Override
        public int compareTo(VariationScore o) {
            return (double)rare /  all < (double)o.rare /  o.all ? - 1 : 1;
        }
    }
    public static void main(String[] args) throws IOException {
        new MakeWordsVariationScore();

    }

    public MakeWordsVariationScore() throws IOException {
        dictionaryAll = new ArrayList<>();
        dictionaryBasic = readFile("/home/suren/Documents/MakeWords/basic_out.txt");
        dictionaryAll.addAll(dictionaryBasic);
        dictionaryMain = readFile("/home/suren/Documents/MakeWords/main_out.txt");
        dictionaryAll.addAll(dictionaryMain);
        dictionaryRare = readFile("/home/suren/Documents/MakeWords/rare_out.txt");
        dictionaryAll.addAll(dictionaryRare);
        variationsRare = readFile("/home/suren/Documents/MakeWords/variations_rare.txt");

        ArrayList<VariationScore> variationScores = new ArrayList<>();
        for (String variation : variationsRare) {
            ArrayList<String> words = checkLetters(variation.toCharArray());
            int basic = 0;
            int main = 0;
            int rare = 0;

            for (String str : words) {
                if (dictionaryBasic.contains(str)) {
                    basic++;
                }

                if (dictionaryMain.contains(str)) {
                    main++;
                }

                if (dictionaryRare.contains(str)) {
                    rare++;
                }
            }
            //System.out.println(variation);
            variationScores.add(new VariationScore(variation, basic, main, rare, words.size()));


        }
        Collections.sort(variationScores);
        for (VariationScore variationScore : variationScores) {
            System.out.println(variationScore.word + " " + variationScore.basic + " " + variationScore.main + " " + variationScore.rare +" " +
            variationScore.all);

        }

    }

    private ArrayList<String> checkLetters(char[] letters) {

        ArrayList<String> currentWords = new ArrayList<String>();

        int counter = 0;
        for (String word : dictionaryAll) {
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

    private static ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                if (words.contains(word)) {
                   // System.out.println("Duplicate : " + word);
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
}
