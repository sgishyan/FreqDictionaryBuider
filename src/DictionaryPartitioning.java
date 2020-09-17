import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by suren on 7/28/15.
 */
public class DictionaryPartitioning {

    public static void main(String[] args) throws IOException {
        String dicPart1 = "first_part_big.txt";
        String dicPart2 = "second_part_big.txt";

        String alternativeString = "/home/suren/Documents/temp/alternative.txt";
        String raresString = "/home/suren/Documents/temp/rares.txt";
        String obsoleteString = "/home/suren/Documents/temp/obsolete.txt";


        HashMap<String, Integer> frequency = new HashMap<String, Integer>(460000);
        ArrayList<Word> dictionary = new ArrayList<Word>();


        //Reading frequency



        //Reading part 1
        BufferedReader dic1 = new BufferedReader(new FileReader(dicPart1));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                String description = dic1.readLine();
                System.out.println(word + "  :" + description);
                Integer freq = frequency.get(word);
                int f = 0;
                if (freq != null) {
                    f = freq;
                }
                dictionary.add(new Word(word, description, f));
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }

        //Reading part 2
        BufferedReader dic2 = new BufferedReader(new FileReader(dicPart2));
        try {

            String line = dic2.readLine();

            while (line != null) {
                String word = line.trim();
                String description = dic2.readLine();
                System.out.println(word + "  :" + description);
                Integer freq = frequency.get(word);
                int f = 0;
                if (freq != null) {
                    f = freq;
                }
                dictionary.add(new Word(word, description, f));
                line = dic2.readLine();
            }

        } finally {
            dic2.close();
        }

        ArrayList<Word> alternatives = selectAlternative(dictionary);
        ArrayList<Word> rares = selectCategoryInParentheses(dictionary, "rare");
        ArrayList<Word> obsoletes = selectCategoryInParentheses(dictionary, "obsolete");

       // writeToFile(alternatives, alternativeString);
       // writeToFile(rares, raresString);
        writeToFile(obsoletes, obsoleteString);
    }

    private static void writeToFile(ArrayList<Word> words, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (Word word : words) {
                output.write(word.getWord() + "\n");
                output.write(word.getDescription() + "\n");
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

    private static ArrayList<Word> selectAlternative(ArrayList<Word> dictionary) {
        ArrayList<Word> alternatives = new ArrayList<Word>();
        for (Word word : dictionary) {
            if (word.getDescription().contains("Alternative form") ||
                    (word.getDescription().contains("Alternative spelling"))) {
                alternatives.add(word);
                //dictionary.remove(word);
            }
        }
        return alternatives;
    }

    private static ArrayList<Word> selectCategoryInParentheses(ArrayList<Word> dictionary, String category) {
        ArrayList<Word> cat = new ArrayList<Word>();
        for (Word word : dictionary) {
            if (word.getDescription().matches(".*(.*" + category +".*).*") ) {
                cat.add(word);
                //dictionary.remove(word);
            }
        }
        return cat;
    }

//        while(true) {
//            Scanner console = new Scanner(System.in);
//            String w = console.next();
//            System.out.println(w + "  :   " + frequency.get(w));
//        }
}
