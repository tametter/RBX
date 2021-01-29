package ch.talionis.rbx.screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.functional.Scope;

public class ExhibitionScreen extends Screen {

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup container) {
        return layoutInflater.inflate(R.layout.screen_exhibition, container, false);
    }

    @Override
    public void onAttached(Scope attachedScope) {

    }
}
