package wordwars;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 10/6/15.
 */
public class MissingWords {
    public static void main(String[] args) throws IOException {


        String path = "/home/suren/Documents/temp/missingWords.txt";


        HashMap<String, Integer> map = new HashMap<String, Integer>();

        BufferedReader old = new BufferedReader(new FileReader(path));
        try {

            String line = old.readLine();
            while (line != null) {
                String w = line.trim().toLowerCase();
                if (map.containsKey(w)) {
                    map.put(w, map.get(w) + 1);
                } else {
                    map.put(w, 1);
                }
                line = old.readLine();
            }


        } finally {
            old.close();
        }

        for (String str : map.keySet()) {
            if (map.get(str) > 1) {
                System.out.println(str + " : " + map.get(str));
            }
        }



    }

}
