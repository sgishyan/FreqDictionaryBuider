package connections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Suren on 8/16/17
 */

public class ConnectionPool {

    private Matrix mMatrix;
    private List<LinkedConnection> mConnections;

    public ConnectionPool(Matrix matrix) {
        mMatrix = matrix;
        mConnections = new ArrayList<>();
    }



    public ConnectionPool(int size) {
        mConnections = new ArrayList<>(size);
        mMatrix = new Matrix(size);
        for(int row = 0; row < size; row++) {
            mConnections.add(new LinkedConnection(row, row + 1, size, true));
        }
    }


    public ConnectionPool(int size, int count) {
        String filename = "/home/suren/Documents/temp/connections/templates/";
        if (size == 10 && count == 7) {
            filename += "is107";
        }




        mConnections = new ArrayList<>(size);
        mMatrix = new Matrix(size);
        int length = size * size / count;
        int largeLength = size * size - (count - 1) * length;
        for(int i = 0; i < count - 1; i++) {
            mConnections.add(new LinkedConnection(i * count, i, size, length));
        }
        mConnections.add(new LinkedConnection((count - 1) * count, count - 1, size, largeLength));

    }

    public boolean join(int path1, int path2) {
        LinkedConnection activeConnection = mConnections.get(path1);
        LinkedConnection operandConnection = mConnections.get(path2);


        if(activeConnection.canBeJoinedAtStart(operandConnection, true)) {
            activeConnection.joinStartStart(operandConnection);
            mConnections.remove(path2);
            //System.out.println("Join paths " + path1 + " " + path2);
           // System.out.println("Start start");
            return true;
        }

        if(activeConnection.canBeJoinedAtStart(operandConnection, false)) {
            activeConnection.joinStartEnd(operandConnection);
            mConnections.remove(path2);
           // System.out.println("Join paths " + path1 + " " + path2);
           // System.out.println("Start end");
            return true;
        }

        if(activeConnection.canBeJoinedAtEnd(operandConnection, true)) {
            activeConnection.joinEndStart(operandConnection);
            mConnections.remove(path2);
           // System.out.println("Join paths " + path1 + " " + path2);
            //System.out.println("end start");
            return true;
        }

        if(activeConnection.canBeJoinedAtEnd(operandConnection, false)) {
            activeConnection.joinEndEnd(operandConnection);
            mConnections.remove(path2);
            //System.out.println("Join paths " + path1 + " " + path2);
           // System.out.println("end end");
            return true;
        }

        return false;
    }

    public boolean splitAndJoin(int path1, int path2) {
        LinkedConnection activeConnection = mConnections.get(path1);
        LinkedConnection operandConnection = mConnections.get(path2);
        boolean isNotPossible = false;
        int splitStartIndex = activeConnection.canSplitAtStart(operandConnection, 1);
        if (splitStartIndex > 0) {
            //Checking if we can join

            //Small first part
            if(splitStartIndex < 4) {
                for (int i = 0; i < splitStartIndex; i++ ) {
                    if (activeConnection.numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0) {
                        isNotPossible = true;
                        break;
                    }
                }
                if (!isNotPossible) {
                    for (int i = splitStartIndex; i >= 0; i-- ) {
                        activeConnection.addStart(operandConnection.mCells.get(i).getX(), operandConnection.mCells.get(i).getY());
                    }
                    int oldLength = operandConnection.getLength();
                    operandConnection.deleteStart(splitStartIndex);
                    if (operandConnection.mCells.size() < 3) {
                        System.out.println("-------------------------KILLING--------------------" + oldLength + " ");
                    }
                    return true;
                }
            }
            isNotPossible = false;
            //Small end part
            if(splitStartIndex > operandConnection.mCells.size() - 4) {
                for (int i = splitStartIndex + 1 ; i < operandConnection.mCells.size(); i++ ) {
                    if (activeConnection.numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0) {
                        isNotPossible = true;
                        break;
                    }
                }
                if (!isNotPossible) {
                    for (int i = splitStartIndex; i < operandConnection.mCells.size(); i++ ) {
                        activeConnection.addStart(operandConnection.mCells.get(i).getX(), operandConnection.mCells.get(i).getY());
                    }
                    int oldLength = operandConnection.getLength();
                    operandConnection.deleteEnd(splitStartIndex);
                    if (operandConnection.mCells.size() < 3) {
                        System.out.println("-------------------------KILLING--------------------" + oldLength + " ");
                    }

                    return true;
                }
            }
        }

        isNotPossible = false;
        int splitEndIndex = activeConnection.canSplitAtEnd(operandConnection, 1);
        if (splitEndIndex > 0) {
            //Checking if we can join

            //Small first part
            if(splitEndIndex < 4) {
                for (int i = 0; i < splitEndIndex; i++ ) {
                    if (activeConnection.numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0) {
                        isNotPossible = true;
                        break;
                    }
                }
                if (!isNotPossible) {
                    for (int i = splitEndIndex; i >= 0; i-- ) {
                        activeConnection.addEnd(operandConnection.mCells.get(i).getX(), operandConnection.mCells.get(i).getY());
                    }
                    operandConnection.deleteStart(splitEndIndex);
                    return true;
                }
            }

            isNotPossible = false;
            //Small end part
            if(splitEndIndex > operandConnection.mCells.size() - 4) {
                for (int i = splitEndIndex + 1 ; i < operandConnection.mCells.size(); i++ ) {
                    if (activeConnection.numberOfAdjacentCells(operandConnection.mCells.get(i)) > 0) {
                        isNotPossible = true;
                        break;
                    }
                }
                if (!isNotPossible) {
                    for (int i = splitEndIndex; i < operandConnection.mCells.size(); i++ ) {
                        activeConnection.addEnd(operandConnection.mCells.get(i).getX(), operandConnection.mCells.get(i).getY());
                    }
                    operandConnection.deleteEnd(splitEndIndex);
                    return true;
                }
            }

        }
        return false;

    }

