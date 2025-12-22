package gebeta.domain;

import java.io.Serializable;

public class Pit implements Serializable{
    private static final long serialVersionUID = 1L;
    private int index;
    private int stoneCount;

    public Pit(int index, int stoneCount) {
        this.index = index;
        this.stoneCount = stoneCount;
    }

    public int getIndex() {
        return index;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public void setStoneCount(int stoneCount) {
        this.stoneCount = stoneCount;
    }

    public void addStone() {
        stoneCount++;
    }

    public int removeAllStones() {
        int stones = stoneCount;
        stoneCount = 0;
        return stones;
    }

    @Override
    public String toString() {
        return "Pit { index = " + index + ", stones = " + stoneCount + " }";
    }
}
