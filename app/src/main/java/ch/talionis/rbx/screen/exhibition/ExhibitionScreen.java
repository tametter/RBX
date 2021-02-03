package ch.talionis.rbx.screen.exhibition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ScrollView;
import android.widget.TextView;

import ch.talionis.rbx.R;
import ch.talionis.rbx.functional.Scope;
import ch.talionis.rbx.screen.Screen;

import static ch.talionis.rbx.activities.ApplicationUtils.getRouter;

public class ExhibitionScreen extends Screen {
    private View mainView;
    private ScrollView scrollView;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        mainView = layoutInflater.inflate(R.layout.screen_exhibition, container, false);

        mainView.findViewById(R.id.top_bar_back_button).setOnClickListener(unused -> getRouter(container.getContext()).pop());
        ((TextView) mainView.findViewById(R.id.top_bar_title)).setText(R.string.screen_exhibition_title);

        scrollView = mainView.findViewById(R.id.exhibition_scrollview);

        return mainView;
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }

    @Override
    public void onInsets(WindowInsets insets) {
        super.onInsets(insets);
        mainView.setPadding(mainView.getPaddingLeft(), mainView.getPaddingTop(), mainView.getPaddingRight(), 0);
        scrollView.setPadding(scrollView.getPaddingLeft(), scrollView.getPaddingTop(), scrollView.getPaddingRight(), insets.getStableInsetBottom());
    }
}
