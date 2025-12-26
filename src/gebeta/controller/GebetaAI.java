package gebeta.controller;
import gebeta.domain.*;

public class GebetaAI {
    private final GameLogic logic = new GameLogic();
    private final int maxDepth;
    public GebetaAI(int depth){
        this.maxDepth = depth;
    }

    public int findBestMove(GameBoard board, Player aiPlayer, Player humanPlayer){
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = aiPlayer.getStartPit(); i <= aiPlayer.getEndPit(); i++){
            GameBoard simulatGameBoard = new GameBoard(board);
            logic.executeMove(simulatGameBoard, aiPlayer, humanPlayer, i);
            int score = minimax(simulatGameBoard, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, false, aiPlayer, humanPlayer);
            if (score > bestScore){
                bestScore = score;
                bestMove = i;
            }

        }
        return bestMove;
    }

    private int minimax(GameBoard board, int depth, int alpha, int beta, boolean maximizingPlayer, Player aiplayer, Player humanPlayer){
        if (depth == 0 || logic.isGameOver(board, aiplayer, humanPlayer)){
            return evaluateBoard(board, aiplayer, humanPlayer);
        }
        if (maximizingPlayer){
            int maxValue = Integer.MIN_VALUE;
            for (int i = aiplayer.getStartPit(); i <= aiplayer.getStartPit(); i++){
                if (board.getStoneCount(i)> 0){

                    GameBoard nextBoard = new GameBoard(board);
                    boolean extraTurn = logic.executeMove(nextBoard, aiplayer, humanPlayer, i);
                    int score = minimax(board, extraTurn? depth : depth-1, alpha, beta, extraTurn, aiplayer, humanPlayer);
                    maxValue = Integer.max(maxValue, score);
                    alpha = Math.max(alpha, score);
                    if (beta <= alpha)break;
            
                }    
            }
            return maxValue;
        }else{
            int minValue = Integer.MAX_VALUE;
            for (int i = humanPlayer.getStorePit(); i < humanPlayer.getEndPit(); i++){
                if (board.getStoneCount(i) > 0) {
                    GameBoard nextBoard = new GameBoard(board);
                    boolean extraTurn = logic.executeMove(nextBoard, humanPlayer, aiplayer, i);
                    int eval = minimax(nextBoard, extraTurn ? depth : depth - 1, alpha, beta, !extraTurn, aiplayer, humanPlayer);
                    minValue = Math.min(minValue, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) break;
                }

            }
            return minValue;
        }
    }
    private int evaluateBoard(GameBoard board, Player ai, Player human) {
        if (logic.isGameOver(board, ai, human)) {
            int aiFinal = board.getStoneCount(ai.getStorePit());
            int huFinal = board.getStoneCount(human.getStorePit());
            
            for (int i = ai.getStartPit(); i <= ai.getEndPit(); i++) aiFinal += board.getStoneCount(i);
            for (int i = human.getStartPit(); i <= human.getEndPit(); i++) huFinal += board.getStoneCount(i);
            
            return (aiFinal - huFinal) * 100; 
        }

        int score = (board.getStoneCount(ai.getStorePit()) - board.getStoneCount(human.getStorePit())) * 10;

        for (int i = ai.getStartPit(); i <= ai.getEndPit(); i++) {
            if (board.getStoneCount(i) == 0) {
                int oppIndex = board.oppositePit(i);
                int stonesToCapture = board.getStoneCount(oppIndex);
                score += (stonesToCapture * 2); 
            }
        }

        for (int i = human.getStartPit(); i <= human.getEndPit(); i++) {
            if (board.getStoneCount(i) == 0) {
                int aiOppositeIndex = board.oppositePit(i);
                int stonesAtRisk = board.getStoneCount(aiOppositeIndex);
                score -= (stonesAtRisk * 2); 
            }
        }

            return score;
        }
}
    
