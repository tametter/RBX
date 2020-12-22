package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.EngineObserver;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;
import ch.talionis.rbx.engine.model.Move;
import ch.talionis.rbx.engine.model.State;

import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;
import static ch.talionis.rbx.engine.model.Direction.DOWN;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;
import static ch.talionis.rbx.logging.Logger.logV;

public class BlockLayout extends ViewGroup implements EngineObserver {
    private Engine engine;
    private int numberOfVerticalBlocks;
    private int numberOfHorizontalBlocks;
    private BlockView touchBlockView;
    private Coordinate touchBlockCoords;
    private int touchX;
    private int touchY;

    public BlockLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClipChildren(false);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        this.engine.addObserver(this);
    }

    private void addViewsForState(State state) {
        if (engine.isComplete()) {
            this.setBackgroundColor(Color.GREEN);
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
        }

        removeAllViews();

        for (int x = 0; x < numberOfHorizontalBlocks; x++) {
            for (int y = 0; y < numberOfVerticalBlocks; y++) {
                Block block = state.get(x, y);

                if (block.getType() == ABSENT) {
                    continue;
                }

                BlockView blockView = new BlockView(getContext(), null);
                blockView.setBlock(block);
                addView(blockView);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0) {
            return;
        }

        // Get layout size
        int width = r - l;
        int height = b - t;

        // Calculate the block size
        int maxHorizontalBlockSize = width / numberOfHorizontalBlocks;
        int maxVerticalBlockSize = height / numberOfVerticalBlocks;
        int blockSize = Math.min(maxHorizontalBlockSize, maxVerticalBlockSize);

        // Check if we can use the desired block size.
        int desiredBlockSize = getContext().getResources().getDimensionPixelSize(R.dimen.block_size);
        blockSize = Math.min(desiredBlockSize, blockSize);

        // Find the starting points for the rows and columns.
        int totalWidth = blockSize * numberOfHorizontalBlocks;
        int totalHeight = blockSize * numberOfVerticalBlocks;
        float blockPaddingLeft = (width - totalWidth) * 0.5f;
        float blockPaddingTop = (height - totalHeight) * 0.5f;

        // Layout the blocks
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (!(child instanceof BlockView)) {
                continue;
            }

            BlockView blockView = (BlockView) child;
            Block block = blockView.getBlock();

            Coordinate coordinate = engine.getState().getPosition(block);
            int left = (int) (blockPaddingLeft + coordinate.getX() * blockSize);
            int top = (int) (blockPaddingTop + coordinate.getY() * blockSize);
            int right = left + blockSize;
            int bottom = top + blockSize;

            logV(this, "Laying out block %d at (%d, %d - %d, %d)", i, left, top, right, bottom);
            blockView.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                Rect hitRect = new Rect();
                if (child instanceof BlockView) {
                    child.getHitRect(hitRect);
                    if (hitRect.contains((int) event.getX(), (int) event.getY())) {
                        touchBlockView = (BlockView) child;
                        touchBlockCoords = engine.getState().getPosition(touchBlockView.getBlock());
                        touchX = (int) event.getX();
                        touchY = (int) event.getY();
                    }
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int deltaX = (int) (event.getX() - touchX);
            int deltaY = (int) (event.getY() - touchY);

            if (touchBlockCoords == null) {
                return true;
            }
            if (deltaX > 100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), RIGHT))) {
                engine.apply(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), RIGHT));
            } else if (deltaX < -100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), LEFT))) {
                engine.apply(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), LEFT));
            } else if (deltaY > 100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), DOWN))) {
                engine.apply(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), DOWN));
            } else if (deltaY < -100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), UP))) {
                engine.apply(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), UP));
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            touchBlockView = null;
            touchX = 0;
            touchY = 0;
        }

        return true;
    }

    @Override
    public void onLevelLoaded() {
        State state = engine.getState();

        numberOfVerticalBlocks = state.getLevel().getHeight();
        numberOfHorizontalBlocks = state.getLevel().getWidth();

        addViewsForState(state);
    }

    @Override
    public void onStateUpdated(State state) {
        addViewsForState(state);
    }
}
