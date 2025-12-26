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
        StartMenuView menu = new StartMenuView(this::launchGame, () -> {});
        stage.setScene(new Scene(menu, 1100, 500));
        stage.show();
    }

    private void launchGame() {
        GameManager manager = new GameManager();
        manager.startNewGame("Player 1", "Player 2");
        GameUIController ui = new GameUIController(manager, this::showStartMenu);
        stage.setScene(new Scene(ui.getRoot(), 1100, 500));
    }

    public static void main(String[] args) { launch(args); }
}