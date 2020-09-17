package wordwars;

import java.math.BigInteger;
import java.util.Set;

/**
 * Created by suren on 6/24/16.
 */
public abstract class ChallengeMetric implements Comparable<ChallengeMetric>{
    protected final int time;
    protected Set<String> allWords;
    protected Set<String> easyWords;
    protected Set<String> mediumWords;
    protected final static double PROBABILITY = 0.005;
    protected int challengeValue;

    public ChallengeMetric(Set<String> allWords, Set<String> easyWords, Set<String> mediumWords, int time) {
        this.allWords = allWords;
        this.easyWords = easyWords;
        this.mediumWords = mediumWords;
        this.time = time;


      //  probability /= ;
    }

    public abstract void printMetrics();


    public long getWordValue(String word) {
        int goodWordCoeff = 1;
        if (easyWords.contains(word)) {
            goodWordCoeff = 3;
        }else
        if (mediumWords.contains(word)) {
            goodWordCoeff = 2;
        }

        int lengthCoeff = 1;
        int len = word.length();
        switch(len){
            case 2:
                lengthCoeff = 1000;
                break;
            case 3:
                lengthCoeff = 250;
                break;
            case 4:
                lengthCoeff = 50;
                break;
            case 5:
                lengthCoeff = 10;
                break;
            case 6:
                lengthCoeff = 2;
                break;
            case 7:
                lengthCoeff = 1;
                break;
            default:
                lengthCoeff = 1;
        }
        return goodWordCoeff * lengthCoeff;

    }
    public long chance(int n, int k, double p) {
        BigInteger fenzi = new BigInteger("1");
        BigInteger fenmu = new BigInteger("1");
        for(int i=n-k+1; i <= n; i++){
            String s = Integer.toString(i);
            BigInteger stobig = new BigInteger(s);
            fenzi = fenzi.multiply(stobig);
        }
        for(int j=1; j <= k; j++){
            String ss = Integer.toString(j);
            BigInteger stobig2 = new BigInteger(ss);
            fenmu = fenmu.multiply(stobig2);
        }
        BigInteger result = fenzi.divide(fenmu);
        Long r = result.longValue();
        Double prob = Math.pow(p, k);
        return (long)(r * prob);
    }

    public abstract int getChallengeValue();


    @Override
    public int compareTo(ChallengeMetric o) {
        return o.challengeValue - challengeValue;
    }
}
