package Crossword;



import java.io.*;
import java.util.*;

/**
 * Created by suren on 12/6/18.
 */
public class LevelWords {

    public static void main(String[] args) throws IOException {
        Calendar aa= new GregorianCalendar(2019,0, 3);

        Set<String> wordsSet = new TreeSet<>();
        int wordsCount = 0;

      //  LinkedList<String> words = usedWords("/home/suren/Documents/temp/crossword/levels_rus/5letters");
        LinkedList<String> words = usedWords("/home/suren/Documents/temp/crossword/levels_rus/levels_all_june");
        LinkedList<String> words3 = read4LetterWords("/home/suren/Documents/languages/English/3letter.txt");
        LinkedList<String> words4 = read4LetterWords("/home/suren/Documents/languages/English/4letter.txt");
        LinkedList<String> words5 = read4LetterWords("/home/suren/Documents/languages/English/5letter.txt");
        LinkedList<String> words6 = read4LetterWords("/home/suren/Documents/languages/English/6letter.txt");
        LinkedList<String> words7 = read4LetterWords("/home/suren/Documents/languages/English/7letter.txt");
        LinkedList<String> easy = read4LetterWords("/home/suren/Documents/languages/English/dictionary_easy.txt");
        LinkedList<String> medium = read4LetterWords("/home/suren/Documents/languages/English/dictionary_medium.txt");
        LinkedList<String> hard = read4LetterWords("/home/suren/Documents/languages/English/engAllRes.txt");

        LinkedList<String> allRussianSeeds = usedSeedWords("/home/suren/Documents/temp/crossword/levels_rus/levels_aug_20");
        Set<String> seeds = new TreeSet<>();
        for (String seed : allRussianSeeds) {
            if (seeds.contains(seed)) {
                System.out.println("Repeat " + seed);
            }else {
                System.out.println(seed);
                seeds.add(seed);
            }
        }
        System.out.println("Total seeds " + seeds.size());
//        System.out.println(words6.size());
////        for(String a : words3) {
////            System.out.println("|" + a + "|" + a.length());
////        }
//        System.out.println("--------------------------------------");
//        for (String a : words) {
////
//                       wordsCount++;
////          //  System.out.println(a);
//            if (wordsSet.contains(a)) {
//                //System.out.println("Collision " + a );
//            }else {
//                wordsSet.add(a);
//            }
//        }
//        ArrayList<String> newList = new ArrayList<>();
//
//
////        for (String a : hard) {
////            if (a.trim().length() == 7  && (!words7.contains(a))) {
////                System.out.println(a);
////            }else {
////                newList.add(a);
////            }
////        }
//
//        //writeToFile(newList, "/home/suren/Documents/languages/English/engAllRes.txt");
//
//        System.out.println("Total : " + wordsCount);
//        System.out.println("Uniuque : " + wordsSet.size());
    }



    private static ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                words.add(word.toLowerCase());
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }
    private static String readLevelSeedWords(String level) throws IOException {
        // System.out.println(level);
        BufferedReader dic1 = new BufferedReader(new FileReader(level));
        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            int boardSize = Integer.parseInt(line);

            //Reading crossword
            for(int i = 0; i < boardSize; i++) {
                line = dic1.readLine();
            }

            //Reading seed word
            line = dic1.readLine();
            return line;

        } finally {
            dic1.close();
        }
    }

    private static LinkedList<String> usedSeedWords(String root) {
        LinkedList<String> used = new LinkedList<>();
        File rootFolder = new File (root);
        for (File inner : rootFolder.listFiles()) {
            if (!inner.isDirectory()) {
                try {
                    if (!inner.getName().contains("~")) {
                        used.add(readLevelSeedWords(inner.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                used.addAll(usedSeedWords(inner.getAbsolutePath()));
            }
        }
        return used;
    }


    private static LinkedList<String> readLevelWords(String level) throws IOException {


        BufferedReader dic1 = new BufferedReader(new FileReader(level));

        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            int boardSize = Integer.parseInt(line);

            //Reading crossword
            for(int i = 0; i < boardSize; i++) {
                line = dic1.readLine();
            }

            //Reading seed word
            line = dic1.readLine();

            //Reading level words count
            line = dic1.readLine();
            int levelWordsCount = Integer.parseInt(line);

            for(int i = 0; i < levelWordsCount; i++) {

                line = dic1.readLine();
               // System.out.println(line);
                if (line.equals("орт")) {
                    System.out.println(level);
                }

                words.add(line);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static void writeToFile(ArrayList<String> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (String word : words) {
                output.write(word + "\n");
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
    private static LinkedList<String> read3LetterWords(String level) throws IOException {


        BufferedReader dic1 = new BufferedReader(new FileReader(level));

        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            while(line != null) {
                String[] w = line.split(" ");
                for (String s : w) {
                    words.add(s.trim());
                }
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
            return words;
        }
    }

    private static LinkedList<String> read4LetterWords(String level) throws IOException {


        BufferedReader dic1 = new BufferedReader(new FileReader(level));

        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            while(line != null) {
                String[] w = line.split(" ");
                for (String s : w) {
                    words.add(s.trim());
                }
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
            return words;
        }
    }


    private  static LinkedList<String> usedWords(String root) {
        LinkedList<String> used = new LinkedList<>();
        File rootFolder = new File (root);
        for (File inner : rootFolder.listFiles()) {
            if (!inner.isDirectory()) {
                try {
                    if (!inner.getName().contains("~")) {
                        used.addAll(readLevelWords(inner.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                used.addAll(usedWords(inner.getAbsolutePath()));
            }
        }
        return used;
    }
}