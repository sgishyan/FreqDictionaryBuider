package connections;



/**
 * Created by David on 8/16/17
 */

public enum MatrixCell {

    EMPTY("Empty"),
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    ORANGE("Orange"),
    GRAY("Gray"),
    PINK("Pink"),
    CONNECTION_CELL("ConCell");

    private String mState;
    private int mColor;

    MatrixCell(String state) {
        mState = state;
        switch (state) {
            case "Red":
                mColor = 1;
                break;
            case "Green":
                mColor = 2;
                break;
            case "Blue":
                mColor = 3;
                break;
            case "Yellow":
                mColor = 4;
                break;
            case "Orange":
                mColor = 5;
                break;
            case "Gray":
                mColor = 6;
                break;
            case "Pink":
                mColor = 7;
                break;
            default:
                mColor = 8;
        }
    }

    public int getColor() {
        return mColor;
    }


    @Override
    public String toString() {
        return mState;
    }

}
