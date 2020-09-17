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
public class ArmenianGenerator {



        public static void main(String[] args) throws IOException {



            //temp
//            generateRussianLevelsFromEnglish();
//            if(1 == 1){
//                return;
//            }

            String words = "/home/suren/Documents/temp/pictures/multilanguage/armenianGeneration.txt";


            ArrayList<Word> pictures = new ArrayList<Word>();
            ArrayList<Word> picturesBad = new ArrayList<Word>();


            //Reading word list file
            int totalLevels = 0;

            BufferedReader dic1 = new BufferedReader(new FileReader(words));
            try {

                String line = dic1.readLine();

                while (line != null) {
                    System.out.println(line);
                    String word = line.trim();
                    String[] tokens = word.split("\t");
                   // System.out.println(tokens.length);

                    //Making Filename;
                    String filename = tokens[0].trim();
                    filename = filename.toLowerCase();
                   //System.out.println((totalLevels + 1) + ") " + filename);
                   // totalLevels++;
                    String englishWord ="";


                    if(filename.endsWith("#")) {

                        englishWord = filename.substring(0, filename.length() - 1);
                        filename = filename.substring(0, filename.length() - 1) + "_second.jpg";

                    } else {

                        if(!filename.endsWith(".jpg")){
                            filename = filename + ".jpg";

                        }



                        //Making English word
                        englishWord = filename.substring(0, filename.length() - 4);


                    }


                    //Making Russian word
                    String russianWord = tokens[1].toLowerCase().trim();

                    //Getting difficulty
                    int difficulty = Integer.parseInt(tokens[2].trim());

                    //Getting category
                    String category =  tokens[3].toLowerCase().trim();

                    //Getting nowOrLater
                    int nowOrLater = Integer.parseInt(tokens[4].trim());

                    /*/
                        ONLY FOR RUSSIAN VERSION

                     */
//                    if(russianWord.length() > 10) {
//                        System.out.println(russianWord);
//                        line = dic1.readLine();
//                        continue;
//                    }


                    ///////////////////////////////

                    Word picture = new Word(filename, englishWord, russianWord, difficulty, nowOrLater, category);

                    if(nowOrLater != 2 && russianWord.trim().length() > 1 ) {
                        pictures.add(picture);
                    } else {

                        picturesBad.add(picture);
                    }

                    line = dic1.readLine();
                    totalLevels++;

                }

            } finally {
                dic1.close();
            }

            Collections.shuffle(pictures);


            System.out.println("TOTAL : " + pictures.size());
            for(Word w : picturesBad) {
                pictures.add(w);
            }


            int index = 555;
            for (Word picture : pictures) {
                System.out.println(index + ") " + picture.englishWord + "     " + picture.difficulty);
                index++;
            }


          //  writeToFilesRus(pictures, "/home/suren/Documents/temp/pictures/levels/rus" );
            writeToFiles(pictures, "/home/suren/Documents/temp/pictures/levels/arm");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------");
            //Printing words
            //  Collections.sort(englishWords);
        for(Word ew : pictures) {
            System.out.println(ew.englishWord + "\t" + "=IMAGE(\"http://mobiloids.com/guessthepicture/images/" + ew.englishWord + ".jpg\")");
        }
//
//        if(1 == 1) {
//            return;
//        }


        }



    private static void writeToFiles(ArrayList<Word> words, String dirName) {

        int startLevel = 554;
        for(int i = 0; i < words.size(); i++) {


            File file = new File(dirName +"/" + (i + 1 + startLevel) + ".txt");
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(file));
                output.write(words.get(i).filename + "\n");
                output.write(words.get(i).russianWord + "\n");
                if(startLevel + i + 1 <= 30) {
                    output.write(5 + "\n");
                    output.write(5 + "\n");
                }else
                if(startLevel + i + 1 > 30  && startLevel + i + 1 <= 100 ) {
                    output.write(6 + "\n");
                    output.write(6 + "\n");
                }else
                if(startLevel + i + 1 > 100  && startLevel + i + 1 <= 200 ) {
                    output.write(7 + "\n");
                    output.write(7 + "\n");
                }else
                if(startLevel + i + 1 > 200 &&   startLevel + i + 1 <=450) {
                    output.write(7 + "\n");
                    output.write(6 + "\n");
                }else
                if(startLevel + i + 1 > 450   ) {
                    output.write(7 + "\n");
                    output.write(5 + "\n");
                }



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

    private static class Word implements Comparable<Word> {
        private String filename;
        private String englishWord;
        private String russianWord;
        private int difficulty;
        private int soonOrLate;
        private String category;

        public Word(String filename, String englishWord, String russianWord, int difficulty, int soonOrLate, String category) {
            this.filename = filename;
            this.englishWord = englishWord;
            this.russianWord = russianWord;
            this.difficulty = difficulty;
            this.soonOrLate = soonOrLate;
            this.category = category;


        }

        @Override
        public String toString() {
            return filename + " " + englishWord + " " + russianWord + " " + difficulty;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getEnglishWord() {
            return englishWord;
        }

        public void setEnglishWord(String englishWord) {
            this.englishWord = englishWord;
        }

        public String getRussianWord() {
            return russianWord;
        }

        public void setRussianWord(String russianWord) {
            this.russianWord = russianWord;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public int getSoonOrLate() {
            return soonOrLate;
        }

        public void setSoonOrLate(int soonOrLate) {
            this.soonOrLate = soonOrLate;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public int compareTo(Word w) {
            if (difficulty != w.difficulty) {
                return difficulty - w.difficulty;
            }
            return -(soonOrLate - w.soonOrLate);

        }
    }

}

