package connections;

import plumber.engine.ConnectionState;

/**
 * Created by Suren on 8/16/17
 */

public class ConnectionCell {

    private int mX;
    private int mY;

    public ConnectionCell(int x, int y) {
        mX = x;
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    @Override
    public int hashCode() {
        return 7 * mX + 11 * mY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ConnectionCell cell = (ConnectionCell) obj;
        return mX == cell.mX && mY == cell.mY;
    }

    public boolean isAdjacent(ConnectionCell cell) {
        if (Math.abs(cell.mX - mX) + Math.abs(cell.mY - mY) == 1) {
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "[" + mX + ", " + mY + "]";
    }
}
