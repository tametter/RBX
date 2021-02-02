package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
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

public class PentagonNumberView extends View {
    private Paint pentagonBackgroundPaint;
    private Paint pentagonStrokePaint;
    private Paint textPaint;
    private Path path;
    private String text;
    private Paint pentagonShaderPaint;

    public PentagonNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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


        textPaint = new Paint();
        textPaint.setFlags(ANTI_ALIAS_FLAG);
        textPaint.setStrokeWidth(getResources().getDimension(R.dimen.view_pentagon_stroke_width));
        textPaint.setColor(getResources().getColor(R.color.view_levels_card_background));
        textPaint.setStyle(FILL);

        path = new Path();
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

                outline.setConvexPath(calculatePentagonPath(w, h));
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path = calculatePentagonPath(getWidth(), getHeight());
        canvas.drawPath(path, pentagonBackgroundPaint);
        canvas.drawPath(path, pentagonShaderPaint);
        canvas.drawPath(path, pentagonStrokePaint);

        if (text != null) {
            textPaint.setTextSize(getHeight() / 2);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(text, getWidth() / 2, (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)), textPaint);
        }
    }

    private Path calculatePentagonPath(int width, int height) {
        int corners = 5;
        double startAngle = -Math.PI / 2;
        int midX = width / 2;
        int midY = height / 2;

        float radius = Math.min(midX, midY) - pentagonStrokePaint.getStrokeWidth();

        double anglePerCorner = Math.PI * 2 / corners;

        PointF[] controlPoints = new PointF[corners];

        for (int i=0; i<corners; i++) {
            double angle = (startAngle + i * anglePerCorner);
            controlPoints[i] = new PointF(
                    (float) (midX + cos(angle) * radius),
                    (float) (midY + sin(angle) * radius)
            );
        }

        float roundingFactor = 0.2f;

        for (int i=0; i<corners; i++) {
            PointF lastControlPoint = i == 0 ? controlPoints[controlPoints.length - 1] : controlPoints[i-1];
            PointF controlPoint = controlPoints[i];
            PointF nextControlPoint = i == controlPoints.length - 1 ? controlPoints[0] : controlPoints[i+1];

            float frontX = controlPoint.x + (lastControlPoint.x - controlPoint.x) * roundingFactor;
            float frontY = controlPoint.y + (lastControlPoint.y - controlPoint.y) * roundingFactor;

            float backX = controlPoint.x + (nextControlPoint.x - controlPoint.x) * roundingFactor;
            float backY = controlPoint.y + (nextControlPoint.y - controlPoint.y) * roundingFactor;

            if (i == 0) {
                path.moveTo(frontX, frontY);
            } else {
                path.lineTo(frontX, frontY);
            }

            path.quadTo(controlPoint.x, controlPoint.y, backX, backY);
        }

        path.close();
        return path;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }
}
