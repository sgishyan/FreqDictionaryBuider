package plumber.engine;

public class WaterPathSegment {

    public int x;
    public int y;
    public Tubes tube;
    public Direction cameFrom;
    public boolean isFirstImage;
    public int rotation;

    public WaterPathSegment(int x, int y, Tubes t, Direction came, boolean firstImage) {
        this.x = x;
        this.y = y;
        tube = t;
        cameFrom = came;
        isFirstImage = firstImage;
    }

    public WaterPathSegment(int x, int y, Tubes t, Direction came, boolean firstImage, int rotation) {
        this.x = x;
        this.y = y;
        tube = t;
        cameFrom = came;
        isFirstImage = firstImage;
        this.rotation = rotation;
    }

    public WaterPathSegment(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) return true;

        if (obj instanceof WaterPathSegment) {
            WaterPathSegment segment = (WaterPathSegment)obj;
            return (segment.x == x && segment.y == y);
        }
        return false;
    }


    @Override
    public String toString() {
        return "[" + x + " " + y + " " + isFirstImage + "]";
    }
}
