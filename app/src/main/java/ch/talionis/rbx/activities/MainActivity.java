package ch.talionis.rbx.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.views.BlockLayout;

import static ch.talionis.rbx.engine.model.Block.absentBlock;
import static ch.talionis.rbx.engine.model.Block.emptyBlock;
import static ch.talionis.rbx.engine.model.Block.endBlock;
import static ch.talionis.rbx.engine.model.Block.normalConnector;
import static ch.talionis.rbx.engine.model.Block.solidBlock;
import static ch.talionis.rbx.engine.model.Block.startBlock;
import static ch.talionis.rbx.engine.model.Direction.LEFT;
import static ch.talionis.rbx.engine.model.Direction.RIGHT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Engine engine = new Engine();
        Level sampleLevel = new Level(new Block[][]{
                {absentBlock(), startBlock(RIGHT), solidBlock(), absentBlock()},
                {absentBlock(), normalConnector(LEFT, RIGHT), emptyBlock(), endBlock(LEFT)},
                {emptyBlock(), emptyBlock(), solidBlock(), absentBlock()},
        });


//        Level sampleLevel = new Level(new Block[][]{
//                {startBlock(LEFT), startBlock(UP), startBlock(RIGHT), startBlock(DOWN)},
//                {endBlock(LEFT), endBlock(UP), endBlock(RIGHT), endBlock(DOWN)},
//                {normalConnector(LEFT, RIGHT), normalConnector(DOWN, UP), normalConnector(RIGHT, LEFT), normalConnector(UP, DOWN)},
//                {normalConnector(LEFT, DOWN), normalConnector(DOWN, RIGHT), normalConnector(RIGHT, UP), normalConnector(UP, LEFT)},
//                {absentBlock(), emptyBlock(), absentBlock(), absentBlock()},
//        });

        BlockLayout blockLayout = findViewById(R.id.block_layout);
        blockLayout.setEngine(engine);

        engine.load(sampleLevel);

//        BlockView blockView = findViewById(R.id.block_view);
//        blockView.setBlock(Block.endBlock(RIGHT));
    }
}
