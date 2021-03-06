package ch.talionis.rbx.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;
import ch.talionis.rbx.engine.model.Direction;

import static ch.talionis.rbx.engine.model.Block.endBlock;
import static ch.talionis.rbx.engine.model.Block.normalConnector;
import static ch.talionis.rbx.engine.model.Block.startBlock;
import static ch.talionis.rbx.engine.model.Coordinate.coordinate;
import static ch.talionis.rbx.engine.model.Direction.DOWN;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;
import static ch.talionis.rbx.logging.Logger.logV;

public class DrawPath extends Step<Block[][], Block[][]> {
    @Override
    public Block[][] apply(Block[][] blocks) {
        int width = blocks.length;
        int height = blocks[0].length;

        Coordinate startPoint = getStartPoint(width, height);
        Coordinate endPoint = getEndPoint(startPoint, width, height);

        List<Coordinate> path = connect(startPoint, endPoint);
        for (int i = 0; i < path.size(); i++) {
            blocks[path.get(i).getX()][path.get(i).getY()] = constructBlock(path, i);
        }

        return blocks;
    }

    private Coordinate getStartPoint(int width, int height) {
        Random random = new Random();
        int randX = random.nextInt(width);
        int randY = random.nextInt(height);

        return random.nextBoolean() ? coordinate(randX, 0) : coordinate(0, randY);
    }

    private Coordinate getEndPoint(Coordinate startPoint, int width, int height) {
        int maxDistance = -1;
        Coordinate endPoint = null;
        for (int x = 0; x < width; x++) {
            Coordinate top = coordinate(x, 0);
            Coordinate bottom = coordinate(x, height - 1);

            if (manhattanDistance(startPoint, top) > maxDistance) {
                maxDistance = manhattanDistance(startPoint, top);
                endPoint = top;
            }

            if (manhattanDistance(startPoint, bottom) > maxDistance) {
                maxDistance = manhattanDistance(startPoint, bottom);
                endPoint = bottom;
            }
        }

        for (int y = 0; y < height; y++) {
            Coordinate left = coordinate(0, y);
            Coordinate right = coordinate(width - 1, y);

            if (manhattanDistance(startPoint, left) > maxDistance) {
                maxDistance = manhattanDistance(startPoint, left);
                endPoint = left;
            }

            if (manhattanDistance(startPoint, right) > maxDistance) {
                maxDistance = manhattanDistance(startPoint, right);
                endPoint = right;
            }
        }

        return endPoint;
    }

    private int manhattanDistance(Coordinate from, Coordinate to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY());
    }

    private List<Coordinate> connect(Coordinate startPoint, Coordinate endPoint) {
        Random random = new Random();
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(startPoint);

        Coordinate current = startPoint;
        while (manhattanDistance(current, endPoint) > 1) {
            Direction testDirection = Direction.values()[random.nextInt(Direction.values().length)];
            Coordinate moved = current.move(testDirection);

            if (manhattanDistance(moved, endPoint) < manhattanDistance(current, endPoint)) {
                current = moved;
                coordinates.add(moved);
            }
        }

        coordinates.add(endPoint);

        return coordinates;
    }

    private Block constructBlock(List<Coordinate> coordinates, int index) {
        if (index == 0) {
            Coordinate from = coordinates.get(index);
            Coordinate to = coordinates.get(index + 1);
            return startBlock(getDirection(from, to));
        } else if (index == coordinates.size() - 1) {
            Coordinate from = coordinates.get(index);
            Coordinate to = coordinates.get(index - 1);
            return endBlock(getDirection(from, to));
        } else {
            Coordinate from = coordinates.get(index - 1);
            Coordinate current = coordinates.get(index);
            Coordinate to = coordinates.get(index + 1);
            logV(this, "Connecting " + getDirection(current, from) + ", " + getDirection(current, to));
            return normalConnector(getDirection(current, from), getDirection(current, to));
        }
    }

    private Direction getDirection(Coordinate from, Coordinate to) {
        if (from.getX() < to.getX()) {
            return RIGHT;
        } else if (from.getX() > to.getX()) {
            return LEFT;
        } else if (from.getY() > to.getY()) {
            return UP;
        } else {
            return DOWN;
        }
    }

    private DrawPath() {
    }

    public static DrawPath drawPath() {
        return new DrawPath();
    }
}
