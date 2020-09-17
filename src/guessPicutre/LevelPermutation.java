package guessPicutre;

import java.util.Random;

/**
 * Created by suren on 4/17/17.
 */
public class LevelPermutation {

    public static void main(String []args) {

        int levelsCount = 300;
        int [] levelsPermutationArray = new int[levelsCount];
        for (int i = 0; i < levelsPermutationArray.length; i++) {
            levelsPermutationArray[i] = i + 1;
        }
        long shuffleKey = System.nanoTime();
       // long shuffleKey = 10000;
        Random timedRand = new Random();
        timedRand.setSeed(shuffleKey);



        //Shuffling 1 - 30 levels
        for (int i = 29; i >= 0; i--) {
            int index = timedRand.nextInt(i + 1);
            int temp = levelsPermutationArray[index];
            levelsPermutationArray[index] = levelsPermutationArray[i];
            levelsPermutationArray[i] = temp;
        }

        //Shuffling 31 - 40 levels
        for (int i = 39; i >= 30; i--) {
            int index = timedRand.nextInt(10) + 30;
            int temp = levelsPermutationArray[index];
            levelsPermutationArray[index] = levelsPermutationArray[i];
            levelsPermutationArray[i] = temp;
        }

        //Shuffling 41 - 50 levels
        for (int i = 49; i >= 40; i--) {
            int index = timedRand.nextInt(10) + 40;
            int temp = levelsPermutationArray[index];
            levelsPermutationArray[index] = levelsPermutationArray[i];
            levelsPermutationArray[i] = temp;
        }

        //Shuffling 51 - last levels
        for (int i = levelsPermutationArray.length - 1; i >= 50; i--) {
            int index = timedRand.nextInt(levelsPermutationArray.length - 50) + 50;
            int temp = levelsPermutationArray[index];
            levelsPermutationArray[index] = levelsPermutationArray[i];
            levelsPermutationArray[i] = temp;
        }



        //Checker
        boolean occurrence[] = new boolean[levelsCount + 1];

        for (int i = 0; i < levelsCount + 1; i++) {
            if ( i < levelsCount)
                System.out.println(levelsPermutationArray[i]);
            occurrence[i] = false;
        }

        System.out.println("------------CHECKING----------");
        System.out.println("------------CHECKING----------");
        System.out.println("------------CHECKING----------");

        for (int i = 0; i < levelsCount; i++) {
            if (occurrence[levelsPermutationArray[i]]) {
                System.out.println("------------ALARM REPEATED----------");
                break;
            }
            occurrence[levelsPermutationArray[i]] = true;
            if(levelsPermutationArray[i] <= 30 && i > 29) {
                System.out.println("------------ALARM WRONG LEVEL POSITION ----------");
                System.out.println(levelsPermutationArray[i]);
            }

            if(levelsPermutationArray[i] > 30 && levelsPermutationArray[i] <=40 &&( i > 39 || i < 30)) {
                System.out.println("------------ALARM WRONG LEVEL POSITION ----------");
                System.out.println(levelsPermutationArray[i]);
            }

            if(levelsPermutationArray[i] > 40 && levelsPermutationArray[i] <=50 &&( i > 49 || i < 40)) {
                System.out.println("------------ALARM WRONG LEVEL POSITION ----------");
                System.out.println(levelsPermutationArray[i]);
            }

            if(levelsPermutationArray[i] > 50 && i < 50 ) {
                System.out.println("------------ALARM WRONG LEVEL POSITION ----------");
                System.out.println(levelsPermutationArray[i]);
            }

        }


    }
}



