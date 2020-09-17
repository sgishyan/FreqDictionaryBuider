package wordwars;

import java.util.Set;

/**
 * Created by suren on 6/24/16.
 */
public class Stairs extends ChallengeMetric {
    private int count;
    private int[] stairs;
    public Stairs(Set<String> words, Set<String> basicWords, Set<String> mediumWords, int time, int... stairs) {
        super(words, basicWords, mediumWords, time);
        count = stairs.length;
        this.stairs = stairs;
        challengeValue = getChallengeValue();
    }

    @Override
    public void printMetrics() {

        System.out.print("Stairs։  ");
        for(int i = 0; i < stairs.length; i++){
            System.out.print(stairs[i] + " ");

        }
        System.out.println("  in time " + time + " : " + challengeValue);

    }

    @Override
    public int getChallengeValue() {

        double probD = 1;

        for (int i = 0; i < stairs.length; i++) {
            MakeWordsWithLengthNChallenge challenge = new MakeWordsWithLengthNChallenge(allWords, easyWords, mediumWords, 1, time / count + 6, stairs[i]);
            int value = challenge.getChallengeValue();
           // System.out.println("Value։  " + value);
            probD *= (value /1000.0);
        }
        return (int)(probD * 1000);
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
