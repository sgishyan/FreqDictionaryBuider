import java.util.ArrayList;

/**
 * Created by suren on 8/4/15.
 */
public class VariationDifficultySorter {



    private  double getAverage(ArrayList<String> dictionary, char[] letters) {

        ArrayList<String> currentWords = new ArrayList<String>();
        int currentWordsCount = 0;
        int sumDifficulty = 0;
        int counter;
        for(int index = 0; index < dictionary.size(); index++)    {
            String word = dictionary.get(index);
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
                currentWordsCount++;
                sumDifficulty += index;
            }
        }
        return (double)sumDifficulty / currentWordsCount;
    }


    private boolean isNormal(ArrayList<String> dictionary, char[] letters, int normalizator) {

        int counter;
        for(int index = 0; index < dictionary.size(); index++)    {
            String word = dictionary.get(index);
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


            }
        }
        return false;
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
        public int compareTo(Word o) {
            return word.compareTo(o.word);
        }
    }
}
