package SpiderProtector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by suren on 10/4/18.
 */
public class LevelProtector {

    public static final String PATH = "/home/suren/Documents/spider_exp/android/assets/puzzles";

    public static void main(String[] args) {

       makeLevelEncryption(PATH);
    }

    public static void makeLevelEncryption(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                makeLevelEncryption(file.getAbsolutePath());
            }else {
                try {
                    //System.out.println(file.getName());
                    if (file.getName().endsWith(".txt")) {
                        encryptLevel(file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String encryptLevel(File levelFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(levelFile));
        StringBuilder file = new StringBuilder();
        try {

            String line = dic1.readLine().trim();
                        while (line != null) {
                String word = line.trim();
                file.append(word +"\n");
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return file.toString();
        }
    }
}