    public boolean split(int path1, int path2) {
        LinkedConnection activeConnection = mConnections.get(path1);
        LinkedConnection operandConnection = mConnections.get(path2);

        int splitStartIndex = activeConnection.canSplitAtStart(operandConnection, 3);
        if (splitStartIndex > 0) {
            activeConnection.addStart(operandConnection.mCells.get(splitStartIndex).getX(), operandConnection.mCells.get(splitStartIndex).getY());
            mConnections.add(new LinkedConnection(operandConnection, splitStartIndex + 1, findMinColor()));
            operandConnection.deleteEnd(splitStartIndex);
            return true;
        }


        int splitEndIndex = activeConnection.canSplitAtEnd(operandConnection, 3);
        if (splitEndIndex > 0) {
            activeConnection.addEnd(operandConnection.mCells.get(splitEndIndex).getX(), operandConnection.mCells.get(splitEndIndex).getY());
            mConnections.add(new LinkedConnection(operandConnection, splitEndIndex + 1, findMinColor()));
            operandConnection.deleteEnd(splitEndIndex);
            return true;
        }

        return false;

    }

    private int findMinColor() {
        int color = 1;
        loop:
        while (true) {
            for (int i = 0; i < mConnections.size(); i++) {
                if (mConnections.get(i).getColor() == color) {
                    color++;
                    continue loop;
                }
            }
            break;
        }

        return color;
    }

    public boolean shift(int path1, int path2) {
        LinkedConnection activeConnection = mConnections.get(path1);
        LinkedConnection operandConnection = mConnections.get(path2);

        if (operandConnection.getLength() <= 3) {
            return false;
        }

        if(activeConnection.canBeAddedAtStart(operandConnection.getFirstEnd())) {
            activeConnection.addStart(operandConnection.getFirstEnd().getX(), operandConnection.getFirstEnd().getY());
            operandConnection.removeFirstEnd();
            return true;
        }

        if(activeConnection.canBeAddedAtEnd(operandConnection.getFirstEnd())) {
            activeConnection.addEnd(operandConnection.getFirstEnd().getX(), operandConnection.getFirstEnd().getY());
            operandConnection.removeFirstEnd();
            return true;
        }



        if(activeConnection.canBeAddedAtStart(operandConnection.getLastEnd())) {
            activeConnection.addStart(operandConnection.getLastEnd().getX(), operandConnection.getLastEnd().getY());
            operandConnection.removeLastEnd();
            return true;
        }

        if(activeConnection.canBeAddedAtEnd(operandConnection.getLastEnd())) {
            activeConnection.addEnd(operandConnection.getLastEnd().getX(), operandConnection.getLastEnd().getY());
            operandConnection.removeLastEnd();
            return true;
        }

        return false;
    }

    public Matrix getMatrix() {
        return mMatrix;
    }

    public boolean contains(LinkedConnection connection) {
        System.out.println("-----CONNECTION_POOL-----");
        System.out.println("----------CONTAINS----------");
        System.out.println("this = " + this);
        System.out.println("connection = " + connection);

        boolean isContains = mConnections.contains(connection);

        System.out.println("isContains = " + isContains);
        System.out.println("----------CONTAINS-end----------");

        return isContains;
    }

    public LinkedConnection getConnectionByIndex(int index) {
        return mConnections.get(index);
    }
    public LinkedConnection getConnection(int x, int y) {
        for (LinkedConnection con : mConnections) {
            if (con.containsCell(x, y)) {
                return con;
            }
        }
        return null;
    }

    public LinkedConnection removeConnection(int x, int y) {
        System.out.println("-----CONNECTION_POOL-----");
        System.out.println("----------REMOVE_CONNECTION----------");
        System.out.println("x = " + x + ", y = " + y);

        LinkedConnection connection;
        for (Iterator<LinkedConnection> iterator = mConnections.iterator(); iterator.hasNext(); ) {
            connection = iterator.next();
            if (connection.containsCell(x, y)) {
                iterator.remove();
                Matrix matrix = Logic.getMatrix();
                for (ConnectionCell cell : connection.getCells()) {
                    if (matrix.get(cell.getX(), cell.getY()) > 0) {
                        matrix.set(cell.getX(), cell.getY(), 0);
                    }
                }
                System.out.println("----------REMOVE_CONNECTION-end----------");
                return connection;
            }
        }

        System.out.println("----------REMOVE_CONNECTION-end----------");
        return null;
    }

