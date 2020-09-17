package wordwars;

import java.util.Set;

/**
 * Created by suren on 6/24/16.
 */
public class MakeWords extends ChallengeMetric {
    private int count;

    public MakeWords(Set<String> words, Set<String> basicWords, Set<String> mediumWords, int c, int time) {
        super(words, basicWords, mediumWords, time);
        count = c;
        challengeValue = getChallengeValue();
    }

    @Override
    public void printMetrics() {

        System.out.println("Make " + count + " words in " + time + " seconds: " + challengeValue);

    }

    @Override
    public int getChallengeValue() {
        int words = 0;
        for (String word : allWords) {
            words += getWordValue(word);

        }

        double guessWordProbability;
        double lambda;




        words /= 15000;


        guessWordProbability = 1 - Math.pow(1 - PROBABILITY, words);
        lambda = 1 / (1 / guessWordProbability);
        double prob = 0;
        for (int it = count; it < 50; it++) {
            prob += probabilityPuasson(time, lambda, it);
        }

        //System.out.println("Length։  " + i + "   GWP: " + guessWordProbability + "  Լambda: "  + lambda);
        //System.out.println("Length։  " + i + "   lt: " + lambda * time);
        return (int)(prob * 1000);
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
