package connections;

import java.io.*;
import java.util.Random;

/**
 * Created by suren on 8/29/17.
 */
public class Generator {

    private static final int MAX_ITERATIONS = 1000000;
    private final int mMinLongestPath;
    private final int mBorder;
    private final int mMaxPointsDistance;
    private int mComplexity;
    private ConnectionPool mPaths;
    private int mSize;
    private int mTargetPathsCount;
    private int mStepsCount;
    private int[][] matrix;
    private Random mRand;
    private int mMinPointsDistance;
    private int mUpperLimit;

    public Generator(int size, int paths, int steps, int complexity, int minLongestPath, int minPointsDistance, int maxPointsDistance, int upperLimit, int border) {
        mSize = size;
        mTargetPathsCount = paths;
        mUpperLimit = upperLimit;
        mStepsCount = steps;
        mMinPointsDistance = minPointsDistance;
        mMaxPointsDistance = maxPointsDistance;
        mPaths = new ConnectionPool(mSize);
        mBorder = border;
        mRand = new Random();
        matrix = new int[size][size];
        mComplexity = complexity;
        mMinLongestPath = minLongestPath;
    }


    public boolean generate() {

        int activePathIndex = 0;
        int complexity = 0;
        int minLongestPath = 0;
        int minPointsDistance = Integer.MAX_VALUE;
        boolean haveBorder = false;
        int maxPointDistance = Integer.MAX_VALUE;

        for (int steps = 0; (!haveBorder) || minPointsDistance < mMinPointsDistance || maxPointDistance > mMaxPointsDistance || steps < mStepsCount || complexity < mComplexity || minLongestPath < mMinLongestPath || mPaths.count() != mTargetPathsCount; steps++) {

            if (steps > MAX_ITERATIONS) {
                return false;
            }

            int pathsCount = mPaths.count();

            //Selecting random path
            activePathIndex = mRand.nextInt(pathsCount);
            print(steps, activePathIndex);


            //Selecting some other path and making some operation with these two paths
            for (int currentPathIndex = 0; currentPathIndex < pathsCount; currentPathIndex++) {
                if (activePathIndex == currentPathIndex) {
                    continue;
                }

                if (pathsCount < 3) {
                    if (mPaths.split(activePathIndex, currentPathIndex)) {
                        //  System.out.println("SPLIT");
                        break;
                    }

                    if (mPaths.shift(activePathIndex, currentPathIndex)) {
                        // System.out.println("SHIFT");
                        break;
                    }

                    if (mPaths.splitAndJoin(activePathIndex, currentPathIndex)) {
                        //  System.out.println("S&J");
                        break;
                    }

                }

                if (pathsCount >= mTargetPathsCount + mUpperLimit) {

                    if (mPaths.join(activePathIndex, currentPathIndex)) {
                        //   System.out.println("JOIN");
                        break;
                    }


                    if (mPaths.shift(activePathIndex, currentPathIndex)) {
                        //   System.out.println("SHIFT");
                        break;
                    }

                    if (mPaths.splitAndJoin(activePathIndex, currentPathIndex)) {
                        //  System.out.println("S&J");
                        break;
                    }


                }

                if (pathsCount < mTargetPathsCount) {
                    if (mPaths.split(activePathIndex, currentPathIndex)) {
                        //  System.out.println("SPLIT");
                        break;
                    }

                    if (mPaths.shift(activePathIndex, currentPathIndex)) {
                        //  System.out.println("SHIFT");
                        break;
                    }

                    if (mPaths.splitAndJoin(activePathIndex, currentPathIndex)) {
                        // System.out.println("S&J");
                        break;
                    }

                    if (mPaths.join(activePathIndex, currentPathIndex)) {
                        //  System.out.println("JOIN");
                        break;
                    }
                }

                if (pathsCount >= mTargetPathsCount) {

                    if (mPaths.join(activePathIndex, currentPathIndex)) {
                        //  System.out.println("JOIN");
                        break;
                    }

                    if (mPaths.split(activePathIndex, currentPathIndex)) {
                        // System.out.println("SPLIT");
                        break;
                    }

                    if (mPaths.splitAndJoin(activePathIndex, currentPathIndex)) {
                        //  System.out.println("S&J");
                        break;
                    }

                    if (mPaths.shift(activePathIndex, currentPathIndex)) {
                        // System.out.println("SHIFT");
                        break;
                    }


                }
                //System.out.println("NO MOVE");
            }

            complexity = 0;
            minLongestPath = 0;
            minPointsDistance = Integer.MAX_VALUE;
            haveBorder = true;
            int maxX = 0;
            int minX = Integer.MAX_VALUE;
            int maxY = 0;
            int minY = Integer.MAX_VALUE;

            for (LinkedConnection path : mPaths.getConnections()) {
                if (path.getLength() > minLongestPath) {
                    minLongestPath = path.getLength();
                }

                int x0 = path.mCells.get(0).getX();
                int x1 = path.mCells.get(path.mCells.size() - 1).getX();
                int y0 = path.mCells.get(0).getY();
                int y1 = path.mCells.get(path.mCells.size() - 1).getY();

                //Checking max and min coordinates
                if (x0 < minX) {
                    minX = x0;
                }
                if (x1 < minX) {
                    minX = x1;
                }

                if (x0 > maxX) {
                    maxX = x0;
                }
                if (x1 > maxX) {
                    maxX = x1;
                }


                if (y0 < minY) {
                    minY = y0;
                }
                if (y1 < minY) {
                    minY = y1;
                }


                if (y0 > maxY) {
                    maxY = y0;
                }
                if (y1 > maxY) {
                    maxY = y1;
                }

                if (x0 < mBorder || x0 > (mSize - 1 - mBorder) || x1 < mBorder || x1 > (mSize - 1 - mBorder) || y0 < mBorder || y0 > (mSize - 1 - mBorder) || y1 < mBorder || y1 > (mSize - 1 - mBorder)) {
                    haveBorder = false;
                }
                if (Math.abs(x1 - x0) + Math.abs(y1 - y0) < minPointsDistance) {
                    minPointsDistance = Math.abs(x1 - x0) + Math.abs(y1 - y0);
                }
                for (int i = 0; i < path.mCells.size() - 3; i++) {
                    if (path.mCells.get(i).getX() == path.mCells.get(i + 1).getX() && (path.mCells.get(i).getX() == path.mCells.get(i + 2).getX()) ||
                            path.mCells.get(i).getY() == path.mCells.get(i + 1).getY() && (path.mCells.get(i).getY() == path.mCells.get(i + 2).getY())) {
                        continue;
                    }
                    complexity++;
                }
            }
            maxPointDistance = Math.abs(maxX - minX) + Math.abs(maxY - minY);
        }


        System.out.println("Min Points distance " + minPointsDistance + " Max points distance " + maxPointDistance + " Complexity " + complexity);
        return true;

    }

