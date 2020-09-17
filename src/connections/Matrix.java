package connections;

import java.util.Map;

/**
 * Created by David on 8/16/17
 */

public class Matrix {

    private int[][] mMatrix;





    public Matrix(int size) {

        final int matrixSize =size;
        mMatrix = new int[size][size];
        for (int i = 0; i < matrixSize; ++i) {
            for (int j = 0; j < matrixSize; ++j) {
                mMatrix[i][j] = i + 1;
            }
        }
    }


    public int get(int x, int y) {
        return mMatrix[x][y];
    }

    public int getSize() {
        return mMatrix.length;
    }

    public void set(int x, int y, int value) {
        mMatrix[x][y] = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mMatrix.length; ++i) {
            for (int j = 0; j < mMatrix[i].length; ++j) {
                builder.append(String.format("%10s", mMatrix[i][j] + ""));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
