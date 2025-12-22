package gebeta.controller;

import gebeta.domain.GameBoard;
import gebeta.domain.Player;

public class MoveValidator {

    public boolean isValidMove(GameBoard board, Player player, int pitIndex) {

        // Check pit index range
        if (pitIndex < 0 || pitIndex >= GameBoard.TOTAL_PITS) {
            return false;
        }

        // Cannot select store pit
        if (pitIndex == player.getStorePit()) {
            return false;
        }

        // Must select pit on player's side
        if (pitIndex < player.getStartPit() || pitIndex > player.getEndPit()) {
            return false;
        }

        // Pit must not be empty
        if (board.getPit(pitIndex).getStoneCount() == 0) {
            return false;
        }

        return true;
    }
}
