package ch.talionis.rbx.levels;

import java.util.ArrayList;
import java.util.List;

import ch.talionis.rbx.R;

public class LevelManager {
    private List<LevelGroup> groups;

    public LevelManager() {
        groups = new ArrayList<>();

        groups.add(new LevelGroup(R.drawable.ic_rat_looking_right, 0));
        groups.add(new LevelGroup(R.drawable.ic_rabbit_facing_right, 3));
        groups.add(new LevelGroup(R.drawable.ic_turtle_facing_right, 2));
        groups.add(new LevelGroup(R.drawable.ic_cat_facing_right, 3));
        groups.add(new LevelGroup(R.drawable.ic_big_toucan, 0));
        groups.add(new LevelGroup(R.drawable.ic_big_owl, 2));

    }

    public int getNumberOfGroups() {
        return groups.size();
    }

    public LevelGroup getGroup(int index) {
        return groups.get(index);
    }
}
