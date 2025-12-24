package gebeta.ui;

import gebeta.controller.GameManager;
import gebeta.domain.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIController {

    // ==============================
    // CORE CONTROLLER
    // ==============================
    private final GameManager gameManager = new GameManager();

    // ==============================
    // ROOT NODE (USED FOR NAVIGATION)
    // ==============================
    @FXML private BorderPane rootNode;

    // ==============================
    // START VIEW COMPONENTS
    // ==============================
    @FXML private TextField player1NameField;
    @FXML private TextField player2NameField;

    // ==============================
    // GAME VIEW COMPONENTS
    // ==============================
    @FXML private Label turnLabel;
    @FXML private Label messageLabel;
    @FXML private GridPane boardGrid;

    // ==============================
    // OVERLAYS
    // ==============================
    @FXML private StackPane pauseOverlay;
    @FXML private StackPane winnerOverlay;
    @FXML private Label resultLabel;
    @FXML private Label scoreLabel;

    private boolean paused = false;

    // ==============================
    // NAVIGATION HELPER (CORE FIX)
    // ==============================
    private void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlFile)
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // START GAME
    // ==============================
    @FXML
    private void onStartGame() {
        String p1 = player1NameField.getText();
        String p2 = player2NameField.getText();

        if (p1.isEmpty() || p2.isEmpty()) {
            showAlert("Please enter both player names.");
            return;
        }

        gameManager.startNewGame(p1, p2);
        switchScene("GameView.fxml");
    }

    // ==============================
    // PIT CLICK
    // ==============================
    @FXML
    private void onPitClicked(javafx.event.ActionEvent event) {
        if (paused) return;

        Button pitButton = (Button) event.getSource();
        int pitIndex = Integer.parseInt(pitButton.getId().replace("pit", ""));

        if (!gameManager.playTurn(pitIndex)) {
            messageLabel.setText("Invalid move!");
            return;
        }

        updateUI();

        if (gameManager.isGameOver()) {
            showWinnerOverlay();
        }
    }

    // ==============================
    // PAUSE
    // ==============================
    @FXML
    private void onPauseGame() {
        paused = true;
        pauseOverlay.setVisible(true);
    }

    @FXML
    private void onResumeGame() {
        paused = false;
        pauseOverlay.setVisible(false);
    }

    @FXML
    private void onExitGame() {
        System.exit(0);
    }

    // ==============================
    // WINNER
    // ==============================
    private void showWinnerOverlay() {
        Player winner = gameManager.getWinner();

        int score1 = gameManager.getScore(gameManager.getPlayer1());
        int score2 = gameManager.getScore(gameManager.getPlayer2());

        if (winner != null) {
            resultLabel.setText("Winner: " + winner.getName());
        } else {
            resultLabel.setText("It's a Draw!");
        }

        scoreLabel.setText(
                gameManager.getPlayer1().getName() + ": " + score1 +
                " | " +
                gameManager.getPlayer2().getName() + ": " + score2
        );

        winnerOverlay.setVisible(true);
    }

    @FXML
    private void onGoToMainMenu() {
        switchScene("StartView.fxml");
    }

    // ==============================
    // SAVE / LOAD
    // ==============================
    @FXML
    private void onSaveGame() {
        gameManager.saveGame();
        messageLabel.setText("Game saved.");
    }

    @FXML
    private void onLoadGame() {
        gameManager.loadGame();
        updateUI();
    }

    // ==============================
    // CULTURAL INFO
    // ==============================
    @FXML
    private void onOpenCulturalInfo() {
        switchScene("CulturalInfoView.fxml");
    }

    // ==============================
    // UI UPDATE
    // ==============================
    private void updateUI() {
        turnLabel.setText(
                "Turn: " + gameManager.getCurrentPlayer().getName()
        );

        boardGrid.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button b = (Button) node;
                int index = Integer.parseInt(b.getId().replace("pit", ""));
                b.setText(
                        String.valueOf(
                                gameManager.getBoard()
                                        .getPit(index)
                                        .getStoneCount()
                        )
                );
            }
        });
    }

    // ==============================
    // ALERT
    // ==============================
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
