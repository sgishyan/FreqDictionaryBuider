package BallSort;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Game {
    public int colors;
    public Flask[] flasks;
    public int width;
    public int height;
    public Random rand;
    HashSet<State> states = new HashSet<>();
    Queue<State> queue = new LinkedList<>();


    public Game(int colors, int height) {
        this.colors = colors;
        this.height = height;
        width = colors + 2;
        flasks = new Flask[width];
        for (int i=0; i<width;i++) {
            if (i < colors) {
                flasks[i] = new Flask(height, false, i + 1);
            }else {
                flasks[i] = new Flask(height, true, 0);

            }
        }
        rand = new Random();
    }
    public Game(State state) {
        this.colors = state.width - 2;
        this.height = state.height;
        this.width = state.width;
        flasks = new Flask[width];
      
        rand = new Random();
    }

    public void printBoard() {
        for (int i = 0; i < height;i++) {
            for(int j = 0; j < width; j++) {
                System.out.print(flasks[j].container[i]);

            }
            System.out.println();
        }

    }

    public void getAllStates() {

    }
    public void makeReverseMove() {
        while(true){
            int ball;
            int indexIn = rand.nextInt(width);
            int indexOut = 0;
            if (flasks[indexIn].canReverseMove()) {
                System.out.println("Move from " + indexIn );
                ball = flasks[indexIn].pop();
                System.out.println(ball);
            }else {
                continue;
            }

            while(true) {
                indexOut = rand.nextInt(width);
                if(indexIn == indexOut) continue;
                if(flasks[indexOut].isFull()) continue;
                flasks[indexOut].push(ball);
                System.out.println("To " + indexOut);
                return;
            }

        }
    }

    public static void main(String[] args) {
        Game game = new Game(4,4);
        for(int i = 0; i < 1000; i++) {
            System.out.println("move : " + i);
            game.makeReverseMove();
            game.printBoard();
        }

    }

    static class State implements Comparable<State> {
        int height;
        int width;
        int[][] board;
        String stringBoard;

        public State(int height, int width, int[][] board) {
            this.height = height;
            this.width = width;
            StringBuilder sb = new StringBuilder();
            this.board = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    this.board[i][j] = board[i][j];
                    sb.append(board[i][j]);
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return height == state.height &&
                    width == state.width &&
                    Arrays.equals(board, state.board) &&
                    stringBoard.equals(state.stringBoard);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stringBoard);
        }

        @Override
        public int compareTo(State state) {
            return stringBoard.compareTo(state.stringBoard);
        }
    }
}
