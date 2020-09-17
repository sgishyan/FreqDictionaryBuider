package wordwars;

/**
 * Created by suren on 6/24/16.
 */
public class SET extends ChallengeMetric {
    private int count;
    private char[] letters;
    public SET(java.util.Set<String> words, java.util.Set<String> basicWords, java.util.Set<String> mediumWords, int time, char... letters) {
        super(words, basicWords, mediumWords, time);
        count = letters.length;
        this.letters = letters;
        challengeValue = getChallengeValue();
    }

    @Override
    public void printMetrics() {

        System.out.print("SET։  ");
        for(int i = 0; i < letters.length; i++){
            System.out.print(letters[i] + " ");

        }
        System.out.println("  in time " + time + " : " + challengeValue);

    }

    @Override
    public int getChallengeValue() {

        double probD = 1;

        for (int i = 0; i < letters.length; i++) {
            CollectLetters challenge = new CollectLetters(allWords, easyWords, mediumWords, 1, time / count + 6, letters[i]);
            int value = challenge.getChallengeValue();
            //System.out.println("Value։  " + value);
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
