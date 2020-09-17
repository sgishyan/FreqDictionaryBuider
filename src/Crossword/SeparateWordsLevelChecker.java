package Crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by suren on 10/21/19.
 */
public class SeparateWordsLevelChecker {

    public static void main(String[] args) throws IOException {
        usedSeedWords("/home/suren/Documents/temp/crossword/levels_rus");
    }

    private static void usedSeedWords(String root) throws IOException {
        LinkedList<String> used = new LinkedList<>();
        File rootFolder = new File (root);
        for (File inner : rootFolder.listFiles()) {
            if (!inner.isDirectory()) {
                if (!checkLevelWords(inner.getAbsolutePath()))
                System.out.println(inner);
            } else {
                usedSeedWords(inner.getAbsolutePath());
            }
        }
    }


    private static boolean  checkLevelWords(String level) throws IOException {

        System.out.println(level);

        BufferedReader dic1 = new BufferedReader(new FileReader(level));

        LinkedList<String> words = new LinkedList<String>();
        try {

            //Reading board size
            String line = dic1.readLine();
            int boardSize = Integer.parseInt(line);

            //Reading crossword
            char[][] board = new char[boardSize][boardSize];

            for(int i = 0; i < boardSize; i++) {
                line = dic1.readLine();
                for (int j = 0 ;j < line.length(); j+=2) {
                    board[i][j / 2] = line.charAt(j);
                }
            }

            //Reading seed word
            line = dic1.readLine();

            //Reading level words count
            line = dic1.readLine();
            int levelWordsCount = Integer.parseInt(line);

            for(int i = 0; i < levelWordsCount; i++) {

                line = dic1.readLine();


                String word = line;
                line = dic1.readLine();
                String[] coords = line.split(" ");
                int direction = Integer.parseInt(coords[2]);
                int startX = Integer.parseInt(coords[0]);
                int startY = Integer.parseInt(coords[1]);
                if (direction == 1) {
                    for (int g = startX; g < word.length(); g++) {
                        if (board[startX][startY+g] != word.charAt(g)) {
                            System.out.println(word + " " + startX + " " + startY);
                            return false;
                        }
                    }
                }else {
                    for (int g = startX; g < word.length(); g++) {
                        if (board[startX + g][startY] != word.charAt(g)) {
                            System.out.println(word + " " + startX + " " + startY);
                            return false;
                        }
                    }
                }

            }
            return true;
        } finally {
            dic1.close();

        }
    }
}
