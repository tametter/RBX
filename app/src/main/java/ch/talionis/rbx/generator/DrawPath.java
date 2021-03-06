package ch.talionis.rbx.generator;

import ch.talionis.rbx.engine.model.Block;

import static ch.talionis.rbx.engine.model.Block.endBlock;
import static ch.talionis.rbx.engine.model.Block.startBlock;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;

public class DrawPath extends Step<Block[][], Block[][]> {
    @Override
    public Block[][] apply(Block[][] blocks) {
        blocks[0][0] = startBlock(RIGHT);
        blocks[1][1] = endBlock(LEFT);
        return blocks;
    }

    private DrawPath() {
    }

    public static DrawPath drawPath() {
        return new DrawPath();
    }
}
