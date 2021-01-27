package ch.talionis.rbx.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.functional.Scope;

public class TestScreen extends Screen {

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        View testView = new View(container.getContext());
        testView.setBackgroundResource(R.color.colorAccent);
        testView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return testView;
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }
}
