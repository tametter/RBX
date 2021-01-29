package ch.talionis.rbx.animation;

import android.view.View;

public interface ScreenAnimation {
    void animatePush(View container, View oldView, View newView, Runnable andThen);

    void animatePop(View container, View oldView, View newView, Runnable andThen);
}
