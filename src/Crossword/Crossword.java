package Crossword;

import connections.ConnectionCell;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by suren on 9/28/18.
 */
public class Crossword {
    private char[][] table;
    private int tableSize;
    private ArrayList<String> dictionary;
    private ArrayList<String> crosswordWords;
    private ArrayList<FixedWord> placedWords;
    private String seedWord;

    public Crossword(ArrayList<String> dictionary, ArrayList<String> crWords ,String word) {
        this.dictionary = dictionary;
        this.crosswordWords = crWords;
        this.seedWord = word;
        placedWords = new ArrayList<>();
        Random random = new Random();

        switch (word.length()) {
            case 3:
                tableSize = 3;
                break;
            case 4:
                tableSize = 4 + random.nextInt(2);
                break;
            case 5:
                tableSize = 6 + random.nextInt(2);
                break;
            case 6:
                tableSize = 8 + random.nextInt(2);
                break;
            case 7:
                tableSize = 9 + random.nextInt(2);
                break;
            case 8:
                tableSize = 10 + random.nextInt(2);
                break;

        }

        table = new char[tableSize][tableSize];
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                table[i][j] = 0;
            }
        }
    }

    public int getTableSize() {
        return tableSize;
    }

    public String getSeedWord() {
        return seedWord;
    }

    public boolean placeWord(String word) {
        ArrayList<FixedWord> availablePlaces = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                availablePlaces.add(new FixedWord(i, j, false, word));
                availablePlaces.add(new FixedWord(i, j, true, word));

            }
        }
        Collections.shuffle(availablePlaces);
        for (FixedWord fixedWord : availablePlaces) {
            boolean isPossible = tryToPlaceWord(fixedWord);
            if (isPossible) {
                placedWords.add(fixedWord);
                addToTable(fixedWord);
                return true;
            }
        }
        return false;
    }

    public ArrayList<FixedWord> getPlacedWords() {
        return placedWords;
    }

    private void addToTable(FixedWord fixedWord) {
        int dx = 0;
        int dy = 0;
        int x = fixedWord.getStartX();
        int y = fixedWord.getStartY();
        if (fixedWord.isHorizontal()) {
            dy = 1;
        } else {
            dx = 1;
        }
        for (int i = 0; i < fixedWord.getWord().length(); i++) {
               table[x][y] = fixedWord.getWord().charAt(i);
               x += dx;
               y += dy;
            }
    }

    public int getPlacedWordsCount() {
        return placedWords.size();
    }

    private boolean tryToPlaceWord(FixedWord fixedWord) {
       // System.out.println("-----------------------------------------");
        int dx = 0;
        int dy = 0;
        int x = fixedWord.getStartX();
        int y = fixedWord.getStartY();
        int crossesCount = 0;
        boolean [][] magicCells = new boolean[tableSize][tableSize];
        if (fixedWord.isHorizontal()) {
            dy = 1;
        } else {
            dx = 1;
        }
        for (int i = 0; i < fixedWord.getWord().length(); i++) {
            if (x < 0 || x >= tableSize || y < 0 || y >= tableSize) {
                //System.out.println("Out of bounds " +fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
                return false;
            }
            if (table[x][y] != 0 && fixedWord.getWord().charAt(i) == table[x][y]) {
                crossesCount++;
            }



            if (table[x][y] != 0 && fixedWord.getWord().charAt(i) != table[x][y]) {
               // System.out.println("Overplaced with other word : " + fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal());
                return false;
            }
            magicCells[x][y] = true;
            //System.out.println("Add magic " + x + " " + y);
            x += dx;
            y += dy;
        }

        if (x >= 0 && x < tableSize && y >= 0 && y < tableSize) {
            if (table[x][y] != 0) {
              //  System.out.println("Overlap " +fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
              //  System.out.println("Overlap value " + (x) + " " + (y) + " " + table[x ][y]);
                return false;
            }
        }

        x = fixedWord.getStartX();
        y = fixedWord.getStartY();
        for (int i = 0; i < fixedWord.getWord().length(); i++) {

            //Checking too close neighbours
            if (table[x][y] == 0) {
                if (x > 0 && table[x - 1][y] != 0 && !magicCells[x - 1][y]) {
                   // System.out.println("Too close neighbour :" + fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
                   // System.out.println(x + " " + y);
                    return false;
                }

                if (y > 0 && table[x][y - 1] != 0 && !magicCells[x][y -1]) {
                  //  System.out.println("Too close neighbour :" +  fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
                   // System.out.println(x + " " + y);
                    return false;
                }

                if (x < tableSize - 1 && table[x + 1][y] != 0 && !magicCells[x + 1][y]) {
                    //System.out.println("Too close neighbour :" +  fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
                   // System.out.println(x + " " + y);
                    return false;
                }

                if (y < tableSize - 1 && table[x][y + 1] != 0 && !magicCells[x][y +1]) {
                    //System.out.println("Too close neighbour :" +  fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
                   // System.out.println(x + " " + y);
                    return false;
                }
            }
            x += dx;
            y += dy;
        }

        //If first word, no crossed are allowed
        if(crossesCount == 0 && placedWords.size() > 0) {
           // System.out.println("No crosses found : " +  fixedWord.getStartY() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
            return false;
        }else if (crossesCount == 0 && placedWords.size() == 0) {
            //System.out.println("First word");
        }
        if (crossesCount == fixedWord.getWord().length()) {
           // System.out.println("Fully overlap " +  fixedWord.getStartY() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
            return false;
        }
        //System.out.println("Good!!! Crosses count" + crossesCount);


        //Checking formerly placed line overlap
        x = fixedWord.getStartX();
        y = fixedWord.getStartY();
        if (fixedWord.isHorizontal()) {
            dy = -1;
        } else {
            dx = -1;
        }
        if (x + dx >= 0 && x + dx < tableSize && y + dy >= 0 && y + dy < tableSize) {
            if (table[x + dx][y + dy] != 0) {
               // System.out.println("Line Overlap " +fixedWord.getStartX() + " " + fixedWord.getStartY() + " " + fixedWord.isHorizontal() );
                return false;
            }
        }
        return true;
    }

    public void print() {

        System.out.println("Words : " + placedWords.size());
        for (int i = 0; i < tableSize; i++) {
            for (int j = 0; j < tableSize; j++) {
                if(table[i][j] == 0) {
                    System.out.print(".");
                }else {
                    System.out.print(table[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("============================================");
    }

    public void writePuzzle(String folder, int levelNum) throws IOException {

        File file = new File(folder + "/" + levelNum + ".txt");

       // System.out.println("Writing Puzzle " + file.getAbsolutePath());
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));

            //Table Size
            output.write(tableSize + "");
            output.newLine();

            //Table
            for(int  i = 0; i < tableSize; i++) {
                for(int j = 0; j < tableSize; j++) {
                    if (table[i][j] == 0) {
                        output.write("0" + " ");
                    } else {
                        output.write(table[i][j] + " ");
                    }

                }
                output.newLine();

            }

            //Seed word
            output.write(seedWord);
            output.newLine();

            //Words
            output.write(placedWords.size() + "");
            output.newLine();
            for(FixedWord word : placedWords) {
                output.write(word.getWord() );
                output.newLine();
                output.write(word.getStartX() +  " " + word.getStartY() + " ");
                if (word.isHorizontal()) {
                    output.write("1");
                }else {
                    output.write("0");
                }
                output.newLine();
            }

            output.close();
        } catch(Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        words.add("стол");
        words.add("сталь");
        words.add("тост");
        words.add("лот");
        words.add("тол");
        words.add("ласт");
        while(true) {
            Crossword crossword = new Crossword(words, words, "восток");
            crossword.print();
            crossword.placeWord("восток");
            crossword.print();
            crossword.placeWord("куртка");
            crossword.print();
            crossword.placeWord("парик");
            crossword.print();
            crossword.placeWord("совок");
            crossword.print();
            crossword.placeWord("воск");
            crossword.print();
            crossword.placeWord("сток");
            crossword.print();
            crossword.placeWord("скот");
            crossword.print();
            crossword.placeWord("кот");
            crossword.print();
            crossword.placeWord("ток");
            crossword.print();
            if (crossword.placedWords.size() >= 9) {
                System.out.println("Done");
                break;
            }
        }


    }
}
