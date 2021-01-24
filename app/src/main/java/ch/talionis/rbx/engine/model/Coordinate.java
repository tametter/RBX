package ch.talionis.rbx.engine.model;

import androidx.annotation.Nullable;

import static ch.talionis.rbx.engine.model.Direction.DOWN;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;

public class Coordinate {
    private final int x;
    private final int y;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate move(Direction direction) {
        int newX = x;
        newX = direction == RIGHT ? newX + 1 : newX;
        newX = direction == LEFT ? newX - 1 : newX;

        int newY = y;
        newY = direction == UP ? newY - 1 : newY;
        newY = direction == DOWN ? newY + 1 : newY;

        return coordinate(newX, newY);
    }

    public static Coordinate coordinate(int x, int y) {
        return new Coordinate(x, y);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
         if (!(obj instanceof Coordinate)) {
             return false;
         }

         Coordinate other = (Coordinate) obj;
         return x == other.getX() && y == other.getY();
    }
}
