package gebeta.ui;

import gebeta.controller.GameManager;
import gebeta.domain.Difficulty;
import gebeta.domain.GameBoard;
import gebeta.domain.Player;
import gebeta.persistence.SaveLoadSystem;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.util.Duration;

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
    private final SaveLoadSystem saveLoadSystem = new SaveLoadSystem();
    // Fields to remember the current game mode
    private final boolean isVsAI;
    private final Difficulty currentDifficulty;

    public GameUIController(GameManager gameManager, Runnable backToHome, boolean vsAI, Difficulty difficulty) {
        this.gameManager = gameManager;
        this.backToHome = backToHome;
        this.isVsAI = vsAI;
        this.currentDifficulty = difficulty;

        setupBackground();
        setupLayout();
        buildBoard();
        refreshBoard();
        playGameMusic();

        // Check if the AI should start the first move
        checkAndTriggerAI();
        // Add this at the end of the constructor
        addMuteButton();
    }

    private void playGameMusic() {
        try {
            var res = getClass().getResource("/gebeta/resources/Ethiopian_Instrumental_1(128k).m4a");
            if (res != null) {
                gameMusicPlayer = new MediaPlayer(new Media(res.toExternalForm()));
                gameMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                gameMusicPlayer.setMute(StartMenuView.isMuted);
                gameMusicPlayer.play();
            }
        } catch (Exception e) {
            System.out.println("Could not play game music: " + e.getMessage());
        }
    }

    private void setupBackground() {
        try {
            Image gameBg = new Image(getClass().getResourceAsStream("/gebeta/resources/final2.png"));
            ImageView bgView = new ImageView(gameBg);
            bgView.setPreserveRatio(false);
            bgView.fitWidthProperty().bind(mainContainer.widthProperty());
            bgView.fitHeightProperty().bind(mainContainer.heightProperty());
            mainContainer.getChildren().add(bgView);
        } catch (Exception e) { mainContainer.setStyle("-fx-background-color: #2c3e50;"); }
        mainContainer.getChildren().add(uiOverlay);
    }

    private void setupLayout() {

        boardPane.setHgap(20);
        boardPane.setVgap(50);
        boardPane.setAlignment(Pos.CENTER);

        turnLabel.setStyle("-fx-font-size: 22; -fx-text-fill: gold; -fx-background-color: rgba(0,0,0,0.8); -fx-padding: 8;");
        scoreP1.setStyle("-fx-text-fill: #3498db; -fx-font-size: 20; -fx-font-weight: bold;");
        scoreP2.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 20; -fx-font-weight: bold;");


        Label p2Ind = new Label((isVsAI ? "MACHINE" : "PLAYER TWO"));
        p2Ind.setStyle("-fx-text-fill: WHITE; -fx-font-size: 22px; -fx-font-weight: bold;");

        Label p1Ind = new Label("PLAYER ONE ");
        p1Ind.setStyle("-fx-text-fill: WHITE; -fx-font-size: 24px; -fx-font-weight: bold;");

        VBox topCenter = new VBox(10, turnLabel, p2Ind);
        topCenter.setAlignment(Pos.CENTER);
        HBox topBox = new HBox(80, scoreP2, topCenter, scoreP1);
        topBox.setAlignment(Pos.CENTER);
        topBox.setStyle("-fx-padding:  5 0 0 0;");

        Button btnPause = new Button("PAUSE");
        btnPause.setOnAction(e -> showPauseMenu());

        uiOverlay.setTop(topBox);
        VBox centerBox = new VBox(boardPane);
        centerBox.setAlignment(Pos.CENTER); // moves board toward top
        VBox.setMargin(boardPane, new Insets(0, 0, 0, 0)); // fine-tune top margin
        uiOverlay.setCenter(centerBox);


        VBox bottomBox = new VBox(5, p1Ind, btnPause);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 0 0 20 0;"); // Bottom padding to lift labels
        uiOverlay.setBottom(bottomBox);
    }

    private void showPauseMenu() {
        VBox pauseBox = new VBox(22);
        pauseBox.setAlignment(Pos.CENTER);
        pauseBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43, #2c5364);" +
                        "-fx-padding: 40;" +
                        "-fx-background-radius: 20;"
        );

        Label pauseLbl = new Label("PAUSED");
        pauseLbl.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 42;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-letter-spacing: 3px;"
        );

        Button resume = new Button("RESUME");
        resume.setStyle(
                "-fx-background-color: #27ae60;" +   // green = positive action
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-padding: 10 30;" +
                        "-fx-background-radius: 30;"
        );
        resume.setOnAction(e -> mainContainer.getChildren().remove(pauseBox));

        Button restart = new Button("RESTART");
        restart.setStyle(
                "-fx-background-color: #2980b9;" +  // blue = neutral action
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-padding: 10 30;" +
                        "-fx-background-radius: 30;"
        );
        restart.setOnAction(e -> {
            if (gameMusicPlayer != null) gameMusicPlayer.stop();

            gameManager.startNewGame(
                    "Player 1",
                    isVsAI ? "Machine" : "Player 2",
                    isVsAI,
                    currentDifficulty
            );

            mainContainer.getChildren().remove(pauseBox);
            playGameMusic();
            refreshBoard();
            checkAndTriggerAI();
        });

        Button btnEndGame = new Button("END GAME");
        btnEndGame.setStyle(
                "-fx-background-color: #c0392b;" +  // red = destructive action
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-padding: 10 30;" +
                        "-fx-background-radius: 30;"
        );
        btnEndGame.setOnAction(e -> {
            gameManager.endGamePermanently();
            backToHome.run();
        });

        Button exit = new Button("HOME");
        exit.setStyle(
                "-fx-background-color: transparent;" + // lowest priority
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-padding: 8 26;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-radius: 30;"
        );
        exit.setOnAction(e -> {
            if (gameMusicPlayer != null) gameMusicPlayer.stop();
            saveLoadSystem.saveGame(
                    gameManager.getBoard(),
                    gameManager.getPlayer1(),
                    gameManager.getPlayer2(),
                    gameManager.getCurrentPlayer()
            );
            backToHome.run();
        });

        pauseBox.getChildren().addAll(
                pauseLbl,
                resume,
                restart,
                btnEndGame,
                exit
        );

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

    // New logic to handle AI moves
    private void checkAndTriggerAI() {
        if (!gameManager.isGameOver() && gameManager.getCurrentPlayer().isAI()) {
            // 1. Let the AI decide its move
            int aiMove = gameManager.getBestMoveForAI();

            if (aiMove != -1) {
                // 2. Find the PitView corresponding to the AI's choice
                PitView selectedPit = pitViews.stream()
                        .filter(pv -> pv.getPitIndex() == aiMove)
                        .findFirst().orElse(null);

                // 3. Highlight the pit so the user knows which one was picked
                if (selectedPit != null) {
                    selectedPit.setStyle("-fx-border-color: red; -fx-border-width: 3; -fx-border-radius: 50;");
                }

                // 4. Use a PauseTransition to create a delay BEFORE the move happens
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(event -> {
                    // Remove highlight
                    if (selectedPit != null) selectedPit.setStyle("");

                    // Execute the move
                    gameManager.playTurn(aiMove);
                    refreshBoard();

                    // 5. Recursively check if the AI gets an extra turn
                    // This will trigger another delay for the next move
                    checkAndTriggerAI();
                });
                pause.play();
            }
        }
    }

    private void showWinnerScreen() {
        VBox winBox = new VBox(22);
        winBox.setAlignment(Pos.CENTER);
        winBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000428, #004e92);" +
                        "-fx-padding: 45;" +
                        "-fx-background-radius: 20;"
        );

        Player w = gameManager.getWinner();
        Label winLbl = new Label(w == null ? "DRAW!" : w.getName() + " WINS!");
        winLbl.setStyle(
                "-fx-text-fill: linear-gradient(to right, #f7971e, #ffd200);" + // gold gradient
                        "-fx-font-size: 52;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-letter-spacing: 3px;"
        );

        Button again = new Button("PLAY AGAIN");
        again.setStyle(
                "-fx-background-color: #27ae60;" +   // success / replay
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-padding: 12 34;" +
                        "-fx-background-radius: 30;"
        );
        again.setOnAction(e -> {
            gameManager.startNewGame(
                    "Player 1",
                    isVsAI ? "Machine" : "Player 2",
                    isVsAI,
                    currentDifficulty
            );
            mainContainer.getChildren().remove(winBox);
            playGameMusic();
            refreshBoard();
            checkAndTriggerAI();
        });

        Button home = new Button("HOME");
        home.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-padding: 8 28;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-radius: 30;"
        );
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
        boardPane.add(pv, col, row);

        if (isStore) {
            // FIX: Span 2 rows and center vertically
            GridPane.setRowSpan(pv, 2);
            GridPane.setValignment(pv, javafx.geometry.VPos.CENTER);
        } else {
            pv.setOnMouseClicked(e -> {
                // Only allow click if it is Human turn
                if (!gameManager.getCurrentPlayer().isAI() && !gameManager.isGameOver()) {
                    if (gameManager.playTurn(index)) {
                        refreshBoard();
                        checkAndTriggerAI(); // Pass turn to AI
                    }
                }
            });
        }
    }
    private void addMuteButton() {
        // Check the global state from StartMenuView
        Button btnMute = new Button(StartMenuView.isMuted ? "🔇" : "🔊");
        btnMute.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-text-fill: gold; -fx-font-size: 20px; -fx-background-radius: 50;");

        // Position in bottom right of the mainContainer
        StackPane.setAlignment(btnMute, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnMute, new javafx.geometry.Insets(20));

        mainContainer.getChildren().add(btnMute);

        btnMute.setOnAction(e -> {
            // Toggle the shared global state
            StartMenuView.isMuted = !StartMenuView.isMuted;

            // Mute the local game music player
            if (gameMusicPlayer != null) {
                gameMusicPlayer.setMute(StartMenuView.isMuted);
            }

            // Update button icon
            btnMute.setText(StartMenuView.isMuted ? "🔇" : "🔊");
        });
    }
    public StackPane getRoot() { return mainContainer; }
}