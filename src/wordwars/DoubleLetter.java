package wordwars;

import java.util.Set;

/**
 * Created by suren on 6/24/16.
 */
public class DoubleLetter extends ChallengeMetric {
    private int count;
    private char[] letters;

    public DoubleLetter(Set<String> words, Set<String> basicWords, Set<String> mediumWords, int time, char... letter) {
        super(words, basicWords, mediumWords, time);
        this.letters = letter;
        challengeValue = getChallengeValue();
    }

    @Override
    public void printMetrics() {

            System.out.print("DoubleLetter։  ");
            for(int i = 0; i < letters.length; i++){
                System.out.print(letters[i] + " ");

            }
        System.out.println("  in time " + time + " : " + challengeValue);
    }

    @Override
    public int getChallengeValue() {
        int words = 0;
        for (String word : allWords) {
            for(char let : letters) {
                if(checkDoubleLetter(word, let)) {
                    words += getWordValue(word);
                }

            }

        }

        double guessWordProbability;
        double lambda;
        words /= 4500;
        //System.out.println(" Words = " + words);


        guessWordProbability = 1 - Math.pow(1 - PROBABILITY, words);
        lambda = 1 / (1 / guessWordProbability);
        double prob = 0;
        for (int it = letters.length; it < 50; it++) {
            prob += probabilityPuasson(time, lambda, it);
        }
        //System.out.println(" Prob = " + prob);
        //System.out.println("Length։  " + i + "   GWP: " + guessWordProbability + "  Լambda: "  + lambda);
        //System.out.println("Length։  " + i + "   lt: " + lambda * time);
        return (int)(prob * 1000);


    }

    private boolean checkDoubleLetter(String word, char let) {
        int count = 0;
        for(int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == let) {
                count++;
            }
        }
        return count >= 2;
    }

    public double probabilityPuasson(int time, double lambda, int count) {

        double f = 1;
        for (int i = 1; i <= count; i++){
            f *= i;
        }

        // System.out.println("f։  " + f);
        // System.out.println("lt^c։  " + Math.pow(lambda * time, count));
        // System.out.println("E^-lt։  " + Math.pow(Math.E, -lambda * time));
        return  (Math.pow(lambda * time, count) * Math.pow(Math.E, -(lambda * time))) / f;

    }

}
