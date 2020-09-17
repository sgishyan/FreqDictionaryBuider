package ger;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Created by suren on 7/2/15.
 */
public class VariationsSorterGerman {

    public static void main(String[] args) throws IOException {





        

        String path = "/home/suren/Documents/temp/ger/variations.txt";
        String outPath = "/home/suren/Documents/temp/ger/variations_sorted_recover.txt";
        ArrayList<String> inputStrings = new ArrayList<String>(160000);
        ArrayList<String> outputStrings = new ArrayList<String>(160000);
        TreeSet<String> goodVariations = new TreeSet<String>();

        BufferedReader brA = null;
        File out = null;
        BufferedWriter output = null;




        try {
            brA = new BufferedReader(new FileReader(path));
            out = new File(outPath);
            output = new BufferedWriter(new FileWriter(out));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int lineN = 1;
        try {
            StringBuilder sb = new StringBuilder();
            String line = brA.readLine();
            while (line != null) {
                if (!goodVariations.contains(line.trim())) {
                    inputStrings.add(line.trim());
                } else {
                    System.out.println(line);
                }
                line = brA.readLine();
//                if (line.length() < 7) {
//                    System.out.println(line + "  " + lineN);
//                }
//                lineN++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            brA.close();
        }



        Collections.shuffle(inputStrings);
        outputStrings.add(inputStrings.get(0));
        inputStrings.remove(0);
        System.out.println("Sorted");

        int currentIndex = 0;
        while (!inputStrings.isEmpty()) {
            String currentString = outputStrings.get(currentIndex);
            int maxDistance = -1;
            int maxDistanceIndex = 0;
            for (int j = 0; j < inputStrings.size(); j++) {
                int distance1 = VariationsSorterGerman.getDistance(currentString, inputStrings.get(j));
                //System.out.println(distance1);
                int distance2 = 0;
                int distance3 = 0;
                int distance4 = 0;
                int distance5 = 0;
                if (currentIndex > 1) {
                    distance2 = VariationsSorterGerman.getDistance(outputStrings.get(currentIndex - 1), inputStrings.get(j));
                }
                if (currentIndex > 2) {
                    distance3 = VariationsSorterGerman.getDistance(outputStrings.get(currentIndex - 2), inputStrings.get(j));
                }
                if (currentIndex > 3) {
                    distance4 = VariationsSorterGerman.getDistance(outputStrings.get(currentIndex - 3), inputStrings.get(j));
                }
                if (currentIndex > 4) {
                    distance5 = VariationsSorterGerman.getDistance(outputStrings.get(currentIndex - 4), inputStrings.get(j));
                }
                int distance = 10 * distance1 + 5 * distance2 + 3 * distance3 + distance4;
                if (distance > maxDistance) {
                    maxDistance = distance;
                    maxDistanceIndex = j;
                }

            }
            //System.out.println(currentString + " " + inputStrings.get(maxDistanceIndex));

            currentIndex++;
            System.out.println(inputStrings.get(maxDistanceIndex) + "   " + maxDistance);
            outputStrings.add(inputStrings.get(maxDistanceIndex));
            inputStrings.remove(maxDistanceIndex);

        }
        for (String string : outputStrings) {
            //System.out.println(string);
            output.write(string + "\n");
        }
        output.close();

    }


    private static int getDistance(String currentString, String s) {
        int sameCharacters = 0;
        boolean[] used1 = new boolean[currentString.length()];
        boolean[] used2 = new boolean[currentString.length()];
        for (int i = 0; i < currentString.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (currentString.charAt(i) == s.charAt(j) && used1[i] == false && used2[j] == false) {
                    sameCharacters++;
                    used1[i] = true;
                    used2[j] = true;
                    break;
                }
            }
        }
        //System.out.println("Length ։ " + currentString.length() + "  " + currentString);
        return currentString.length() - sameCharacters;
    }
}











