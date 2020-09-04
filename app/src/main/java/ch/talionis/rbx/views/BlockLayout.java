package ch.talionis.rbx.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.State;

public class BlockLayout extends ViewGroup {
    private Engine engine;
    private int numberOfVerticalBlocks;
    private int numberOfHorizontalBlocks;

    public BlockLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;

        State state = engine.getState();
        numberOfVerticalBlocks = state.getLevel().getHeight();
        numberOfHorizontalBlocks = state.getLevel().getWidth();

        for (int x = 0; x<numberOfHorizontalBlocks; x++) {
            for (int y = 0; y<numberOfVerticalBlocks; y++) {
                Block block = state.get(x, y);
                BlockView blockView = new BlockView(getContext(), null);
                blockView.setBlock(block);
                addView(blockView);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
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
        for (int i = 0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            if (!(child instanceof BlockView)) {
                continue;
            }

            BlockView blockView = (BlockView) child;
            Block block = blockView.getBlock();

            Pair<Integer, Integer> position = engine.getState().getPosition(block);
            int x = position.first;
            int y = position.second;
            int left = (int) (blockPaddingLeft + x * blockSize);
            int top = (int) (blockPaddingTop + y * blockSize);

            Log.v("RBX", "Laying out block " + i + " at " + left + ", " + top);
            blockView.layout(left, top, left + blockSize, top + blockSize);
        }
    }
}
