package plumber.engine.models;


import plumber.engine.*;


import java.util.ArrayList;


/**
 * Created by Liana on 2/6/18
 */

public class Model {

    public enum Colors {
        BLUE,
        GREEN,
        PINK,
        ORANGE,
        RED,
        ALL_COLORS
    }

    protected static final String CONSTRUCT_PATH_HINT_USED = "com.mobiloids.waterpipespuzzle.CONSTRUCT_PATH_HINT_USED";
    protected static final String EMPTY_HINT_USED = "com.mobiloids.waterpipespuzzle.EMPTY_HINT_USED";
    protected static final String LAST_INDEX = "com.mobiloids.waterpipespuzzle.LAST_INDEX";
    public static final String SECONDS = "com.mobiloids.waterpipespuzzle.SECONDS";

    public static final int CONSTRUCT_HINT_COINS = 25;
    public static final int REMOVE_HINT_COINS = 50;

    //Hint usage array. Each element described if the tube is freezed to correct position.
    protected boolean[][] isFreezed;
    protected boolean isUnusedTubesHintUsed = false;
    protected boolean isConstructPathHintUsed = false;

    protected int seconds = 0;
    //Board sizes
    protected int height, width;

    //The game board
    protected TubeShape[][] gameBoard;
    protected boolean[] isPathConstructed;

    protected ArrayList<Point> arrayOfCoords;
    protected boolean[] isPointSet;

    //The water path(sequence of segments the water passes)

    protected ArrayList<WaterPathSegment> path;
    private boolean gameOverStatus;

    protected int endTube00X = 0;
    protected int endTube00Y = 0;

    public TubeShape getWorldPiece(int x, int y) {
        return gameBoard[x][y];
    }

    public void setWorldPiece(int x, int y, Tubes type) {
        switch (type) {
            case DOUBLE_ANGLE:
                gameBoard[x][y] = new DoubleAngle(gameBoard[x][y].getStartRotation());
                break;

            case DOUBLE_TUBE:
                gameBoard[x][y] = new DoubleTube(gameBoard[x][y].getStartRotation());
                break;

            case TUBE:
                gameBoard[x][y] = new Tube(gameBoard[x][y].getStartRotation());
                break;

            case ANGLE:
                gameBoard[x][y] = new Angle(gameBoard[x][y].getStartRotation());
                break;

            case EMPTY_TUBE:
                gameBoard[x][y] = new EmptyTube(gameBoard[x][y].getStartRotation());
                break;

            case END_TUBE:
                gameBoard[x][y] = new EndTube(gameBoard[x][y].getStartRotation());
                break;

            case VALVE:
                gameBoard[x][y] = new ValveTube(gameBoard[x][y].getStartRotation());
                break;
        }
    }

    protected boolean makePath(int startX, int startY, Colors startColor) {
        return false;
    }

    public ArrayList<ArrayList<SolutionSegment>> getSolution() {
        return null;
    }

    public TouchStatus makeTouch(int x, int y, Colors startColor) {
        if (x < 0 || x >= height || y < 0 || y >= width)
            return TouchStatus.CANNOT_ROTATE;


        if (gameBoard[x][y].getTubeType() == TubeType.TUBE) {
            gameBoard[x][y].rotate();
            return TouchStatus.ROTATED;
        }

        if (gameBoard[x][y].getTubeType() == TubeType.END) {
            return TouchStatus.CANNOT_ROTATE;
        }

        if (gameBoard[x][y].getTubeType() == TubeType.VALVE) {

            boolean pathFound = makePath(x, y, startColor);
            if (pathFound)
                return TouchStatus.PATH_FOUND;
            else
                return TouchStatus.NO_PATH_FOUND;
        }

        //Dummy return
        return TouchStatus.CANNOT_ROTATE;
    }

    public int getSeconds() {
        return seconds;
    }

    public ArrayList<WaterPathSegment> getPath() {
        return path;
    }

    public boolean canUseConstructPathHint() {
        return true;
    }

    public ArrayList<SolutionSegment> useConstructPathHint() {
        return null;
    }

    public ArrayList<SolutionSegment> construct() {
        return null;
    }

    public boolean isUnusedTubesHintUsed() {
        return isUnusedTubesHintUsed;
    }

    public boolean isConstructPathHintUsed() {
        return isConstructPathHintUsed;
    }

    public boolean[][] useRemoveTubesHint() {
        return null;
    }

    public boolean isFreezed(int i, int j) {
        return isFreezed[i][j];
    }

    public void unfreeze(int i, int j) {
        isFreezed[i][j] = false;
    }




    //This function converts last move direction according to new tube
    protected Direction convertDirection(Direction lastMove) {
        switch (lastMove) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            case PORTAL:
                return Direction.PORTAL;
            //Cannot be in a normal state
            default:
                return Direction.NO_WAY;
        }
    }

    public boolean getGameOverStatus() {
        return false;
    }

    public void setPathConstructed(WaterPathSegment waterPathSegment) {
    }

    public void setPathDestroyed(int x, int y) {
    }

    public ArrayList<ArrayList<WaterPathSegment>>  updateImages(int x, int y) {
        return null;
    }



    public boolean isPathAlreadyPassed() {
        return false;
    }

    public boolean[] getIsPathConstructed() {
        return isPathConstructed;
    }

    public int getCountOfConstructedPaths() {
        return 0;
    }

    public Colors getCurrentColor(int i, int j, TubeType type) {
        return Colors.RED;
    }


    public int getSolutionIndexFromValveCoords(int i, int j) {
        return 0;
    }


    public boolean needToChangeValveBg() {
        return false;
    }

    public boolean needToUnfreezeTubes() {
        return true;
    }

    public boolean needToSetFinishState() {
        return false;
    }


    public int getEndTube00X() {
        return endTube00X;
    }

    public int getEndTube00Y() {
        return endTube00Y;
    }

    public void putPiece(Point point) {}

    public void removePiece(Point point) {}


    public Point getFirstPortalCoordinates() {
        return new Point(-1, -1);
    }

    public Point getSecondPortalCoordinates() {
        return new Point(-1, -1);
    }

    public boolean isThereAPortalInTheLevel() {
        return false;
    }


    public Tubes getTubeTypeByCode(int code) {

        int tube = code / 10;

        switch (tube) {
            case 1:
                return Tubes.TUBE;
            case 2:
                return Tubes.ANGLE;
            case 3:
                return Tubes.DOUBLE_TUBE;
            case 4:
                return Tubes.DOUBLE_ANGLE;
            case 5:
                return Tubes.VALVE;
            case 6:
                return Tubes.END_TUBE;
            case 7:
                return Tubes.EMPTY_TUBE;
            case 10:
                return Tubes.PORTAL;
        }

        return Tubes.EMPTY_TUBE;
    }
}
