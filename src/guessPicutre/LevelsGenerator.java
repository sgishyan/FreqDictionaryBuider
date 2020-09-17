package guessPicutre;



import wordwars.CollectLetters;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Removes all long words and replace "strange" letters
 * Created by suren on 12/9/15.
 */
public class LevelsGenerator {



        public static void main(String[] args) throws IOException {



            //temp
//            generateRussianLevelsFromEnglish();
//            if(1 == 1){
//                return;
//            }

            String words = "/home/suren/Documents/temp/pictures/wordsList.txt";


            ArrayList<Word> pictures = new ArrayList<Word>();

            ArrayList<Word> warmUpPictures = new ArrayList<Word>();
            ArrayList<Word> beginnerPictures = new ArrayList<Word>();
            ArrayList<Word> easyPictures = new ArrayList<Word>();
            ArrayList<Word> mediumPictures = new ArrayList<Word>();
            ArrayList<Word> hardPictures = new ArrayList<Word>();
            Word finalPicture = null;
            //Reading Level files

             String endLevels = "/home/suren/Documents/temp/pictures/levels/eng/final";
            Set<String > levelsWords = new TreeSet<String>();
            int levelsNumber = 600;




            for(int i = 1; i <= levelsNumber; i++) {

                BufferedReader dic1 = null;
                try {

                    dic1 = new BufferedReader(new FileReader(endLevels + "/" + i + ".txt"));
                    String line = dic1.readLine();
                    String word = dic1.readLine().trim();
                    if (levelsWords.add(word) !=true) {
                        System.out.println("ALERT !!! " + word);
                    }
                    //System.out.println(word);


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




            //Reading word list file
            int totalLevels = 0;

            BufferedReader dic1 = new BufferedReader(new FileReader(words));
            try {

                String line = dic1.readLine();

                while (line != null) {

                    String word = line.trim();
                    String[] tokens = word.split("\t");
                    //System.out.println(tokens.length);

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

                    //Checking if this level already exist
                    if(levelsWords.contains(englishWord) && !filename.contains("_")) {
                        line = dic1.readLine();
                        continue;
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

                    switch(picture.getDifficulty()) {
                        case -1:
                            warmUpPictures.add(picture);
                            break;
                        case 0:
                            beginnerPictures.add(picture);
                            break;
                        case 1:
                            easyPictures.add(picture);
                            break;
                        case 2:
                            mediumPictures.add(picture);
                            break;
                        case 3:
                            hardPictures.add(picture);
                            break;
                        case 4:
                            finalPicture = picture;
                    }

                    line = dic1.readLine();
                    totalLevels++;

                }

            } finally {
                dic1.close();
            }

            Collections.shuffle(warmUpPictures);
            Collections.shuffle(beginnerPictures);
            Collections.shuffle(easyPictures);
            Collections.shuffle(mediumPictures);
            Collections.shuffle(hardPictures);

            Collections.sort(warmUpPictures);
            Collections.sort(beginnerPictures);
            Collections.sort(easyPictures);
            Collections.sort(mediumPictures);
            Collections.sort(hardPictures);


//
//            System.out.println(warmUpPictures.size());
//            System.out.println(easyPictures.size());
//            System.out.println(mediumPictures.size());
//            System.out.println(hardPictures.size());

            for(Word word : warmUpPictures) {
                pictures.add(word);
                totalLevels--;
            }

            for(Word word : beginnerPictures) {
                pictures.add(word);
                totalLevels--;
            }

            while(totalLevels > 1) {
                ArrayList<Word> bucket = new ArrayList<Word>();

                if(hardPictures.size() > 0) {
                    bucket.add(hardPictures.get(0));
                    hardPictures.remove(0);
                    totalLevels--;
                }

                for(int i = 0; i < 3; i++) {
                    if(mediumPictures.size() > 0) {
                        bucket.add(mediumPictures.get(0));
                        mediumPictures.remove(0);
                        totalLevels--;
                    }
                }

                for(int i = 0; i < 4; i++) {
                    if(easyPictures.size() > 0) {
                        bucket.add(easyPictures.get(0));
                        easyPictures.remove(0);
                        totalLevels--;
                    }
                }


                Collections.shuffle(bucket);
                for (Word word : bucket) {
                    pictures.add(word);
                }
                //System.out.println(totalLevels);
            }
           // pictures.add(finalPicture);

            System.out.println("TOTAL : " + pictures.size());

            int index = 01;
            for (Word picture : pictures) {
                System.out.println(index + ") " + picture.englishWord + "     " + picture.difficulty);
                index++;
            }


          //  writeToFilesRus(pictures, "/home/suren/Documents/temp/pictures/levels/rus" );
            writeToFilesEng(pictures, "/home/suren/Documents/temp/pictures/levels/eng");
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

        private static void writeToFilesRus(ArrayList<Word> words, String dirName) {

            int index = 0;
            int startLevel = 500;
            for (int i = 0; i < words.size(); i++) {

                if (words.get(i).russianWord.length() > 10) {
                    continue;
                }
                File file = new File(dirName + "/" + (startLevel + index + 1) + ".txt");
                BufferedWriter output = null;
                try {
                    output = new BufferedWriter(new FileWriter(file));
                    output.write(words.get(i).englishWord + ".jpg\n");
                    output.write(words.get(i).russianWord + "\n");
                    if (startLevel + i + 1 <= 30) {
                        output.write(5 + "\n");
                        output.write(5 + "\n");
                    }else
                    if (startLevel + i + 1 > 30 && startLevel + i + 1 <= 100) {
                        output.write(6 + "\n");
                        output.write(6 + "\n");
                    }else
                    if (startLevel + i + 1 > 100 && startLevel + i + 1 <= 200) {
                        output.write(7 + "\n");
                        output.write(7 + "\n");
                    }else
                    if(startLevel + i + 1 > 200 &&   startLevel + i + 1 <=400) {
                        output.write(7 + "\n");
                        output.write(6 + "\n");
                    }else
                    if(startLevel + i + 1 > 400   ) {
                        output.write(7 + "\n");
                        output.write(5 + "\n");
                    }
                    index++;


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


    private static void generateRussianLevelsFromEnglish() {


        ArrayList<String> englishWords = new ArrayList<String>();
        ArrayList<String> russianWords = new ArrayList<String>();
        ArrayList<Word> newLevels = new ArrayList<Word>();


        //Reading english levels
        String endLevels = "/home/suren/Documents/temp/pictures/levels/eng/final";
        Set<String > wordsSet = new TreeSet<String>();
        int levelsNumber = 680;



        for(int i = 1; i <= levelsNumber; i++) {

            BufferedReader dic1 = null;
            try {

                dic1 = new BufferedReader(new FileReader(endLevels + "/" + i + ".txt"));
                String line = dic1.readLine();
                String word = dic1.readLine().trim();
                if (wordsSet.add(word) !=true) {
                    System.out.println("ALERT !!! " + word);
                }

                englishWords.add(word);
                //System.out.println(word);


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


        //Printing words
      //  Collections.sort(englishWords);
//        for(String ew : englishWords) {
//            System.out.println("=IMAGE(\"http://mobiloids.com/guessthepicture/images/" + ew + ".jpg\")");
//        }
//
//        if(1 == 1) {
//            return;
//        }
        //Reading russian levels
        endLevels = "/home/suren/Documents/temp/pictures/levels/arm/final";
        wordsSet = new TreeSet<String>();
        int levelsNumberRus = 500;


        Set<String> englishWordsSet = new TreeSet<String>();

        for(int i = 1; i <= levelsNumberRus; i++) {

            BufferedReader dic1 = null;
            try {

                dic1 = new BufferedReader(new FileReader(endLevels + "/" + i + ".txt"));
                String line = dic1.readLine().trim();
                englishWordsSet.add(line.substring(0, line.length() - 4));
                String word = dic1.readLine().trim();
                //System.out.println(line);
                if (wordsSet.add(word) !=true) {
                    System.out.println("!!! ALERT !!! " + word);
                }
               // System.out.println(word);
                russianWords.add(word);

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

        System.out.println("------------------------  Missing in translation -----------------------");
//        for(String engStr : englishWords) {
//            if (!englishWordsSet.contains(engStr)) {
//                System.out.println(engStr);
//            }
//        }

        System.out.println("------------------------------------------------------------------------");

        System.out.println("Russian words count " + russianWords.size());
        //Read wordlist
        ArrayList<Word> pictures = new ArrayList<Word>();
        String words = "/home/suren/Documents/temp/pictures/multilanguage/armenianWordListFinal.txt";
        BufferedReader dic1 = null;
        try {
            dic1 = new BufferedReader(new FileReader(words));
            String line = dic1.readLine();

            while (line != null) {

                String word = line.trim();
                String[] tokens = word.split("\t");
//                System.out.println(word);
//                System.out.println(tokens.length);
//

                //Making Filename;
                String filename = tokens[0].trim();
                filename = filename.toLowerCase();
                if(!filename.endsWith(".jpg")){
                    filename = filename + ".jpg";

                }

                //Making English word
                String englishWord = filename.substring(0, filename.length() - 4);


                //Making Russian word
                String russianWord = tokens[1].toLowerCase().trim();

                //Getting difficulty
                String difficulty = tokens[2].trim();

                //Getting category
                String category =  tokens[3].toLowerCase().trim();

                //Getting nowOrLater
                String nowOrLater = tokens[4].trim();

                    /*/
                        ONLY FOR RUSSIAN VERSION

                     */
//                    if(russianWord.length() > 10) {
//                        System.out.println(russianWord);
//                        line = dic1.readLine();
//                        continue;
//                    }


                ///////////////////////////////

                Word picture = new Word(filename, englishWord, russianWord, 0, 0, category);
                pictures.add(picture);


                line = dic1.readLine();
                }




            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("Armenian Words Count " + pictures.size() );

        //Generating russian files
        int startLevel = 0;
        //Passing through all levels
        for(int i = 0; i < englishWords.size(); i++) {
            //Getting english word;
            String engWord = englishWords.get(i);
            String rusWord = "";

            boolean foundRusWord = false;
            //Finding russian word
            for(Word word : pictures) {
                if(word.englishWord.equals(engWord)) {
                    if(word.russianWord.length() <= 10 && !word.russianWord.equals("-")) {
                        rusWord = word.russianWord;
                        foundRusWord = true;

                        ///Temp
                        pictures.remove(word);




                        break;
                    }
                }
            }

            if(!foundRusWord) {
                continue;
            }
            //System.out.println(engWord + " " + rusWord);

            //Checking if this words is already in levels
            boolean isUsed = false;
            for( int j = 0; j < russianWords.size(); j++) {
                if (russianWords.get(j).equals(rusWord)) {
                    isUsed = true;
                    System.out.println("Word is used " + russianWords.get(j));
                    break;
                }
            }

            if(isUsed) {
                continue;
            }
            boolean normalWord = true;
            for(int l = 0; l < rusWord.length(); l++) {
                if(!Character.isLetter(rusWord.charAt(l)) && rusWord.charAt(l) != '#') {
                    normalWord = false;
                    System.out.println("ERROR WORD " + engWord + " | " + rusWord);
                    break;
                }
            }
            if(normalWord) {
                newLevels.add(new Word("", engWord, rusWord, 0, 0, ""));
                System.out.println((startLevel++) + ") " + rusWord + " " + engWord + " " + i);
            }
            // NEW WORD found


        }
        System.out.println("Lost Words");

        for(Word word : pictures) {
            System.out.println(word.englishWord + " ");
        }
        for(Word ew : newLevels) {
            System.out.println(ew.englishWord + "\t" + ew.russianWord + "\t" + "=IMAGE(\"http://mobiloids.com/guessthepicture/images/" + ew.englishWord + ".jpg\")");
        }
        writeToFilesRus(newLevels, "/home/suren/Documents/temp/pictures/levels/arm");
    }

    private static void writeToFilesEng(ArrayList<Word> words, String dirName) {

        int startLevel = 500;
        for(int i = 0; i < words.size(); i++) {


            File file = new File(dirName +"/" + (i + 1 + startLevel) + ".txt");
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(file));
                output.write(words.get(i).filename + "\n");
                output.write(words.get(i).englishWord + "\n");
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

