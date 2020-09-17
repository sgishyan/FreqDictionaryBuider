package carparking;

/**
 * Created by suren on 4/19/19.
 */
public class Figure {
    int position;
    int size;
    int stride;
    long mask;

    public Figure(int position, int size, int stride) {
        this.position = position;
        this.size = size;
        this.stride = stride;
        mask = 0;
        int p = position;
        for (int i = 0; i < size; i++) {
            mask |= (long)1 << p;
            p += stride;
        }
    }

    void move (int steps) {
        int d = stride * steps;
        position += d;
        if (steps > 0) {
            mask <<= d;
        } else {
            mask >>= -d;
        }
    }

    boolean fixed() {
        return size == 1;
    }
}
