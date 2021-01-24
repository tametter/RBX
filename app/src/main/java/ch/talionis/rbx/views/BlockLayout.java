package ch.talionis.rbx.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.EngineObserver;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Coordinate;
import ch.talionis.rbx.engine.model.Move;
import ch.talionis.rbx.engine.model.State;
import ch.talionis.rbx.functional.AnimationListener;

import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;
import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Direction.DOWN;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;
import static ch.talionis.rbx.logging.Logger.logV;

public class BlockLayout extends ViewGroup implements EngineObserver {
    private Engine engine;
    private int numberOfVerticalBlocks;
    private int numberOfHorizontalBlocks;
    private Coordinate touchBlockCoords;
    private int touchX;
    private int touchY;
    private int blockSize;

    public BlockLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        this.engine.addObserver(this);
    }

    private void addViewsForState(State state) {
        if (engine.isComplete()) {
            this.setBackgroundColor(Color.GREEN);
        } else {
//            this.setBackgroundColor(Color.TRANSPARENT);
        }

        removeAllViews();

        // Add background views
        for (int x = 0; x < numberOfHorizontalBlocks; x++) {
            for (int y = 0; y < numberOfVerticalBlocks; y++) {
                Block block = state.get(x, y);

                if (block.getType() == ABSENT) {
                    continue;
                }

                BlockBackgroundView blockBackgroundView = new BlockBackgroundView(getContext(), null);
                blockBackgroundView.setCoordinate(x, y);
                addView(blockBackgroundView);
            }
        }

        // Add blocks
        for (int x = 0; x < numberOfHorizontalBlocks; x++) {
            for (int y = 0; y < numberOfVerticalBlocks; y++) {
                Block block = state.get(x, y);

                if (block.getType() == ABSENT) {
                    continue;
                }

                if (block.getType() != EMPTY) {
                    BlockView blockView = new BlockView(getContext(), null);
                    blockView.setBlock(block);
                    blockView.setPadding(20, 20, 20, 20);
                    addView(blockView);
                }
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
        blockSize = Math.min(maxHorizontalBlockSize, maxVerticalBlockSize);

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
            if (child instanceof BlockBackgroundView) {
                BlockBackgroundView blockBackgroundView = (BlockBackgroundView) child;
                blockBackgroundView.setPadding(0, 0, 0, 0);
                layoutChildForGridPosition(child, blockBackgroundView.getCoordinate().getX(), blockBackgroundView.getCoordinate().getY(), blockPaddingLeft, blockPaddingTop, blockSize);
            } else if (child instanceof BlockView) {
                BlockView blockView = (BlockView) child;
                Block block = blockView.getBlock();

                Coordinate coordinate = engine.getState().getPosition(block);
                layoutChildForGridPosition(blockView, coordinate.getX(), coordinate.getY(), blockPaddingLeft, blockPaddingTop, blockSize);
            }
        }
    }

    private void layoutChildForGridPosition(View child, int x, int y, float blockPaddingLeft, float blockPaddingTop, int blockSize) {
        int left = (int) (blockPaddingLeft + x * blockSize);
        int top = (int) (blockPaddingTop + y * blockSize);
        int right = left + blockSize;
        int bottom = top + blockSize;

        // Enlarge to account for padding (for elevation shadows)
        left = left - child.getPaddingLeft();
        top = top - child.getPaddingTop();
        right = right + child.getPaddingRight();
        bottom = bottom + child.getPaddingBottom();

        logV(this, "Laying out block %s at (%d, %d - %d, %d)", child, left, top, right, bottom);
        child.layout(left, top, right, bottom);
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
                        BlockView touchBlockView = (BlockView) child;
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
                animate(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), RIGHT));
            } else if (deltaX < -100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), LEFT))) {
                animate(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), LEFT));
            } else if (deltaY > 100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), DOWN))) {
                animate(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), DOWN));
            } else if (deltaY < -100 && engine.isValid(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), UP))) {
                animate(new Move(touchBlockCoords.getX(), touchBlockCoords.getY(), UP));
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            touchX = 0;
            touchY = 0;
        }

        return true;
    }

    private void animate(Move move) {
        touchBlockCoords = null;
        String propertyName = move.getDirection().isHorizontal() ? "translationX" : "translationY";
        float moveMultiplier = move.getDirection() == LEFT || move.getDirection() == UP ? -1 : 1;

        Map<Coordinate, Block> blocksThatWillMove = engine.getBlocksThatWillMove(move);
        List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (child instanceof BlockView && blocksThatWillMove.containsValue(((BlockView) child).getBlock())) {
                animators.add(ObjectAnimator.ofFloat(child, propertyName, 0, moveMultiplier * blockSize));
            }
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(70);
        animatorSet.playTogether(animators);

        AnimationListener.onAnimationEnd(animatorSet, () -> engine.apply(move));

        animatorSet.start();
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
