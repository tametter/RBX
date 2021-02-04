package ch.talionis.rbx.levels;

public class LevelGroup {
    private final int groupIconRes;
    private final int stars;

    public LevelGroup(int groupIconRes, int stars) {
        this.groupIconRes = groupIconRes;
        this.stars = stars;
    }

    public int getGroupIconRes() {
        return groupIconRes;
    }

    public int getStars() {
        return stars;
    }
}
