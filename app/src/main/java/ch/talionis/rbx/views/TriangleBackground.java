package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

import ch.talionis.rbx.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class TriangleBackground extends View {
    private final float triangleWidth = 80;
    private final float triangleHeight = (float) Math.sqrt(Math.pow(triangleWidth, 2) - Math.pow(0.5f * triangleWidth, 2));
    private Paint paint;
    private Random random;
    private int[][] upperTriangleAlphas;
    private int[][] lowerTriangleAlphas;
    Path upperTrianglePathSource = new Path();
    Path lowerTrianglePathSource = new Path();
    Path upperTrianglePath = new Path();
    Path lowerTrianglePath = new Path();

    public TriangleBackground(Context context) {
        super(context);
        init();
    }

    public TriangleBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setFlags(ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.triangle_base_color));

        random = new Random();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawTriangle(canvas);
    }

    private void drawTriangle(Canvas canvas) {
        calculateUpperTrianglePathSource(canvas);
        calculateLowerTrianglePathSource(canvas);

        int horizontalTriangleCount = (int) (canvas.getWidth() / triangleWidth) + 1;
        int verticalTriangleCount = (int) (canvas.getHeight() / triangleHeight / 2);

        if (upperTriangleAlphas == null || lowerTriangleAlphas == null) {
            upperTriangleAlphas = new int[horizontalTriangleCount][verticalTriangleCount];
            lowerTriangleAlphas = new int[horizontalTriangleCount][verticalTriangleCount];

            for (int x = 0; x < horizontalTriangleCount; x++) {
                for (int y = 0; y < verticalTriangleCount; y++) {
                    upperTriangleAlphas[x][y] = getAlphaForIndex(x, y, horizontalTriangleCount, verticalTriangleCount);
                    lowerTriangleAlphas[x][y] = getAlphaForIndex(x, y, horizontalTriangleCount, verticalTriangleCount);
                }
            }
        }

        for (int x = 0; x < horizontalTriangleCount; x++) {
            for (int y = 0; y < verticalTriangleCount; y++) {
                float horizontalPosition = x * triangleWidth + y % 2 * 0.5f * triangleWidth;
                float verticalPosition = -y * triangleHeight;

                upperTrianglePath.reset();
                upperTrianglePathSource.offset(horizontalPosition, verticalPosition, upperTrianglePath);
                paint.setAlpha(upperTriangleAlphas[x][y]);
                canvas.drawPath(upperTrianglePath, paint);

                lowerTrianglePath.reset();
                lowerTrianglePathSource.offset(horizontalPosition, verticalPosition, lowerTrianglePath);
                paint.setAlpha(lowerTriangleAlphas[x][y]);
                canvas.drawPath(lowerTrianglePath, paint);
            }
        }
    }

    private int getAlphaForIndex(int x, int y, int maxX, int maxY) {
        int minMax = Math.min(maxX, maxY);
        float dist = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        if (dist >= minMax) {
            return 0;
        }

        if (dist - random.nextInt(minMax) > minMax * 0.3f) {
            return 0;
        }

        float brightnessByDistance = (200 - (255 * dist / minMax));
        brightnessByDistance = brightnessByDistance + random.nextInt(50) * (random.nextBoolean() ? 1 : -1);

        return (int) Math.min(255, Math.max(0, brightnessByDistance));
    }

    private void calculateUpperTrianglePathSource(Canvas canvas) {
        int height = canvas.getHeight();

        upperTrianglePathSource.reset();
        upperTrianglePathSource.setLastPoint(0, height);
        upperTrianglePathSource.lineTo(triangleWidth, height);
        upperTrianglePathSource.lineTo(0.5f * triangleWidth, height - triangleHeight);
        upperTrianglePathSource.lineTo(0, height);
    }

    private void calculateLowerTrianglePathSource(Canvas canvas) {
        int height = canvas.getHeight();

        lowerTrianglePathSource.reset();
        lowerTrianglePathSource.setLastPoint(-0.5f * triangleWidth, height - triangleHeight);
        lowerTrianglePathSource.lineTo(0.5f * triangleWidth, height - triangleHeight);
        lowerTrianglePathSource.lineTo(0, height);
        lowerTrianglePathSource.lineTo(-0.5f * triangleWidth, height - triangleHeight);
    }
}
