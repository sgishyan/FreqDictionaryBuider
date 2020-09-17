package TOF;

import java.util.Random;

/**
 * Created by suren on 11/8/16.
 */
public class TofMultiplayerSimulator {

    public static void main(String[] args) {
        Random rand = new Random();


        final int QUESTIONS_IN_CATEGORY = 800;
        final int TOTAL_CATEGORIES = 7;
        final int EXPERIMENTS = 10000;
        final int GAMES = 200;
        int[][] collisions = new int [EXPERIMENTS][GAMES];
        for (int experiment = 0; experiment < EXPERIMENTS; experiment++) {

            int[] questions = new int[QUESTIONS_IN_CATEGORY * 7];
            for (int i = 0; i < questions.length; i++ ) {
                questions[i] = 0;
            }

            for(int games = 0; games < GAMES; games++) {
            //Players move
            //Choosing categories
                int cat1 = rand.nextInt(TOTAL_CATEGORIES);
                int cat2 = 0;
                do{
                    cat2 = rand.nextInt(TOTAL_CATEGORIES);
                }while(cat2 == cat1);

                //Playing questions from choosed categories
                int cat1Limit = 3;
                int cat2Limit = 3;

                //Category 1
                int index = 0;
                boolean needToBeUnplayed = true;
                while(cat1Limit > 0) {
                    if (needToBeUnplayed) {
                        if(questions[cat1 * QUESTIONS_IN_CATEGORY + index] == 0) {
                            questions[cat1 * QUESTIONS_IN_CATEGORY + index] = 1;
                            cat1Limit--;
                        }
                    } else {
                        questions[cat1 * QUESTIONS_IN_CATEGORY + index]++;
                        collisions[experiment][games]++;
                        cat1Limit--;
                    }
                    index++;
                    if(index >= QUESTIONS_IN_CATEGORY) {
                        index = 0;
                        needToBeUnplayed = false;
                    }

                }
                //Category 2
                index = 0;
                needToBeUnplayed = true;
                while(cat2Limit > 0) {
                    if (needToBeUnplayed) {
                        if(questions[cat2 * QUESTIONS_IN_CATEGORY + index] == 0) {
                            questions[cat2 * QUESTIONS_IN_CATEGORY + index] = 1;
                            cat2Limit--;
                        }
                    } else {
                        questions[cat2 * QUESTIONS_IN_CATEGORY + index]++;
                        collisions[experiment][games]++;

                        cat2Limit--;
                    }
                    index++;
                    if(index >= QUESTIONS_IN_CATEGORY) {
                        index = 0;
                        needToBeUnplayed = false;
                    }

                }
            //Opponents Move
                //Choosing categories

                int cat3 = 0;
                int cat4 = 0;
                do{
                    cat3 = rand.nextInt(TOTAL_CATEGORIES);
                }while(cat3 == cat1 || cat3 == cat2);

                do{
                    cat4 = rand.nextInt(TOTAL_CATEGORIES);
                }while(cat4 == cat1 || cat4 == cat2 || cat4 == cat3);

                //Choosing questions
                int question1 = rand.nextInt(QUESTIONS_IN_CATEGORY) + cat3 * QUESTIONS_IN_CATEGORY;
                int question2 = rand.nextInt(QUESTIONS_IN_CATEGORY) + cat3 * QUESTIONS_IN_CATEGORY;
                int question3 = rand.nextInt(QUESTIONS_IN_CATEGORY) + cat3 * QUESTIONS_IN_CATEGORY;
                int question4 = rand.nextInt(QUESTIONS_IN_CATEGORY) + cat4 * QUESTIONS_IN_CATEGORY;
                int question5 = rand.nextInt(QUESTIONS_IN_CATEGORY) + cat4 * QUESTIONS_IN_CATEGORY;
                int question6 = rand.nextInt(QUESTIONS_IN_CATEGORY) + cat4 * QUESTIONS_IN_CATEGORY;

                if (questions[question1] != 0) {
                    collisions[experiment][games]++;
                }
                if (questions[question2] != 0) {
                    collisions[experiment][games]++;
                }
                if (questions[question3] != 0) {
                    collisions[experiment][games]++;
                }
                if (questions[question4] != 0) {
                    collisions[experiment][games]++;
                }
                if (questions[question5] != 0) {
                    collisions[experiment][games]++;
                }
                if (questions[question6] != 0) {
                    collisions[experiment][games]++;
                }
                questions[question1]++;
                questions[question2]++;
                questions[question3]++;
                questions[question4]++;
                questions[question5]++;
                questions[question6]++;

            }



        }

        for(int g = 0; g < GAMES; g++) {
            double sum = 0;
            for(int e = 0; e < EXPERIMENTS; e++) {
                sum += collisions[e][g];
            }
            System.out.println("GAME: " + (g + 1) + " Collisions : " + sum / EXPERIMENTS);
            //System.out.println("GAME: " + (g + 1) + " Collisions : " + collisions[0][g]);
        }




    }
}
