package guessPicutre;

import mobiloids.Pair;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by suren on 5/30/17.
 */
public class Mainenerator {

    public static void main(String[] args) throws IOException {


        DataInputStream dataInputStream = null;



        ArrayList<String> totalWords = readFile("/home/suren/Documents/temp/pictures/packs/main.txt");
        ArrayList<String> armWords = readFile("/home/suren/Documents/temp/pictures/packs/main_hindi.txt");

        ArrayList<Pair<String, String>> words = new ArrayList<>();
        for (int i = 0 ; i < totalWords.size(); i++) {
            words.add(new Pair<String,String> (totalWords.get(i), armWords.get(i).replaceAll("ու", "#")));
        }
      //  Collections.shuffle(words);

        writeToFilesRus(words, "/home/suren/Documents/temp/pictures/packs/hindi_level_pack/main");
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

        //Start level
        int index = 0;


        for (int i = 0; i < words.size(); i++) {

            if (words.get(i).getValue().length() > 1 && words.get(i).getValue().length() <= 10 && !words.get(i).getValue().contains(" ") && !words.get(i).getValue().contains("-")) {

                boolean isOK = true;
                for(int j = 0; j < words.get(i).getValue().length(); j++) {
                    if (words.get(i).getValue().charAt(j) >= '\u0958' && words.get(i).getValue().charAt(j) <= '\u095F' || words.get(i).getValue().charAt(j) == '\u0911'  ) {
                        System.out.println("Bad char.... " + words.get(i).getValue().charAt(j) + " " + words.get(i).getKey());
                        isOK = false;
                        break;

                    }
                }
                if (!isOK){
                    continue;
                }


                //System.out.println(words.get(i).getKey() + " " +words.get(i).getValue());
                File file = new File(dirName + "/" + (index + 1) + ".txt");
                BufferedWriter output = null;
                try {
                    output = new BufferedWriter(new FileWriter(file));
                    output.write(words.get(i).getKey() + ".jpg\n");
                    output.write(words.get(i).getValue() + "\n");
                    output.write(7 + "\n");
                    output.write(6 + "\n");
                    index++;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (words.get(i).getValue().length() > 10)
                System.out.println("Too long " + words.get(i).getValue().length() + " " +words.get(i).getValue());
            }

        }
    }

}
