package ch.talionis.rbx.engine.model;

import static ch.talionis.rbx.engine.model.Block.copy;
import static ch.talionis.rbx.engine.model.Coordinate.coordinate;

/**
 * State of a level plus moves.
 */
public class State {
    private final Level level;
    private final Block[][] currentBlocks;

    public State(Level level) {
        this.level = level;
        this.currentBlocks = new Block[level.getWidth()][level.getHeight()];

        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                this.currentBlocks[x][y] = copy(level.get(x, y));
            }
        }
    }

    public Level getLevel() {
        return level;
    }

    public Coordinate getPosition(Block block) {
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                if (currentBlocks[x][y] == block) {
                    return coordinate(x, y);
                }
            }
        }
        throw new IllegalStateException("Failed to find position for block.");
    }

    public Block get(int x, int y) {
        return currentBlocks[x][y];
    }

    public void set(int x, int y, Block block) {
        currentBlocks[x][y] = block;
    }
}
