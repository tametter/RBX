package ch.talionis.rbx.engine;

import ch.talionis.rbx.engine.model.State;

public interface EngineObserver {
    void onStateLoaded(State state);
}
