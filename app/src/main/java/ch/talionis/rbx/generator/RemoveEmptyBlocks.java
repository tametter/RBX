package ch.talionis.rbx.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;

import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;
import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.absentBlock;
import static ch.talionis.rbx.engine.model.Block.emptyMovable;
import static ch.talionis.rbx.engine.model.Coordinate.coordinate;

public class RemoveEmptyBlocks extends Step<Block[][], Block[][]> {
    private static final float baseChance = 0.3f;
    private static final float chancePerNeighbor = 0.3f;

    @Override
    public Block[][] apply(Block[][] blocks) {
        int width = blocks.length;
        int height = blocks[0].length;

        Random random = new Random();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (blocks[x][y].getType() != EMPTY) {
                    continue;
                }

                int movableNeighbors = countMovableNeighbors(blocks, x, y);
                float chanceToRemain = Math.min(1, baseChance + chancePerNeighbor * movableNeighbors);
                if (random.nextFloat() > chanceToRemain) {
                    blocks[x][y] = absentBlock();
                }
            }
        }

        // Remove islands
        // TODO: This only finds singular islands...
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (countAbsentNeighbors(blocks, x, y) == 4) {
                    blocks[x][y] = absentBlock();
                }
            }
        }

        List<Coordinate> emptyBlocks = new ArrayList<>();
        // Set all but one empty block to empty movable
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (blocks[x][y].getType() == EMPTY) {
                    emptyBlocks.add(coordinate(x, y));
                }
            }
        }
        Collections.shuffle(emptyBlocks);
        if (emptyBlocks.size() > 0) {
            emptyBlocks.remove(0);
            for (Coordinate coordinate : emptyBlocks) {
                blocks[coordinate.getX()][coordinate.getY()] = emptyMovable();
            }
        }



        return blocks;
    }

    private int countMovableNeighbors(Block[][] blocks, int x, int y) {
        int count = 0;
        count = isMovable(blocks, x + 1, y) ? count + 1 : count;
        count = isMovable(blocks, x - 1, y) ? count + 1 : count;
        count = isMovable(blocks, x, y + 1) ? count + 1 : count;
        count = isMovable(blocks, x, y - 1) ? count + 1 : count;
        return count;
    }

    private int countAbsentNeighbors(Block[][] blocks, int x, int y) {
        int count = 0;
        count = isAbsent(blocks, x + 1, y) ? count + 1 : count;
        count = isAbsent(blocks, x - 1, y) ? count + 1 : count;
        count = isAbsent(blocks, x, y + 1) ? count + 1 : count;
        count = isAbsent(blocks, x, y - 1) ? count + 1 : count;
        return count;
    }

    private boolean isMovable(Block[][] blocks, int x, int y) {
        if (x < 0 || x >= blocks.length || y < 0 || y >= blocks[0].length) {
            return false;
        }

        return blocks[x][y].getType() == MOVABLE;
    }

    private boolean isAbsent(Block[][] blocks, int x, int y) {
        if (x < 0 || x >= blocks.length || y < 0 || y >= blocks[0].length) {
            return true;
        }

        return blocks[x][y].getType() == ABSENT;
    }


    private RemoveEmptyBlocks() {
    }

    public static RemoveEmptyBlocks removeEmptyBlocks() {
        return new RemoveEmptyBlocks();
    }
}
