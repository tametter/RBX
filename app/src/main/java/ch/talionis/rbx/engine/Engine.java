package ch.talionis.rbx.engine;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Direction;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.engine.model.Move;
import ch.talionis.rbx.engine.model.State;

import static ch.talionis.rbx.engine.model.Direction.*;

/**
 * Operates on a state.
 */
public class Engine {
    private State state;

    public void load(Level level) {
        state = new State(level);
    }

    public void load(State state) {
        this.state = state;
    }

    public boolean canUndo() {
        return state.peek() != null;
    }

    public void undo() {
        Move move = state.pop();
        apply(move);
    }

    public boolean isValid(Move move) {
        if (move.getIndex() < 0) {
            return false;
        }

        if (isHorizontal(move.getDirection())) {
            if (move.getIndex() >= state.getLevel().getWidth()) {
                return false;
            }
        } else {
            if (move.getIndex() >= state.getLevel().getHeight()) {
                return false;
            }
        }

        Block block = getInitialBlock(move);

        // TODO

        return false;
    }

    public void apply(Move move) {
        if (!isValid(move)) {
            throw new IllegalArgumentException("Invalid move.");
        }
        state.push(move);

        // TODO
    }

    private static boolean isHorizontal(Direction direction) {
        return direction == RIGHT || direction == LEFT;
    }

    private Block getInitialBlock(Move move) {
        boolean isHorizontal = isHorizontal(move.getDirection());

        int x = isHorizontal ? (move.getDirection() == RIGHT ? 0 : state.getLevel().getWidth()) : move.getIndex();
        int y = isHorizontal ? move.getIndex() : (move.getDirection() == DOWN ? 0 : state.getLevel().getHeight());

        return state.get(x, y);
    }
}
