package ch.talionis.rbx.generator;

import ch.talionis.rbx.engine.model.Block;
import ch.talionis.rbx.engine.model.Level;

public class CreateLevel extends Step<Block[][], Level> {
    @Override
    public Level apply(Block[][] input) {
        return new Level(input);
    }

    private CreateLevel() {}

    public static CreateLevel createLevel() {
        return new CreateLevel();
    }
}
