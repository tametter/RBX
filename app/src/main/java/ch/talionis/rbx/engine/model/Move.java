package ch.talionis.rbx.engine.model;

/**
 * A player move.
 */
public class Move {
    private final int x;
    private final int y;
    private final Direction direction;

    public Move(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }
}