    public boolean removeConnection(LinkedConnection connection) {
        System.out.println("-----CONNECTION_POOL-----");
        System.out.println("----------REMOVE_CONNECTION----------");
        System.out.println("connection = " + connection);

        boolean isRemoved = mConnections.remove(connection);
        System.out.println("isRemoved = " + isRemoved);

        if (isRemoved) {
            Matrix matrix = Logic.getMatrix();
            for (ConnectionCell cell : connection.getCells()) {
                if (matrix.get(cell.getX(), cell.getY()) > 0) {
                    matrix.set(cell.getX(), cell.getY(), 0);
                }
            }
        }

        System.out.println("----------REMOVE_CONNECTION-end----------");
        return isRemoved;
    }

    public List<LinkedConnection> getConnections() {
        return mConnections;
    }

//    public boolean add(int x1, int y1, int x2, int y2) {
//        System.out.println("-----CONNECTION_POOL-----");
//        System.out.println("----------ADD----------");
//        System.out.println("x1 = " + x1 + ", y1 = " + y1);
//        System.out.println("x2 = " + x2 + ", y2 = " + y2);
//
//        Matrix matrix = Logic.getMatrix();
//        LinkedConnection connection = getConnection(x1, y1);
//        if (connection == null) {
//            connection = new LinkedConnection(x1, y1, matrix.get(x1, y1).getColor());
//        }
//
//        System.out.println("connection = " + connection);
//
//        switch (matrix.get(x2, y2)) {
//            case EMPTY:
//              /*  if (connection.isConnected()) {
//                    if (matrix.get(x1, y1) == MatrixCell.CONNECTION_CELL) {
//                        cut(x1, y1);
//                    } else {
//                        removeConnection(connection);
//                        connection = new LinkedConnection(x1, y1, matrix.get(x1, y1).getColor());
//                    }
//                }*/
//              if(!connection.isConnected()) {
//                  connection.add(x2, y2);
//                  matrix.set(x2, y2, MatrixCell.CONNECTION_CELL);
//                  if (!mConnections.contains(connection)) {
//                      mConnections.add(connection);
//                  }
//                  return true;
//              }
//                break;
//            default:
//                System.out.println("add node");
//                if (connection.containsCell(x2, y2)) {
//                    removeConnection(connection);
//                } else {
//                    removeConnection(x2, y2);
//                    connection.add(x2, y2);
//                    connection.setContainsLastNode(true);
//                    System.out.println("connection.isConnected() = "+ connection.isConnected());
//                    return true;
//                }
//        }
//
//        System.out.println("----------ADD-end----------");
//        return false;
//}

    public void merge(int x1, int y1, int x2, int y2) {
        System.out.println("-----CONNECTION_POOL-----");
        System.out.println("----------MERGE----------");

        LinkedConnection connection1 = getConnection(x1, y1);
        LinkedConnection connection2 = getConnection(x2, y2);

        System.out.println("connection1 = " + connection1);
        System.out.println("connection2 = " + connection2);

        if (connection1 == null) {
            connection1 = new LinkedConnection(x1, y1, Logic.getMatrix().get(x1, y1));
            mConnections.add(connection1);
        }

        cut(x1, y1);
        cut(x2, y2);
        connection2.merge(connection1);
        mConnections.remove(connection1);

        System.out.println("----------MERGE-end----------");
    }

    public void cut(int x, int y) {
        System.out.println("-----CONNECTION_POOL-----");
        System.out.println("----------CUT----------");
        System.out.println("x = " + x + ", y = " + y);

        Matrix matrix = Logic.getMatrix();
        System.out.println("matrix.get(x, y) = " + matrix.get(x, y));

        for (ConnectionCell cell : getConnection(x, y).cut(x, y)) {
            if (matrix.get(cell.getX(), cell.getY()) > 0) {
                matrix.set(cell.getX(), cell.getY(), 0);
            }
        }

        System.out.println("----------CUT-end----------");
    }

    public void cutAndRemoveCurrentCell(int x, int y) {
        System.out.println("-----CONNECTION_POOL-----");
        System.out.println("----------CUT_AND_REMOVE----------");
        System.out.println("x = " + x + ", y = " + y);

        Matrix matrix = Logic.getMatrix();
        for (ConnectionCell cell : getConnection(x, y).cutAndRemoveCurrentCell(x, y)) {
            if (matrix.get(cell.getX(), cell.getY()) >0) {
                matrix.set(cell.getX(), cell.getY(), 0);
            }
        }

        System.out.println("----------CUT_AND_REMOVE-end----------");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (LinkedConnection con : mConnections) {
            builder.append(con.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public int count() {
        return mConnections.size();
    }
}
