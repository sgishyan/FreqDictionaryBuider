import java.io.*;
import java.util.*;

/**
 * Created by suren on 10/6/15.
 */
public class PluralWordsDetector {
    public static void main(String[] args) throws IOException {


        String childPath = "/home/suren/Documents/temp/dictionaries/Category/update/basic_out.txt";
        String mainPath = "/home/suren/Documents/temp/dictionaries/Category/update/main_out.txt";
        String rarePath = "/home/suren/Documents/temp/dictionaries/Category/update/rare_out_new.txt";


        String childNewPath = "/home/suren/Documents/temp/dictionaries/Category/update/child_out_add.txt";
        String mainNewPath = "/home/suren/Documents/temp/dictionaries/Category/update/main_out_add.txt";
        String rareNewPath = "/home/suren/Documents/temp/dictionaries/Category/update/rare_cleared.out.txt";

        Set<Word> oldChild = new TreeSet<Word>();
        Set<Word> oldMain = new TreeSet<Word>();
        Set<Word> oldExtended = new TreeSet<Word>();


        Set<Word> childAdd = new TreeSet<Word>();
        Set<Word> mainAdd = new TreeSet<Word>();
        Set<Word> newRare = new TreeSet<Word>();
        //Reading old child
        BufferedReader old = new BufferedReader(new FileReader(childPath));
        try {

            String line = old.readLine();
            String description = old.readLine();
            while (line != null) {
                String w = line.trim().toLowerCase();
                String d = description.trim();
                oldChild.add(new Word(w, d, 0));
                line = old.readLine();
                description = old.readLine();
            }

        } finally {
            old.close();
        }

        //Reading main
        old = new BufferedReader(new FileReader(mainPath));
        try {

            String line = old.readLine();
            String description = old.readLine();
            while (line != null) {
                String w = line.trim().toLowerCase();
                String d = description.trim();
                oldMain.add(new Word(w, d, 0));
                line = old.readLine();
                description = old.readLine();
            }

        } finally {
            old.close();
        }

        //Reading main
        old = new BufferedReader(new FileReader(rarePath));
        try {

            String line = old.readLine();
            String description = old.readLine();
            while (line != null) {
                String w = line.trim().toLowerCase();
                String d = description.trim();
                oldExtended.add(new Word(w, d, 0));
                line = old.readLine();
                description = old.readLine();
            }

        } finally {
            old.close();
        }

        Iterator<Word> iterator = oldExtended.iterator();
        while(iterator.hasNext() ) {
            Word word = iterator.next();
            String description =word.getDescription();
            if (description.toLowerCase()   .contains("plural form of")) {
                String[] lemmas = description.split(" ");
                String singular = lemmas[lemmas.length - 1].trim();
                for (int index = 0; index < lemmas.length; index++) {
                    if (lemmas[index] == "plural") {
                        singular = lemmas[index + 3].trim();
                    }
                }

                Word singularWord = new Word(singular, "", 0);


                if (oldChild.contains(singularWord)) {
                    for (Word childWord : oldChild) {
                        if (childWord.equals(singularWord)) {
                            childAdd.add(word);
                            iterator.remove();
                            //System.out.println(word.getWord());
                            break;
                        }
                    }

                }
                else
                if (oldMain.contains(singularWord)) {
                    for (Word mainWord : oldMain) {
                        if (mainWord.equals(singularWord)) {
                            mainAdd.add(word);
                            iterator.remove();
                            //System.out.println(word.getWord());
                            break;
                        }
                    }
                }



            }
        }

       writeToFile(childAdd, childNewPath);
       writeToFile(mainAdd, mainNewPath);
       writeToFile(oldExtended, rareNewPath);
    }

    private static void writeToFile(Set<Word> words, String filename) {
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
}
