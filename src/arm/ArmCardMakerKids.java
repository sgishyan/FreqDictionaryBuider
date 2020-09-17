package arm;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 10/5/15.
 */
public class ArmCardMakerKids {

    public static void main(String [] args) throws IOException {


    String heshtPath = "/home/suren/Documents/temp/arm/heshtKids.txt";
    String mijinPath = "/home/suren/Documents/temp/arm/mijinKids.txt";
    String bardPath = "/home/suren/Documents/temp/arm/bardKids.txt";


    String filteredNounsBySize = "/home/suren/Documents/temp/Testing/barerUnique.txt";

    ArrayList<String> hesht = new ArrayList<String>();
    ArrayList<String> mijin = new ArrayList<String>();
    ArrayList<String> bard = new ArrayList<String>();


    Set<String> repeatedWords = new TreeSet<String>();
    BufferedReader dic1 = null;




        dic1 = new BufferedReader(new FileReader(heshtPath));
        String line = dic1.readLine();
        while (line != null) {

            line = line.trim();
            hesht.add(line);
            line = dic1.readLine();
        }
        dic1.close();

        dic1 = new BufferedReader(new FileReader(mijinPath));
        line = dic1.readLine();
        while (line != null) {

            line = line.trim();
            mijin.add(line);
            line = dic1.readLine();
        }
        dic1.close();

        dic1 = new BufferedReader(new FileReader(bardPath));
        line = dic1.readLine();
        while (line != null) {

            line = line.trim();
            bard.add(line);
            line = dic1.readLine();
        }
        dic1.close();




        Collections.shuffle(hesht);
        Collections.shuffle(mijin);
        Collections.shuffle(bard);


        for (int i = 0; i < 200; i++) {
            System.out.println("Card #" + (i + 1));
            if(2 * i < hesht.size()) {
                System.out.println(hesht.get(2 * i));
            } else {
                System.out.println("--- Hesht bar ---");
            }

            if(2 * i + 1 < hesht.size()) {
                System.out.println(hesht.get(2 * i + 1));
            } else {
                System.out.println("--- Hesht bar ---");
            }

            if(i < mijin.size()) {
                System.out.println(mijin.get(i));
            } else {
                System.out.println("--- Mijin bar ---");
            }

            if(i < bard.size()) {
                System.out.println(bard.get(i));
            } else {
                System.out.println("--- Bard bar ---");
            }

            System.out.println("--------------------------------------------------------");


        }

    }



    private static void writeToFile(Set<String> words, String filename) {
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
