package connections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Suren on 8/16/17
 */

/**
 * This class describes the connection of one color.
 */
public class LinkedConnection {

    private int mColor;
    List<ConnectionCell> mCells;
    private boolean mContainsLastNode;

    /**
     * Creates a connection from one node element and the specified color.
     *
     * @param nodeX - the x coordinate of node
     * @param nodeY - the y coordinate of node
     * @param color - node color
     */
    public LinkedConnection(int nodeX, int nodeY, int color) {
        mColor = color;
        mCells = new ArrayList<>();
        mCells.add(new ConnectionCell(nodeX, nodeY));
        mContainsLastNode = false;
    }


    public LinkedConnection(int row, int color, int size, boolean isSquare) {
        mColor = color;
        mCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mCells.add(new ConnectionCell(row, i));
        }
    }

    public LinkedConnection(int startCell, int color, int size, int length) {
        mColor = color;
        mCells = new ArrayList<>();


        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < size; j++) {
                    if (counter >= startCell && counter < startCell + length) {
                        mCells.add(new ConnectionCell(i, j));
                    }
                    counter++;
                }
            } else {
                for (int j = size - 1; j >= 0; j--) {
                    if (counter >= startCell && counter < startCell + length) {
                        mCells.add(new ConnectionCell(i, j));
                    }
                    counter++;
                }
            }
        }

    }

    public LinkedConnection(LinkedConnection connection) {
        mColor = connection.mColor;
        mCells = new ArrayList<>(connection.mCells.size());
        for (ConnectionCell cell : connection.mCells) {
            mCells.add(new ConnectionCell(cell.getX(), cell.getY()));
        }
        mContainsLastNode = connection.mContainsLastNode;
    }

    public LinkedConnection(LinkedConnection operandConnection, int index, int minColor) {
        mColor = minColor;
        mCells = new ArrayList<>();
        for (int i = index; i < operandConnection.getLength(); i++) {
            mCells.add(operandConnection.mCells.get(i));
        }
    }

    public boolean containsCell(int x, int y) {
        for (ConnectionCell cell : mCells) {
            if (cell.getX() == x && cell.getY() == y) {
                return true;
            }
        }
        return false;
    }

    void add(int x, int y) {
        if (!containsCell(x, y)) {
            mCells.add(new ConnectionCell(x, y));
        }
    }

    void setContainsLastNode(boolean containsLastNode) {
        mContainsLastNode = containsLastNode;
    }

    List<ConnectionCell> cut(int x, int y) {
        System.out.println("-----LINKED_CONNECTION-----");
        System.out.println("----------CUT----------");
        System.out.println("x = " + x + ", y = " + y);

        List<ConnectionCell> removedCells = new ArrayList<>();
        ConnectionCell cell;

        if (mContainsLastNode) {
            int i = 0;
            int size = mCells.size();
            while (i < size) {
                cell = mCells.get(i);
                if (cell.getX() == x && cell.getY() == y) {
                    break;
                }
                ++i;
            }

            int j = size - i - 1;
            if (i < j) {
                while (i > 0) {
                    removedCells.add(mCells.remove(0));
                    --i;
                }
                for (i = 0, size = mCells.size(); i < size / 2; ++i) {
                    cell = mCells.get(i);
                    mCells.set(i, mCells.get(size - i - 1));
                    mCells.set(size - i - 1, cell);
                }
            } else {
                while (j > 0) {
                    removedCells.add(mCells.remove(i + 1));
                    --j;
                }
            }
            mContainsLastNode = false;

        } else {
                 for (ListIterator<ConnectionCell> iter = mCells.listIterator(mCells.size()); iter.hasPrevious(); ) {
            cell = iter.previous();
            if (cell.getX() == x && cell.getY() == y) {
                break;
            }
            removedCells.add(cell);
            iter.remove();
        }
    }

        if (mCells.size() == 1) {
            Logic.getConnectionPool().removeConnection(this);
        }

        System.out.println("removedCells = " + Arrays.toString(removedCells.toArray()));
        System.out.println("mContainsLastNode = " + mContainsLastNode);
        System.out.println("----------CUT-end----------");

        return removedCells;
    }

    List<ConnectionCell> cutAndRemoveCurrentCell(int x, int y) {
        System.out.println("-----LINKED_CONNECTION-----");
        System.out.println("----------CUT_AND_REMOVE----------");
        System.out.println("x = " + x + ", y = " + y);

        List<ConnectionCell> removedCells = cut(x, y);
        ConnectionCell cell;
        for (Iterator<ConnectionCell> iter = mCells.iterator(); iter.hasNext(); ) {
            cell = iter.next();
            if (cell.getX() == x && cell.getY() == y) {
                removedCells.add(cell);
                iter.remove();
                break;
            }
        }
        if (mCells.size() == 1) {
            Logic.getConnectionPool().removeConnection(this);
        }

        System.out.println("removedCells = " + Arrays.toString(removedCells.toArray()));
        System.out.println("----------CUT_AND_REMOVE-end----------");

        return removedCells;
    }

    public LinkedConnection merge(LinkedConnection connection) {
        System.out.println("-----LINKED_CONNECTION-----");
        System.out.println("----------MERGE----------");
        System.out.println("this = " + this);
        System.out.println("connection = " + connection);

        ConnectionCell cell;
        for (ListIterator<ConnectionCell> iter = connection.mCells.listIterator(connection.mCells.size());
             iter.hasPrevious(); ) {
            cell = iter.previous();
            if (!mCells.contains(cell)) {
                mCells.add(cell);
            }
        }
        mContainsLastNode = true;

        System.out.println("this = " + this);
        System.out.println("----------MERGE-end----------");

        return this;
    }

    public int getLength() {
        return mCells.size();
    }

    public int getColor(){
        return mColor;
    }

    public  List<ConnectionCell> getCells(){
        return  mCells;
    }

    public boolean isConnected() {
        return mContainsLastNode;
    }

    @Override
    public int hashCode() {
        int hash = 31 * mColor
                + 11 * (mContainsLastNode ? 1 : 0);

        for (ConnectionCell cell : mCells) {
            hash += cell.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }


        LinkedConnection con = (LinkedConnection) obj;
        boolean isCellsEquals = mCells.size() == con.mCells.size();

        for (int i = 0, size = mCells.size(); i < size && isCellsEquals; i++) {
            if (!con.mCells.contains(mCells.get(i))) {
                isCellsEquals = false;
            }
        }

        return mColor == con.mColor
                && mContainsLastNode == con.mContainsLastNode
                && isCellsEquals;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ConnectionCell cell : mCells) {
            builder.append(cell.toString());
            builder.append(" ");
        }
        builder.append("\n");
        return builder.toString();
    }

    public ConnectionCell getFirstEnd() {
        return  mCells.get(0);
    }

    public ConnectionCell getLastEnd() {
        return  mCells.get(mCells.size() - 1);
    }

    public void removeFirstEnd() {
        mCells.remove(0);
    }

    public void removeLastEnd() {
        mCells.remove(mCells.size() - 1);
    }

    public int numberOfAdjacentCells(ConnectionCell cell) {
        int totalNeighbours = 0;
        for (int i = 0; i < mCells.size(); i++) {
            if (mCells.get(i).isAdjacent(cell)) {
                totalNeighbours++;
            }
        }
        return totalNeighbours;
    }

    public boolean canBeAddedAtStart(ConnectionCell cell) {
        if (numberOfAdjacentCells(cell) == 1 && getFirstEnd().isAdjacent(cell)) {
            return true;
        }
        return false;
    }

    public boolean canBeAddedAtEnd(ConnectionCell cell) {
        if (numberOfAdjacentCells(cell) == 1 && getLastEnd().isAdjacent(cell)) {
            return true;
        }
        return false;
    }

    public void addStart(int x, int y) {
        mCells.add(0, new ConnectionCell(x, y));
    }

    public void addEnd(int x, int y) {
        mCells.add(new ConnectionCell(x, y));
    }



    public boolean canBeJoinedAtStart(LinkedConnection operandConnection, boolean isOperandFromStart) {
        //Checking if the first cells of active and operand connections are adjacent
        if (isOperandFromStart) {
            if (getFirstEnd().isAdjacent(operandConnection.getFirstEnd())) {
                //All other cells must not be adjacent
                for(int i = 1; i < operandConnection.getLength(); i++) {
                    if(numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0)
                        return false;
                }

                for(int i = 1; i < mCells.size(); i++) {
                    if(operandConnection.numberOfAdjacentCells(mCells.get(i)) > 0)
                        return false;
                }

            } else {
                return false;
            }

        } else {
            if (getFirstEnd().isAdjacent(operandConnection.getLastEnd())) {
                //All other cells must not be adjacent
                for(int i = 0; i < operandConnection.getLength() - 1; i++) {
                    if(numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0)
                        return false;
                }

                for(int i = 1; i < mCells.size(); i++) {
                    if(operandConnection.numberOfAdjacentCells(mCells.get(i)) > 0)
                        return false;
                }
            } else {
                return false;
            }


        }
        return true;
    }

    public boolean canBeJoinedAtEnd(LinkedConnection operandConnection, boolean isOperandFromStart) {
        //Checking if the first cells of active and operand connections are adjacent
        if (isOperandFromStart) {
            if (getLastEnd().isAdjacent(operandConnection.getFirstEnd())) {
                //All other cells must not be adjacent
                for(int i = 1; i < operandConnection.getLength(); i++) {
                    if(numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0)
                        return false;
                }

                for(int i = 0; i < mCells.size() - 1; i++) {
                    if(operandConnection.numberOfAdjacentCells(mCells.get(i)) > 0)
                        return false;
                }
            }
            else {
                return false;
            }
        } else {
            if (getLastEnd().isAdjacent(operandConnection.getLastEnd())) {
                //All other cells must not be adjacent
                for(int i = 0; i < operandConnection.getLength() - 1; i++) {
                    if(numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0)
                        return false;
                }

                for(int i = 0; i < mCells.size() - 1; i++) {
                    if(operandConnection.numberOfAdjacentCells(mCells.get(i)) > 0)
                        return false;
                }
            }
            else {
                return false;
            }

        }
        return true;
    }

    public void joinStartStart(LinkedConnection operandConnection) {
        for(int i = 0; i < operandConnection.getLength(); i++) {
            mCells.add(0, operandConnection.mCells.get(i));
        }
    }

    public void joinStartEnd(LinkedConnection operandConnection) {
        for(int i = operandConnection.getLength() - 1; i >= 0; i--) {
            mCells.add(0, operandConnection.mCells.get(i));
        }
    }

    public void joinEndStart(LinkedConnection operandConnection) {
        for(int i = 0; i < operandConnection.getLength(); i++) {
            mCells.add(operandConnection.mCells.get(i));
        }
    }

    public void joinEndEnd(LinkedConnection operandConnection) {
        for(int i = operandConnection.getLength() - 1; i >= 0; i--) {
            mCells.add(operandConnection.mCells.get(i));
        }
    }

    /*
        Checks if we can split the connection onto 2 connections
        minLength shows the minimal length of target connection
     */
    public int canSplitAtStart(LinkedConnection operandConnection, int minLength) {
        //Connection with length less than 3 are not allowed
        outer:
        for(int i = minLength; i < operandConnection.getLength() - minLength; i++) {
            if (getFirstEnd().isAdjacent(operandConnection.mCells.get(i))) {
                //Other cells must not be adjacent
                for(int j = 1; j < mCells.size(); j++) {
                    if (mCells.get(j).isAdjacent(operandConnection.mCells.get(i))) {
                        continue outer;
                    }
                }
                if (i < 4 && operandConnection.getLength() - i < 4  ) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public int canSplitAtEnd(LinkedConnection operandConnection, int minLength) {
        //Connection with length less than 3 are not allowed
        outer:
        for(int i = minLength; i < operandConnection.getLength() - minLength; i++) {
            if (getLastEnd().isAdjacent(operandConnection.mCells.get(i))) {
                for(int j = 0; j < mCells.size() - 1; j++) {
                    if (mCells.get(j).isAdjacent(operandConnection.mCells.get(i))) {
                        continue outer;
                    }
                }
                if (i < 4 && operandConnection.getLength() - i < 4  ) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    public void deleteEnd(int index) {
        for(int i = mCells.size() - 1; i >= index; i-- )
        mCells.remove(i);
    }

    public void deleteStart(int index) {
        for(int i = 0; i <= index; i++ )
            mCells.remove(0);
    }

    public void setColor(int color) {
        this.mColor = color;
    }
}
