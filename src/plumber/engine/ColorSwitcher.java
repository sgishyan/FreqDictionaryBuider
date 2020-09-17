package plumber.engine;

import plumber.engine.models.*;

public class ColorSwitcher extends TubeShape {


    private Model.Colors topOrRightColor;
    private Model.Colors bottomOrLeftColor;

    private Model.Colors startTopOrRightColor;
    private Model.Colors startBottomOrLeftColor;

    public ColorSwitcher(int startRotation, Model.Colors topOrRightColor, Model.Colors bottomOrLeftColor) {
        super(startRotation);
        uniquePositionsCount = 4;

        startTopOrRightColor = topOrRightColor;
        startBottomOrLeftColor = bottomOrLeftColor;

        this.topOrRightColor = topOrRightColor;
        this.bottomOrLeftColor = bottomOrLeftColor;

        if (startRotation == 2 || startRotation == 3) {

            this.topOrRightColor = bottomOrLeftColor;
            this.bottomOrLeftColor = topOrRightColor;
        }

       // System.out.println("SWITCHER__ startRotation = " + startRotation);
       // System.out.println("SWITCHER__ topColor = " + this.topOrRightColor);
       // System.out.println("SWITCHER__ bottomColor = " + this.bottomOrLeftColor);
    }

    @Override
    public Direction getDirection(Direction from) {

        switch (rotationsNumber) {
            case 0:
            case 2:
                if (from == Direction.UP)
                    return Direction.DOWN;
                if (from == Direction.DOWN)
                    return Direction.UP;
                break;

            case 1:
            case 3:
                if (from == Direction.RIGHT)
                    return Direction.LEFT;
                if (from == Direction.LEFT)
                    return Direction.RIGHT;
                break;
            default:

                return Direction.NO_WAY;
        }
        return Direction.NO_WAY;
    }


    @Override
    public boolean canRotate() {
        return true;
    }


    @Override
    public TubeType getTubeType() {
        return TubeType.TUBE;
    }

    @Override
    public Tubes getTubesType() {
        return Tubes.COLOR_SWITCHER;
    }


    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "|x|";
            case 1:
                return "-x-";
            default:
                return "X";
        }
    }

    @Override
    public void rotate() {
        super.rotate();

        switch (rotationsNumber) {
            case 0:
            case 1:
                topOrRightColor = startTopOrRightColor;
                bottomOrLeftColor = startBottomOrLeftColor;
                break;
            case 2:
            case 3:
                topOrRightColor = startBottomOrLeftColor;
                bottomOrLeftColor = startTopOrRightColor;
                break;
        }
    }

    @Override
    public void setRotation(int rotation) {
        super.setRotation(rotation);
        switch (rotationsNumber) {
            case 0:
            case 1:
                topOrRightColor = startTopOrRightColor;
                bottomOrLeftColor = startBottomOrLeftColor;
                break;
            case 2:
            case 3:
                topOrRightColor = startBottomOrLeftColor;
                bottomOrLeftColor = startTopOrRightColor;
                break;
        }
    }

    public Model.Colors getTopOrRightColor() {
        return topOrRightColor;
    }

    public Model.Colors getBottomOrLeftColor() {
        return bottomOrLeftColor;
    }

    @Override
    public int getTubesTypeIndex() {
        return 11;
    }
}
