import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Created by suren on 1/14/16.
 */
public class LetterDistribution {
    public static void main(String[] args) throws IOException {

        prob();

/*

        String oldDic =  "/home/suren/Documents/temp/eng/wordwars/eng_words.txt";
        int[] lettersOccurrence = new int['z' - 'a' + 1];


        char[] glasn = {'a', 'e', 'i', 'o', 'u'};



        int gl = 0;
        int sogl = 0;
        //Reading  dic
        //
        BufferedReader dic1 = new BufferedReader(new FileReader(oldDic));
        try {

            String line = dic1.readLine();
            int coef = 1;
            while (line != null) {
                String word = line.trim();
//                if (word.length()>=2 && word.length()<=3 && word.contains("э")) {
//                    System.out.println(word);
//                }
               System.out.println(word);
                switch (word.length()) {
                    case 2:
                        coef = 15;
                        break;
                    case 3:
                        coef = 10;
                        break;
                    case 4:
                        coef = 5;
                        break;
                    case 5:
                        coef = 1;
                        break;
                    default :
                        coef = 0;


                }
                coef = 1;
                //String description = dic1.readLine().trim();
                word:
                for (int i = 0; i < word.length(); i++) {
                    lettersOccurrence[word.charAt(i) - 'a'] += coef;
                   for(char g : glasn){
                       if (g == word.charAt(i)) {
                           gl++;
                           continue word;
                       }
                   }
                    sogl++;
//                    if (word.length()<= 3 && word.charAt(i) == 'ш') {
//                        System.out.println(word);
//                        break;
//                    }
                }
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }

        int totalLetters = 0;
        for (int i = 0; i < lettersOccurrence.length; i++ ) {

            totalLetters += lettersOccurrence[i];
        }
        System.out.println("Total Letters = " + totalLetters);
        System.out.println("Glas = " + gl);
        System.out.println("Soglas = " + sogl);
        //Arrays.sort(lettersOccurrence);
        for (int i = 0; i < lettersOccurrence.length; i++ ) {
            NumberFormat formatter = new DecimalFormat("#0");
            double percent = 100 * lettersOccurrence[i] / (double)totalLetters;
            System.out.println((char)('a' + i) + "   " + formatter.format(lettersOccurrence[i] /220.0 ) + "   Percent:" + percent);
        }
*/

    }

    private static void prob() {
        final int games_count = 100;
        int[] questions = new int [2800];
        int EXPERIMENTS = 10000;
        int[][] overlaps = new int [games_count][EXPERIMENTS];

        Random rand = new Random();
        for (int exp = 0; exp < EXPERIMENTS;exp++) {
            for(int e = 0; e <  2800; e++) {
                questions[e] = 0;
            }
                for (int game = 0; game < games_count; game++) {
//
//                for (int questionCategory = 0; questionCategory < 7; questionCategory++) {
//                    for (int questionsPerCategory = 0; questionsPerCategory < 1; questionsPerCategory++) {
//                        int questionIndex = rand.nextInt(questionCategory * 400 + 400);
//                        if (questions[questionIndex] != 0) {
//                            overlaps[game][exp]++;
//                        }
//                        questions[questionIndex]++;
//                    }
//                }
//            }
            for(int questionCategory = 0; questionCategory < 12; questionCategory++) {

                    int questionIndex = rand.nextInt( 2800);
                    if (questions[questionIndex] != 0) {
                        overlaps[game][exp]++;
                    }
                    questions[questionIndex]++;

                }
            }
        }


//        for(int i=0; i < games_count; i++) {
//            double s = 0;
//
//            for (int exp = 0; exp < EXPERIMENTS;exp++) {
//                s+= overlaps[i][exp];
//            }
//            System.out.println("GAME " + i + " overlaps: " + overlaps[i][0] );
//        }
        for(int i=0; i < 100; i++) {
            double s = 0;

            for (int exp = 0; exp < EXPERIMENTS;exp++) {
                s+= overlaps[i][exp];
            }
          // System.out.println("GAME " + (i+1) + " overlaps: " + s / EXPERIMENTS );
            System.out.println( s / EXPERIMENTS );
        }
    }


}
