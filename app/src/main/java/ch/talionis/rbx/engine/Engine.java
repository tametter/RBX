package ch.talionis.rbx.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;
import ch.talionis.rbx.engine.model.Direction;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.engine.model.Move;
import ch.talionis.rbx.engine.model.State;

import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.END;
import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.NONE;
import static ch.talionis.rbx.engine.model.Block.BlockConnectionType.START;
import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;
import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.BlockType.SOLID;
import static ch.talionis.rbx.engine.model.Block.emptyBlock;

/**
 * Operates on a state.
 */
public class Engine {
    private final List<EngineObserver> engineObservers = new ArrayList<>();
    private State state;

    public void load(Level level) {
        state = new State(level);
        calculatePowered();
        for (EngineObserver engineObserver : engineObservers) {
            engineObserver.onLevelLoaded();
        }
    }

    public void addObserver(EngineObserver engineObserver) {
        if (!engineObservers.contains(engineObserver)) {
            engineObservers.add(engineObserver);
        }
    }

    public State getState() {
        return state;
    }

    public boolean isValid(Move move) {
        if (move.getX() < 0 || move.getX() >= state.getLevel().getWidth()) {
            return false;
        }

        if (move.getY() < 0 || move.getY() >= state.getLevel().getHeight()) {
            return false;
        }

        Block currentBlock = state.get(move.getX(), move.getY());
        if (currentBlock.getType() != MOVABLE) {
            return false;
        }

        while (true) {
            Coordinate currentBlockCoords = state.getPosition(currentBlock);
            currentBlock = getNeighborOrNull(currentBlockCoords, move.getDirection());

            if (currentBlock == null || currentBlock.getType() == SOLID || currentBlock.getType() == ABSENT) {
                return false;
            } else if (currentBlock.getType() == EMPTY) {
                return true;
            }
        }
    }

    public Map<Coordinate, Block> getBlocksThatWillMove(Move move) {
        Block firstMovingBlock = state.get(move.getX(), move.getY());

        Map<Coordinate, Block> blocksToBeSet = new HashMap<>();
        blocksToBeSet.put(getState().getPosition(firstMovingBlock), emptyBlock());

        Coordinate position = getState().getPosition(firstMovingBlock);
        blocksToBeSet.put(position.move(move.getDirection()), firstMovingBlock);
        while (true) {
            Block neighborOrNull = getNeighborOrNull(position, move.getDirection());
            if (neighborOrNull == null || neighborOrNull.getType() != MOVABLE) {
                break;
            }
            position = position.move(move.getDirection());
            blocksToBeSet.put(position.move(move.getDirection()), neighborOrNull);
        }

        return blocksToBeSet;
    }

    public void apply(Move move) {
        if (!isValid(move)) {
            throw new IllegalArgumentException("Invalid move.");
        }

        for (Map.Entry<Coordinate, Block> blockToBeSet : getBlocksThatWillMove(move).entrySet()) {
            state.set(blockToBeSet.getKey().getX(), blockToBeSet.getKey().getY(), blockToBeSet.getValue());
        }

        calculatePowered();

        notifyStateChanged();
    }

    public boolean isComplete() {
        List<Block> blocksThatNeedToBePowered = getBlocksThatNeedToBePowered();
        for (int i = 0; i < blocksThatNeedToBePowered.size(); i++) {
            Block block = blocksThatNeedToBePowered.get(i);
            if (!block.isPowered()) {
                return false;
            }
        }
        return true;
    }

    private void calculatePowered() {
        List<Block> blocksThatNeedToBePowered = getBlocksThatNeedToBePowered();

        Block startBlock = null;
        Block endBlock = null;

        for (Block block : blocksThatNeedToBePowered) {
            block.setPowered(false);
            if (block.getConnectionType() == START) {
                startBlock = block;
            } else if (block.getConnectionType() == END) {
                endBlock = block;
            }
        }

        if (startBlock == null || endBlock == null) {
            throw new IllegalStateException("Invalid level. Missing start or end block.");
        }

        List<Block> chain = new ArrayList<>();
        chain.add(startBlock);

        while (true) {
            Block lastBlock = chain.get(chain.size() - 1);
            Block currentBlock = getNeighborOrNull(state.getPosition(lastBlock), lastBlock.to());
            if (currentBlock == null) {
                break;
            }

            if (currentBlock.from() == null) {
                break;
            }

            if (!lastBlock.to().connectsTo(currentBlock.from())) {
                break;
            }

            chain.add(currentBlock);

            if (currentBlock.getConnectionType() == END) {
                break;
            }
        }

        for (Block block : chain) {
            block.setPowered(true);
        }
    }

    private List<Block> getBlocksThatNeedToBePowered() {
        int width = state.getLevel().getWidth();
        int height = state.getLevel().getHeight();
        List<Block> blocksThatNeedToBePowered = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Block block = state.get(x, y);
                if (block.getConnectionType() != NONE) {
                    blocksThatNeedToBePowered.add(block);
                }
            }
        }
        return blocksThatNeedToBePowered;
    }

    private Block getNeighborOrNull(Coordinate coordinate, Direction direction) {
        Coordinate newCoordinate = coordinate.move(direction);

        if (newCoordinate.getX() < 0 || newCoordinate.getX() >= state.getLevel().getWidth()) {
            return null;
        }

        if (newCoordinate.getY() < 0 || newCoordinate.getY() >= state.getLevel().getHeight()) {
            return null;
        }

        return state.get(newCoordinate.getX(), newCoordinate.getY());
    }

    private void notifyStateChanged() {
        for (EngineObserver observer : engineObservers) {
            observer.onStateUpdated(state);
        }
    }
}
