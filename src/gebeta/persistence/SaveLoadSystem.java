package gebeta.persistence;

import java.io.*;

import gebeta.domain.GameBoard;
import gebeta.domain.Player;

public class SaveLoadSystem {
    private static final String SAVE_FILE = "gebeta_save.dat";
    public void saveGame(GameBoard board, Player player1, Player player2, Player currentPlayer){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))){
            out.writeObject(board);
            out.writeObject(player1);
            out.writeObject(player2);
            out.writeObject(currentPlayer);


        } catch (IOException e){
            System.out.println("Error saving game: " + e.getMessage());
        }
    }
public LoadResult loadGame(){
    try(ObjectInputStream in =  new ObjectInputStream(new FileInputStream(SAVE_FILE))){
        GameBoard board = (GameBoard) in.readObject();
        Player player1 = (Player) in.readObject();
        Player player2 = (Player) in.readObject();
        Player currentPlayer = (Player) in.readObject();

        System.out.println("Game loaded successfully.");

        return new LoadResult(board, player1, player2, currentPlayer);
        

    } catch (IOException | ClassNotFoundException e){
        System.out.println("No saved game found.");
        return null;
    }

}
public class LoadResult{
    private final GameBoard board;
    private final Player player1;
    private final Player player2;
    private final Player currentPlayer;
    
    public LoadResult(GameBoard board,
                      Player player1,
                      Player player2,
                      Player currentPlayer) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
    }

    
    public GameBoard getBoard() { return board; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getCurrentPlayer() { return currentPlayer; }
}

    


    
}
