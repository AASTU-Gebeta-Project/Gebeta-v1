package gebeta.domain;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int startPit;
    private int endPit;
    private int storePit;

    public Player(String name, int startPit, int endPit, int storePit) {
        this.name = name;
        this.startPit = startPit;
        this.endPit = endPit;
        this.storePit = storePit;
    }

    public String getName() {
        return name;
    }

    public int getStartPit() {
        return startPit;
    }

    public int getEndPit() {
        return endPit;
    }

    public int getStorePit() {
        return storePit;
    }

    // Score = the number of stones in the player's store pit
    public int getScore(GameBoard board) {
        return board.getPit(storePit).getStoneCount();
    }
}
