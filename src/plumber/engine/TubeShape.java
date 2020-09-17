package plumber.engine;

import plumber.engine.*;
import plumber.engine.models.Model;

/*
    Base class for all kind of tubes
 */
public abstract class TubeShape {

    // Indicates the current rotation of tube
    public int rotationsNumber;
    // Total number of unique rotations for current type of tube
    protected int uniquePositionsCount = 1;
    protected int startRotation;
    protected Model.Colors color = Model.Colors.ALL_COLORS;

    public TubeShape(int startRotation) {
        rotationsNumber = startRotation;
        this.startRotation = startRotation;
    }

    /*
        Must return the direction of water after this tube, if the water came from given side.
     */
    public abstract Direction getDirection(Direction from);

    public int getRotation() {
        return rotationsNumber;
    }

    public int getUniquePositionsCount () {
        return uniquePositionsCount;
    }

    /*
        Rotates the tube
     */
    public void rotate() {
        rotationsNumber = (rotationsNumber + 1) % uniquePositionsCount;
    }

    public abstract TubeType getTubeType();

    public abstract int getTubesTypeIndex();

    public abstract Tubes getTubesType();

    public abstract boolean canRotate();

    public void setRotation(int rotation) {
        this.rotationsNumber = rotation;
    }

    public int getStartRotation() {
        return startRotation;
    }

    public void setColor(Model.Colors colorFromCode) {
        this.color = colorFromCode;
    }

    public Model.Colors getColor() {
        return color;
    }
}
