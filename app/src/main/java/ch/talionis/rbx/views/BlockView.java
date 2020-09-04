package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import ch.talionis.rbx.engine.model.Block;

public class BlockView extends View {
    private final Paint paint = new Paint();
    private Block block;

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.MAGENTA);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setBlock(Block block) {
        this.block = block;

        switch (block.getType()) {
            case SOLID:
                paint.setColor(Color.DKGRAY);
                break;
            case EMPTY:
                paint.setColor(Color.LTGRAY);
                break;
            case MOVABLE:
                paint.setColor(Color.RED);
                break;
        }
    }

    public Block getBlock() {
        return block;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
