package plumber.engine;

/**
 * Created by sgishyan on 11/10/10.
 */

/*
    Empty element
 */
public class EmptyTube extends Tube {

    private boolean isWall = false;
    public EmptyTube(int startRotation) {
        super(startRotation);
    }

    @Override
    public Direction getDirection(Direction from) {
        return Direction.NO_WAY;
    }

    @Override
    public boolean canRotate() {
        return false;
    }


    @Override
    public TubeType getTubeType() {
        return TubeType.EMPTY;
    }

    @Override
    public Tubes getTubesType() {
        return Tubes.EMPTY_TUBE;
    }


    public String toString()
    {
      return "  ";
    }

    @Override
    public int getTubesTypeIndex()
    {
        return isWall ? 9 : 7;
    }

    public void setIsWall() {
        isWall = true;
    }

    public boolean getIsWall () {
        return isWall;
    }
}
