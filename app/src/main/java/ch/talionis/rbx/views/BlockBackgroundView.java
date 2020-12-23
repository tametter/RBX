package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.model.Coordinate;

import static ch.talionis.rbx.engine.model.Coordinate.coordinate;

public class BlockBackgroundView extends View {
    Paint backgroundPaint;
    private int x;
    private int y;

    public BlockBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.block_empty));
        setElevation(getResources().getDimension(R.dimen.background_elevation));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, getWidth(), getHeight());
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate getCoordinate() {
        return coordinate(x, y);
    }
}
