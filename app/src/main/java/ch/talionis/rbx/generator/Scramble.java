package ch.talionis.rbx.generator;

import java.util.Random;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;
import ch.talionis.rbx.engine.model.Direction;

import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.emptyBlock;
import static ch.talionis.rbx.engine.model.Coordinate.coordinate;

public class Scramble extends Step<Block[][], Block[][]> {
    @Override
    public Block[][] apply(Block[][] blocks) {
        int width = blocks.length;
        int height = blocks[0].length;

        Random random = new Random();

        for (int move = 0; move < 1000; move++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            if (blocks[x][y].getType() != MOVABLE) {
                continue;
            }

            Direction testDirection = Direction.values()[random.nextInt(Direction.values().length)];
            Coordinate currentCoordinate = coordinate(x, y);
            Coordinate movedCoordinate = currentCoordinate.move(testDirection);
            if (isEmpty(blocks, movedCoordinate.getX(), movedCoordinate.getY())) {
                blocks[movedCoordinate.getX()][movedCoordinate.getY()] = blocks[x][y];
                blocks[x][y] = emptyBlock();
            }
        }

        return blocks;
    }

    private boolean isEmpty(Block[][] blocks, int x, int y) {
        if (x < 0 || x >= blocks.length || y < 0 || y >= blocks[0].length) {
            return false;
        }

        return blocks[x][y].getType() == EMPTY;
    }


    private Scramble() {
    }

    public static Scramble scramble() {
        return new Scramble();
    }
}
