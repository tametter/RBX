package ch.talionis.rbx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.talionis.rbx.levels.LevelGroup;

import static ch.talionis.rbx.android.AndroidUtils.dpToPx;

public class LevelsView extends FrameLayout {
    private List<LevelView> levelViews = new ArrayList<>();
    private float separatorSize;

    public LevelsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        separatorSize = dpToPx(getContext(), 8);

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            LevelView levelView = new LevelView(getContext(), null);
            levelView.setLevelNumber(i + 1);
            levelView.setStars(random.nextInt(4));
            levelViews.add(levelView);
            addView(levelView);
        }

        setClipToPadding(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int height = bottom - top - getPaddingTop() - getPaddingEnd();
        int width = right - left - getPaddingLeft() - getPaddingRight();

        float totalHorizontalSeparators = 4 * separatorSize;
        float totalVerticalSeparators = separatorSize;

        float availableWidth = width - totalHorizontalSeparators;
        float availableHeight = height - totalVerticalSeparators;

        float maxLevelWidth = availableWidth / 5;
        float maxLevelHeight = availableHeight / 2;

        float levelSize = Math.min(maxLevelWidth, maxLevelHeight);

        float horizontalLeftover = width - (levelSize * 5 + totalHorizontalSeparators);
        float verticalLeftover = height - (levelSize * 2 + totalVerticalSeparators);

        float insetLeft = 0.5f * horizontalLeftover + getPaddingLeft();
        float insetTop = 0.5f * verticalLeftover + getPaddingTop();

        for (int i = 0; i < levelViews.size(); i++) {
            int row = i < 5 ? 0 : 1;
            int column = i - row * 5;

            LevelView currentView = levelViews.get(i);

            currentView.layout((int) (insetLeft + levelSize * column + column * separatorSize), (int) (insetTop + (row * (levelSize + separatorSize))), (int) (insetLeft + levelSize * column + levelSize + column * separatorSize), (int) (insetTop + (row * (levelSize + separatorSize)) + levelSize));
        }
    }

    public void setLevelGroup(LevelGroup levelGroup) {
        for (int i=0; i<levelViews.size();i++) {
            levelViews.get(i).setVisibility(i < levelGroup.getNumberOfLevels() ? View.VISIBLE : View.GONE);
        }
    }
}
