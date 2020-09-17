package fra;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by suren on 7/2/15.
 */
public class VariationsSorterFrench {

    public static void main(String[] args) throws IOException {

        String path = "/home/suren/Documents/temp/french/variations2.txt";
        String outPath = "/home/suren/Documents/temp/french/variations_sorted_2.txt";
        ArrayList<String> inputStrings = new ArrayList<String>(40000);
        ArrayList<String> outputStrings = new ArrayList<String>(40000);


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


        try {
            StringBuilder sb = new StringBuilder();
            String line = brA.readLine();
            while (line != null) {
                inputStrings.add(line.trim());
                line = brA.readLine();
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
                int distance1 = VariationsSorterFrench.getDistance(currentString, inputStrings.get(j));
                int distance2 = 7;
                int distance3 = 7;
                int distance4 = 7;
                int distance5 = 7;
                if (currentIndex > 1) {
                    distance2 = VariationsSorterFrench.getDistance(outputStrings.get(currentIndex - 1), inputStrings.get(j));
                }
                if (currentIndex > 2) {
                    distance3 = VariationsSorterFrench.getDistance(outputStrings.get(currentIndex - 2), inputStrings.get(j));
                }
                if (currentIndex > 3) {
                    distance4 = VariationsSorterFrench.getDistance(outputStrings.get(currentIndex - 3), inputStrings.get(j));
                }
                if (currentIndex > 4) {
                    distance5 = VariationsSorterFrench.getDistance(outputStrings.get(currentIndex - 4), inputStrings.get(j));
                }
                int distance = distance1 * 5 + distance2 * 4 + distance3 * 3 + distance4 * 2 + distance5;
                if (distance > maxDistance) {
                    maxDistance = distance;
                    maxDistanceIndex = j;
                }

            }
            System.out.println(currentString + " " + inputStrings.get(maxDistanceIndex));

            currentIndex++;
            outputStrings.add(inputStrings.get(maxDistanceIndex));
            inputStrings.remove(maxDistanceIndex);

        }
        for (String string : outputStrings) {
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
        return currentString.length() - sameCharacters;
    }
}











