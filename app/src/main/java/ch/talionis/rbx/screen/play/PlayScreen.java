package ch.talionis.rbx.screen.play;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.EngineObserver;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.engine.model.State;
import ch.talionis.rbx.functional.Scope;
import ch.talionis.rbx.screen.Screen;
import ch.talionis.rbx.views.BlockLayout;

import static android.view.View.GONE;
import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;
import static ch.talionis.rbx.engine.model.Block.absentBlock;
import static ch.talionis.rbx.engine.model.Block.emptyBlock;
import static ch.talionis.rbx.engine.model.Block.endBlock;
import static ch.talionis.rbx.engine.model.Block.normalConnector;
import static ch.talionis.rbx.engine.model.Block.startBlock;
import static ch.talionis.rbx.engine.model.Direction.DOWN;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;

public class PlayScreen extends Screen {
    private Engine engine = new Engine();
    private BlockLayout blockLayout;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.screen_play, container, false);

        view.findViewById(R.id.top_bar_back_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());
        view.findViewById(R.id.top_bar_title).setVisibility(GONE);

        engine = new Engine();
        blockLayout = view.findViewById(R.id.block_layout);
        blockLayout.setEngine(engine);
        return view;
    }

    @Override
    public void onAttached(Scope attachedScope) {
        engine.load(getLevel(0));

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
                    engine.load(getLevel(1));
                }, 1000);
            }

            @Override
            public void onLevelUnloaded() {

            }
        });
    }

    private Level getLevel(int level) {
        if (level == 0) {
            return new Level(new Block[][]{
                    {absentBlock(), startBlock(RIGHT)},
                    {endBlock(LEFT), normalConnector(LEFT, RIGHT)},
                    {emptyBlock(), emptyBlock()},
            });
        }

        return new Level(new Block[][]{
                {absentBlock(), startBlock(RIGHT)},
                {normalConnector(LEFT, UP), emptyBlock()},
                {emptyBlock(), endBlock(DOWN)},
        });
    }
}
