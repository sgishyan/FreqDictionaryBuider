package guesstheword;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by suren on 9/29/17.
 */
public class LevelGenerator {
    public static void main(String[] args) throws IOException {
        String inputPath = "/home/suren/Documents/temp/GuessTheWord/georgian_words.txt";
        String outputPath = "/home/suren/Documents/temp/GuessTheWord/georgian_levels";
        int startLevel = 1;
        ArrayList<Question> questions = new ArrayList<Question>();



        //Reading word list file
        int totalLevels = 0;

        BufferedReader dic1 = new BufferedReader(new FileReader(inputPath));
        try {

            String line = dic1.readLine();
            while (line != null) {
                //System.out.println(line);
                String word = line.trim();
                String[] tokens = word.split("\t");
              //  System.out.println(tokens.length);

                //Making Filename;
                String english = tokens[0].trim().toLowerCase();
                String pic1 = tokens[1].trim().toLowerCase();
                String pic2 = tokens[2].trim().toLowerCase();
                String pic3 = tokens[3].trim().toLowerCase();
                String pic4 = tokens[4].trim().toLowerCase();
                String translated = tokens[5].trim().toLowerCase();
                translated = translated.replaceAll("ու", "#");
                line = dic1.readLine();
                if  (translated.length() > 10 || translated.length() < 2) {
                    System.out.println("Bad level: " + english + " " + translated);
                    continue;
                }

                int sum = Integer.parseInt(pic1) + Integer.parseInt(pic2) + Integer.parseInt(pic3) + Integer.parseInt(pic4);
                if  (sum != 10) {
                    System.out.println("Bad Numbers: " + english + " " + sum);
                    continue;
                }
                totalLevels++;
                questions.add(new Question(english, translated, pic1, pic2, pic3, pic4));

            }

        } finally {
            dic1.close();
        }

       // Collections.shuffle(questions);

        for (int i = 0; i < questions.size(); i++) {
            File file = new File(outputPath + "/" + (i + startLevel) + ".txt");
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(file));
                questions.get(i).writeToFile(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Question {
        String englishWord;
        String translatedWord;
        String pic1;
        String pic2;
        String pic3;
        String pic4;

        public Question(String englishWord, String translatedWord, String pic1, String pic2, String pic3, String pic4) {
            this.englishWord = englishWord;
            this.translatedWord = translatedWord;
            this.pic1 = pic1;
            this.pic2 = pic2;
            this.pic3 = pic3;
            this.pic4 = pic4;
        }

        public void writeToFile(BufferedWriter writer) {
            try {
                writer.write(translatedWord + "\n");
                writer.write(englishWord + pic1 + ".jpg\n");
                writer.write(englishWord + pic2 + ".jpg\n");
                writer.write(englishWord + pic3 + ".jpg\n");
                writer.write(englishWord + pic4 + ".jpg\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
