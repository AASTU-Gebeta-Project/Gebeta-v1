package gebeta.ui;

import gebeta.controller.GameManager;
import gebeta.domain.Difficulty;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage stage;
    private GameManager gameManager;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.gameManager = new GameManager();

        stage.setOnCloseRequest(event -> {
            // Only save if the board exists AND the game isn't already over
            if (gameManager.getBoard() != null && !gameManager.isGameOver()) {
                gameManager.saveGame();
            } else {
                // If the game was over or never started, make sure no "ghost" file remains
                gameManager.deleteSaveFile();
            }
        });

        showStartMenu();
    }
    public void showStartMenu() {
        // StartMenuView expects a BiConsumer (Boolean, Difficulty) and a Runnable
        StartMenuView menu = new StartMenuView(this::launchGame, this::showCulturalInfo);

        if (stage.getScene() == null) {
            stage.setScene(new Scene(menu, 1100, 500));
        } else {
            stage.getScene().setRoot(menu);
        }
        stage.show();
    }

    private void showCulturalInfo() {
        CulturalInfoView infoPage = new CulturalInfoView(this::showStartMenu);
        stage.getScene().setRoot(infoPage.getRoot());
    }

    /**
     * UPDATED launchGame logic:
     * If vsAI is null, it means the user clicked the "RESUME" button.
     */
    private void launchGame(Boolean vsAI, Difficulty difficulty) {
        boolean finalVsAI;
        Difficulty finalDifficulty;

        if (vsAI == null) {
            // 1. Logic for RESUMING a saved game
            gameManager.loadGame();

            // Re-detect the settings from the loaded data
            finalVsAI = gameManager.getPlayer2().isAI();
            finalDifficulty = (difficulty != null) ? difficulty : Difficulty.MEDIUM;
        } else {
            // 2. Logic for a FRESH NEW GAME
            finalVsAI = vsAI;
            finalDifficulty = difficulty;
            gameManager.startNewGame("Player 1", finalVsAI ? "Machine" : "Player 2", finalVsAI, finalDifficulty);
        }

        // Initialize the UI with the determined settings
        GameUIController ui = new GameUIController(gameManager, this::showStartMenu, finalVsAI, finalDifficulty);

        stage.getScene().setRoot(ui.getRoot());
    }

    public static void main(String[] args) {
        launch(args);
    }
}