import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by suren on 2/5/15.
 */
public class FreqDictionaryBuilderRussian {


    public static void main(String[] args) throws IOException {
        String dicPart1 = "first_part.txt";
        String dicPart2 = "second_part.txt";
        String freq_dic = "ru.txt";

        String out1 = "/home/suren/Documents/temp/dic_part1.txt";
        String out2 = "/home/suren/Documents/temp/dic_part2.txt";

        HashMap<String, Integer> frequency = new HashMap<String, Integer>(22000);
        ArrayList<Word> part1 = new ArrayList<Word>();
        ArrayList<Word> part2 = new ArrayList<Word>();

        //Reading frequency
        BufferedReader br = new BufferedReader(new FileReader(freq_dic));
        try {

            String line = br.readLine();

            while (line != null) {
                String[] separate = line.split(" ");
                String word = separate[0];
                System.out.println("u " + separate.length);

                Double freq = Double.parseDouble(separate[separate.length - 1]);
                frequency.put(word, (int)(freq * 10));
                line = br.readLine();
            }

        } finally {
            br.close();
        }

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
                part1.add(new Word(word, description, f));
                line = dic1.readLine();
            }

        } finally {
            br.close();
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
                part2.add(new Word(word, description, f));
                line = dic2.readLine();
            }

        } finally {
            br.close();
        }

        //Writing Dictionaries
        try {
            File file = new File("out1");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            for (Word word : part1) {
                output.write(word.getWord() + "\n");
                output.write(word.getDescription() + "\n");
                output.write(word.getFrequency() + "\n");
            }
            output.close();

        } catch ( IOException e ) {
            e.printStackTrace();
        }


        try {
            File file = new File("out2");
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            for (Word word : part2) {
                output.write(word.getWord() + "\n");
                output.write(word.getDescription() + "\n");
                output.write(word.getFrequency() + "\n");
            }
            output.close();

        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

//        while(true) {
//            Scanner console = new Scanner(System.in);
//            String w = console.next();
//            System.out.println(w + "  :   " + frequency.get(w));
//        }


}
