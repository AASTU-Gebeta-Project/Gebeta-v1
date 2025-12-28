package gebeta.domain;

import java.io.Serializable;

public class GameBoard implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int TOTAL_PITS = 14;
    public static final int INITIAL_STONE = 4;

    public static final int P1_START = 0;
    public static final int P1_END = 5;
    public static final int P1_STORE = 6;

    public static final int P2_START = 7;
    public static final int P2_END = 12;
    public static final int P2_STORE = 13;

    private Pit[] pits;

    public GameBoard() {
        pits = new Pit[TOTAL_PITS];

        for (int i = 0; i < TOTAL_PITS; i++) {

            if (i == P1_STORE || i == P2_STORE) {
                pits[i] = new Pit(i, 0);
            } else {
                pits[i] = new Pit(i, INITIAL_STONE);
            }
        }
    }
    public GameBoard(GameBoard other){
        this.pits = new Pit[TOTAL_PITS];
        for(int i = 0; i < TOTAL_PITS; i++){
            this.pits[i] = new Pit(i, other.getPit(i).getStoneCount());
        }
    }

    public Pit getPit(int index) {
        // if (index < 0 || index >= TOTAL_PITS)
        //     System.out.println("Invalid pit index " + index);

        return pits[index];
    }
    public int getStoneCount(int index) {
        return pits[index].getStoneCount();
    }

    public void setStoneCount(int index, int stoneCount) {
        pits[index].setStoneCount(stoneCount);
    }

    public int oppositePit(int index) {
            return 12 - index;
    
    }

    // public void printBoard() {
    //     for (Pit p : pits) {
    //         System.out.println(p);
    //     }
    // }
}

