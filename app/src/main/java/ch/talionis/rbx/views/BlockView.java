package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Direction;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;

public class BlockView extends View {
    private final Paint paint = new Paint(ANTI_ALIAS_FLAG);
    private final Paint lightPaint = new Paint(ANTI_ALIAS_FLAG);
    private final Paint poweredPaint = new Paint(ANTI_ALIAS_FLAG);
    private Block block;

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayerType(LAYER_TYPE_HARDWARE, null);

        paint.setColor(Color.MAGENTA);
        paint.setStyle(FILL);

        lightPaint.setStyle(STROKE);
        lightPaint.setStrokeWidth(20);
        lightPaint.setColor(Color.parseColor("#99FFFFFF"));

        poweredPaint.setStyle(FILL);
        poweredPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    public void setBlock(Block block) {
        if (block.getType() == ABSENT) {
            throw new IllegalArgumentException("Views for absent blocks make no sense.");
        }

        this.block = block;

        switch (block.getType()) {
            case SOLID:
                paint.setColor(getResources().getColor(R.color.block_solid));
                setElevation(5);
                break;
            case EMPTY:
                paint.setColor(getResources().getColor(R.color.block_empty));
                setElevation(5);
                break;
            case MOVABLE:
                paint.setColor(getResources().getColor(R.color.block_movable));
                setElevation(30);
                break;
        }
    }

    public Block getBlock() {
        return block;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        if (block.isPowered()) {
            canvas.drawCircle(20, 20, 10, poweredPaint);
        }

        switch (block.getConnectionType()) {
            case NONE:
                break;
            case START:
                drawStartCircle(canvas);
                drawLineSegment(canvas, block.to());
                break;
            case END:
                drawEndSquare(canvas);
                drawLineSegment(canvas, block.from());
                break;
            case NORMAL:
                drawLineSegment(canvas, block.from());
                drawLineSegment(canvas, block.to());
                break;
        }
    }

    private void drawStartCircle(Canvas canvas) {
        canvas.drawCircle(0.5f * getWidth(), 0.5f * getHeight(), 0.1f * getWidth(), lightPaint);
    }

    private void drawEndSquare(Canvas canvas) {
        float halfWidth = (float) (getWidth() * 0.1);
        canvas.drawRect(0.5f * getWidth() - halfWidth, 0.5f * getHeight() - halfWidth, 0.5f * getWidth() + halfWidth, 0.5f * getHeight() + halfWidth, lightPaint);
    }

    private void drawLineSegment(Canvas canvas, Direction direction) {
        float endX = 0.5f * getWidth();
        float endY = 0.5f * getHeight();

        switch (direction) {
            case LEFT:
                endX = 0;
                break;
            case RIGHT:
                endX = getWidth();
                break;
            case UP:
                endY = 0;
                break;
            case DOWN:
                endY = getHeight();
                break;
        }

        canvas.drawLine(0.5f * getWidth(), 0.5f * getHeight(), endX, endY, lightPaint);
    }
}
