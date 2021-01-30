package ch.talionis.rbx.animation;

import android.view.View;

public interface ScreenAnimation {
    interface AnimationCallable<T, E, I, K, L> {
        void apply(T t, E e, I i, K k, L l);
    }

    AnimationCallable<View, View, View, View, Runnable> pushAnimation();

    AnimationCallable<View, View, View, View, Runnable> popAnimation();
}
