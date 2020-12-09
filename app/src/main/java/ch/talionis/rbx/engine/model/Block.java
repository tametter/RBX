package ch.talionis.rbx.engine.model;

import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.END;
import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.NONE;
import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.NORMAL;
import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.START;
import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;
import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.BlockType.SOLID;

/**
 * A single block in a level.
 */
public class Block {
    public enum BlockType {
        SOLID,
        EMPTY,
        MOVABLE,
        ABSENT
    }

    public enum BlockConnectionType {
        NONE,
        START,
        END,
        NORMAL
    }

    private final BlockType type;
    private final BlockConnectionType connection;
    private final Direction from;
    private final Direction to;

    private Block(BlockType type, BlockConnectionType connection, Direction from, Direction to) {
        this.type = type;
        this.connection = connection;
        this.from = from;
        this.to = to;
    }

    public BlockType getType() {
        return type;
    }

    public BlockConnectionType getConnectionType() {
        return connection;
    }

    public Direction from() {
        return from;
    }

    public Direction to() {
        return to;
    }

    public static Block emptyBlock() {
        return new Block(EMPTY, NONE, null, null);
    }

    public static Block solidBlock() {
        return new Block(SOLID, NONE, null, null);
    }

    public static Block absentBlock() {
        return new Block(ABSENT, NONE, null, null);
    }

    public static Block startBlock(Direction to) {
        return new Block(MOVABLE, START, null, to);
    }

    public static Block endBlock(Direction from) {
        return new Block(MOVABLE, END, from, null);
    }

    public static Block normalConnector(Direction from, Direction to) {
        return new Block(MOVABLE, NORMAL, from, to);
    }
}
