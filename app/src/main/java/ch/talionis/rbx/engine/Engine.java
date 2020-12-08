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

import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Block.BlockType.MOVABLE;
import static ch.talionis.rbx.engine.model.Block.BlockType.SOLID;

/**
 * Operates on a state.
 */
public class Engine {
    private final List<EngineObserver> engineObservers = new ArrayList<>();
    private State state;

    public void load(Level level) {
        state = new State(level);
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

            if (currentBlock == null || currentBlock.getType() == SOLID) {
                return false;
            } else if (currentBlock.getType() == EMPTY) {
                return true;
            }
        }
    }

    public void apply(Move move) {
        if (!isValid(move)) {
            throw new IllegalArgumentException("Invalid move.");
        }

        Direction inverseDirection = move.getDirection().inverse();
        Block firstMovingBlock = state.get(move.getX(), move.getY());
        while (true) {
            Block preceedingBlock = getNeighborOrNull(getState().getPosition(firstMovingBlock), inverseDirection);
            if (preceedingBlock == null || preceedingBlock.getType() != MOVABLE) {
                break;
            }
            firstMovingBlock = preceedingBlock;
        }

        Map<Coordinate, Block> blocksToBeSet = new HashMap<>();
        blocksToBeSet.put(getState().getPosition(firstMovingBlock), new Block(EMPTY));

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

        for (Map.Entry<Coordinate, Block> blockToBeSet : blocksToBeSet.entrySet()) {
            state.set(blockToBeSet.getKey().getX(), blockToBeSet.getKey().getY(), blockToBeSet.getValue());
        }

        notifyStateChanged();
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
