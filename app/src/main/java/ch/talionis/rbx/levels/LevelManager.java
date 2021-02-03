package ch.talionis.rbx.levels;

import java.util.ArrayList;
import java.util.List;

import ch.talionis.rbx.R;

public class LevelManager {
    private List<LevelGroup> groups;

    public LevelManager() {
        groups = new ArrayList<>();

        groups.add(new LevelGroup(R.drawable.ic_rat_looking_right));
        groups.add(new LevelGroup(R.drawable.ic_rabbit_facing_right));
        groups.add(new LevelGroup(R.drawable.ic_turtle_facing_right));
        groups.add(new LevelGroup(R.drawable.ic_cat_facing_right));
        groups.add(new LevelGroup(R.drawable.ic_big_toucan));
        groups.add(new LevelGroup(R.drawable.ic_big_owl));

    }

    public int getNumberOfGroups() {
        return groups.size();
    }

    public LevelGroup getGroup(int index) {
        return groups.get(index);
    }
}
