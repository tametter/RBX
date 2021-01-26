package ch.talionis.rbx.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class FadeAnimations {

    public static void fadeOut(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setStartDelay(0);
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "elevation", 0, 20),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.2f),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.2f));
        animatorSet.start();
    }

    public static void fadeIn(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setStartDelay(0);
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "elevation", 20, 0),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1),
                ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1));
        animatorSet.start();
    }
}
