import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 7/30/15.
 */
public class UnionCleaner {
    public static void main(String[] args) throws IOException {


        String union = "/home/suren/Documents/temp/Testing/dictionary2.txt";
        String huge = "/home/suren/Documents/temp/freq_huge.txt";
        String hugeLemma = "/home/suren/Documents/temp/freq_huge_lemma.txt";
        String mostUsed = "/home/suren/Documents/temp/most_used_3.txt";
        String rareUsed = "/home/suren/Documents/temp/rare_used_3.txt";
        String childUsed = "/home/suren/Documents/temp/child_used_3.txt";

        ArrayList<Word> unionList = new ArrayList<Word>();
        ArrayList<String> hugeList = new ArrayList<String>();
        ArrayList<String> hugeLemmaList = new ArrayList<String>();
        Set<Word> mostUsedSet = new TreeSet<Word>();
        Set<Word> rareUsedSet = new TreeSet<Word>();
        Set<Word> childUsedSet = new TreeSet<Word>();
        //Reading old
        BufferedReader old = new BufferedReader(new FileReader(union));
        try {

            String line = old.readLine();
            while (line != null) {
                String word = line.trim().toLowerCase();
                String description = old.readLine();
                unionList.add(new Word(word, description, 0));
                line = old.readLine();
            }

        } finally {
            old.close();
        }



        //Reading huge
        BufferedReader dic1 = new BufferedReader(new FileReader(huge));
        try {

            String line = dic1.readLine();

            while (line != null) {
                String word = line.trim();
                hugeList.add(word);
                line = dic1.readLine();
            }

        } finally {
            dic1.close();
        }

        //Reading huge lemmas
        BufferedReader dic2 = new BufferedReader(new FileReader(hugeLemma));
        try {

            String line = dic2.readLine();
            while (line != null) {
                String word = line.trim();
                hugeLemmaList.add(word);
                line = dic2.readLine();
            }
        } finally {
            dic1.close();
        }


        for (int i = 0; i < unionList.size(); i++) {

            huge:
            for (int j = 0; j < hugeList.size(); j++) {
                if (unionList.get(i).getWord().equals(hugeList.get(j))) {

                    if (j <= 10000) {
                        childUsedSet.add(unionList.get(i));
                        break huge;
                    }

                    if (j < 50000) {

                        if (childUsedSet.contains(unionList.get(i))) {
                            break huge;
                        }

                        mostUsedSet.add(unionList.get(i));
                        break huge;

                    } else {


                        if (childUsedSet.contains(unionList.get(i))) {
                            break huge;
                        }

                        if (mostUsedSet.contains(unionList.get(i))) {
                            break huge;
                        }

                        rareUsedSet.add(unionList.get(i));
                        break huge;
                    }
                }
            }
        }

//         for (int i = 0; i < unionList.size(); i++) {
//
//            huge:
//            for (int j = 0; j < hugeList.size(); j++) {
//                if (unionList.get(i).getWord().equals(hugeList.get(j))) {
//
//                    if (j < 60000) {
//                        mostUsedSet.add(unionList.get(i));
//                        break huge;
//                    } else {
//
//
//                        if (mostUsedSet.contains(unionList.get(i))) {
//                            break huge;
//                        }
//
//                        rareUsedSet.add(unionList.get(i));
//                        break huge;
//                    }
//                }
//            }
//        }



//        //Basic
//        for (int i = 0; i < unionList.size(); i++) {
//
//            huge:
//            for (int j = 0; j < hugeList.size(); j++) {
//                if (unionList.get(i).getWord().equals(hugeList.get(j))) {
//
//                    if (j <= 20000) {
//                        childUsedSet.add(unionList.get(i));
//                        break huge;
//                    } else {
//
//                        rareUsedSet.add(unionList.get(i));
//                        break huge;
//                    }
//                }
//            }
//        }

        for (Word word : mostUsedSet) {
            int index = hugeList.indexOf(word.getWord());
            String lemma = hugeLemmaList.get(index);
            Word temp = new Word(lemma, "", 0);
            if (childUsedSet.contains(temp)) {
                System.out.println(word.getWord());
                childUsedSet.add(word);
            }
        }


        for (Word word : rareUsedSet) {
            int index = hugeList.indexOf(word.getWord());
            String lemma = hugeLemmaList.get(index);
            Word temp = new Word(lemma, "", 0);
            if (childUsedSet.contains(temp)) {
                System.out.println(word.getWord());
                childUsedSet.add(word);
            }
        }




        for (Word word : childUsedSet) {
            if (rareUsedSet.contains(word)) {
                rareUsedSet.remove(word);
            }

            if (mostUsedSet.contains(word)) {
                mostUsedSet.remove(word);
            }
        }

        for (Word word : rareUsedSet) {
            int index = hugeList.indexOf(word.getWord());
            String lemma = hugeLemmaList.get(index);
            Word temp = new Word(lemma, "", 0);
            if (mostUsedSet.contains(temp)) {
                System.out.println(word.getWord());
                mostUsedSet.add(word);
            }
        }

        for (Word word : mostUsedSet) {
            if (rareUsedSet.contains(word)) {
                rareUsedSet.remove(word);
            }
        }


        //writeMostUsed(mostUsedSet, mostUsed);
        writeMostUsed(childUsedSet, childUsed);
        writeMostUsed(mostUsedSet, mostUsed);
        writeMostUsed(rareUsedSet, rareUsed);

    }



    private static void writeMostUsed(Set<Word> used, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            for (Word word : used) {
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

    private static void writeStrangeWords(ArrayList<String> old, ArrayList<String> huge, String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < old.size(); i++) {
                String string = old.get(i);
                if (!huge.contains(string)) {
                    output.write(string + "\n");
                }
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
