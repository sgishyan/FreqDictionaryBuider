import mobiloids.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by suren on 7/24/17.
 */
public class LongestWordSeq {

    public static void main(String[] args) throws IOException {

        int[] count = new int [26];

        ArrayList<ArrayList<Set<String>>> graph = new ArrayList<>();
        for(int i = 0; i< 26; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < 26; j++) {
               graph.get(i).add(new TreeSet<>());
            }
        }
        ArrayList<String> dictionary = readFile("/home/suren/Downloads/dictionary.txt");
        for(String str : dictionary) {
            if (str.contains("-") || str.contains("'")) {
                continue;
            }
            if (str.startsWith("z")) {
                System.out.println(str);
            }
            graph.get(str.charAt(0) - 'a').get(str.charAt(str.length() - 1) - 'a').add(str);
            count[str.charAt(0) - 'a']++;
        }

//
//        Set<String> w = graph.get(4).get(6);
//        for(String s : w) {
//            System.out.println(s);
//        }

        String word = "x";
        int length = 0;
        while(true) {
            //Closing letter
            Set<String> selfSet = graph.get(word.charAt(word.length() - 1) - 'a').get(word.charAt(word.length() - 1) - 'a');
            Iterator<String> iterator = selfSet.iterator();
            while(iterator.hasNext()) {
                System.out.println(iterator.next());
                iterator.remove();
                count[word.charAt(word.length() - 1) - 'a']--;
                length++;
            }

            //Finding maxSizeNeighbour;
            int maxIndex = -1;
            int max = -1;

            for(int i = 0; i < 26; i++) {
                if (!graph.get(word.charAt(word.length() - 1) - 'a').get(i).isEmpty()) {
                    //System.out.println(i + " " + count[i]);
                    if (count[i] > max) {
                        max = count[i];
                        maxIndex = i;

                    }
                }

            }
//            for(int i = 0; i < 26; i++) {
//                if (!graph.get(word.charAt(word.length() - 1) - 'a').get(i).isEmpty()) {
//                    int size = graph.get(i).get(i).size();
//                    if (size > max) {
//                        max = size;
//                        maxIndex = i;
//                    }
//                }
//            }
////
//            if (max == 0) {
//                for(int i = 0; i < 26; i++) {
//
//                    if (graph.get(word.charAt(word.length() - 1) - 'a').get(i).size() > max) {
//                        max = graph.get(word.charAt(word.length() - 1) - 'a').get(i).size();
//                        maxIndex = i;
//                    }
//                }
//            }

            if (maxIndex == -1) {
                System.out.println("Answer :" + length);
                break;

            }
            //Remove one mre word and switching active WORD
            Set<String> anotherSet = graph.get(word.charAt(word.length() - 1) - 'a').get(maxIndex);
            iterator = anotherSet.iterator();
            if (iterator.hasNext()) {
                word = iterator.next();
                iterator.remove();
                count[word.charAt(word.length() - 1) - 'a']--;
                System.out.println(word);
                length++;
            } else {

                break;
            }
            //System.out.println("--------------------------------------------------------");

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
