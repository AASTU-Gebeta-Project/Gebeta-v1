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
            if (gameManager.getBoard() != null && !gameManager.isGameOver()) {
                gameManager.saveGame();
            } else {
                gameManager.deleteSaveFile();
            }
        });

        showStartMenu();
    }
    public void showStartMenu() {
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


    private void launchGame(Boolean vsAI, Difficulty difficulty) {
        boolean finalVsAI;
        Difficulty finalDifficulty;

        if (vsAI == null) {
            gameManager.loadGame();
            finalVsAI = gameManager.getPlayer2().isAI();
            finalDifficulty = (difficulty != null) ? difficulty : Difficulty.MEDIUM;
        } else {
            finalVsAI = vsAI;
            finalDifficulty = difficulty;
            gameManager.startNewGame("Player 1", finalVsAI ? "Machine" : "Player 2", finalVsAI, finalDifficulty);
        }
        GameUIController ui = new GameUIController(gameManager, this::showStartMenu, finalVsAI, finalDifficulty);

        stage.getScene().setRoot(ui.getRoot());
    }

    public static void main(String[] args) {
        launch(args);
    }
}