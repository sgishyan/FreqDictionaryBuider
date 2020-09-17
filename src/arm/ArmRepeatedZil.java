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
public class ArmRepeatedZil {

    public static void main(String[] args) throws IOException {

        String reverseText = "/home/suren/Documents/temp/arm/zil/reverse.txt";
        String paintText = "/home/suren/Documents/temp/arm/zil/paint.txt";
        String showText = "/home/suren/Documents/temp/arm/zil/show.txt";
        String yesNoAll = "/home/suren/Documents/temp/arm/zil/yesno.txt";



        String yesnoBnutyun = "/home/suren/Documents/temp/arm/zil/bnutyun.txt";
        String yesnoTun = "/home/suren/Documents/temp/arm/zil/tun.txt";
        String yesnoSnund = "/home/suren/Documents/temp/arm/zil/snund.txt";
        String yesnoDproc = "/home/suren/Documents/temp/arm/zil/dproc.txt";
        String yesnoMshakuyt = "/home/suren/Documents/temp/arm/zil/mshakuyt.txt";
        String yesnoHin = "/home/suren/Documents/temp/arm/zil/hin.txt";
        String yesnoHavatum = "/home/suren/Documents/temp/arm/zil/havatum.txt";
        String yesnoMasnagitutyun = "/home/suren/Documents/temp/arm/zil/masnagitutyun.txt";
        String yesnoVoj = "/home/suren/Documents/temp/arm/zil/voj.txt";


        ArrayList<String> reverse = readFile(reverseText);
        ArrayList<String> paint = readFile(paintText);
        ArrayList<String> show = readFile(showText);
        ArrayList<String> yesnoAll = new ArrayList<String>(); // = readFile(yesNoAll);

        ArrayList<String> yesnoSorteed = readFile(yesNoAll);
        ArrayList<Pair<String, String>> yesno = new ArrayList<Pair<String, String>>();



        int [] yesNoSizes = {49, 46, 55, 36, 24, 22, 15, 35, 19};

        addToList(yesno, yesnoBnutyun, "Բնություն");
        addToList(yesno, yesnoTun, "Տուն և կենցաղ");
        addToList(yesno, yesnoSnund, "Սնունդ և խոհանոց");
        addToList(yesno, yesnoDproc, "Դպրոց");
        addToList(yesno, yesnoMshakuyt, "Մշակույթ և արվեստ");
        addToList(yesno, yesnoHin, "Հին օրեր");
        addToList(yesno, yesnoHavatum, "Հավատում եմ");
        addToList(yesno, yesnoMasnagitutyun, "Մասնագիտություն");
        addToList(yesno, yesnoVoj, "Ոճ և իր");


        for(Pair<String, String> p : yesno) {
            yesnoAll.add(p.getKey());
        }
        yesnoAll.add("------------");

        Set<String> selfRepeating = new TreeSet<String>();

        for(String str : reverse ) {
            if (selfRepeating.add(str) != true) {
                System.out.println("Self repeating REVERSE : " + str);
            }
        }
        selfRepeating.clear();

        for(String str : paint ) {
            if (selfRepeating.add(str) != true) {
                System.out.println("Self repeating PAINT : " + str);
            }
        }
        selfRepeating.clear();

        for(String str : show ) {
            if (selfRepeating.add(str) != true) {
                System.out.println("Self repeating SHOW : " + str);
            }
        }
        selfRepeating.clear();

        for(String str : yesnoAll ) {
            if (selfRepeating.add(str) != true) {
                System.out.println("Self repeating YESNO : " + str);
            }
        }
        selfRepeating.clear();


        for(String str : reverse)  {
            boolean isRepeated = false;

            if(paint.contains(str)) {
                System.out.print(" Paint ");
                paint.remove(str);
                isRepeated = true;
            }
            if(show.contains(str)) {
                System.out.print(" Show ");
                show.remove(str);
                isRepeated = true;
            }
            if(yesnoAll.contains(str)) {
                System.out.print(" YesNo ");
                yesno.remove(str);
                isRepeated = true;
            }

            if (isRepeated) {
                System.out.print(" Reverse ");
                System.out.println("");
                System.out.println("Word = " + str);
                System.out.println("----------------------------------------------------------------------------------------");
            }
        }

        for(String str : paint)  {
            boolean isRepeated = false;

            if(show.contains(str)) {
                System.out.print(" Show ");
                show.remove(str);
                isRepeated = true;
            }
            if(yesnoAll.contains(str)) {
                System.out.print(" YesNo ");
                yesno.remove(str);
                isRepeated = true;
            }

            if (isRepeated) {
                System.out.print(" Paint ");
                System.out.println("");
                System.out.println("Word = " + str);
                System.out.println("----------------------------------------------------------------------------------------");
            }
        }

        for(String str : show)  {
            boolean isRepeated = false;

            if(yesnoAll.contains(str)) {
                System.out.print(" YesNo ");
                yesno.remove(str);
                isRepeated = true;
            }

            if (isRepeated) {
                System.out.print(" Show ");
                System.out.println("");
                System.out.println("Word = " + str);
                System.out.println("----------------------------------------------------------------------------------------");
            }
        }
//
        Collections.shuffle(reverse);
        Collections.shuffle(paint);
        Collections.shuffle(yesno);
        Collections.shuffle(show);
//
        System.out.println("--SHOW  SIZE " + show.size());
        System.out.println("--PAINT  SIZE " + paint.size());
        System.out.println("--YESNO  SIZE " + yesno.size());
        System.out.println("--REVERSE  SIZE " + reverse.size());
        int yesnoIndex = 0;
        int yesNoSizeIndex = 0;
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


        for(int i = 0; i < 301; i++) {
            System.out.println("-------Card # " + (i + 1));
            System.out.println(new StringBuffer(reverse.get(i)).reverse().toString().replaceAll("ւո", "ու") + " "  + "( " + reverse.get(i) + " )" );
            System.out.println(show.get(i));
            System.out.println(yesno.get(i).getKey() + " / " + yesno.get(i).getValue() + " /");
            System.out.println(paint.get(i));
            System.out.println();
//            System.out.println("SizeIndex" + yesNoSizeIndex);
//            System.out.println("Index" + yesnoIndex);
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
