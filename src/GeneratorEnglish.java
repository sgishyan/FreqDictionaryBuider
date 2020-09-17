import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Babun
 * Date: 19.01.14
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class GeneratorEnglish {
    public long global_counter = 0;

    public static final int DESIRED_COUNT = 36;
    public HashMap<String, Integer> wordUsageFrequency;
    public static void main(String[] args) throws IOException {
        GeneratorEnglish parser = new GeneratorEnglish();
        int count = parser.parseFile("/home/suren/Documents/temp/dictionaries/new/rare_used_union_3.txt", "/home/suren/Documents/temp/variationsRare3.txt", "/home/suren/Documents/temp/Testing/frequencyRare3.txt");
        System.out.println("WORDS COUNT " + count);


    }

    public int parseFile(String filename, String wFilename, String wFrequencyFilename) throws IOException {

        wordUsageFrequency = new HashMap<String, Integer>();
        FileWriter fw = new FileWriter(wFilename);
        FileWriter freqFw = new FileWriter(wFrequencyFilename);
        ArrayList<String> dictionary = new ArrayList<String>();
        HashMap<String, Word> descriptions = new HashMap<String, Word>();
        Set<String> desiredLetters = new TreeSet<String>();
        Set<String> allWords = new TreeSet<String>();


        BufferedReader br = new BufferedReader(new FileReader(filename));
        Scanner reader = new Scanner(System.in);
        int count = 1000000;
        int index = 0;
        char[] topLetters = {'e', 'a', 'i', 'o'};
        char[] goodLetters = {'n', 'r', 't', 'l', 's', 'u','d','g'};
        char[] mediumLetters = {'b', 'c', 'm', 'p', 'f', 'h', 'v', 'w', 'y'};
        char[] badLetters = {'k', 'j', 'x', 'q', 'z'};

        char[] newTopLetters = {'e', 'a', 'i', 'o', 'n', 'r', 't', 'l', 's', 'u','d','g', 'c', 'm', 'b', 'p', 'f', 'h', 'v', 'w', 'y', 'k', 'j', 'x', 'q', 'z'};
         char[] newBadLetters = { 'k', 'j', 'x', 'q', 'z'};

         // char[] letters = new char[7];
        Random rand = new Random();

        long totalVariations = (topLetters.length * topLetters.length) * (goodLetters.length * goodLetters.length) *
                (mediumLetters.length * mediumLetters.length) * badLetters.length;

        double newTotalVariations = Math.pow(newTopLetters.length, 7);
        //StringBuffer buffer = new StringBuffer();


        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                dictionary.add(line);
                String description = br.readLine();
                descriptions.put(line, new Word(line, description));
                line = br.readLine();
            }
            String everything = sb.toString();
        } finally {
            br.close();

        }
        //System.out.println("Total descriptions =  " + descriptions.size() + "");
        int letterVariations = 0;
//        while (index++ < count) {
//            letters[0] = topLetters[rand.nextInt(topLetters.length)];
//            letters[1] = topLetters[rand.nextInt(topLetters.length)];
//            letters[2] = goodLetters[rand.nextInt(goodLetters.length)];
//            letters[3] = goodLetters[rand.nextInt(goodLetters.length)];
//            letters[4] = mediumLetters[rand.nextInt(mediumLetters.length)];
//            letters[5] = mediumLetters[rand.nextInt(mediumLetters.length)];
//            letters[6] = badLetters[rand.nextInt(badLetters.length)];

//                while (index++ < count) {
//            letters[0] = alphabet[rand.nextInt(alphabet.length)];
//            letters[1] = alphabet[rand.nextInt(alphabet.length)];
//            letters[2] = alphabet[rand.nextInt(alphabet.length)];
//            letters[3] = alphabet[rand.nextInt(alphabet.length)];
//            letters[4] = alphabet[rand.nextInt(alphabet.length)];
//            letters[5] = alphabet[rand.nextInt(alphabet.length)];
//            letters[6] = alphabet[rand.nextInt(alphabet.length)];




        int checkCounter = 1;
        //int letterVariations = 0;
