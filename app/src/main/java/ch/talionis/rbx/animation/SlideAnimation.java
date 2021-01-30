package ch.talionis.rbx.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import static ch.talionis.rbx.functional.AnimationListener.onAnimationEnd;

public class SlideAnimation implements ScreenAnimation {
    private static final long DURATION = 400;
    private static final long PARALLAX_MAGNITUDE = 200;

    @Override
    public AnimationCallable<View, View, View, View, Runnable> pushAnimation() {
        return (container, parallaxBackground, oldView, newView, andThenRunnable) -> {
            newView.setTranslationX(container.getWidth());

            ObjectAnimator viewOutAnimator = ObjectAnimator.ofFloat(oldView, "translationX", 0, -container.getWidth());
            ObjectAnimator viewInAnimator = ObjectAnimator.ofFloat(newView, "translationX", container.getWidth(), 0);
            ObjectAnimator parallaxAnimator = ObjectAnimator.ofFloat(parallaxBackground, "translationX", parallaxBackground.getTranslationX(), parallaxBackground.getTranslationX() - PARALLAX_MAGNITUDE);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(DURATION);
            animatorSet.playTogether(viewOutAnimator, viewInAnimator, parallaxAnimator);
            onAnimationEnd(animatorSet, andThenRunnable);
            animatorSet.start();
        };
    }

    @Override
    public AnimationCallable<View, View, View, View, Runnable> popAnimation() {
        return (container, parallaxBackground, oldView, newView, andThenRunnable) -> {
            newView.setTranslationX(-container.getWidth());

            ObjectAnimator viewOutAnimator = ObjectAnimator.ofFloat(oldView, "translationX", 0, container.getWidth());
            ObjectAnimator viewInAnimator = ObjectAnimator.ofFloat(newView, "translationX", -container.getWidth(), 0);
            ObjectAnimator parallaxAnimator = ObjectAnimator.ofFloat(parallaxBackground, "translationX", parallaxBackground.getTranslationX(), parallaxBackground.getTranslationX() + PARALLAX_MAGNITUDE);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(DURATION);
            animatorSet.playTogether(viewOutAnimator, viewInAnimator, parallaxAnimator);
            onAnimationEnd(animatorSet, andThenRunnable);
            animatorSet.start();
        };
    }
}
