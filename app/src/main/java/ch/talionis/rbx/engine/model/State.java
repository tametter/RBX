package ch.talionis.rbx.engine.model;

import android.util.Pair;

import java.util.Stack;

/**
 * State of a level plus moves.
 */
public class State {
    private final Level level;
    private final Stack<Move> moves;
    private final Block[][] currentBlocks;

    public State(Level level) {
        this.level = level;
        this.moves = new Stack<>();
        this.currentBlocks = new Block[level.getWidth()][level.getHeight()];

        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                this.currentBlocks[x][y] = level.get(x, y);
            }
        }
    }

    public Level getLevel() {
        return level;
    }

    public Pair<Integer, Integer> getPosition(Block block) {
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                if (currentBlocks[x][y] == block) {
                    return new Pair<>(x, y);
                }
            }
        }
        throw new IllegalStateException("Failed to find position for block.");
    }

    public Move peek() {
        return moves.peek();
    }

    public void push(Move move) {
        moves.add(move);
    }

    public Move pop() {
        return moves.pop();
    }

    public Block get(int x, int y) {
        return currentBlocks[x][y];
    }

    public void set(int x, int y, Block block) {
        currentBlocks[x][y] = block;
    }
}
