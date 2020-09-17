package eng;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by suren on 1/14/16.
 */
public class LetterDistributionEng {
    public static void main(String[] args) throws IOException {


        String oldDic = "/home/suren/Documents/temp/eng/wordwars/eng_words.txt";
        int[] lettersOccurrence = new int['z' - 'a' + 1];


        char[] vowels = {'a', 'e', 'i', 'o', 'u'};



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
                    boolean isVowel = false;
                    lettersOccurrence[word.charAt(i) - 'a'] += coef;
                   for(char g : vowels){
                       if (g == word.charAt(i)) {
                           gl++;
                           isVowel = true;
                           break;
                       }
                   }
                    if (!isVowel) {
                        sogl++;
                    }

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
            System.out.println((char)('a' + i) + "   " + formatter.format(lettersOccurrence[i]  ) + "   Percent:" + percent);
        }


    }


}
