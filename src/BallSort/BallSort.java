package BallSort;



import java.util.ArrayList;
import java.util.Random;

public class BallSort {
    public static void main(String[] args) {
        int [][] board;
        int colors = 4;
        int width = colors + 2;
        int height = 4;


        board = new int[height][width];
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < colors; j++) {
                board[i][j] = j + 1;
            }
        }

        Random rand = new Random();
        int targetIndex = rand.nextInt(width);

        printBoard(board, height, width);

    }

    public static void printBoard(int[][] board, int height, int width) {
        System.out.println("------------------");
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

}
