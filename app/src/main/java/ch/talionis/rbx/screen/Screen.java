package ch.talionis.rbx.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.functional.Scope;

import static ch.talionis.rbx.functional.Callback.onAttach;

public abstract class Screen {
    private View view;

    public View getOrCreateView(ViewGroup container) {
        if (view == null) {
            view = createView((LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), container);
        }

        onAttach(view, (attachedScope, view) -> onAttached(attachedScope));
        return view;
    }

    public abstract View createView(LayoutInflater layoutInflater, ViewGroup container);

    public abstract void onAttached(Scope attachedScope);
}
