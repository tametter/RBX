package ch.talionis.rbx.generator;

import java.util.Random;

import ch.talionis.rbx.engine.model.Block;

import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.absentBlock;

public class RemoveEmptyBlocks extends Step<Block[][], Block[][]> {
    private static final float chancePerNeighbor = 0.45f;

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
                float chanceToRemain = Math.min(1, chancePerNeighbor * movableNeighbors);
                if (random.nextFloat() > chanceToRemain) {
                    blocks[x][y] = absentBlock();
                }
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

    private boolean isMovable(Block[][] blocks, int x, int y) {
        if (x < 0 || x >= blocks.length || y < 0 || y >= blocks[0].length) {
            return false;
        }

        return blocks[x][y].getType() == MOVABLE;
    }


    private RemoveEmptyBlocks() {
    }

    public static RemoveEmptyBlocks removeEmptyBlocks() {
        return new RemoveEmptyBlocks();
    }
}
