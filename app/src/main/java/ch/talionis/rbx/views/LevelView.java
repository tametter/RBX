package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ch.talionis.rbx.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Cap.ROUND;
import static android.graphics.Paint.Style.STROKE;
import static ch.talionis.rbx.android.AndroidUtils.dpToPx;

public class LevelView extends FrameLayout {
    private Path path;
    private Paint strokePaint;
    private float roundRectRadius;
    private int strokeWidth;

    public LevelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);

        strokeWidth = (int) getResources().getDimension(R.dimen.view_level_stroke_width);

        strokePaint = new Paint();
        strokePaint.setFlags(ANTI_ALIAS_FLAG);
        strokePaint.setStrokeCap(ROUND);
        strokePaint.setStyle(STROKE);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setColor(getResources().getColor(R.color.view_level_stroke));

        path = new Path();

        roundRectRadius = dpToPx(getContext(), 8);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path = calculatePath(w, h);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(path);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (path == null) {
            calculatePath(getWidth(), getHeight());
        }

        canvas.drawPath(path, strokePaint);
    }

    private Path calculatePath(int width, int height) {

        Path path = new Path();
        path.moveTo(strokeWidth, strokeWidth);
        path.addRoundRect(strokeWidth, strokeWidth, width - strokeWidth - strokeWidth, height - strokeWidth - strokeWidth, roundRectRadius, roundRectRadius, Path.Direction.CW);
        return path;
    }
}
