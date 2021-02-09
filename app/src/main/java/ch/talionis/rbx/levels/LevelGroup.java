package ch.talionis.rbx.levels;

public class LevelGroup {
    private final int groupIconRes;
    private final int stars;
    private final int numberOfLevels;

    public LevelGroup(int groupIconRes, int stars, int numberOfLevels) {
        this.groupIconRes = groupIconRes;
        this.stars = stars;
        this.numberOfLevels = numberOfLevels;
    }

    public int getGroupIconRes() {
        return groupIconRes;
    }

    public int getStars() {
        return stars;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }
}
