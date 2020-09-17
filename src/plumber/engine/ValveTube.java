package plumber.engine;

/*
    Valve element class
 */
public class ValveTube extends TubeShape {

    public ValveTube(int startRotation) {
        super(startRotation);
    }

    public void rotate() {
        //Do nothing . Can not rotate.
    }

    @Override
    public boolean canRotate() {
        return false;
    }

    @Override
    public TubeType getTubeType() {
        return TubeType.VALVE;
    }

    @Override
    public Direction getDirection(Direction from) {
        if (from != Direction.NO_WAY) {
            return Direction.NO_WAY;
        }
        switch (rotationsNumber) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
            default:
                return Direction.NO_WAY;
        }
    }

    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "|S";
            case 1:
                return "S-";
            case 2:
                return "S|";
            case 3:
                return "-S";
            default:
                return "X";
        }
    }

    @Override
    public Tubes getTubesType() {
        // TODO Auto-generated method stub
        return Tubes.VALVE;
    }

    @Override
    public int getTubesTypeIndex() {
        return 5;
    }
}
