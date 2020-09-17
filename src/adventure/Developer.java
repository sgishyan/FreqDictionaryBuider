package adventure;

import wordwars.CollectLetters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suren on 1/9/19.
 */

class Rectangle {

    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
class Developer implements Map.Entry {

    String s;
    String p;

    public Developer(String s, String p) {
        this.s = s;
        this.p = p;
    }

    public Developer(Developer a) {
        this.s = a.s;
        this.p = a.p;
    }




    public static void main(String[] args) throws IOException {

        HashMap<Rectangle, Integer> areaMap = new HashMap<>();
        areaMap.put(new Rectangle(1,2), 2);



//        ArrayList<String> words = readFile("/home/suren/Documents/languages/Georgian/georgianFiver.txt");
//
//        for (String str : words ) {
//            System.out.println(str);
//        }
    }

    private static ArrayList<String> readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();

                if (word.length() > 8 || word.contains(".") ) {
                    words.add((word.split("\t"))[0]);
                    line = dic1.readLine();
                    //System.out.println(word);
                    continue;
                }
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

    @Override
    public Object getKey() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Object setValue(Object value) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

