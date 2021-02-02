package ch.talionis.rbx.screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

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

    public void onInsets(WindowInsets insets) {
        Log.v("Badum", "setting insets");
        view.setPadding(insets.getStableInsetLeft(), insets.getStableInsetTop(), insets.getStableInsetRight(), insets.getStableInsetBottom());
    }

    public abstract View createView(LayoutInflater layoutInflater, ViewGroup container);

    public abstract void onAttached(Scope attachedScope);
}
