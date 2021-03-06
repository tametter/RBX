package ch.talionis.rbx.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Block.BlockType;
import ch.talionis.rbx.engine.model.Coordinate;
import ch.talionis.rbx.engine.model.Direction;
import ch.talionis.rbx.functional.PathSupplier;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Style.FILL;
import static ch.talionis.rbx.engine.model.Block.BlockType.ABSENT;
import static ch.talionis.rbx.engine.model.Block.BlockType.EMPTY;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;
import static ch.talionis.rbx.engine.model.Direction.UP;
import static ch.talionis.rbx.logging.Logger.logV;

public class BlockView extends FrameLayout implements ViewWithCoordinate {
    private final Paint poweredPaint = new Paint(ANTI_ALIAS_FLAG);
    private Block block;
    private Engine engine;

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayerType(LAYER_TYPE_HARDWARE, null);
        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);

        poweredPaint.setStyle(FILL);
        poweredPaint.setColor(Color.GREEN);
        setElevation(getResources().getDimension(R.dimen.background_elevation));
    }

    public void setBlock(Block block) {
        if (block.getType() == ABSENT || block.getType() == EMPTY) {
            throw new IllegalArgumentException("Views for absent or empty blocks make no sense.");
        }

        this.block = block;

        removeAllViews();

        if (block.getType() == EMPTY) {
            // Filled in by the layout
            return;
        }

        switch (block.getConnectionType()) {
            case NONE: {
                PartView partView = new PartView(getContext(), null);
                partView.setPathSupplier(BlockViewPathGenerator::none);
                addView(partView);
                break;
            }
            case START: {
                PartView partView = new PartView(getContext(), null);
                partView.setPathSupplier(BlockViewPathGenerator::end);
                partView.setRotation(getRotationForDirection(block.to()));
                addView(partView);
                break;
            }
            case END: {
                PartView partView = new PartView(getContext(), null);
                partView.setPathSupplier(BlockViewPathGenerator::end);
                partView.setRotation(getRotationForDirection(block.from()));
                addView(partView);
                break;
            }
            case NORMAL: {
                if (block.from().isOpposite(block.to())) {
                    // A line
                    PartView partView = new PartView(getContext(), null);
                    partView.setPathSupplier(BlockViewPathGenerator::lineTop);
                    partView.setRotation(getRotationForDirection(block.to()));
                    addView(partView);

                    partView = new PartView(getContext(), null);
                    partView.setPathSupplier(BlockViewPathGenerator::lineBottom);
                    partView.setRotation(getRotationForDirection(block.to()));
                    addView(partView);
                } else {
                    // A corner
                    PartView partView = new PartView(getContext(), null);
                    partView.setPathSupplier(BlockViewPathGenerator::cornerLarge);
                    partView.setRotation(getRotationForCorner(block.from(), block.to()));
                    addView(partView);

                    partView = new PartView(getContext(), null);
                    partView.setPathSupplier(BlockViewPathGenerator::cornerSmall);
                    partView.setRotation(getRotationForCorner(block.from(), block.to()));
                    addView(partView);
                }
                break;
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            PartView child = (PartView) getChildAt(i);
            child.setElevation(getResources().getDimension(R.dimen.background_elevation));
            child.setPaintColor(getColorForBlockType(block.getType()));
        }
    }

    private int getColorForBlockType(BlockType blockType) {
        logV(this, "BlockType " + blockType);
        switch (blockType) {
            case SOLID:
                return getResources().getColor(R.color.block_solid);
            case EMPTY:
                return getResources().getColor(R.color.block_empty);
            default:
                return getResources().getColor(R.color.block_movable);
        }
    }

    private float getRotationForDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                return 180;
            case RIGHT:
                return 0;
            case UP:
                return 270;
            default:
                return 90;
        }
    }

    private float getRotationForCorner(Direction from, Direction to) {
        switch (from) {
            case LEFT:
                return to == Direction.DOWN ? 0 : 90;
            case RIGHT:
                return to == UP ? 180 : 270;
            case UP:
                return to == LEFT ? 90 : 180;
            default:
                return to == RIGHT ? 270 : 0;
        }
    }

    public Block getBlock() {
        return block;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            PartView child = (PartView) getChildAt(i);
            child.layout(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (block == null) {
            return;
        }

        if (block.isPowered()) {
            canvas.drawCircle(0.5f * getWidth(), 0.5f * getHeight(), 10, poweredPaint);
        }
    }

    @Override
    public Coordinate getCoordinate() {
        return engine.getState().getPosition(block);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    private static class PartView extends View {
        protected final Paint paint = new Paint(ANTI_ALIAS_FLAG);
        private PathSupplier pathSupplier;

        public PartView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            paint.setColor(Color.MAGENTA);
            paint.setStyle(FILL);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {

                    outline.setConvexPath(pathSupplier.getPath(getWidth(), getHeight()));
                }
            });
        }

        public void setPaintColor(int color) {
            paint.setColor(color);
        }

        public void setPathSupplier(PathSupplier pathSupplier) {
            this.pathSupplier = pathSupplier;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (pathSupplier != null) {
                // Memoize the path
                canvas.drawPath(pathSupplier.getPath(getWidth(), getHeight()), paint);
            }
        }
    }
}
