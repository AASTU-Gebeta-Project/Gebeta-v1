package gebeta.domain;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int startPit;
    private int endPit;
    private int storePit;
    private boolean isAI;

    public Player(String name, int startPit, int endPit, int storePit, boolean isAI) {
        this.name = name;
        this.startPit = startPit;
        this.endPit = endPit;
        this.storePit = storePit;
        this.isAI = isAI;
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

    public int getScore(GameBoard board) {
        return board.getPit(storePit).getStoneCount();
    }
    public boolean isAI(){
        return isAI;
    }

}
