package plumber.engine;

public class Angle extends TubeShape {

    public Angle(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 4;
    }

    @Override
    public Direction getDirection(Direction from) {

        switch (rotationsNumber) {
            case 0:
                if (from == Direction.UP)
                    return Direction.RIGHT;
                if (from == Direction.RIGHT)
                    return Direction.UP;
                break;

            case 1:
                if (from == Direction.RIGHT)
                    return Direction.DOWN;
                if (from == Direction.DOWN)
                    return Direction.RIGHT;
                break;

            case 2:
                if (from == Direction.LEFT)
                    return Direction.DOWN;
                if (from == Direction.DOWN)
                    return Direction.LEFT;
                break;

            case 3:
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
        return Tubes.ANGLE;
    }

    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "|_";
            case 1:
                return "|-";
            case 2:
                return "-|";
            case 3:
                return "_|";
            default:
                return "X";

        }
    }

    @Override
    public int getTubesTypeIndex() {
        return 2;
    }
}