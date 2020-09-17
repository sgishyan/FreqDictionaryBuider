import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 9/1/15.
 */
public class CategorySplitter {


    public static void main(String[] args) throws IOException {


        String childDic = "/home/suren/Documents/temp/dictionaries/Category/basic.txt";
        String mainDic = "/home/suren/Documents/temp/dictionaries/Category/main.txt";
        String extendedDic = "/home/suren/Documents/temp/dictionaries/Category/rare.txt";

        String childDicNew = "/home/suren/Documents/temp/dictionaries/Category/basic_out.txt";
        String mainDicNew = "/home/suren/Documents/temp/dictionaries/Category/main_out.txt";
        String extendedDicNew = "/home/suren/Documents/temp/dictionaries/Category/rare_out.txt";


        Set<Word> oldChild = new TreeSet<Word>();
        Set<Word> oldMain = new TreeSet<Word>();
        Set<Word> oldExtended = new TreeSet<Word>();

        Set<Word> newMain = new TreeSet<Word>();
        Set<Word> newExtended = new TreeSet<Word>();
        Set<Word> newChild = new TreeSet<Word>();

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

        //Reading old extended
        old = new BufferedReader(new FileReader(extendedDic));
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

        System.out.println("Old Child Size  =  " + oldChild.size());
        System.out.println("Old Main Size  =  " + oldMain.size());
        System.out.println("Old Extended Size  =  " + oldExtended.size());

        for (Word word : oldMain) {
            if (!oldChild.contains(word)) {
                newMain.add(word);
            }
        }


        for (Word word : oldExtended) {
            if (!oldChild.contains(word) && !oldMain.contains(word)) {
                newExtended.add(word);
            }
        }





//        for (Word word : oldExtended) {
//            if (!oldChild.contains(word) && !oldMain.contains(word)) {
//                newExtended.add(word);
//            }
//        }

        System.out.println("New Child Size  =  " + newChild.size());
        System.out.println("New Main Size  =  " + newMain.size());
        System.out.println("New Extended Size  =  " + newExtended.size());


        writeToFile(oldChild, childDicNew);
        writeToFile(newMain, mainDicNew);
        writeToFile(newExtended, extendedDicNew);
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
