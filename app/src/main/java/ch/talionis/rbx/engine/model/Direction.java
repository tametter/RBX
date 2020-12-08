package ch.talionis.rbx.engine.model;

/**
 * The direction for a move.
 */
public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public Direction inverse() {
        if (this == LEFT) {
            return RIGHT;
        } else if (this == RIGHT) {
            return LEFT;
        } else if (this == UP) {
            return DOWN;
        } else {
            return UP;
        }
    }
}
