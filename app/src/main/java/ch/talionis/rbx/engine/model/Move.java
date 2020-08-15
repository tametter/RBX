package ch.talionis.rbx.engine.model;

/**
 * A player move.
 */
public class Move {
    private final int index;
    private final Direction direction;

    public Move(int index, Direction direction) {
        this.index = index;
        this.direction = direction;
    }

    public int getIndex() {
        return index;
    }

    public Direction getDirection() {
        return direction;
    }
}
