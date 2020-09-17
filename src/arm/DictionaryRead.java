package arm;

import mobiloids.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/16/15.
 */
public class DictionaryRead {

    public static void main(String[] args) throws IOException {

        String engPath = "/home/suren/Documents/languages/Armenian/eng5000.txt";
//        String heshtPath = "/home/suren/Documents/temp/arm/bank/hesht.txt";
//        String mijinPath = "/home/suren/Documents/temp/arm/bank/mijin.txt";
//        String bardPath = "/home/suren/Documents/temp/arm/bank/bard.txt";
//        String bayPath = "/home/suren/Documents/temp/arm/bank/bay.txt";
//        String barakPath = "/home/suren/Documents/temp/arm/bank/barak.txt";

//
//        ArrayList<String> hesht = readFile(heshtPath);
//        ArrayList<String> mijin = readFile(mijinPath);
//        ArrayList<String> bard = readFile(bardPath);
//        ArrayList<String> bay = readFile(bayPath);
//        ArrayList<String> barak = readFile(barakPath);

//        ArrayList<String> easy = readFileEasy(engPath);
//        for (String string : easy) {
//            System.out.println(string);
//        }
        ArrayList<String> verb = readFileVerb(engPath);
        for (String string : verb) {
            System.out.println(string);
        }
//
//
//        Set<String> selfRepeating = new TreeSet<String>();
//
//        for(String str : hesht ) {
//            if (selfRepeating.add(str) != true) {
//                System.out.println("Self repeating HESHT : " + str);
//            }
//        }
//        selfRepeating.clear();
//
//        for(String str : mijin ) {
//            if (selfRepeating.add(str) != true) {
//                System.out.println("Self repeating MIJIN : " + str);
//            }
//        }
//        selfRepeating.clear();
//
//        for(String str : bard ) {
//            if (selfRepeating.add(str) != true) {
//                System.out.println("Self repeating BARD : " + str);
//            }
//        }
//        selfRepeating.clear();
//
//        for(String str : bay ) {
//            if (selfRepeating.add(str) != true) {
//                System.out.println("Self repeating BAY : " + str);
//            }
//        }
//        selfRepeating.clear();
//
//        for(String str : barak ) {
//            if (selfRepeating.add(str) != true) {
//                System.out.println("Self repeating BARAK : " + str);
//            }
//        }
//        selfRepeating.clear();
//
//        for(String str : hesht)  {
//            boolean isRepeated = false;
//
//            if(mijin.contains(str)) {
//                System.out.print(" Mijin ");
//                mijin.remove(str);
//                isRepeated = true;
//            }
//
//            if(bard.contains(str)) {
//                System.out.print(" Bard ");
//                bard.remove(str);
//                isRepeated = true;
//            }
//            if(bay.contains(str)) {
//                System.out.print(" Bay ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//            if(barak.contains(str)) {
//                System.out.print(" barak ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//            if (isRepeated) {
//                System.out.print(" hesht ");
//                System.out.println("");
//                System.out.println("Word = " + str);
//                System.out.println("----------------------------------------------------------------------------------------");
//            }
//        }
//
//        for(String str : mijin)  {
//            boolean isRepeated = false;
//
//            if(bard.contains(str)) {
//                System.out.print(" bard ");
//                bard.remove(str);
//                isRepeated = true;
//            }
//            if(bay.contains(str)) {
//                System.out.print(" bay ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//            if(barak.contains(str)) {
//                System.out.print(" barak ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//            if (isRepeated) {
//                System.out.print(" mijin ");
//                System.out.println("");
//                System.out.println("Word = " + str);
//                System.out.println("----------------------------------------------------------------------------------------");
//            }
//        }
//
//        for(String str : bard)  {
//            boolean isRepeated = false;
//
//
//            if(bay.contains(str)) {
//                System.out.print(" bay ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//            if(barak.contains(str)) {
//                System.out.print(" barak ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//            if (isRepeated) {
//                System.out.print(" bard ");
//                System.out.println("");
//                System.out.println("Word = " + str);
//                System.out.println("----------------------------------------------------------------------------------------");
//            }
//        }
//
//        for(String str : bay)  {
//            boolean isRepeated = false;
//
//            if(barak.contains(str)) {
//                System.out.print(" barak ");
//                bay.remove(str);
//                isRepeated = true;
//            }
//
//
//            if (isRepeated) {
//                System.out.print(" bay ");
//                System.out.println("");
//                System.out.println("Word = " + str);
//                System.out.println("----------------------------------------------------------------------------------------");
//            }
//        }
////
//        Collections.shuffle(hesht);
//        Collections.shuffle(mijin);
//        Collections.shuffle(bard);
//        Collections.shuffle(bay);
//        Collections.shuffle(barak);
////
//        System.out.println("--HESHT  SIZE " + hesht.size());
//        System.out.println("--MIJIN  SIZE " + mijin.size());
//        System.out.println("--BARD  SIZE " + bard.size());
//        System.out.println("--BAY  SIZE " + bay.size());
//        System.out.println("--BARAK  SIZE " + barak.size());
//        int yesnoIndex = 0;
//        int yesNoSizeIndex = 0;
//        for(int i = 0; i < 301; i++) {
//            System.out.println("-------Card # " + (i + 1));
//            System.out.println(new StringBuffer(reverse.get(i)).reverse() + " "  + "( " + reverse.get(i) + " )");
//            System.out.println(show.get(i));
//            System.out.println(yesno.get(yesnoIndex).getKey() + " / " + yesno.get(yesnoIndex).getValue() + " /");
//            yesno.remove(yesnoIndex);
//            yesNoSizes[yesNoSizeIndex]--;
//            if(yesno.size() > 0) {
//                yesnoIndex = (yesnoIndex + yesNoSizes[yesNoSizeIndex]) % yesno.size();
//                yesNoSizeIndex = (yesNoSizeIndex + 1) % 9;
//                while(yesNoSizes[yesNoSizeIndex] == 0) {
//                    yesNoSizeIndex = (yesNoSizeIndex + 1) % 9;
//                }
//            }
//
//
//
//            System.out.println(paint.get(i));
//            System.out.println();
////            System.out.println("SizeIndex" + yesNoSizeIndex);
////            System.out.println("Index" + yesnoIndex);
//        }


//        for(int i = 0; i < 301; i++) {
//            System.out.println("-------Card # " + (i + 1));
//            System.out.println(new StringBuffer(reverse.get(i)).reverse().toString().replaceAll("ւո", "ու") + " "  + "( " + reverse.get(i) + " )" );
//            System.out.println(show.get(i));
//            System.out.println(yesno.get(i).getKey() + " / " + yesno.get(i).getValue() + " /");
//            System.out.println(paint.get(i));
//            System.out.println();
////            System.out.println("SizeIndex" + yesNoSizeIndex);
////            System.out.println("Index" + yesnoIndex);
       // }
    }

    private static ArrayList<String> readFileVerb(String engPath) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(engPath));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine().trim();
            while (line != null) {
                String[] tokens = line.split("\t");
                String word = tokens[0];
                String ps = tokens[1];
                if (ps.equals("v" )) {
                    words.add(word);
                }
                line = dic1.readLine().trim();;
            }
        } finally {
            dic1.close();
            return words;
        }    }

    private static ArrayList<String> readFileEasy(String engPath) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(engPath));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine().trim();
            while (line != null) {
                String[] tokens = line.split("\t");
                String word = tokens[0];
                String ps = tokens[1];
                if (ps.equals("n" ) || ps.equals("j")) {
                    words.add(word);
                }
               line = dic1.readLine().trim();;
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    private static void addToList(ArrayList<Pair<String, String>> yesno, String filename, String category) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(filename));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                yesno.add(new Pair<String, String>(word.toLowerCase(), category));
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return;
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
