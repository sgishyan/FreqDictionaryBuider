package arm;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by suren on 8/10/15.
 */
public class ArmSorter {
    public static void main(String[] args) throws IOException {


        String oldDictionary = "/home/suren/Documents/temp/Testing/armWords.txt";
        String newWords = "/home/suren/Documents/temp/armFrequencyWords.txt";

        ArrayList<Word> oldList = new ArrayList<Word>();



        //Reading old
        BufferedReader old = new BufferedReader(new FileReader(oldDictionary));
        try {

            String line = old.readLine();
            while (line != null) {
                String word = line.trim().toLowerCase();
                String[] lemmas = word.split("\t");
                if (lemmas.length != 2) {
                    System.out.println(word);
                } else {
                    Word w = new Word(lemmas[0], Integer.parseInt(lemmas[1]));
                    oldList.add(w);

                }
                line = old.readLine();

            }

        } finally {
            old.close();
        }

        Collections.sort(oldList);
        Collections.reverse(oldList);

        writeNewWords(oldList, newWords);
    }

    private static void writeNewWords(ArrayList<Word> list, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < 100000; i++) {
                output.write(list.get(i).getWord() + " " + list.get(i).getFrequency()  + "\n");
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

    private static class Word implements Comparable<Word>
    {
        private String word;
        private int frequency;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Word)) return false;

            Word word1 = (Word) o;

            if (!word.equals(word1.word)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return word.hashCode();
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

        public Word(String word, int frequency) {

            this.word = word;
            this.frequency = frequency;
        }


        @Override
        public int compareTo(Word o) {
            return this.frequency - o.frequency;
        }
    }

}
