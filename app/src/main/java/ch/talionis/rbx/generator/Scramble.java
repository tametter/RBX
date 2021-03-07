package ch.talionis.rbx.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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


        List<Coordinate> emptyBlocks = new ArrayList<>();
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                if (blocks[x][y].getType() == EMPTY) {
                    emptyBlocks.add(coordinate(x, y));
                }
            }
        }

        for (int move = 0; move < 1000; move++) {
            Collections.shuffle(emptyBlocks);
            Direction testDirection = Direction.values()[random.nextInt(Direction.values().length)];
            Coordinate currentCoordinate = emptyBlocks.get(0);
            Coordinate movedCoordinate = currentCoordinate.move(testDirection);
            if (isMovable(blocks, movedCoordinate.getX(), movedCoordinate.getY())) {
                blocks[currentCoordinate.getX()][currentCoordinate.getY()] = blocks[movedCoordinate.getX()][movedCoordinate.getY()];
                blocks[movedCoordinate.getX()][movedCoordinate.getY()] = emptyBlock();
                emptyBlocks.set(0, movedCoordinate);
            }
        }

        return blocks;
    }

    private boolean isMovable(Block[][] blocks, int x, int y) {
        if (x < 0 || x >= blocks.length || y < 0 || y >= blocks[0].length) {
            return false;
        }

        return blocks[x][y].getType() == MOVABLE;
    }


    private Scramble() {
    }

    public static Scramble scramble() {
        return new Scramble();
    }
}