    public void print(int step, int index) {

        for (int i = 0; i < mSize; i++) {
            for (int j = 0; j < mSize; j++) {
                matrix[i][j] = -1000;
            }
        }
        int complexity = 0;
        for (LinkedConnection path : mPaths.getConnections()) {
            for (int i = 0; i < path.mCells.size() - 3; i++) {
                if (path.mCells.get(i).getX() == path.mCells.get(i + 1).getX() && (path.mCells.get(i).getX() == path.mCells.get(i + 2).getX()) ||
                        path.mCells.get(i).getY() == path.mCells.get(i + 1).getY() && (path.mCells.get(i).getY() == path.mCells.get(i + 2).getY())) {
                    continue;
                }
                complexity++;
            }
        }

        for (LinkedConnection path : mPaths.getConnections()) {
            for (ConnectionCell cell : path.getCells()) {
                if (matrix[cell.getX()][cell.getY()] != -1000) {
                    // System.out.println("!!!!!!!!!!!!!!! ALARM !!!!!!!!!!!!!!");
                    System.out.println(cell.getX() + " " + cell.getY() + " " + matrix[cell.getX()][cell.getY()] + " " + path.getColor());
                }
                matrix[cell.getX()][cell.getY()] = path.getColor();
            }
        }


        //Checking matrix correctness
        for (int i = 0; i < mSize - 2; i++) {
            for (int j = 0; j < mSize - 2; j++) {

                if (matrix[i][j] == matrix[i + 1][j] &&
                        matrix[i][j] == matrix[i][j + 1] &&
                        matrix[i][j] == matrix[i + 1][j + 1]) {
                    // System.out.println("!!!!!!!!!!!!!!! INCORRECT !!!!!!!!!!!!!!");

                }
            }
        }

    }

