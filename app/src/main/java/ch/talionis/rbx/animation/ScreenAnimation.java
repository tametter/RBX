package ch.talionis.rbx.animation;

import android.view.View;

import ch.talionis.rbx.functional.QuadConsumer;

public interface ScreenAnimation {
    QuadConsumer<View, View, View, Runnable> pushAnimation();

    QuadConsumer<View, View, View, Runnable> popAnimation();
}
