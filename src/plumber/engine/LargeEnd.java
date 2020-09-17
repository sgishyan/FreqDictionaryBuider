package plumber.engine;

/**
 * Created by suren on 3/6/18
 */


public class LargeEnd extends TubeShape {

    public LargeEnd(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 4;
    }

    @Override
    public Direction getDirection(Direction from) {

        System.out.println("ROTATIONS_NUMBER = " + rotationsNumber  + " from = " + from.toString());
        switch(rotationsNumber)
        {
            case 0:
                if(from == Direction.UP) return Direction.FINISH;
                break;

            case 1:
                if(from == Direction.RIGHT) return Direction.FINISH;
                break;

            case 2:
                if(from == Direction.DOWN) return Direction.FINISH;
                break;

            case 3:
                if(from == Direction.LEFT) return Direction.FINISH;
                break;

            default:
                return Direction.NO_WAY;
        }
        return Direction.NO_WAY;
    }

    @Override
    public TubeType getTubeType() {
        return TubeType.END;
    }

    @Override
    public int getTubesTypeIndex() {
        return 8;
    }

    @Override
    public Tubes getTubesType() {
        return Tubes.LARGE_END_TUBE;
    }

    @Override
    public boolean canRotate() {
        return false;
    }

    public String toString()
    {
        switch(rotationsNumber) {
            case 0:
                return "|L";
            case 1:
                return "L-";
            case 2:
                return "L|";
            case 3:
                return "-L";
            default:
                return "X";

        }
    }
}
