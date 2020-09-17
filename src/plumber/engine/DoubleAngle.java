package plumber.engine;

/*
    Double Angle tube class
 */
public class DoubleAngle extends TubeShape {

    public DoubleAngle(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 2;
    }

    @Override
    public Direction getDirection(Direction from) {

        switch (rotationsNumber) {
            case 0:
                if (from == Direction.UP)
                    return Direction.RIGHT;
                if (from == Direction.RIGHT)
                    return Direction.UP;
                if (from == Direction.LEFT)
                    return Direction.DOWN;
                if (from == Direction.DOWN)
                    return Direction.LEFT;
                break;

            case 1:
                if (from == Direction.RIGHT)
                    return Direction.DOWN;
                if (from == Direction.DOWN)
                    return Direction.RIGHT;
                if (from == Direction.LEFT)
                    return Direction.UP;
                if (from == Direction.UP)
                    return Direction.LEFT;
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
        return Tubes.DOUBLE_ANGLE;
    }

    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "()";
            case 1:
                return ")(";
            default:
                return "X";

        }
    }

    @Override
    public int getTubesTypeIndex() {
        // TODO Auto-generated method stub
        return 4;
    }


}