package BallSort;

public class Flask {
    int [] container;
    int size;
    int index = 0;

    public Flask(int size, boolean isEmpty, int color) {
        this.size = size;
        container = new int[size];
        if (isEmpty) {
            index = 0;
        }else {
            for (int i = 0; i < size; i++) {
                container[i] = color;
                index = size;
            }
        }
    }
    public boolean isFull() {
        return index >= size;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public int topColor() {
        return container[index];
    }

    public boolean canReverseMove() {
        if (index == 0) return false;
        if (index == 1) return true;
        return container[index - 1] == container[index - 2];
    }
    public int pop() {
        int ball = container[index - 1];
        container[index - 1] = 0;
        index--;
        return ball;
    }
    public void push(int item) {
        System.out.println("Pushed = " + item);
        container[index++] = item;
    }

    public boolean isSolved() {
        if (index < size) return false;
        int color = container[0];
        for(int i = 1; i < size; i++) {
            if (container[i] != color) return false;
        }
        return true;
    }
}
