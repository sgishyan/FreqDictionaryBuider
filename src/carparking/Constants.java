package carparking;

import java.util.ArrayList;

/**
 * Created by suren on 4/19/19.
 */
public class Constants {

    static final int GAME_BOARD_SIZE = 6;
    static final int ROWS = 2;
    static final int SIZE = 2;
    static final int LOW_SIZE = 1;
    static final int HIGH_SIZE = 3;


    static final int TOTAL_ITEMS = GAME_BOARD_SIZE * GAME_BOARD_SIZE;
    static final int TARGET_ITEMS = ROWS * GAME_BOARD_SIZE + GAME_BOARD_SIZE - SIZE;
    static final int H = 1;
    static final int V = GAME_BOARD_SIZE;

    static ArrayList<Long> rowMasks() {
        ArrayList<Long> rMasks = new ArrayList<>();
        for (int y = 0; y < GAME_BOARD_SIZE; y++) {
            long mask = 0;
            for (int x = 0; x < GAME_BOARD_SIZE; x++) {
                int i = y * GAME_BOARD_SIZE + x;
                mask |= (long)1 << i;
            }
            rMasks.add(mask);
        }
        return rMasks;
    }

    static ArrayList<Long> columnMasks() {
        ArrayList<Long> cMasks = new ArrayList<>();
        for (int x = 0; x < GAME_BOARD_SIZE; x++) {
            long mask = 0;
            for (int y = 0; y < GAME_BOARD_SIZE; y++) {
                int i = y * GAME_BOARD_SIZE + x;
                mask |= (long)1 << i;
            }
            cMasks.add(mask);
        }
        return cMasks;
    }

    final static long TOP = rowMasks().get(0);
    final static int R_SIZE = rowMasks().size();
    final static long BOTTOM = rowMasks().get(R_SIZE - 1);
    final static int C_SIZE = columnMasks().size();
    final static long LEFT = columnMasks().get(0);
    final static long RIGHT = columnMasks().get(C_SIZE - 1);
}
