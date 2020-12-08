package ch.talionis.rbx.engine;

import ch.talionis.rbx.engine.model.State;

public interface EngineObserver {
    void onLevelLoaded();

    void onStateUpdated(State state);
}
