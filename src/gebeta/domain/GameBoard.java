package gebeta.domain;

public class GameBoard {

    public static final int TOTAL_PITS = 14;
    public static final int INITIAL_STONE = 4;

    // Player 1
    public static final int P1_START = 0;
    public static final int P1_END = 5;
    public static final int P1_STORE = 6;

    // Player 2
    public static final int P2_START = 7;
    public static final int P2_END = 12;
    public static final int P2_STORE = 13;

    private Pit[] pits;

    public GameBoard() {
        pits = new Pit[TOTAL_PITS];

        for (int i = 0; i < TOTAL_PITS; i++) {

            // Stores start with 0 stones
            if (i == P1_STORE || i == P2_STORE) {
                pits[i] = new Pit(i, 0);
            } else {
                pits[i] = new Pit(i, INITIAL_STONE);
            }
        }
    }

    public Pit getPit(int index) {
        if (index < 0 || index >= TOTAL_PITS)
            System.out.println("Invalid pit index " + index);

        return pits[index];
    }
    public int getStoneCount(int index) {
        return pits[index].getStoneCount();
    }

    public void setStoneCount(int index, int stoneCount) {
        pits[index].setStoneCount(stoneCount);
    }

    public int oppositePit(int index) {
        // need to work on this for all pits execpt mancala
        return 12 - index;
    }

    public void printBoard() {
        for (Pit p : pits) {
            System.out.println(p);
        }
    }
}

