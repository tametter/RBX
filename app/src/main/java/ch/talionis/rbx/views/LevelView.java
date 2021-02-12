package ch.talionis.rbx.views;

import android.content.Context;
import android.content.res.ColorStateList;
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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import ch.talionis.rbx.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Cap.ROUND;
import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static ch.talionis.rbx.android.AndroidUtils.dpToPx;

public class LevelView extends FrameLayout {
    private Path path;
    private float roundRectRadius;
    private int strokeWidth;
    private Paint shaderPaint;
    private Paint textPaint;
    private int levelNumber;
    private ImageView firstStar;
    private ImageView secondStar;
    private ImageView thirdStar;
    private LinearGradient shaderGradient;

    public LevelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        shaderPaint = new Paint();
        shaderPaint.setFlags(ANTI_ALIAS_FLAG);
        shaderPaint.setStyle(FILL);
        shaderPaint.setColor(getResources().getColor(R.color.white));

        textPaint = new Paint();
        textPaint.setFlags(ANTI_ALIAS_FLAG);
        textPaint.setStrokeWidth(getResources().getDimension(R.dimen.view_pentagon_stroke_width));
        textPaint.setColor(getResources().getColor(R.color.text_dark));
        textPaint.setStyle(FILL);

        path = new Path();

        roundRectRadius = dpToPx(getContext(), 0);

        firstStar = new ImageView(getContext(), null);
        firstStar.setImageResource(R.drawable.ic_star_24);
        addView(firstStar);
        secondStar = new ImageView(getContext(), null);
        secondStar.setImageResource(R.drawable.ic_star_24);
        addView(secondStar);
        thirdStar = new ImageView(getContext(), null);
        thirdStar.setImageResource(R.drawable.ic_star_24);
        addView(thirdStar);

        setElevation(dpToPx(getContext(), 4));
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        int starSize = (int) (0.3 * width);
        int inset = (int) (0.5f * (width - 3 * starSize));

//        if (shaderGradient == null || changed) {
//            View parent = (View) getParent();
//            shaderGradient = new LinearGradient(-getLeft(), -getTop(), parent.getRight(),parent.getBottom(), getResources().getColor(R.color.view_level_gradient_top), getResources().getColor(R.color.view_level_gradient_bottom), Shader.TileMode.MIRROR);
//            shaderPaint.setShader(shaderGradient);
//        }

        firstStar.layout(inset, height - inset - starSize, inset + starSize, height - inset);
        secondStar.layout(inset + starSize, height - inset - starSize, inset + 2 * starSize, height - inset);
        thirdStar.layout(inset + 2 * starSize, height - inset - starSize, inset + 3 * starSize, height - inset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, textPaint);
        canvas.drawPath(path, shaderPaint);

        textPaint.setTextSize(getHeight() / 2);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(levelNumber), getWidth() / 2, (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)), textPaint);


        super.onDraw(canvas);
    }

    private Path calculatePath(int width, int height) {

        Path path = new Path();
        path.addRoundRect(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth, roundRectRadius, roundRectRadius, Path.Direction.CW);
        return path;
    }

    public void setLevelNumber(int number) {
        this.levelNumber = number;
    }

    public void setStars(int rating) {
        int activeColor = getResources().getColor(R.color.bg_gradient_bottom);
        int inactiveColor = getResources().getColor(R.color.light_grey);

        firstStar.setImageTintList(ColorStateList.valueOf(rating > 0 ? activeColor : inactiveColor));
        secondStar.setImageTintList(ColorStateList.valueOf(rating > 1 ? activeColor : inactiveColor));
        thirdStar.setImageTintList(ColorStateList.valueOf(rating > 2 ? activeColor : inactiveColor));

    }
}
