package ch.talionis.rbx.engine.model;

/**
 * An initial unmodifiable level.
 */
public class Level {
    private final Block[][] blocks;

    public Level(Block[][] blocks) {
        this.blocks = blocks;
    }

    public int getWidth() {
        return blocks.length;
    }

    public int getHeight() {
        if (getWidth() == 0) {
            throw new IllegalStateException("Zero width level.");
        }

        return blocks[0].length;
    }

    public Block get(int x, int y) {
        return blocks[x][y];
    }
}
