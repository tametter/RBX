package ch.talionis.rbx.screen.play;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.EngineObserver;
import ch.talionis.rbx.engine.model.State;
import ch.talionis.rbx.functional.Scope;
import ch.talionis.rbx.levels.LevelManager;
import ch.talionis.rbx.screen.Screen;
import ch.talionis.rbx.views.BlockLayout;

import static android.view.View.GONE;
import static ch.talionis.rbx.activities.ApplicationUtils.getLevelManager;
import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class PlayScreen extends Screen {
    private Engine engine = new Engine();
    private BlockLayout blockLayout;
    private LevelManager levelManager;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.screen_play, container, false);

        view.findViewById(R.id.top_bar_back_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());
        view.findViewById(R.id.top_bar_title).setVisibility(GONE);

        engine = new Engine();
        blockLayout = view.findViewById(R.id.block_layout);
        blockLayout.setEngine(engine);

        levelManager = getLevelManager(view.getContext());
        return view;
    }

    @Override
    public void onAttached(Scope attachedScope) {
        engine.load(levelManager.getNextLevel());

        engine.addObserver(new EngineObserver() {
            @Override
            public void onLevelLoaded() {

            }

            @Override
            public void onStateUpdated(State state) {

            }

            @Override
            public void onLevelComplete() {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    engine.load(levelManager.getNextLevel());
                }, 1000);
            }

            @Override
            public void onLevelUnloaded() {

            }
        });
    }
}
