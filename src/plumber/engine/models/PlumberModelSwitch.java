package plumber.engine.models;

import plumber.engine.*;

import java.util.ArrayList;


public class PlumberModelSwitch extends Model {


    private static final int CORRECTION_PART = 4;
    //The coordinates of valve
    private int valveY;
    private int valveX;

    //The level solution
    protected ArrayList<SolutionSegment> mSolution;

    boolean alreadyUsesPortal = false;
    private int lastCorrectedIndex;

    protected int firstPortalX = -1;
    protected int firstPortalY = -1;

    protected int secondPortalX = -1;
    protected int secondPortalY = -1;

    private boolean isThereAPortalInTheLevel = false;
    private ArrayList<ArrayList<WaterPathSegment>> solutions;
    private Colors color = Colors.ALL_COLORS;

    public PlumberModelSwitch(int[][] array, ArrayList<SolutionSegment> solution, int h, int w, ArrayList<Point> arrayOfCoords) {

        height = h;
        width = w;
        gameBoard = new TubeShape[height][width];
        path = new ArrayList<>();
        mSolution = solution;
        isFreezed = new boolean[height][width];
        solutions = new ArrayList<>();
        if (arrayOfCoords != null) {
            this.arrayOfCoords = arrayOfCoords;
            isPointSet = new boolean[arrayOfCoords.size()];
        }

        //Initializing game board according to init array
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int code = array[i][j];
                int tube = code / 10;
                int rotation = code % 10;
                int colorCode = 0;

                if (code / 1000 != 0) {
                    tube = code / 1000;
                } else if (code / 100 != 0 && (code / 100 == 5 || code / 100 == 6)) {
                    colorCode = (code / 10) % 10;
                    tube = code / 100;
                }

                switch (tube) {
                    case 1:
                        gameBoard[i][j] = new Tube(rotation);
                        break;

                    case 2:
                        gameBoard[i][j] = new Angle(rotation);
                        break;

                    case 3:
                        gameBoard[i][j] = new DoubleTube(rotation);
                        break;

                    case 4:
                        gameBoard[i][j] = new DoubleAngle(rotation);
                        break;

                    case 5:
                        gameBoard[i][j] = new ValveTube(rotation);

                        if (colorCode != 0) {
                            gameBoard[i][j].setColor(getColorFromCode(colorCode));
                            color = getColorFromCode(colorCode);
                        }

                        valveX = i;
                        valveY = j;

                        path.add(new WaterPathSegment(valveX, valveY, Tubes.VALVE, Direction.NO_WAY, true, rotation));

                        break;

                    case 6:
                        gameBoard[i][j] = new EndTube(rotation);
                        if (colorCode != 0) {
                            gameBoard[i][j].setColor(getColorFromCode(colorCode));
                        }else {
                            gameBoard[i][j].setColor(Colors.ALL_COLORS);
                        }

                        break;

                    case 7:
                        gameBoard[i][j] = new EmptyTube(rotation);
                        break;

                    case 9:
                        gameBoard[i][j] = new EmptyTube(rotation);
                        ((EmptyTube) gameBoard[i][j]).setIsWall();
                        break;

                    case 10:
                        gameBoard[i][j] = new PortalTube(rotation);
                        isThereAPortalInTheLevel = true;

                        if (firstPortalX == -1) {
                            firstPortalX = i;
                            firstPortalY = j;
                        } else {
                            secondPortalX = i;
                            secondPortalY = j;
                        }
                        break;

                    case 11:

                        int incomingColorCode = (code / 100) % 10;
                        int outgoingColorCode = (code / 10) % 10;

                        Colors incomingColor = getColorFromCode(incomingColorCode);
                        Colors outgoingColor = getColorFromCode(outgoingColorCode);

                        gameBoard[i][j] = new ColorSwitcher(rotation, incomingColor, outgoingColor);
                        break;
                }
            }
        }
    }

    public PlumberModelSwitch(int[][] board, int heigth, int width) {
        this(board, new ArrayList<SolutionSegment>(), heigth, width, new ArrayList<>() );
    }

    private Colors getColorFromCode(int code) {
        switch (code) {
            case 0:
                return Colors.ALL_COLORS;

            case 1:
                return Colors.BLUE;

            case 2:
                return Colors.GREEN;

            case 3:
                return Colors.PINK;

            default:
                return Colors.RED;
        }
    }

    public Point getFirstPortalCoordinates() {
        return new Point(firstPortalX, firstPortalY);
    }

    public Point getSecondPortalCoordinates() {
        return new Point(secondPortalX, secondPortalY);
    }

    public boolean isThereAPortalInTheLevel() {
        return isThereAPortalInTheLevel;
    }

    @Override
    public void putPiece(Point point) {
        for (int i = 0; i < arrayOfCoords.size(); i++) {
            if (point.x == arrayOfCoords.get(i).x && point.y == arrayOfCoords.get(i).y) {
                isPointSet[i] = true;
            }
        }
    }

    @Override
    public void removePiece(Point point) {
        for (int i = 0; i < arrayOfCoords.size(); i++) {
            if (point.x == arrayOfCoords.get(i).x && point.y == arrayOfCoords.get(i).y) {
                isPointSet[i] = false;
            }
        }
    }




    @Override
    public ArrayList<ArrayList<SolutionSegment>> getSolution() {
        ArrayList<ArrayList<SolutionSegment>> result = new ArrayList<>();
        result.add(mSolution);
        return result;
    }

    public ArrayList<WaterPathSegment> shortestPath () {


        findAllPaths(path, Direction.NO_WAY, false, color);
        if (solutions.size() == 0) {
            return null;
        }


        //    System.out.println("Total solutions : " + solutions.size());
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).size() < min) {
                min = solutions.get(i).size();
                minIndex = i;
            }
        }
        int count = 0;
        // System.out.println("Best solutions : " + min);
        ArrayList<WaterPathSegment> solution = solutions.get(minIndex);
        for (WaterPathSegment segment : solution) {
            if (segment.tube == Tubes.COLOR_SWITCHER) {
                count++;
            }
        }
        if (count >= 2) {
            return  solution;
        }
        return null;
    }

    public ArrayList<ArrayList<WaterPathSegment>>  allSolutions() {


        findAllPaths(path, Direction.NO_WAY, false, color);
        if (solutions.size() == 0) {
            return null;
        }else {
            return solutions;
        }


    }

    public void findAllPaths(ArrayList<WaterPathSegment> currentPath, Direction cameFrom, boolean alreadyUsesPortal, Colors pathColor) {

        int curX = currentPath.get(currentPath.size() - 1).x;
        int curY = currentPath.get(currentPath.size() - 1).y;
        int oldX = curX;
        int oldY = curY;

        boolean isThereColorSwitcherElement = false;

        int startRotation = gameBoard[curX][curY].getRotation();
         // System.out.println("( "  + curX + " ; " + curY + " )  : " + currentPath.size() + " " + cameFrom) ;
        // Log.i("New Vertex:", curX + " " + curY);
        //Adding start

        Direction weCameFrom = cameFrom;

        if (curX < 0 || curX >= height || curY < 0 || curY >= width)
            return;

        //Checking if there was a loop, in that case just don't rotate
        boolean isThereALoop = false;
        for (int i = 0; i < currentPath.size() - 1; i++) {
            if(currentPath.get(i).x == curX && currentPath.get(i).y == curY) {
                isThereALoop = true;
                break;
            }
        }
        int rotationsLimit = gameBoard[curX][curY].getUniquePositionsCount();
        if (gameBoard[curX][curY] instanceof DoubleTube) {
            rotationsLimit = 1;
        }
        if (isThereALoop) {
            rotationsLimit = 1;
        }


        if (gameBoard[curX][curY].getTubesType() == Tubes.COLOR_SWITCHER) {
            Colors inComingColor = ((ColorSwitcher) gameBoard[curX][curY]).getTopOrRightColor();
            Colors outGoingColor = ((ColorSwitcher) gameBoard[curX][curY]).getBottomOrLeftColor();

          //  System.out.println("SWITCHER__ topColor = " + inComingColor);
          //  System.out.println("SWITCHER__ bottomColor = " + outGoingColor);
          //  System.out.println("SWITCHER__ pathColor = " + pathColor);

            isThereColorSwitcherElement = true;

            switch(cameFrom) {
                case UP:
                case RIGHT:
               //     System.out.println("SWITCHER__ moveDirection_UR");
                    if (inComingColor != pathColor) {
                        return;
                    } else {
                        pathColor = outGoingColor;
                    }
                    break;
                case DOWN:
                case LEFT:
                 //   System.out.println("SWITCHER__ moveDirection_DL");
                    if (outGoingColor != pathColor) {
                        return;
                    } else {
                        pathColor = inComingColor;
                    }
                    break;
            }

                /*if (inComingColor == Colors.ALL_COLORS) {
                    startColor = outGoingColor;
                } else {
                    if (inComingColor != startColor) {
                        return false;
                    } else {
                        startColor = outGoingColor;
                    }
                }*/
        }

      //  System.out.println("Rotations " + gameBoard[curX][curY].getUniquePositionsCount());
        for (int r = 0; r < rotationsLimit; r++) {


            //System.out.println("try");
            weCameFrom=gameBoard[curX][curY].getDirection(cameFrom);
            //System.out.println("New direction " + weCameFrom + " Tube type : " + gameBoard[curX][curY].getTubeType());
            switch (weCameFrom) {
                case UP:
                    curX--;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        curX = oldX;
                        curY = oldY;
                        gameBoard[curX][curY].rotate();
                        continue;
                    }
                    break;
                case DOWN:
                    curX++;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        curX = oldX;
                        curY = oldY;
                        gameBoard[curX][curY].rotate();
                        continue;
                    }
                    break;
                case LEFT:
                    curY--;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        curX = oldX;
                        curY = oldY;
                        gameBoard[curX][curY].rotate();
                        continue;
                    }
                    break;
                case RIGHT:
                    curY++;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        curX = oldX;
                        curY = oldY;
                        gameBoard[curX][curY].rotate();
                        continue;
                    }
                    break;

                case PORTAL:
                    if (curX == firstPortalX && curY == firstPortalY) {
                        curX = secondPortalX;
                        curY = secondPortalY;
                    } else {
                        curX = firstPortalX;
                        curY = firstPortalY;
                    }

                    //path.add(new WaterPathSegment(curX, curY, gameBoard[curX][curY].getTubesType(), convertDirection(moveDirection), true));
                    alreadyUsesPortal = true;

                    break;

                case NO_WAY:
                    curX = oldX;
                    curY = oldY;
                    gameBoard[curX][curY].rotate();
                    continue;

            }

            boolean isFirstTime = true;
            for (WaterPathSegment p : currentPath)
                if (p.x == curX && p.y == curY) {
                    isFirstTime = false;
                    break;
                }
            if (curX < 0 || curX >= height || curY < 0 || curY >= width) {
                curX = oldX;
                curY = oldY;
                gameBoard[curX][curY].rotate();
                continue;
            }


            //System.out.println("Going to..." + curX + " " + curY );
            ArrayList<WaterPathSegment> newPath = new ArrayList<>(currentPath);
            newPath.add(new WaterPathSegment(curX, curY, gameBoard[curX][curY].getTubesType(), convertDirection(weCameFrom), isFirstTime, gameBoard[oldX][oldY].getRotation()));

            weCameFrom = convertDirection(weCameFrom);

            if (gameBoard[curX][curY].getTubeType() == TubeType.END) {
                if (gameBoard[curX][curY].getColor() == pathColor || gameBoard[curX][curY].getColor() == Colors.ALL_COLORS) {
                    solutions.add(newPath);
                }else {
                    return;
                }


            } else {
                findAllPaths(newPath, weCameFrom, alreadyUsesPortal, pathColor);
            }
            curX = oldX;
            curY = oldY;
            gameBoard[curX][curY].rotate();

        }
        gameBoard[curX][curY].rotationsNumber = startRotation;
    }

    //Determines is there any path from current valve to end tube.
    //This function  also creates an array list which contains every node of the path.
    //Returns true if path found, false otherwise.
    @Override
    protected boolean makePath(int startX, int startY, Colors startColor) {

        path.clear();
        //Starting from valve
        int curX = startX;
        int curY = startY;

        boolean alreadyUsesPortal = false;
        boolean isThereColorSwitcherElement = false;


        //Adding start
        path.add(new WaterPathSegment(curX, curY, gameBoard[curX][curY].getTubesType(), Direction.NO_WAY, true));

        //This variable holds the direction we came from,to this tube.
        //In case of valve, it equals NO_WAY.
        Direction moveDirection = Direction.NO_WAY;
        while (true) {

            //Checking bounds
            if (curX < 0 || curX >= height || curY < 0 || curY >= width)
                return false;

            Direction cameFrom = moveDirection;
            //Determining where the path goes to, and changing the variables.
            moveDirection = gameBoard[curX][curY].getDirection(moveDirection);


            if (gameBoard[curX][curY].getTubesType() == Tubes.COLOR_SWITCHER) {
                Colors inComingColor = ((ColorSwitcher) gameBoard[curX][curY]).getTopOrRightColor();
                Colors outGoingColor = ((ColorSwitcher) gameBoard[curX][curY]).getBottomOrLeftColor();

               // System.out.println("SWITCHER__ topColor = " + inComingColor);
               // System.out.println("SWITCHER__ bottomColor = " + outGoingColor);
               // System.out.println("SWITCHER__ startColor = " + startColor);

                isThereColorSwitcherElement = true;

                switch(cameFrom) {
                    case UP:
                    case RIGHT:
                       // System.out.println("SWITCHER__ moveDirection_UR");
                        if (inComingColor != startColor) {
                            return false;
                        } else {
                            startColor = outGoingColor;
                        }
                        break;
                    case DOWN:
                    case LEFT:
                      //  System.out.println("SWITCHER__ moveDirection_DL");
                        if (outGoingColor != startColor) {
                            return false;
                        } else {
                            startColor = inComingColor;
                        }
                        break;
                }

                /*if (inComingColor == Colors.ALL_COLORS) {
                    startColor = outGoingColor;
                } else {
                    if (inComingColor != startColor) {
                        return false;
                    } else {
                        startColor = outGoingColor;
                    }
                }*/
            }

            //Changing current coordinates according to the direction of movement
            switch (moveDirection) {
                case UP:
                    curX--;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        return false;
                    }

                    break;
                case DOWN:
                    curX++;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        return false;
                    }
                    break;
                case LEFT:
                    curY--;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        return false;
                    }
                    break;
                case RIGHT:
                    curY++;
                    if (!checkForPortal(curX, curY, alreadyUsesPortal)) {
                        return false;
                    }
                    break;
                case PORTAL:
                    if (curX == firstPortalX && curY == firstPortalY) {
                        curX = secondPortalX;
                        curY = secondPortalY;
                    } else {
                        curX = firstPortalX;
                        curY = firstPortalY;
                    }

                    path.add(new WaterPathSegment(curX, curY, gameBoard[curX][curY].getTubesType(), convertDirection(moveDirection), true));
                    alreadyUsesPortal = true;
                    continue;

                    //If the water has no way from current tube, function returns false.
                case NO_WAY:
                    return false;

                //Cannot be in a normal state
                default:
                    break;
            }

            if (arrayOfCoords != null && arrayOfCoords.contains(new Point(curX, curY))) {
                for (int i = 0; i < arrayOfCoords.size(); ++i) {
                    if (arrayOfCoords.get(i).x == curX && arrayOfCoords.get(i).y == curY) {
                        if (!isPointSet[i]) {
                            return false;
                        }
                    }
                }
            }

            //This variable holds the information if the water path goes throw this tube first time.
            //Can be false for complex tubes(DoubleTube and DoubleAngle)
            boolean isFirstTime = true;

            //Checking all previous segments of the path.
            for (WaterPathSegment p : path)
                if (p.x == curX && p.y == curY) {
                    isFirstTime = false;
                    break;
                }

            //Checking the bounds again! Maybe dummy!
            if (curX < 0 || curX >= height || curY < 0 || curY >= width)
                return false;

            //Adding new segment to path.
            path.add(new WaterPathSegment(curX, curY, gameBoard[curX][curY].getTubesType(), convertDirection(moveDirection), isFirstTime));

            //If the water path reaches the END tube, returns true.
            if (gameBoard[curX][curY].getTubeType() == TubeType.END) {

                if(isThereColorSwitcherElement) {
                    return startColor == gameBoard[curX][curY].getColor();
                }

                return true;
            }

            moveDirection = convertDirection(moveDirection);
