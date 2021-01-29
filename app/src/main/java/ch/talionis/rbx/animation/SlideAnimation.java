package ch.talionis.rbx.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import static ch.talionis.rbx.functional.AnimationListener.onAnimationEnd;

public class SlideAnimation implements ScreenAnimation {
    private static final long DURATION = 400;

    @Override
    public void animatePush(View container, View oldView, View newView, Runnable andThen) {
        newView.setTranslationX(container.getWidth());

        ObjectAnimator viewOutAnimator = ObjectAnimator.ofFloat(oldView, "translationX", 0, -container.getWidth());
        ObjectAnimator viewInAnimator = ObjectAnimator.ofFloat(newView, "translationX", container.getWidth(), 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.playTogether(viewOutAnimator, viewInAnimator);
        onAnimationEnd(animatorSet, andThen);
        animatorSet.start();
    }

    @Override
    public void animatePop(View container, View oldView, View newView, Runnable andThen) {
        newView.setTranslationX(-container.getWidth());

        ObjectAnimator viewOutAnimator = ObjectAnimator.ofFloat(oldView, "translationX", 0, container.getWidth());
        ObjectAnimator viewInAnimator = ObjectAnimator.ofFloat(newView, "translationX", -container.getWidth(), 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.playTogether(viewOutAnimator, viewInAnimator);
        onAnimationEnd(animatorSet, andThen);
        animatorSet.start();
    }
}
