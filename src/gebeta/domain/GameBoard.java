package gebeta.domain;

public class GameBoard {

    private Pit[] pits;

    public static final int TOTAL_PITS = 12;  

    public GameBoard(int initialStones) {
        pits = new Pit[TOTAL_PITS];

        // initialize each pit with stones
        for (int i = 0; i < TOTAL_PITS; i++) {
            pits[i] = new Pit(i, initialStones);
        }
    }

    public Pit getPit(int index) {
        if (index < 0 || index >= TOTAL_PITS) {
            System.out.println("Invalid pit index: " + index);
        }
        return pits[index];
    }

    public int getStoneCount(int index) {
        return pits[index].getStoneCount();
    }

    public void setStoneCount(int index, int stoneCount) {
        pits[index].setStoneCount(stoneCount);
    }

    public void printBoard() {
        for (Pit pit : pits) {
            System.out.println(pit);
        }
    }
}
