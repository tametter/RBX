package ch.talionis.rbx.engine.model;

/**
 * A single block in a level.
 */
public class Block {
    public enum BlockType {
        SOLID,
        EMPTY,
        MOVABLE
    }

    private final BlockType type;

    public Block(BlockType type) {
        this.type = type;
    }

    public BlockType getType() {
        return type;
    };
}