//        for (char c1 : topLetters)
//            for (char c2 : topLetters)
//                for (char c3 : goodLetters)
//                    for (char c4 : goodLetters)
//                        for (char c5 : mediumLetters)
//                            for (char c6 : mediumLetters)
//                                for (char c7 : badLetters){
        for (char c1 : newTopLetters)
            for (char c2 : newTopLetters)
                for (char c3 : newTopLetters)
                    for (char c4 : newTopLetters)
                        for (char c5 : newTopLetters)
                            for (char c6 : newTopLetters)
                                for (char c7 : newTopLetters){

                                    checkCounter++;
                                    char[] letters = {c1, c2, c3, c4, c5, c6, c7};
                                    Arrays.sort(letters);
                                    String newString = new String(letters);

                                    if (allWords.contains(newString))   {
                                        continue;
                                    }
                                    allWords.add(newString);

                                    //System.out.println("Total check " + checkCounter + " of " + totalVariations +" (" +(checkCounter  / totalVariations) * 100  + " %)");
                                    if (checkLetters(dictionary, letters)) {
                                       // System.out.println("Total check " + checkCounter + " of " + totalVariations +" (" +(double)((double)checkCounter  / (double)totalVariations) * 100  + " %)");
                                        System.out.println("Total check " + checkCounter + " of " + (long)newTotalVariations +" (" +((double)checkCounter  / +newTotalVariations) * 100  + " %)");
                                        System.out.println("Fount Letter variations  = " + ++letterVariations );
                                        System.out.println("Words in frequency Dic  = " + wordUsageFrequency.size() );

                                        //Arrays.sort(letters);
                                        //String myString = new String(letters);
                                        desiredLetters.add(newString);
                                        //System.out.println("Desired Letters Count " + desiredLetters.size());
                                    }
                                }

//            if (checkLetters(dictionary, letters)) {
//                Arrays.sort(letters);
//                desiredLetters.add(new String(letters));
//                System.out.println("Total check " + index + " of " + count +" (" +(double)((double)index  / (double)count) * 100  + " %)");
//                System.out.println("Fount Letter variations  = " + ++letterVariations );
//                System.out.println("Desired Letters Count " + desiredLetters.size());
//            }
//       }
        for (String let : desiredLetters)  {
            fw.write(let+ "\n");
        }
        fw.close();

        FrequencyWord[] words = new FrequencyWord[wordUsageFrequency.size()];
        int wIndex = 0;
        for (String str : wordUsageFrequency.keySet())  {
            words[wIndex++] = new FrequencyWord(str, wordUsageFrequency.get(str), descriptions.get(str).getDescription());
        }
        Arrays.sort(words);

        for (FrequencyWord word : words)  {
            freqFw.write(word.getWord() + " " + word.getFrequency() + "\n");
            freqFw.write(word.description + "\n");
        }
        freqFw.close();


        return desiredLetters.size();

    }

    private boolean checkLetters(ArrayList<String> dictionary, char[] letters) {

        ArrayList<String> currentWords = new ArrayList<String>();
        int currentWordsCount = 0;
        int counter = 0;
        for(String word : dictionary )    {
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
            }
        }
        //System.out.println(counter);
        if (currentWordsCount >= 36 && currentWordsCount <= 44)
        {
//            if (currentWordsCount == 50) {
//                String newString = new String(letters);
//                System.out.println("Variation with 50 words: " + newString);
//            }
            global_counter++;

            for(String str :  currentWords) {
                if (wordUsageFrequency.containsKey(str)) {
                    int oldValue = wordUsageFrequency.get(str);
                    wordUsageFrequency.put(str, oldValue + 1);
                } else {
                    wordUsageFrequency.put(str, 1);
                }
            }
//            System.out.println(global_counter++);
//            System.out.println(new String(letters) + " : " + currentWordsCount);

//            System.out.println("----------------------------------------------");
//            System.out.println("Current Letters: ");
//            for(char c : letters ) {
//                System.out.print(c);
//            }
//            System.out.println();
//            System.out.println("Current Words: " + currentWords.size());
//            for (String str : currentWords ) {
//                System.out.println(str);
//            }
            return true;
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
