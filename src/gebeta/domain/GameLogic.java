package gebeta.domain;

public class GameLogic {

    public boolean executeMove(GameBoard board, Player player, Player opponent, int pitIndex) {

        Pit selectedPit = board.getPit(pitIndex);

        int stones = selectedPit.removeAllStones();
        int currentIndex = pitIndex;

        // Distribute stones counter-clockwise
        while (stones > 0) {
            currentIndex = (currentIndex + 1) % GameBoard.TOTAL_PITS;

            // Skip opponent STORE
            if (currentIndex == opponent.getStorePit()) {
                continue;
            }

            board.getPit(currentIndex).addStone();
            stones--;
        }

        // RULE 1: EXTRA TURN if last stone lands in player's store
        if (currentIndex == player.getStorePit()) {
            return true;  // extra turn
        }

        // RULE 2: CAPTURE if last stone lands in an EMPTY pit on own side 
        boolean landedOnOwnSide = currentIndex >= player.getStartPit() && currentIndex <= player.getEndPit();

        Pit lastPit = board.getPit(currentIndex);

        if (landedOnOwnSide && lastPit.getStoneCount() == 1) {  // was empty before placing
            int oppositeIndex = board.oppositePit(currentIndex);

            Pit oppositePit = board.getPit(oppositeIndex);
            int captured = oppositePit.removeAllStones();

            if (captured > 0) {
                // remove the stone from this pit too (the landing stone)
                lastPit.setStoneCount(0);

                // captured + the landing stone go to player's store
                Pit store = board.getPit(player.getStorePit());
                store.setStoneCount(store.getStoneCount() + captured + 1);
            }
        }

        return false;  // no extra turn
    }

    public boolean isGameOver(GameBoard board, Player p1, Player p2) {

        boolean p1Empty = sideEmpty(board, p1.getStartPit(), p1.getEndPit());
        boolean p2Empty = sideEmpty(board, p2.getStartPit(), p2.getEndPit());

        return p1Empty || p2Empty;
    }

    private boolean sideEmpty(GameBoard board, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (board.getPit(i).getStoneCount() > 0)
                return false;
        }
        return true;
    }


    // Move all remaining stones to the correct player's store at game end.
    public void collectRemainingStones(GameBoard board, Player p1, Player p2) {

        // Player 1 remaining stones
        int p1Remaining = 0;
        for (int i = p1.getStartPit(); i <= p1.getEndPit(); i++) {
            p1Remaining += board.getPit(i).removeAllStones();
        }
        board.getPit(p1.getStorePit()).setStoneCount(
                board.getPit(p1.getStorePit()).getStoneCount() + p1Remaining
        );

        // Player 2 remaining stones
        int p2Remaining = 0;
        for (int i = p2.getStartPit(); i <= p2.getEndPit(); i++) {
            p2Remaining += board.getPit(i).removeAllStones();
        }
        board.getPit(p2.getStorePit()).setStoneCount(
                board.getPit(p2.getStorePit()).getStoneCount() + p2Remaining
        );
    }
}
