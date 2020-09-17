package Crossword;

/**
 * Created by suren on 9/28/18.
 */
public class FixedWord {
    private int startX;
    private int startY;
    private boolean isHorizontal;
    private String word;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public FixedWord(int startX, int startY, boolean isHorizontal, String word) {

        this.startX = startX;
        this.startY = startY;
        this.isHorizontal = isHorizontal;
        this.word = word;
    }
}
