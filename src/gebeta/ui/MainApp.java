package gebeta.ui;

import gebeta.controller.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showStartMenu();
    }

    public void showStartMenu() {
        // Replace the empty lambda () -> {} with this::showCulturalInfo
        StartMenuView menu = new StartMenuView(this::launchGame, this::showCulturalInfo);
        stage.setScene(new Scene(menu, 1100, 500));
        stage.show();
    }

    // Logic to switch to the new Info page
    private void showCulturalInfo() {
        CulturalInfoView infoPage = new CulturalInfoView(this::showStartMenu);
        stage.getScene().setRoot(infoPage.getRoot());
    }

    private void launchGame() {
        GameManager manager = new GameManager();
        manager.startNewGame("Player 1", "Player 2");
        GameUIController ui = new GameUIController(manager, this::showStartMenu);
        stage.setScene(new Scene(ui.getRoot(), 1100, 500));
    }

    public static void main(String[] args) { launch(args); }
}