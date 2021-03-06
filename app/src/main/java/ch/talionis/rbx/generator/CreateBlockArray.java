package ch.talionis.rbx.generator;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;

import static ch.talionis.rbx.engine.model.Block.emptyBlock;

public class CreateBlockArray extends Step<Coordinate, Block[][]> {
    @Override
    public Block[][] apply(Coordinate input) {
        Block[][] blocks = new Block[input.getX()][input.getY()];

        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[0].length; y++) {
                blocks[x][y] = emptyBlock();
            }
        }

        return blocks;
    }

    private CreateBlockArray() {}

    public static CreateBlockArray createBlockArray() {
        return new CreateBlockArray();
    }
}
