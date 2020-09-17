package dictionary;

import mobiloids.Pair;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by suren on 10/12/18.
 */
public class RussianSplitter {
    static ArrayList<String> capitalWords = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        String path1 = "/home/suren/Documents/languages/Russian/russianWM.txt";
        String pathOut = "/home/suren/Documents/languages/Russian/russian_wm_cleared.txt";
        ArrayList<String> words1 = readFileSplitLines(path1);

        System.out.println(words1.size());
        for(String w : words1) {
            System.out.println(w);
        }



//        for (String word : words3) {
//            if (!allWords.contains(word) && !capitalWords.contains(word)) {
//                //System.out.println(word);
//                allWords.add(word);
//            }
//        }
//
         writeToFile(words1, pathOut);
//        writeToFile(easyWords, pathOutEasy);

    }

    private static ArrayList<String> readFileSplit(String path1) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(path1));
        ArrayList<String> words = new ArrayList<>();
        try {

            String lineRus= dic1.readLine().trim();
            String[] tokens = lineRus.split(" ");
            while (lineRus != null) {

                if (tokens[3].equals("noun") && tokens[2].length() >=3 && tokens[2].length()<9 ) {
                    words.add(tokens[2].toLowerCase());
                }

                lineRus = dic1.readLine();
                tokens = lineRus.split(" ");
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static ArrayList<String> readFileSplitLines(String path1) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(path1));
        ArrayList<String> words = new ArrayList<>();
        try {

            String lineRus= dic1.readLine().trim();
            dic1.readLine();
            while (lineRus != null) {

                if (lineRus.length() >=3 && lineRus.length()<9 ) {
                    words.add(lineRus.toLowerCase());
                }

                lineRus = dic1.readLine();
                dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }
    private static ArrayList<String> readFileFull(String spanishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(spanishFile));
        ArrayList<String> words = new ArrayList<>();
        try {

            String lineSpa = dic1.readLine().trim();
            while (lineSpa != null) {
                lineSpa = lineSpa.trim();
                if (lineSpa.startsWith("el ") || lineSpa.startsWith("la ")) {
                    lineSpa = lineSpa.substring(3);
                }
                if (!lineSpa.contains(" ") && lineSpa.length() <= 8 && lineSpa.length() > 2) {

                    int indexN = lineSpa.indexOf('ñ');

                    String temp = Normalizer.normalize(lineSpa, Normalizer.Form.NFD);
                    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                    lineSpa = pattern.matcher(temp).replaceAll("");
                    if (indexN != -1) {
                        StringBuilder ls = new StringBuilder(lineSpa);
                        ls.setCharAt(indexN, 'ñ');
                        lineSpa = ls.toString();
                    }

                    if(Character.isUpperCase(lineSpa.charAt(0))) {
                        System.out.println(lineSpa);
                        capitalWords.add(lineSpa);
                        capitalWords.add(lineSpa.toLowerCase());
                    }

                    if (!Character.isUpperCase(lineSpa.charAt(0)) && !words.contains(lineSpa)) {
                        words.add(lineSpa);
                    }

                }
                lineSpa = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static ArrayList<Pair<String, String>> readFile(String spanishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(spanishFile));
        ArrayList<Pair<String, String>> words = new ArrayList<>();
        try {

            String lineSpa = dic1.readLine();
            while (lineSpa != null) {
                String lineEng =  dic1.readLine().trim();
                lineSpa = lineSpa.trim();
                if (lineSpa.startsWith("el ") || lineSpa.startsWith("la ")) {
                    lineSpa = lineSpa.substring(3);
                }
                if (!lineSpa.contains(" ") && lineSpa.length() <= 8) {
                    words.add(new Pair<String, String>(lineSpa, lineEng));
                }
                lineSpa = dic1.readLine();
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
}
