package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import ch.talionis.rbx.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;

public class CircleAnimalView extends FrameLayout {
    private ImageView imageView;
    private Paint pentagonBackgroundPaint;
    private Paint pentagonStrokePaint;
    private Path path;
    private Paint pentagonShaderPaint;

    public CircleAnimalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        imageView = new ImageView(this.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setAlpha(0.6f);
        addView(imageView);

        init();
    }

    private void init() {
        pentagonBackgroundPaint = new Paint();
        pentagonBackgroundPaint.setFlags(ANTI_ALIAS_FLAG);
        pentagonBackgroundPaint.setStyle(FILL);
        pentagonBackgroundPaint.setColor(getResources().getColor(R.color.white));

        pentagonShaderPaint = new Paint();
        pentagonShaderPaint.setFlags(ANTI_ALIAS_FLAG);
        pentagonShaderPaint.setStyle(FILL);
        pentagonShaderPaint.setShader(new LinearGradient(getWidth() / 2, 0, getWidth() / 2, getHeight(), getResources().getColor(R.color.bg_gradient_top), getResources().getColor(R.color.bg_gradient_bottom), Shader.TileMode.MIRROR));

        pentagonStrokePaint = new Paint();
        pentagonStrokePaint.setFlags(ANTI_ALIAS_FLAG);
        pentagonStrokePaint.setColor(getResources().getColor(R.color.view_levels_card_background));
        pentagonStrokePaint.setStyle(STROKE);
        pentagonStrokePaint.setStrokeWidth(getResources().getDimension(R.dimen.view_pentagon_stroke_width));

        path = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        int padding = Math.min(width / 4, height / 4);
        imageView.layout(padding, padding, width - padding, height - padding);

        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(calculatePath(w, h));
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path = calculatePath(getWidth(), getHeight());
        canvas.drawPath(path, pentagonBackgroundPaint);
        canvas.drawPath(path, pentagonShaderPaint);
        canvas.drawPath(path, pentagonStrokePaint);

        super.onDraw(canvas);
    }

    private Path calculatePath(int width, int height) {
        int midX = width / 2;
        int midY = height / 2;

        float radius = Math.min(midX, midY) - pentagonStrokePaint.getStrokeWidth();
        path.reset();
        path.addCircle(midX, midY, radius, Path.Direction.CW);
        return path;
    }

    public void setImageResId(int imageResid) {
        imageView.setImageResource(imageResid);
    }
}
