package ch.talionis.rbx.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.functional.Scope;

import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class SettingsScreen extends Screen {

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.screen_settings, container, false);

        view.findViewById(R.id.top_bar_back_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());
        ((TextView) view.findViewById(R.id.top_bar_title)).setText(R.string.screen_settings_title);

        return view;
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }
}
