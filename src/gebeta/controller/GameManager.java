package gebeta.controller;

import gebeta.domain.GameBoard;
import gebeta.domain.GameLogic;
import gebeta.domain.Player;
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

    public GameManager(){
        gameLogic  = new GameLogic();
        moveValidator = new MoveValidator();
        saveLoadSystem = new SaveLoadSystem();
    }

    public void startNewGame(String Player1Name, String Player2Name){
        board = new GameBoard();
        player1 = new Player(Player1Name, GameBoard.P1_START, GameBoard.P1_END, GameBoard.P1_STORE);

        player2 = new Player(Player2Name, GameBoard.P2_START, GameBoard.P2_END,  GameBoard.P2_STORE);
        currentPlayer = player1;
        gameOver = false;
    }
    public boolean playTurn(int pitIndex){
        if (gameOver){
            return false;
        }
        if (!moveValidator.isValidMove(board, currentPlayer, pitIndex)){
            return false;
        }
        Player opponent = currentPlayer == player1? player2: player1;
        boolean extraTurn = gameLogic.executeMove(board, currentPlayer, opponent, pitIndex);

        if (gameLogic.isGameOver(board, player1, player2)){
            gameOver = true;
            gameLogic.collectRemainingStones(board, player1, player2);
            return false;
        }
        if (!extraTurn){
            switchTurn();
        }

        return true;
    }
    private void switchTurn(){
        currentPlayer = currentPlayer == player1? player2: player1;

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
        }
    }

    public GameBoard getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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

}