    public static void main(String[] args) {

        Random rand = new Random();
//        for(int i = 1; i <= 100; i++) {
//
//            Generator generator = null;
//            if (i <= 5 ) {
//                generator = new Generator(5, 3, 500,  rand.nextInt(4) + 2,  rand.nextInt(8) + 3, 2);
//            }
//            if (i <= 10 && i > 5) {
//                generator = new Generator(6, 4, 500,  rand.nextInt(7) + 4,  rand.nextInt(10) + 4, 3);
//            }
//
//            if (i <= 15 && i > 10) {
//                generator = new Generator(7, 5, 500,  rand.nextInt(10) + 4,  rand.nextInt(11) + 4, 3);
//            }
//            if (i <= 20 && i > 15) {
//                generator = new Generator(8, 6, 1000,  rand.nextInt(15) + 5,  rand.nextInt(12) + 8, 4);
//            }
//            if (i <= 25 && i > 20) {
//                generator = new Generator(9, 7, 1000,  rand.nextInt(22) + 6,  rand.nextInt(16) + 8, 4);
//            }
//            if (i <= 30 && i > 25) {
//                generator = new Generator(10, 7, 1000,  rand.nextInt(25) + 10,  rand.nextInt(25) + 12, 4);
//            }


        //                if (i <= 25 ) {
//                    generator = new Generator(5, 3, 500,  rand.nextInt(4) + 2,  rand.nextInt(8) + 3, 2);
//                }
//                if (i <= 50 && i > 25) {
//                    generator = new Generator(6, 3, 500,  rand.nextInt(6) + 4,  rand.nextInt(12) + 4, 2);
//                }
//
//                if (i <= 75 && i > 50) {
//                    generator = new Generator(7, 3, 500,  rand.nextInt(10) + 4,  rand.nextInt(17) + 5, 2);
//                }
//                if (i <= 100 && i > 75) {
//                    generator = new Generator(8, 3, 1000,  rand.nextInt(8) + 5,  rand.nextInt(21) + 5, 2);
//                }

        int iteration = 3;
        int start_index = 205;
        int end_index = 225;


        int index = start_index;
        while (index <= end_index) {

            Generator generator = null;
//
//            if (i % 4 == 3) {
//                generator = new Generator(5, 4, 1000, 8 + rand.nextInt(3), 7,  2);
//            }else
//            if (i % 4 == 0) {
//                generator = new Generator(5, 3, 1000, 8 + rand.nextInt(3), 9,  2);
//            }else {
//                generator = new Generator(5, 5, 1000, 8 + rand.nextInt(3), 5,  2);
//            }
//            //


            int size = 10;
            int colors = 5;
            int steps = 1500;
            int turnovers = 15 + rand.nextInt(15);
            int minLongestPath = (size * size) / colors + 1;
            int minPointsDistance = 1;
            int maxPointsDistance = 200;
            int overColorLimit = 0;
            int borderSize = 0;

            generator = new Generator(size, colors, steps, turnovers, minLongestPath, minPointsDistance, maxPointsDistance, overColorLimit, borderSize);
            //generator = new Generator(4, 4, 1000, 5 + rand.nextInt(5), 6,  1, 0, 1);
            boolean state = generator.generate();
            if (!state) {
                System.out.println("Level overflow");
                continue;
            }
            String packName = "Final/christmas";
            String filename1 = "/home/suren/Documents/temp/connections/" + packName + "/sol" + index + ".txt";
            generator.writeToFile("/home/suren/Documents/temp/connections/" + packName + "/level" + index + ".txt");
            generator.writeToFile2("/home/suren/Documents/temp/connections/" + packName + "/sol" + index + ".txt");


            try {
                Runtime rt = Runtime.getRuntime();
                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec("/home/suren/Downloads/scala-2.10.6/bin/scala -cp /home/suren/Downloads/copris-numberlink-v1-1.jar numberlink.Solver -o multi " + filename1);


                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                StringBuilder output = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    output.append(line);
                }
                int result = pr.waitFor();

                if (result != 0) {
                    System.out.println(result);
                }
                //System.out.println(output);

                if (output.toString().contains("NumOfSolutions = 1")) {
                    System.out.println("Unique solution " + index);
                    index += iteration;
                } else {
                    System.out.println("Bad solution " + index);
                }

            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(String filename) {
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(mSize + "\n");
            output.write(mTargetPathsCount + "\n");
            for (int i = 0; i < mPaths.getConnections().size(); i++) {
                LinkedConnection connection = mPaths.getConnections().get(i);
                output.write(i + "\n");
                output.write(connection.mCells.get(0).getX() + " " + connection.mCells.get(0).getY() + "\n");
                output.write(connection.mCells.get(connection.mCells.size() - 1).getX() + " " + connection.mCells.get(connection.mCells.size() - 1).getY() + "\n");
            }


            for (int i = 0; i < mPaths.getConnections().size(); i++) {
                LinkedConnection connection = mPaths.getConnections().get(i);
                output.write(i + " " + connection.mCells.size() + "\n");
                for (int j = 0; j < connection.mCells.size(); j++) {
                    output.write(connection.mCells.get(j).getX() + " " + connection.mCells.get(j).getY() + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile2(String filename) {

        int matrix[][] = new int[mSize][mSize];
        for (int i = 0; i < mPaths.getConnections().size(); i++) {
            LinkedConnection connection = mPaths.getConnections().get(i);
            matrix[connection.mCells.get(0).getX()][connection.mCells.get(0).getY()] = i + 1;
            matrix[connection.mCells.get(connection.mCells.size() - 1).getX()][connection.mCells.get(connection.mCells.size() - 1).getY()] = i + 1;
        }
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(mSize + "\n");
            output.write(mSize + "\n");

            for (int i = 0; i < mSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    if (matrix[i][j] == 0) {
                        output.write("- ");
                    } else {
                        output.write(matrix[i][j] + " ");
                    }
                }
                output.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
