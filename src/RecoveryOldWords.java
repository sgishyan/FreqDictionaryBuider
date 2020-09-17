import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 10/7/15.
 */
public class RecoveryOldWords {
    public static void main(String[] args) throws IOException {


        String childDic = "/home/suren/Documents/temp/dictionaries/Category/update/basic_out.txt";
        String mainDic = "/home/suren/Documents/temp/dictionaries/Category/update/main_out.txt";
        //String extendedDic = "/home/suren/Documents/temp/dictionaries/Category/rare_out.txt";

        String dictionary = "/home/suren/Documents/temp/Testing/dictionary.txt";
        String missing = "/home/suren/Documents/temp/strange_words.txt";
        String extendedOut = "/home/suren/Documents/temp/dictionaries/Category/rare_out_new.txt";


        Set<Word> oldChild = new TreeSet<Word>();
        Set<Word> oldMain = new TreeSet<Word>();
        Set<Word> oldExtended = new TreeSet<Word>();

        Map<String, Word> dictionaryOld = new HashMap<String, Word>();
        Set<Word> newExtended = new TreeSet<Word>();
        Set<Word> missingWords = new TreeSet<Word>();

        //Reading old child
        BufferedReader old = new BufferedReader(new FileReader(childDic));
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



        //Reading old main
        old = new BufferedReader(new FileReader(mainDic));
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



        //Reading old dictionary
        old = new BufferedReader(new FileReader(dictionary));
        try {

            String line = old.readLine();
            String description = old.readLine();
            while (line != null) {
                String w = line.trim().toLowerCase();
                dictionaryOld.put(w, new Word(w, description, 0));
                line = old.readLine();
                description = old.readLine();
            }

        } finally {
            old.close();
        }


        //Reading old dictionary
        old = new BufferedReader(new FileReader(missing));
        try {

            String line = old.readLine();
            while (line != null) {
                String w = line.trim().toLowerCase();
                //String d = description.trim();
                missingWords.add(new Word(w, "", 0));
                line = old.readLine();
            }

        } finally {
            old.close();
        }


        System.out.println("Old Child Size  =  " + oldChild.size());
        System.out.println("Old Main Size  =  " + oldMain.size());
        System.out.println("Old Extended Size  =  " + oldExtended.size());
        System.out.println("Strange Size  =  " + missingWords.size());


        for (Word word : missingWords) {

            if (oldChild.contains(word) || oldMain.contains(word)) {
               continue;
            }
            System.out.println(word.getWord());
            Word missedWord = dictionaryOld.get(word.getWord());
            newExtended.add(missedWord);
        }




        writeToFile(newExtended, extendedOut);

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
