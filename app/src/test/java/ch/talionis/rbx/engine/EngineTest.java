package ch.talionis.rbx.engine;

import org.junit.Before;
import org.junit.Test;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Direction;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.engine.model.Move;

import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;

public class EngineTest {
    private static final Level SAMPLE_LEVEL = new Level(new Block[][] {
            {new Block(Block.BlockType.MOVABLE), new Block(Block.BlockType.SOLID), new Block(EMPTY)},
            {new Block(Block.BlockType.MOVABLE), new Block(EMPTY), new Block(Block.BlockType.MOVABLE)},
            {new Block(EMPTY), new Block(Block.BlockType.SOLID), new Block(Block.BlockType.SOLID)},
            {new Block(Block.BlockType.SOLID), new Block(EMPTY), new Block(Block.BlockType.MOVABLE)}
    });
    private Engine engine;

    @Before
    public void setUp() {
        engine = new Engine();
        engine.load(SAMPLE_LEVEL);
    }

    @Test
    public void isValid_moveRight_solid_isFalse() {
        Move move = new Move(0, 1, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveLeft_solid_isFalse() {
        Move move = new Move(0, 1, Direction.LEFT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveUp_solid_isFalse() {
        Move move = new Move(0, 1, Direction.UP);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveDown_solid_isFalse() {
        Move move = new Move(0, 1, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveRight_empty_isFalse() {
        Move move = new Move(1, 1, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveLeft_empty_isFalse() {
        Move move = new Move(1, 1, Direction.LEFT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveUp_empty_isFalse() {
        Move move = new Move(1, 1, Direction.UP);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_moveDown_empty_isFalse() {
        Move move = new Move(1, 1, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveRight_isFalse() {
        Move move = new Move(3, 2, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveDown_isFalse() {
        Move move = new Move(3, 2, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveLeft_isFalse() {
        Move move = new Move(3, 2, Direction.LEFT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveUp_isTrue() {
        Move move = new Move(3, 2, Direction.UP);
        boolean valid = engine.isValid(move);

        assert(valid);
    }

    @Test
    public void isValid_topLeftMovable_moveUp_isFalse() {
        Move move = new Move(0, 0, Direction.UP);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_topLeftMovable_moveLeft_isFalse() {
        Move move = new Move(0, 0, Direction.LEFT);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_topLeftMovable_moveDown_isFalse() {
        Move move = new Move(0, 0, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert(!valid);
    }

    @Test
    public void isValid_topLeftMovable_moveRight_isTrue() {
        Move move = new Move(0, 0, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert(valid);
    }

    @Test
    public void apply_bottomRightMovable_moveUp_setRightFields() {
        Move move = new Move(3, 2, Direction.UP);

        engine.apply(move);

        assert(engine.getState().get(3, 1).getType() == Block.BlockType.MOVABLE);
        assert(engine.getState().get(3, 2).getType() == EMPTY);
    }

    @Test
    public void apply_topLeftMovable_moveRight_setRightFields() {
        Move move = new Move(0, 0, Direction.RIGHT);

        engine.apply(move);

        assert(engine.getState().get(0, 0).getType() == EMPTY);
        assert(engine.getState().get(1, 0).getType() == MOVABLE);
        assert(engine.getState().get(2, 0).getType() == MOVABLE);
    }

    @Test
    public void apply_topLeftMovable2_moveRight_setRightFields() {
        Move move = new Move(1, 0, Direction.RIGHT);

        engine.apply(move);

        assert(engine.getState().get(0, 0).getType() == EMPTY);
        assert(engine.getState().get(1, 0).getType() == MOVABLE);
        assert(engine.getState().get(2, 0).getType() == MOVABLE);
    }
}
