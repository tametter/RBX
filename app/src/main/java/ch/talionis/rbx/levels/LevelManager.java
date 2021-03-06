package ch.talionis.rbx.levels;

import java.util.ArrayList;
import java.util.List;

import ch.talionis.rbx.R;
import ch.talionis.rbx.engine.model.Level;
import ch.talionis.rbx.generator.LevelGenerator;

public class LevelManager {
    private List<LevelGroup> groups;
    private LevelGenerator levelGenerator;

    public LevelManager() {
        groups = new ArrayList<>();

        groups.add(new LevelGroup(R.drawable.ic_rat_looking_right, 0, 8));
        groups.add(new LevelGroup(R.drawable.ic_rabbit_facing_right, 3, 6));
        groups.add(new LevelGroup(R.drawable.ic_turtle_facing_right, 2, 10));
        groups.add(new LevelGroup(R.drawable.ic_cat_facing_right, 3, 8));
        groups.add(new LevelGroup(R.drawable.ic_big_toucan, 0, 7));
        groups.add(new LevelGroup(R.drawable.ic_big_owl, 2, 9));

        levelGenerator = new LevelGenerator();
    }

    public int getNumberOfGroups() {
        return groups.size();
    }

    public LevelGroup getGroup(int index) {
        return groups.get(index);
    }

    public Level getNextLevel() {
        return levelGenerator.generateLevel();
    }
}
