package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ch.talionis.rbx.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Cap.ROUND;
import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static ch.talionis.rbx.android.AndroidUtils.dpToPx;

public class LevelView extends FrameLayout {
    private Path path;
    private Paint strokePaint;
    private float roundRectRadius;
    private int strokeWidth;
    private int starRowHorizontalPadding;
    private StarRow starRowView;
    private Paint shaderPaint;
    private Paint textPaint;
    private String text = "1";

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

        shaderPaint = new Paint();
        shaderPaint.setFlags(ANTI_ALIAS_FLAG);
        shaderPaint.setStyle(FILL);

        textPaint = new Paint();
        textPaint.setFlags(ANTI_ALIAS_FLAG);
        textPaint.setStrokeWidth(getResources().getDimension(R.dimen.view_pentagon_stroke_width));
        textPaint.setColor(getResources().getColor(R.color.view_level_text_paint));
        textPaint.setStyle(FILL);

        path = new Path();

        roundRectRadius = dpToPx(getContext(), 8);

        starRowHorizontalPadding = (int) dpToPx(getContext(), 4);
        starRowView = new StarRow(getContext(), null);
        addView(starRowView);

        //setElevation(dpToPx(getContext(), 4));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path = calculatePath(w, h);
        shaderPaint.setShader(new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(), getResources().getColor(R.color.view_level_gradient_top), getResources().getColor(R.color.view_level_gradient_bottom), Shader.TileMode.MIRROR));
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(path);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;

        int starRowHeight = height / 3;

        starRowView.layout(starRowHorizontalPadding, 2 * starRowHeight, width - starRowHorizontalPadding, 3 * starRowHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (path == null) {
            calculatePath(getWidth(), getHeight());
        }

        canvas.drawPath(path, shaderPaint);
        canvas.drawPath(path, strokePaint);

        if (text != null) {
            textPaint.setTextSize(getHeight() / 2);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, getWidth() / 2, (int) ((getHeight() / 3) - ((textPaint.descent() + textPaint.ascent()) / 2)), textPaint);
        }

        super.onDraw(canvas);
    }

    private Path calculatePath(int width, int height) {

        Path path = new Path();
        path.addRoundRect(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth, roundRectRadius, roundRectRadius, Path.Direction.CW);
        return path;
    }
}
