package plumber.engine;

/*
    Double Tube tube class
 */
public class DoubleTube extends TubeShape {

    public DoubleTube(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 2;
    }

    @Override
    public Direction getDirection(Direction from) {

        if (from == Direction.DOWN)
            return Direction.UP;
        if (from == Direction.UP)
            return Direction.DOWN;
        if (from == Direction.LEFT)
            return Direction.RIGHT;
        if (from == Direction.RIGHT)
            return Direction.LEFT;

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
        return Tubes.DOUBLE_TUBE;
    }

    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "##";
            case 1:
                return "HH";
            default:
                return "X";
        }
    }

    @Override
    public int getTubesTypeIndex() {
        // TODO Auto-generated method stub
        return 3;
    }

}
