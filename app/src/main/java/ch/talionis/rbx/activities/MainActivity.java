package ch.talionis.rbx.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.Engine;
import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.views.BlockLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Engine engine = new Engine();
        Level sampleLevel = new Level(new Block[][]{
                {new Block(Block.BlockType.MOVABLE), new Block(Block.BlockType.SOLID), new Block(Block.BlockType.EMPTY)},
                {new Block(Block.BlockType.MOVABLE), new Block(Block.BlockType.EMPTY), new Block(Block.BlockType.MOVABLE)},
                {new Block(Block.BlockType.EMPTY), new Block(Block.BlockType.SOLID), new Block(Block.BlockType.SOLID)},
                {new Block(Block.BlockType.SOLID), new Block(Block.BlockType.EMPTY), new Block(Block.BlockType.MOVABLE)}
        });

        BlockLayout blockLayout = findViewById(R.id.block_layout);
        blockLayout.setEngine(engine);

        engine.load(sampleLevel);
    }
}
