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

    public boolean connectsTo(Direction to) {
        if (this == LEFT) {
            return to == RIGHT;
        } else if (this == RIGHT) {
            return to == LEFT;
        } else if (this == UP) {
            return to == DOWN;
        } else {
            return to == UP;
        }
    }

    public boolean isOpposite(Direction to) {
        return connectsTo(to);
    }
}
