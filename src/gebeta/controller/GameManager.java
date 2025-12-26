package gebeta.controller;

import gebeta.domain.*;
import gebeta.persistence.SaveLoadSystem;


public class GameManager{
    private GameBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;
    
    private final GameLogic gameLogic;
    private final MoveValidator moveValidator;
    private final SaveLoadSystem saveLoadSystem;

    private  GebetaAI ai;
    private Difficulty currentDifficulty;

    public GameManager(){
        gameLogic  = new GameLogic();
        moveValidator = new MoveValidator();
        saveLoadSystem = new SaveLoadSystem();
    }

    public void startNewGame(String p1Name, String p2Name, boolean vsAI, Difficulty difficulty){
        board = new GameBoard();
        player1 = new Player(p1Name, GameBoard.P1_START, GameBoard.P1_END, GameBoard.P1_STORE, false);

        player2 = new Player(p2Name, GameBoard.P2_START, GameBoard.P2_END,  GameBoard.P2_STORE, vsAI);
        this.currentDifficulty = difficulty;
        if (vsAI) {
            this.ai = new GebetaAI(difficulty);
        }
        currentPlayer = player1;
        gameOver = false;
    }
    public boolean playTurn(int pitIndex) {
        if (gameOver) return false;

        if (!moveValidator.isValidMove(board, currentPlayer, pitIndex)) return false;

        Player opponent = (currentPlayer == player1) ? player2 : player1;
        boolean extraTurn = gameLogic.executeMove(board, currentPlayer, opponent, pitIndex);

        // CHECK IF GAME IS OVER AFTER MOVE
        if (gameLogic.isGameOver(board, player1, player2)) {
            gameLogic.collectRemainingStones(board, player1, player2);
            this.gameOver = true;
            return true; // Return true to trigger UI refresh
        }

        if (!extraTurn) {
            switchTurn();
        }

        return true;
    }
    private void switchTurn(){
        currentPlayer = getOpponent(currentPlayer);

    }

    public void requestAIMove() {
        if (!gameOver && currentPlayer == player2) {
            int bestPit = ai.findBestMove(board, player2, player1);
            playTurn(bestPit);
        }
    }
    public int getBestMoveForAI() {
        if (currentPlayer.isAI()) {
            return ai.findBestMove(board, currentPlayer, getOpponent(currentPlayer));
        }
        return -1;
    }
   
    
    public Player getWinner() {
        if (!gameOver) {
            return null;
        }

        int score1 = player1.getScore(board);
        int score2 = player2.getScore(board);

        if (score1 > score2) {
            return player1;
        } else if (score2 > score1) {
            return player2;
        } else {
            return null;
        }
    }

    public boolean isDraw() {
        if (!gameOver) {
            return false;
        }
        return player1.getScore(board) == player2.getScore(board);
    }
//// save and load the game
    public void saveGame() {
        saveLoadSystem.saveGame(board, player1, player2, currentPlayer);
    }

    public void loadGame() {
        SaveLoadSystem.LoadResult result = saveLoadSystem.loadGame();

        if (result != null) {
            this.board = result.getBoard();
            this.player1 = result.getPlayer1();
            this.player2 = result.getPlayer2();
            this.currentPlayer = result.getCurrentPlayer();
            this.gameOver = false;
            if (this.player2.isAI()) {
                // You might need to save/load the difficulty too!
                this.ai = new GebetaAI(this.currentDifficulty); 
            }
        }
        
    }

    public GameBoard getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Player getOpponent(Player currentPlayer){
        Player opponent = currentPlayer == player1? player2: player1;
        return opponent;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    public int getScore(Player player){
        return player.getScore(board);
    }

}