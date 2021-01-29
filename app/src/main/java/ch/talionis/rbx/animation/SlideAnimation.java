package ch.talionis.rbx.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import ch.talionis.rbx.functional.QuadConsumer;

import static ch.talionis.rbx.functional.AnimationListener.onAnimationEnd;

public class SlideAnimation implements ScreenAnimation {
    private static final long DURATION = 400;

    @Override
    public QuadConsumer<View, View, View, Runnable> pushAnimation() {
        return (container, oldView, newView, andThenRunnable) -> {
            newView.setTranslationX(container.getWidth());

            ObjectAnimator viewOutAnimator = ObjectAnimator.ofFloat(oldView, "translationX", 0, -container.getWidth());
            ObjectAnimator viewInAnimator = ObjectAnimator.ofFloat(newView, "translationX", container.getWidth(), 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(DURATION);
            animatorSet.playTogether(viewOutAnimator, viewInAnimator);
            onAnimationEnd(animatorSet, andThenRunnable);
            animatorSet.start();
        };
    }

    @Override
    public QuadConsumer<View, View, View, Runnable> popAnimation() {
        return (container, oldView, newView, andThenRunnable) -> {
            newView.setTranslationX(-container.getWidth());

            ObjectAnimator viewOutAnimator = ObjectAnimator.ofFloat(oldView, "translationX", 0, container.getWidth());
            ObjectAnimator viewInAnimator = ObjectAnimator.ofFloat(newView, "translationX", -container.getWidth(), 0);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(DURATION);
            animatorSet.playTogether(viewOutAnimator, viewInAnimator);
            onAnimationEnd(animatorSet, andThenRunnable);
            animatorSet.start();
        };
    }
}
