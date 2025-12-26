package gebeta.ui;

import gebeta.controller.GameManager;
import gebeta.domain.GameBoard;
import gebeta.domain.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;

public class GameUIController {
    private final GameManager gameManager;
    private final StackPane mainContainer = new StackPane();
    private final BorderPane uiOverlay = new BorderPane();
    private final GridPane boardPane = new GridPane();
    private final List<PitView> pitViews = new ArrayList<>();
    private final Label turnLabel = new Label();
    private final Label scoreP1 = new Label();
    private final Label scoreP2 = new Label();
    private final Runnable backToHome;
    private MediaPlayer gameMusicPlayer;

    public GameUIController(GameManager gameManager, Runnable backToHome) {
        this.gameManager = gameManager;
        this.backToHome = backToHome;
        setupBackground();
        setupLayout();
        buildBoard();
        refreshBoard();
        playGameMusic();
    }

    private void playGameMusic() {
        try {

            var res = getClass().getResource("/gebeta/resources/Ethiopian_Instrumental_1(128k).m4a");
            if (res != null) {
                gameMusicPlayer = new MediaPlayer(new Media(res.toExternalForm()));
                gameMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                gameMusicPlayer.play();
            }
        } catch (Exception e) {
            System.out.println("Could not play game music: " + e.getMessage());
        }
    }

    private void setupBackground() {
        try {
            Image gameBg = new Image(getClass().getResourceAsStream("/gebeta/resources/Gemini_Generated_Image_6hac4m6hac4m6hac.png"));
            ImageView bgView = new ImageView(gameBg);
            bgView.setFitWidth(1100); bgView.setFitHeight(500);
            mainContainer.getChildren().add(bgView);
        } catch (Exception e) { mainContainer.setStyle("-fx-background-color: #2c3e50;"); }
        mainContainer.getChildren().add(uiOverlay);
    }

    private void setupLayout() {

        boardPane.setHgap(12);
        boardPane.setVgap(-10);
        boardPane.setAlignment(Pos.CENTER);

        turnLabel.setStyle("-fx-font-size: 22; -fx-text-fill: gold; -fx-background-color: rgba(0,0,0,0.8); -fx-padding: 8;");
        scoreP1.setStyle("-fx-text-fill: #3498db; -fx-font-size: 18; -fx-font-weight: bold;");
        scoreP2.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 18; -fx-font-weight: bold;");


        Label p2Ind = new Label("PLAYER TWO (TOP)");
        p2Ind.setStyle("-fx-text-fill: black; -fx-font-size: 22px; -fx-font-weight: bold;");

        Label p1Ind = new Label("PLAYER ONE (BOTTOM)");
        p1Ind.setStyle("-fx-text-fill: black; -fx-font-size: 22px; -fx-font-weight: bold;");

        VBox topCenter = new VBox(3, turnLabel, p2Ind);
        topCenter.setAlignment(Pos.CENTER);
        HBox topBox = new HBox(80, scoreP2, topCenter, scoreP1);
        topBox.setAlignment(Pos.CENTER);
        topBox.setStyle("-fx-padding:  5 0 0 0;");

        Button btnPause = new Button("PAUSE");
        btnPause.setOnAction(e -> showPauseMenu());

        uiOverlay.setTop(topBox);
        VBox centerBox = new VBox(boardPane);
        centerBox.setAlignment(Pos.TOP_CENTER); // moves board toward top
        VBox.setMargin(boardPane, new Insets(0, 0, 0, 0)); // fine-tune top margin
        uiOverlay.setCenter(centerBox);


        VBox bottomBox = new VBox(5, p1Ind, btnPause);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 0 0 20 0;"); // Bottom padding to lift labels
        uiOverlay.setBottom(bottomBox);
    }

    private void showPauseMenu() {
        VBox pauseBox = new VBox(20);
        pauseBox.setAlignment(Pos.CENTER);
        pauseBox.setStyle("-fx-background-color: rgba(0,0,0,0.85);");

        Label pauseLbl = new Label("PAUSED");
        pauseLbl.setStyle("-fx-text-fill: white; -fx-font-size: 40;");

        Button resume = new Button("RESUME");
        resume.setOnAction(e -> mainContainer.getChildren().remove(pauseBox));
        Button restart = new Button("RESTART");
        restart.setOnAction(e -> {
            // Stop music first
            if (gameMusicPlayer != null) {
                gameMusicPlayer.stop();
            }

            // Reset game logic
            gameManager.startNewGame("Player 1", "Player 2");

            // Remove pause menu
            mainContainer.getChildren().remove(pauseBox);

            // Restart music and refresh UI
            playGameMusic();
            refreshBoard();
        });


        Button exit = new Button("HOME");
        exit.setOnAction(e -> {
            if (gameMusicPlayer != null) gameMusicPlayer.stop();
            backToHome.run();
        });

        pauseBox.getChildren().addAll(pauseLbl, resume, restart, exit);
        mainContainer.getChildren().add(pauseBox);
    }

    public void refreshBoard() {
        for (PitView pit : pitViews) {
            pit.animateToCount(gameManager.getBoard().getPit(pit.getPitIndex()).getStoneCount());
        }
        turnLabel.setText("TURN: " + gameManager.getCurrentPlayer().getName());
        scoreP1.setText("P1: " + gameManager.getPlayer1().getScore(gameManager.getBoard()));
        scoreP2.setText("P2: " + gameManager.getPlayer2().getScore(gameManager.getBoard()));

        if (gameManager.isGameOver()) {
            if (gameMusicPlayer != null) gameMusicPlayer.stop();
            showWinnerScreen();
        }
    }

    private void showWinnerScreen() {
        VBox winBox = new VBox(20);
        winBox.setAlignment(Pos.CENTER);
        winBox.setStyle("-fx-background-color: rgba(0,0,0,0.9);");
        Player w = gameManager.getWinner();
        Label winLbl = new Label(w == null ? "DRAW!" : w.getName() + " WINS!");
        winLbl.setStyle("-fx-text-fill: gold; -fx-font-size: 50;");
        Button again = new Button("PLAY AGAIN");
        again.setOnAction(e -> {
            gameManager.startNewGame("Player 1", "Player 2");
            mainContainer.getChildren().remove(winBox);
            playGameMusic();
            refreshBoard();
        });
        Button home = new Button("HOME");
        home.setOnAction(e -> backToHome.run());
        winBox.getChildren().addAll(winLbl, again, home);
        mainContainer.getChildren().add(winBox);
    }

    private void buildBoard() {
        boardPane.getChildren().clear(); pitViews.clear();
        addPit(GameBoard.P2_STORE, 0, 0, true);
        int col = 1;
        for (int i = GameBoard.P2_END; i >= GameBoard.P2_START; i--) addPit(i, col++, 0, false);
        col = 1;
        for (int i = GameBoard.P1_START; i <= GameBoard.P1_END; i++) addPit(i, col++, 1, false);
        addPit(GameBoard.P1_STORE, 7, 0, true);
    }

    private void addPit(int index, int col, int row, boolean isStore) {
        PitView pv = new PitView(index, gameManager.getBoard().getPit(index).getStoneCount(), isStore);
        pitViews.add(pv);

        // Add to the grid normally
        boardPane.add(pv, col, row);

        if (isStore) {
            // Pushes ONLY the store down by a specific pixel amount.
            // Adjust the '45' until it is perfectly centered between your pit rows.
            pv.setTranslateY(60);
        } else {
            pv.setOnMouseClicked(e -> {
                if(gameManager.playTurn(index)) refreshBoard();
            });
        }
    }

    public StackPane getRoot() { return mainContainer; }
}