//            Log.i("New Vertex:", curX + " " + curY + " We came from " + moveDirection
//                    + " type " + gameBoard[curX][curY].getTubesType() + "rotation = " + gameBoard[curX][curY].getRotation());
        }
    }

    boolean checkForPortal(int curX, int curY, boolean alreadyUsesPortal) {
        return !(curX < 0 || curX >= height || curY < 0 || curY >= width) && !(gameBoard[curX][curY].getTubesType() == Tubes.PORTAL && alreadyUsesPortal);

    }

    @Override
    public boolean canUseConstructPathHint() {
        return mSolution.size() / CORRECTION_PART > 0 && lastCorrectedIndex < mSolution.size();
    }

    //Returns a list of corrected pipes
    @Override
    public ArrayList<SolutionSegment> useConstructPathHint() {
        ArrayList<SolutionSegment> constructRotations = new ArrayList<>();

        //The number of pipes we will correct
        int correctionsNumber = mSolution.size() / CORRECTION_PART;

        //Starting with last fixed position(Will be useful in case of several taken hints)
        int index = lastCorrectedIndex;

        //We will begin out count of fixed pipes starting with the first incorrect placed pipe.
        //This variable will be useful to determine it.
        boolean isPrefixSegment = true;

        //Determining correction
        while (correctionsNumber > 0 && index < mSolution.size()) {
            int x = mSolution.get(index).getX();
            int y = mSolution.get(index).getY();

            if (!isPrefixSegment) {
                correctionsNumber--;
            } else {
                if (mSolution.get(index).getRotation() != gameBoard[x][y].getRotation()) {
                    correctionsNumber--;
                    isPrefixSegment = false;
                }
            }

            //We don't want to freeze valve or target
            //if (/*gameBoard[x][y].getTubeType() != TubeType.VALVE && */gameBoard[x][y].getTubeType() != TubeType.END) {
            isFreezed[x][y] = true;
            gameBoard[x][y].setRotation(mSolution.get(index).getRotation());
          //  System.out.println("CONSTRUCT__ rotation = " + mSolution.get(index).getRotation());
            constructRotations.add(new SolutionSegment(x, y, mSolution.get(index).getRotation()));
            //}
            index++;
        }
        lastCorrectedIndex = index;
        isConstructPathHintUsed = true;
        return constructRotations;
    }

    //Returns a list of corrected pipes
    @Override
    public ArrayList<SolutionSegment> construct() {
        ArrayList<SolutionSegment> constructRotations = new ArrayList<>();

        //Starting with last fixed position(Will be useful in case of several taken hints)
        int index = 0;

        //We will begin out count of fixed pipes starting with the first incorrect placed pipe.
        //This variable will be useful to determine it.
        boolean isPrefixSegment = true;

        //Determining correction
        while (index < lastCorrectedIndex && lastCorrectedIndex <= mSolution.size()) {
            int x = mSolution.get(index).getX();
            int y = mSolution.get(index).getY();

            if (isPrefixSegment) {
                if (mSolution.get(index).getRotation() != gameBoard[x][y].getRotation()) {
                    isPrefixSegment = false;
                }
            }

            //We don't want to freeze valve or target
            if (/*gameBoard[x][y].getTubeType() != TubeType.VALVE && */gameBoard[x][y].getTubeType() != TubeType.END) {
                isFreezed[x][y] = true;
                gameBoard[x][y].setRotation(mSolution.get(index).getRotation());
                constructRotations.add(new SolutionSegment(x, y, mSolution.get(index).getRotation()));
            }
            index++;
        }
        lastCorrectedIndex = index;
        isConstructPathHintUsed = true;
        return constructRotations;
    }

    /*
        Removes unused tubes if player have enough coins and hint
        wasn't used yet
    */
    @Override
    public boolean[][] useRemoveTubesHint() {

        //ToDo check coins
        //ToDo check already usage

        boolean[][] result = new boolean[gameBoard.length][gameBoard[0].length];
        int totalRemoved = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!mSolution.contains(new SolutionSegment(i, j, 0))) {
                    result[i][j] = true;
                    gameBoard[i][j] = new EmptyTube(0);
                    totalRemoved++;
                }
            }
        }
//        Log.i("Hint", totalRemoved + "");
        isUnusedTubesHintUsed = true;
        return result;
    }




    @Override
    public boolean getGameOverStatus() {
        return true;
    }

    @Override
    public boolean needToUnfreezeTubes() {
        return false;
    }
}
