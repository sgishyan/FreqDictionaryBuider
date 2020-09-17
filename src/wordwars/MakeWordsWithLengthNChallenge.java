package wordwars;

import java.util.Set;

/**
 * Created by suren on 6/24/16.
 */
public class  MakeWordsWithLengthNChallenge extends ChallengeMetric {
    private int count;
    private int length;

    public MakeWordsWithLengthNChallenge(Set<String> words, Set<String> basicWords, Set<String> mediumWords, int c, int time, int length) {
        super(words, basicWords, mediumWords, time);
        count = c;
        this.length = length;
        challengeValue = getChallengeValue();
        ///System.out.println("challenge value" + challengeValue);
    
    }

    @Override
    public void printMetrics() {
        //System.out.println("Length։  " + i + "   GWP: " + guessWordProbability + "  Լambda: "  + lambda);
        //System.out.println("Length։  " + i + "   lt: " + lambda * time);
        System.out.println("Len: " + count + " words with length " + length + "  in " + time  + " seconds: "  + challengeValue);
    }

    @Override
    public int getChallengeValue() {
        int[] lengths = new int[50];
        for (String word : allWords) {
            int len = word.length();
            lengths[len] += getWordValue(word);

        }

        double guessWordProbability;
        double lambda;


        if (length == 2) {
            lengths[length] /= 2000;

        }else {
            lengths[length] /= 10000;
        }



        guessWordProbability = 1 - Math.pow(1 - PROBABILITY, lengths[length]);
        lambda = 1 / (1 / guessWordProbability);
        double prob = 0;
        for (int it = count; it < 50; it++) {
            prob += probabilityPuasson(time, lambda, it);
        }

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
