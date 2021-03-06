package ch.talionis.rbx.generator;

import java.util.Random;

import ch.talionis.rbx.engine.model.Coordinate;

import static ch.talionis.rbx.engine.model.Coordinate.coordinate;
import static ch.talionis.rbx.logging.Logger.logV;

/**
 * Generates a board size.
 */
public class BoardSize extends Step<Coordinate, Coordinate> {

    @Override
    public Coordinate apply(Coordinate bounds) {
        logV(this, "Generating board size with bounds %d, %d", bounds.getX(), bounds.getY());

        Random random = new Random();
        int sizeX = random.nextInt(bounds.getX() - 1) + 2;
        int sizeY = random.nextInt(bounds.getY() - 1) + 2;
        logV(this, "... generated size %d, %d", sizeX, sizeY);
        return coordinate(sizeX, sizeY);
    }

    private BoardSize() {}

    public static BoardSize boardSize() {
        return new BoardSize();
    }
}
