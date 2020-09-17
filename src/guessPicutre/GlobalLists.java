package guessPicutre;

import mobiloids.Pair;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by suren on 4/6/17.
 */
public class GlobalLists {

    public static void main(String[] args) throws IOException {


        ArrayList<String> totalList;
        ArrayList<Pair<String,String>> foreignList;
        ArrayList<String> google;

        ArrayList<String> acceptedWords = new ArrayList<String>();

        final String totalWords = "/home/suren/Documents/temp/pictures/packs/temp_geo.txt";
        //final String englishLevelsDir = "/home/suren/Documents/temp/pictures/levels/eng/final";
        final String russianLevelsDir = "/home/suren/Documents/creative_soft_new_server/picturesArmenian/app/src/main/assets/levels";

        totalList = readFile(totalWords);
        foreignList = readFolder(russianLevelsDir);
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");


//        int index = 0;
//        String prefix = "";
//        for (String word : totalList) {
//
//            if (index < 49) {
//                prefix = "animals_";
//            }
//            if (index >= 49 && index < 88) {
//                prefix = "sports_";
//            }
//            if (index >= 88 && index < 152) {
//                prefix = "flag_";
//            }
//            if (index >= 152 && index < 202) {
//                prefix = "retro_";
//            }
//            if (index >= 202 ) {
//                prefix = "prof_";
//            }
//            index++;
//            System.out.println("=IMAGE(\"http://mobiloids.com/guessthepicture/images/" + prefix + word.trim().toLowerCase() + ".jpg\")");
//        }

        for (String word : totalList) {

            System.out.println("=IMAGE(\"http://mobiloids.com/guessthepicture/images/" + "cartoon_" + word.trim().toLowerCase() + ".jpg\")");
      }


//        outer_loop:
//        for (String word : totalList) {
//            for( mobiloids.Pair<String, String> pair : foreignList ) {
//                if (pair.getKey().equals(word)) {
//                    System.out.println(pair.getValue());
//                    continue outer_loop;
//                }
//            }
//            System.out.println("-");
//        }
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println("-----------------------------------------------------------------");
//        outer_loop2:
//        for( mobiloids.Pair<String, String> pair : foreignList ) {
//            for (String word : totalList) {
//                if (pair.getKey().equals(word)) {
//                    continue outer_loop2;
//                }
//            }
//            System.out.println(pair.getKey());
//
//        }

    }

    private static ArrayList<Pair<String,String>> readFolder(String englishLevelsDir) throws IOException {
        File folder = new File(englishLevelsDir);
        ArrayList<Pair<String,String>> words = new ArrayList<Pair<String,String>>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory())
                continue;
            BufferedReader dic1 = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
            try {

                String line = dic1.readLine().trim().toLowerCase();
                String word = line.substring(0, line.length() - 4);
                String translation = dic1.readLine().trim().toLowerCase();
                words.add(new Pair<String, String>(word, translation));
               // System.out.println(line);

            } finally {
                dic1.close();
            }

        }
        System.out.println("Words in folder : " + words.size());
        return words;
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

}
