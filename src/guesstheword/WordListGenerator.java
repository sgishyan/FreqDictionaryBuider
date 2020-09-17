package guesstheword;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/28/17.
 */
public class WordListGenerator {

    public static void main(String... args) {
        Set<String> words = new TreeSet<>();
        HashMap<String, Integer> accurance = new HashMap<>();
        String path = "/home/suren/Downloads/4 Photos/resized_all";
        File folder = new File(path);

        for (File file : folder.listFiles()) {
            String name = file.getName();
            int dotIndex = name.indexOf(".");
            String word = name.substring(0, dotIndex - 1);
            //System.out.println(word);
            if (accurance.containsKey(word)) {
                accurance.put(word, accurance.get(word) + 1);
            } else {
                accurance.put(word, 1);
            }
            words.add(word);
        }
        System.out.println("Total count " + words.size());
        for (String word : accurance.keySet()) {
            if (accurance.get(word) != 4) {
                System.out.println("WRONG " + word);
            }
        }
        for (String word : words) {
            System.out.println(word);
        }

    }
}

