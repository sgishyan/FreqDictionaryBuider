package plumber.engine;


/*
    End Tube class
 */
public class EndTube extends TubeShape {


    public EndTube(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 1;
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
        return TubeType.END;
    }

    @Override
    public Direction getDirection(Direction from) {
        return Direction.FINISH;
   //     switch (from) {

//            case UP:
//                if (rotationsNumber == 0)
//                    return Direction.FINISH;
//                else
//                    return Direction.NO_WAY;
//
//            case RIGHT:
//                if (rotationsNumber == 1)
//                    return Direction.FINISH;
//                else
//                    return Direction.NO_WAY;
//
//            case DOWN:
//                if (rotationsNumber == 2)
//                    return Direction.FINISH;
//                else
//                    return Direction.NO_WAY;
//            case LEFT:
//                if (rotationsNumber == 3)
//                    return Direction.FINISH;
//                else
//                    return Direction.NO_WAY;
//            default:
//                return Direction.NO_WAY;


    }

    public String toString() {
        switch (rotationsNumber) {
            case 0:
                return "|F";
            case 1:
                return "F-";
            case 2:
                return "F|";
            case 3:
                return "-F";
            default:
                return "X";
        }
    }

    @Override
    public Tubes getTubesType() {
        // TODO Auto-generated method stub
        return Tubes.END_TUBE;
    }

    @Override
    public int getTubesTypeIndex() {
        return 6;
    }


}
