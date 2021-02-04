package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;

import ch.talionis.rbx.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class StarView extends View {
    private Paint starPaint;
    private Path path;
    private boolean isSelected;
    private int strokeColorSelected;
    private int fillColorSelected;
    private int strokeColorUnselected;
    private int fillColorUnselected;

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        starPaint = new Paint();
        starPaint.setFlags(ANTI_ALIAS_FLAG);
        starPaint.setStrokeWidth(getResources().getDimension(R.dimen.view_star_stroke_width));

        path = new Path();

        strokeColorSelected = getResources().getColor(R.color.view_star_selected_stroke);
        fillColorSelected = getResources().getColor(R.color.view_star_selected_fill);
        strokeColorUnselected = getResources().getColor(R.color.view_star_unselected_stroke);
        fillColorUnselected = getResources().getColor(R.color.view_star_unselected_fill);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setConvexPath(calculateStarPath(w, h));
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path = calculateStarPath(getWidth(), getHeight());

        starPaint.setStyle(FILL);
        starPaint.setColor(isSelected ? fillColorSelected : fillColorUnselected);
        canvas.drawPath(path, starPaint);

        starPaint.setStyle(STROKE);
        starPaint.setColor(isSelected ? strokeColorSelected : strokeColorUnselected);
        canvas.drawPath(path, starPaint);
    }

    private Path calculateStarPath(int width, int height) {
        double startAngle = -Math.PI / 2;
        int midX = width / 2;
        int midY = height / 2;

        float radius = Math.min(midX, midY) - starPaint.getStrokeWidth();
        float innerRadius = radius * 0.4f;

        double anglePerCorner = Math.PI * 2 / 5;

        PointF[] outerControlPoints = new PointF[5];
        PointF[] innerControlPoints = new PointF[5];

        for (int i = 0; i < 5; i++) {
            double outerAngle = (startAngle + i * anglePerCorner);
            outerControlPoints[i] = new PointF(
                    (float) (midX + cos(outerAngle) * radius),
                    (float) (midY + sin(outerAngle) * radius)
            );

            double innerAngle = outerAngle + 0.5 * anglePerCorner;
            innerControlPoints[i] = new PointF(
                    (float) (midX + cos(innerAngle) * innerRadius),
                    (float) (midY + sin(innerAngle) * innerRadius)
            );
        }

        for (int i = 0; i < outerControlPoints.length; i++) {
            PointF outerControlPoint = outerControlPoints[i];
            PointF innerControlPoint = innerControlPoints[i];
            if (i == 0) {
                path.moveTo(outerControlPoint.x, outerControlPoint.y);
            } else {
                path.lineTo(outerControlPoint.x, outerControlPoint.y);
            }
            path.lineTo(innerControlPoint.x, innerControlPoint.y);
        }

        path.close();
        return path;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }
}
