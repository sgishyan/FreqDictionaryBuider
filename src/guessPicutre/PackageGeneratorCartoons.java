package guessPicutre;

import mobiloids.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by suren on 5/30/17.
 */
public class PackageGeneratorCartoons {

    public static void main(String[] args) throws IOException {

        ArrayList<String> totalWords = readFile("/home/suren/Documents/temp/pictures/packs/cartoons_total.txt");
        ArrayList<String> armWords = readFile("/home/suren/Documents/temp/pictures/packs/cartoons_hindi.txt");

        ArrayList<Pair<String, String>> words1 = new ArrayList<>();
        ArrayList<Pair<String, String>> words2 = new ArrayList<>();
        int counter = 0;
        System.out.println(totalWords.size());
        System.out.println(armWords.size());
        for (int i = 0 ; i < totalWords.size(); i++) {
            if (armWords.get(i).length() > 1 && armWords.get(i).replaceAll("ու", "#").length() <= 10 ) {

                if (counter < 40) {
                    words1.add(new Pair<String,String> (totalWords.get(i), armWords.get(i).replaceAll("ու", "#")));
                } else {
                    words2.add(new Pair<String,String> (totalWords.get(i), armWords.get(i).replaceAll("ու", "#")));
                }
                counter++;
            }

        }
        System.out.println(words1.size());
        System.out.println(words2.size());
        Collections.shuffle(words1);
        Collections.shuffle(words2);
        writeToFilesRus(words1, "/home/suren/Documents/temp/pictures/packs/hindi_level_packs/cartoons_1");
        writeToFilesRus(words2, "/home/suren/Documents/temp/pictures/packs/hindi_level_packs/cartoons_2");
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
            dic1.close ();
            return words;
        }
    }
    private static void writeToFilesRus(ArrayList<Pair<String, String>> words, String dirName) {

        int index = 0;

        for (int i = 0; i < words.size(); i++) {

            if (words.get(i).getValue().length() > 1 && words.get(i).getValue().length() <= 10) {
                System.out.println(words.get(i).getValue());
                File file = new File(dirName + "/" + (index + 1) + ".txt");
                BufferedWriter output = null;
                try {
                    output = new BufferedWriter(new FileWriter(file));
                    output.write("cartoon_" + words.get(i).getKey() + ".jpg\n");
                    output.write(words.get(i).getValue() + "\n");
                    output.write(7 + "\n");
                    output.write(7 + "\n");
                    index++;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("########### " + words.get(i).getValue());
            }

        }
    }

}
