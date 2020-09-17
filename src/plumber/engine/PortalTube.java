package plumber.engine;

/**
 * Created by Liana on 4/18/19
 */

public class PortalTube extends TubeShape {


    public PortalTube(int startRotation) {
        super(startRotation);
        uniquePositionsCount = 1;
    }

    @Override
    public Direction getDirection(Direction from) {
        if (from != Direction.PORTAL) {

            switch(rotationsNumber) {
                case 0:
                    if (from == Direction.UP) {
                        return Direction.PORTAL;
                    }
                    break;
                case 1:
                    if (from == Direction.RIGHT) {
                        return Direction.PORTAL;
                    }
                    break;

                case 2:
                    if (from == Direction.DOWN) {
                        return Direction.PORTAL;
                    }
                    break;

                case 3:
                    if (from == Direction.LEFT) {
                        return Direction.PORTAL;
                    }
                    break;
            }

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

    @Override
    public TubeType getTubeType() {
        return TubeType.PORTAL;
    }

    @Override
    public int getTubesTypeIndex() {
        return 10;
    }

    @Override
    public Tubes getTubesType() {
        return Tubes.PORTAL;
    }

    @Override
    public boolean canRotate() {
        return false;
    }
}
