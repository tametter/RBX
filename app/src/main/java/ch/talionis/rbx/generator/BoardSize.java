package ch.talionis.rbx.generator;

import android.util.Pair;

import java.util.Random;

import ch.talionis.rbx.engine.model.Coordinate;

import static ch.talionis.rbx.engine.model.Coordinate.coordinate;
import static ch.talionis.rbx.logging.Logger.logV;

/**
 * Generates a board size.
 */
public class BoardSize extends Step<Pair<Integer, Integer>, Coordinate> {

    @Override
    public Coordinate apply(Pair<Integer, Integer> minMax) {
        logV(this, "Generating board size with bounds between %d-%d", minMax.first, minMax.second);


        int sizeX = getSize(minMax.first, minMax.second);
        int sizeY = getSize(minMax.first, minMax.second);
        logV(this, "... generated size %d, %d", sizeX, sizeY);
        return coordinate(sizeX, sizeY);
    }

    private int getSize(int min, int max) {
        int randomBound = max - min + 1;
        Random random = new Random();
        return random.nextInt(randomBound) + min;
    }

    private BoardSize() {
    }

    public static BoardSize boardSize() {
        return new BoardSize();
    }
}
