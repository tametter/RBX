package ch.talionis.rbx.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.animation.VerticalSlideAnimation;
import ch.talionis.rbx.functional.Scope;

import static android.view.View.GONE;
import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class MainScreen extends Screen {

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.screen_main, container, false);

        view.findViewById(R.id.top_bar_back_button).setVisibility(GONE);
        view.findViewById(R.id.top_bar_title).setVisibility(GONE);
        view.findViewById(R.id.top_bar_settings_button).setVisibility(View.VISIBLE);
        view.findViewById(R.id.top_bar_settings_button).setOnClickListener(unused -> getRouter(container.getContext()).push(new SettingsScreen(), new VerticalSlideAnimation()));

        view.findViewById(R.id.continue_button).setOnClickListener(unused -> getRouter(container.getContext()).push(new PlayScreen()));
        view.findViewById(R.id.levels_button).setOnClickListener(unused -> getRouter(container.getContext()).push(new LevelsScreen()));
        view.findViewById(R.id.exhibition_button).setOnClickListener(unused -> getRouter(container.getContext()).push(new ExhibitionScreen()));
        view.findViewById(R.id.quit_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());

        return view;
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }
}
