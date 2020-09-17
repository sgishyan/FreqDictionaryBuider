package rus;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/29/15.
 */
public class LongWords {
    public static void main(String[] args) throws IOException {



        String cleaned = "/home/suren/Documents/temp/rus/rus_shortlist.txt";


        ArrayList<Word> cleanedWords= new ArrayList<Word>();





        //Reading old dic
        //
        BufferedReader dic1 = new BufferedReader(new FileReader(cleaned));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                if (word.length() == 8) {
                    System.out.println(word);
                    System.out.println(word);
                }line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }


    }

}
