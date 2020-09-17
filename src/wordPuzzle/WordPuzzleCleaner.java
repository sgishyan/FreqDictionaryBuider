package wordPuzzle;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by suren on 1/16/17.
 */
public class WordPuzzleCleaner {
    public static void main(String[] args) throws IOException {


        ArrayList<PuzzleWord> english;



        ArrayList<String> acceptedWords = new ArrayList<String>();

        final String englishFile = "/home/suren/Documents/temp/wp/words_all_eng.txt";
            final String cleanedFile = "/home/suren/Documents/temp/wp/words_cleaned_eng.txt";
        english = readFile(englishFile);
        writeToFilesEng(english, cleanedFile);

    }

    private static ArrayList<PuzzleWord> readFile(String englishFile) throws IOException {
        System.out.println("Reading...");
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<PuzzleWord> words = new ArrayList<PuzzleWord>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                String[] parts = word.split("\t");
                PuzzleWord w = new PuzzleWord(parts[1].trim(), Integer.parseInt(parts[0].trim()), parts[2].trim());
              //  System.out.println(parts.length);
                if(w.getWord().length() > 2 && w.getWord().length() < 8 && !w.getWord().contains("-") && !w.getWord().contains("'") &&
                        Character.isLowerCase(w.getWord().charAt(0))) {
                    if (!words.contains(w)) {
                        words.add(w);
                        if(w.getWord().length() < 3) {
                            System.out.println(w.getWord());
                        }
                    }

                } else {
                   // System.out.println(w.getWord());
                }
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static void writeToFilesEng(ArrayList<PuzzleWord> words, String filename) throws IOException {

        File file = new File(filename);
        BufferedWriter output = null;
        output = new BufferedWriter(new FileWriter(file));
        for(int i = 0; i < words.size(); i++) {
            output.write(words.get(i).getIndex() + "\t" +  words.get(i).getWord() + "\t" + words.get(i).getPartOfLanguage() + "\n");
        }

        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PuzzleWord {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PuzzleWord)) return false;

            PuzzleWord that = (PuzzleWord) o;
            if (!word.equals(that.word)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = word.hashCode();
            result = 31 * result + index;
            result = 31 * result + partOfLanguage.hashCode();
            return result;
        }

        String word;
        int index;
        String partOfLanguage;

        public PuzzleWord(String word, int index, String partOfLanguage) {
            this.word = word;
            this.index = index;
            this.partOfLanguage = partOfLanguage;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getPartOfLanguage() {
            return partOfLanguage;
        }

        public void setPartOfLanguage(String partOfLanguage) {
            this.partOfLanguage = partOfLanguage;
        }
    }

}

