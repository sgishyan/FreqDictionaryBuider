package guessPicutre;



import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Removes all long words and replace "strange" letters
 * Created by suren on 12/9/15.
 */
public class LongLevelsWordsDetector {


    public static void main(String[] args) throws IOException {


                //Reading english levels
        String endLevels = "/home/suren/Documents/creative_soft_new_server/picturesNew/app/src/main/assets/levels/por";
        Set<String> wordsSet = new TreeSet<String>();
        int levelsNumber = 516;


        for (int i = 1; i <= levelsNumber; i++) {

            BufferedReader dic1 = null;
            try {

                dic1 = new BufferedReader(new FileReader(endLevels + "/" + i + ".txt"));
                String line = dic1.readLine();
                String word = dic1.readLine().trim();
                if (word.length() > 10 || word.length() < 3) {
                    System.out.println("ALERT !!! "  + i + " " + word);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dic1 != null) {
                    try {
                        dic1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }
}
