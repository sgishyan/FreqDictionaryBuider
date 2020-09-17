package mobiloids;

public class Pair<T, V> {
    public T first;
    public V second;

    public T getKey() {
        return first;
    }

    public void setKey(T first) {
        this.first = first;
    }

    public V getValue() {
        return second;
    }

    public void setValue(V second) {
        this.second = second;
    }

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }
}
