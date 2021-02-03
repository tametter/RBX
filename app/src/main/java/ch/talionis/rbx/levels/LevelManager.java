package ch.talionis.rbx.levels;

import android.content.Context;

public class LevelManager {
    private Context context;

    public LevelManager(Context context) {
        this.context = context;
    }

    public int getNumberOfGroups() {
        return 10;
    }
}
