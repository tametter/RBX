package ch.talionis.rbx.engine;

import org.junit.Before;
import org.junit.Test;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Direction;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.engine.model.Move;

import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.absentBlock;
import static ch.talionis.rbx.engine.model.Block.emptyBlock;
import static ch.talionis.rbx.engine.model.Block.endBlock;
import static ch.talionis.rbx.engine.model.Block.normalConnector;
import static ch.talionis.rbx.engine.model.Block.solidBlock;
import static ch.talionis.rbx.engine.model.Block.startBlock;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;

public class EngineTest {
    private static final Level SAMPLE_LEVEL = new Level(new Block[][]{
            {startBlock(UP), solidBlock(), emptyBlock()},
            {normalConnector(LEFT, UP), emptyBlock(), normalConnector(LEFT, UP)},
            {emptyBlock(), solidBlock(), solidBlock()},
            {solidBlock(), emptyBlock(), endBlock(LEFT)}
    });

    private static final Level SAMPLE_LEVEL_WITH_ABSENT = new Level(new Block[][]{{normalConnector(LEFT, UP), normalConnector(LEFT, UP), absentBlock(), endBlock(LEFT)}});
    private static final Level SAMPLE_LEVEL_COMPLETE = new Level(new Block[][]{
            {startBlock(RIGHT), emptyBlock()},
            {emptyBlock(), normalConnector(LEFT, RIGHT)},
            {endBlock(LEFT), emptyBlock()},
    });
    private Engine engine;
    private Engine engine2;
    private Engine engine3;

    @Before
    public void setUp() {
        engine = new Engine();
        engine.load(SAMPLE_LEVEL);

        engine2 = new Engine();
        engine2.load(SAMPLE_LEVEL_WITH_ABSENT);

        engine3 = new Engine();
        engine3.load(SAMPLE_LEVEL_COMPLETE);
    }

    @Test
    public void isValid_moveRight_solid_isFalse() {
        Move move = new Move(0, 1, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveLeft_solid_isFalse() {
        Move move = new Move(0, 1, LEFT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveUp_solid_isFalse() {
        Move move = new Move(0, 1, UP);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveDown_solid_isFalse() {
        Move move = new Move(0, 1, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveRight_empty_isFalse() {
        Move move = new Move(1, 1, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveLeft_empty_isFalse() {
        Move move = new Move(1, 1, LEFT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveUp_empty_isFalse() {
        Move move = new Move(1, 1, UP);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveDown_empty_isFalse() {
        Move move = new Move(1, 1, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveRight_isFalse() {
        Move move = new Move(3, 2, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveDown_isFalse() {
        Move move = new Move(3, 2, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveLeft_isFalse() {
        Move move = new Move(3, 2, LEFT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_bottomRightMovable_moveUp_isTrue() {
        Move move = new Move(3, 2, UP);
        boolean valid = engine.isValid(move);

        assert (valid);
    }

    @Test
    public void isValid_topLeftMovable_moveUp_isFalse() {
        Move move = new Move(0, 0, UP);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_topLeftMovable_moveLeft_isFalse() {
        Move move = new Move(0, 0, LEFT);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_topLeftMovable_moveDown_isFalse() {
        Move move = new Move(0, 0, Direction.DOWN);
        boolean valid = engine.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_topLeftMovable_moveRight_isTrue() {
        Move move = new Move(0, 0, Direction.RIGHT);
        boolean valid = engine.isValid(move);

        assert (valid);
    }

    @Test
    public void isValid_moveOverAbsent_isFalse() {
        Move move = new Move(0, 0, Direction.DOWN);
        boolean valid = engine2.isValid(move);

        assert (!valid);
    }

    @Test
    public void isValid_moveOverAbsent2_isFalse() {
        Move move = new Move(0, 2, UP);
        boolean valid = engine2.isValid(move);

        assert (!valid);
    }

    @Test
    public void apply_bottomRightMovable_moveUp_setRightFields() {
        Move move = new Move(3, 2, UP);

        engine.apply(move);

        assert (engine.getState().get(3, 1).getType() == MOVABLE);
        assert (engine.getState().get(3, 2).getType() == EMPTY);
    }

    @Test
    public void apply_topLeftMovable_moveRight_setRightFields() {
        Move move = new Move(0, 0, Direction.RIGHT);

        engine.apply(move);

        assert (engine.getState().get(0, 0).getType() == EMPTY);
        assert (engine.getState().get(1, 0).getType() == MOVABLE);
        assert (engine.getState().get(2, 0).getType() == MOVABLE);
    }

    @Test
    public void apply_topLeftMovable2_moveRight_setRightFields() {
        Move move = new Move(1, 0, Direction.RIGHT);

        engine.apply(move);

        assert (engine.getState().get(0, 0).getType() == EMPTY);
        assert (engine.getState().get(1, 0).getType() == MOVABLE);
        assert (engine.getState().get(2, 0).getType() == MOVABLE);
    }

    @Test
    public void isComplete_notComplete_returnsFalse() {
        boolean isComplete = engine3.isComplete();

        assert (!isComplete);
    }

    @Test
    public void isComplete_complete_returnsTrue() {
        engine3.apply(new Move(1,1, UP));
        boolean isComplete = engine3.isComplete();

        assert (isComplete);
    }
}
