package plumber.engine;

/*
    Ordinary tube class
 */
public class Tube extends TubeShape {


    public Tube(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 2;
    }

    @Override
    public Direction getDirection(Direction from) {

        switch (rotationsNumber) {
            case 0:
                if (from == Direction.DOWN)
                    return Direction.UP;
                if (from == Direction.UP)
                    return Direction.DOWN;
                break;

            case 1:
                if (from == Direction.LEFT)
                    return Direction.RIGHT;
                if (from == Direction.RIGHT)
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
        return Tubes.TUBE;
    }


    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "||";
            case 1:
                return "--";
            default:
                return "X";
        }
    }


    @Override
    public int getTubesTypeIndex() {
        return 1;
    }
}
