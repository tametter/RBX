package ch.talionis.rbx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static ch.talionis.rbx.android.AndroidUtils.dpToPx;

public class StarRow extends FrameLayout {
    private StarView firstStar;
    private StarView secondStar;
    private StarView thirdStar;
    private int starSpacing;

    public StarRow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        firstStar = new StarView(getContext(), null);
        secondStar = new StarView(getContext(), null);
        thirdStar = new StarView(getContext(), null);

        addView(firstStar);
        addView(secondStar);
        addView(thirdStar);

        int elevationDp = 2;
        firstStar.setElevation(dpToPx(getContext(), elevationDp));
        secondStar.setElevation(dpToPx(getContext(), elevationDp));
        thirdStar.setElevation(dpToPx(getContext(), elevationDp));

        starSpacing = (int) dpToPx(getContext(), 4);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left - getPaddingLeft() - getPaddingRight();
        int height = bottom - top - getPaddingTop() - getPaddingBottom();

        int starSize = Math.min((width - 2 * starSpacing) / 3, height);

        firstStar.layout(0, 0, starSize, starSize);
        secondStar.layout(starSize + starSpacing, 0, starSize + starSpacing + starSize, starSize);
        thirdStar.layout(2 * starSize + 2 * starSpacing, 0, 3 * starSize + 2 * starSpacing, starSize);
    }

    public void setSelected(int selectedStars) {
        firstStar.setSelected(selectedStars > 0);
        secondStar.setSelected(selectedStars > 1);
        thirdStar.setSelected(selectedStars > 2);
    }
}
