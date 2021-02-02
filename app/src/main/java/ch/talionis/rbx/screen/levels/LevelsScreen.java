package ch.talionis.rbx.screen.levels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.functional.Scope;
import ch.talionis.rbx.screen.Screen;

import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class LevelsScreen extends Screen {
    private LevelsAdapter levelsAdapter;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.screen_levels, container, false);

        view.findViewById(R.id.top_bar_back_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());
        ((TextView) view.findViewById(R.id.top_bar_title)).setText(R.string.screen_levels_title);

        RecyclerView levelsRecyclerView = view.findViewById(R.id.levels_recyclerview);
        levelsAdapter = new LevelsAdapter(container.getContext());
        levelsRecyclerView.setAdapter(levelsAdapter);
        levelsRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }
}
