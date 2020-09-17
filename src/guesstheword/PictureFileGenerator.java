package guesstheword;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by suren on 11/14/17.
 */
public class PictureFileGenerator {
    public static void main(String[] args) throws IOException {

        String path = "/home/suren/Documents/temp/GuessTheWord/all_levels.txt";
        BufferedReader dic1 = new BufferedReader(new FileReader(path));
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();

                System.out.println(   "=IMAGE(\"http://mobiloids.com/4photos/" + word + "4.jpg\")");
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
        }
    }
}
