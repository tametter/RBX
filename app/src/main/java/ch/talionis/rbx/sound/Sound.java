package ch.talionis.rbx.sound;

import ch.talionis.rbx.R;

public enum Sound {
    BLOCK_MOVE(R.raw.block_move),
    LEVEL_COMPLETE(R.raw.level_complete);

    private int rawId;

    public int getRawId() {
        return rawId;
    }

    Sound(int rawId) {
        this.rawId = rawId;
    }
}
