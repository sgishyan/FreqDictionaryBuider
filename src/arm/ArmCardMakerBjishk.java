package arm;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 10/5/15.
 */
public class ArmCardMakerBjishk {

    public static void main(String [] args) throws IOException {


    String heshtPath = "/home/suren/Documents/temp/arm/bjshkakan/hesht.txt";
    String mijinPath = "/home/suren/Documents/temp/arm/bjshkakan/mijin.txt";
    String bardPath = "/home/suren/Documents/temp/arm/bjshkakan/bard.txt";
    String hatukPath = "/home/suren/Documents/temp/arm/bjshkakan/hatuk.txt";
    String xarePath = "/home/suren/Documents/temp/arm/bjshkakan/bay.txt";
    String barakapPath = "/home/suren/Documents/temp/arm/bjshkakan/barak.txt";

    String filteredNounsBySize = "/home/suren/Documents/temp/Testing/barerUnique.txt";

    ArrayList<String> hesht = new ArrayList<String>();
    ArrayList<String> mijin = new ArrayList<String>();
    ArrayList<String> bard = new ArrayList<String>();
    ArrayList<String> hatuk = new ArrayList<String>();
    ArrayList<String> bay = new ArrayList<String>();
    ArrayList<String> barak = new ArrayList<String>();

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


        dic1 = new BufferedReader(new FileReader(xarePath));
        line = dic1.readLine();
        while (line != null) {

            line = line.trim();
            bay.add(line);
            line = dic1.readLine();
        }
        dic1.close();


        dic1 = new BufferedReader(new FileReader(hatukPath));
        line = dic1.readLine();
        while (line != null) {

            line = line.trim();
            hatuk.add(line);
            line = dic1.readLine();
        }
        dic1.close();

        dic1 = new BufferedReader(new FileReader(barakapPath));
        line = dic1.readLine();
        while (line != null) {

            line = line.trim();
            barak.add(line);
            line = dic1.readLine();
        }
        dic1.close();

        Collections.shuffle(hesht);
        Collections.shuffle(mijin);
        Collections.shuffle(bard);
        Collections.shuffle(barak);
        Collections.shuffle(hatuk);
        Collections.shuffle(bay);

        for (int i = 0; i < 120; i++) {
            System.out.println("Card #" + (i + 1));
            System.out.println(hesht.get(i));
            System.out.println(mijin.get(2 * i));
            System.out.println(mijin.get(2 * i + 1));
            System.out.println(bard.get(i));
            System.out.println(hatuk.get(i));
            System.out.println(barak.get(i));
            System.out.println(bay.get(i));

            System.out.println("------------------------");

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
