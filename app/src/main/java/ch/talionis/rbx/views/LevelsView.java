package ch.talionis.rbx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static ch.talionis.rbx.android.AndroidUtils.dpToPx;

public class LevelsView extends FrameLayout {
    private List<LevelView> levelViews = new ArrayList<>();
    private float levelViewSize;
    private float verticalSeparator;

    public LevelsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        levelViewSize = dpToPx(getContext(), 64);
        verticalSeparator = dpToPx(getContext(), 8);

        levelViews.add(new LevelView(getContext(), null));
        levelViews.add(new LevelView(getContext(), null));
        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));
//        levelViews.add(new LevelView(getContext(), null));

        for (LevelView levelView : levelViews) {
            addView(levelView);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < levelViews.size(); i++) {
            int row = i <5 ? 0 : 1;
            int column = i - row * 5;

            LevelView currentView = levelViews.get(i);

            currentView.layout((int) (column * levelViewSize), (int) (row * (levelViewSize + verticalSeparator)), (int) (column * levelViewSize + levelViewSize), (int) ((row * (levelViewSize + verticalSeparator)) + levelViewSize));
        }
    }
}
