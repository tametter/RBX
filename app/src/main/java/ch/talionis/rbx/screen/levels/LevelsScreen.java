package ch.talionis.rbx.screen.levels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.functional.Scope;
import ch.talionis.rbx.screen.Screen;

import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class LevelsScreen extends Screen {
    private View mainView;
    private RecyclerView levelsRecyclerView;
    private LevelsAdapter levelsAdapter;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        mainView = layoutInflater.inflate(R.layout.screen_levels, container, false);

        mainView.findViewById(R.id.top_bar_back_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());
        ((TextView) mainView.findViewById(R.id.top_bar_title)).setText(R.string.screen_levels_title);

        levelsRecyclerView = mainView.findViewById(R.id.levels_recyclerview);
        levelsAdapter = new LevelsAdapter(container.getContext());
        levelsRecyclerView.setAdapter(levelsAdapter);
        levelsRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return mainView;
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }

    @Override
    public void onInsets(WindowInsets insets) {
        super.onInsets(insets);
        mainView.setPadding(mainView.getPaddingLeft(), mainView.getPaddingTop(), mainView.getPaddingRight(), 0);
        levelsRecyclerView.setPadding(levelsRecyclerView.getPaddingLeft(), levelsRecyclerView.getPaddingTop(), levelsRecyclerView.getPaddingRight(), insets.getStableInsetBottom());
    }
}
