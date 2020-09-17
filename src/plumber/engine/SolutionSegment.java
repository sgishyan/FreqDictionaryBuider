package plumber.engine;

/**
 * Created by suren on 8/4/17
 */
public class SolutionSegment {
    private int x;
    private int y;
    private int rotation;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public SolutionSegment(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + getX();
        result = PRIME * result + getY();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) return true;

        if (obj instanceof SolutionSegment) {
            SolutionSegment segment = (SolutionSegment)obj;
            return (segment.x == x && segment.y == y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + x + " " + y + " " + rotation + "]" ;
    }
}
