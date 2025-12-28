package gebeta.ui;

import gebeta.domain.Difficulty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.prefs.Preferences;
public class StartMenuView extends StackPane {
    private MediaPlayer mediaPlayer;
    private final VBox container = new VBox(20);
    private final BiConsumer<Boolean, Difficulty> onStartGame;
    private final Runnable onShowInfo;
    private final ImageView bgView = new ImageView();

    // Java Preferences API to save settings permanently on the hard drive
    private static final Preferences prefs = Preferences.userNodeForPackage(StartMenuView.class);

    // Retrieve saved paths or use defaults
    private static String currentBgPath = prefs.get("bg_path", "/gebeta/resources/menu_bg.png");
    private static String currentMusicPath = prefs.get("music_path", "/gebeta/resources/background_music.m4a");

    public StartMenuView(BiConsumer<Boolean, Difficulty> onStartGame, Runnable onShowInfo) {
        this.onStartGame = onStartGame;
        this.onShowInfo = onShowInfo;

        // Load the saved background
        updateBackgroundImage(currentBgPath);
        bgView.setPreserveRatio(false);
        bgView.fitWidthProperty().bind(this.widthProperty());
        bgView.fitHeightProperty().bind(this.heightProperty());
        getChildren().add(bgView);

        container.setAlignment(Pos.CENTER);
        getChildren().add(container);

        showMainMenu();

        // Load the saved music
        playMenuMusic(currentMusicPath);
    }

    private void showMainMenu() {
        container.getChildren().clear();

        Label title = new Label("GEBETA");

// Using a Serif font for a traditional/royal feel
        title.setStyle(
                "-fx-font-family: 'Georgia', serif;" +
                        "-fx-font-size: 72px;" +               // Increased size for impact
                        "-fx-text-fill: #FFD700;" +            // Hex Gold for better precision
                        "-fx-font-weight: bold;" +
                        "-fx-letter-spacing: 8px;" +           // Spaced out letters for elegance
                        "-fx-padding: 0 0 30 0;"
        );

// Add a 3D Shadow effect
        javafx.scene.effect.DropShadow titleShadow = new javafx.scene.effect.DropShadow();
        titleShadow.setRadius(10.0);
        titleShadow.setOffsetX(3.0);
        titleShadow.setOffsetY(3.0);
        titleShadow.setColor(javafx.scene.paint.Color.color(0, 0, 0, 0.7)); // Semi-transparent black

        title.setEffect(titleShadow);
        container.getChildren().add(title);
        File saveFile = new File("gebeta_save.dat");
        // --- RESUME LOGIC ---
        // Check if file exists AND is not empty (length > 0)
        if (saveFile.exists() && saveFile.length() > 0) {
            Button btnResume = createMenuButton("RESUME PREVIOUS GAME");
            btnResume.setStyle("-fx-font-size: 18px; -fx-background-color: #27ae60; -fx-text-fill: white; -fx-border-color: gold;");
            btnResume.setOnAction(e -> {
                stopMusic();
                onStartGame.accept(null, null);
            });
            container.getChildren().add(btnResume);
        }

        Button btnStart = createMenuButton("START NEW GAME");
        btnStart.setOnAction(e -> showModeSelection());

        Button btnSettings = createMenuButton("SETTINGS");
        btnSettings.setOnAction(e -> showSettingsMenu());

        Button btnInfo = createMenuButton("CULTURAL INFO");
        btnInfo.setOnAction(e -> onShowInfo.run());

        Button btnExit = createMenuButton("EXIT APP");
        btnExit.setStyle("-fx-font-size: 18px; -fx-background-color: #c0392b; -fx-text-fill: white; -fx-min-width: 200;");
        btnExit.setOnAction(e -> System.exit(0));

        container.getChildren().addAll(btnStart, btnSettings, btnInfo, btnExit);
    }

    private void showSettingsMenu() {
        container.getChildren().clear();

        Label label = new Label("SETTINGS");
        label.setStyle("-fx-font-size: 32px; -fx-text-fill: gold; -fx-font-weight: bold;");

        Label bgLabel = new Label("Change Background Theme:");
        bgLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Button btnMenuBg = createMenuButton("MENU THEME (BROWN)");
        btnMenuBg.setOnAction(e -> updateBackgroundImage("/gebeta/resources/menu_bg.png"));

        Button btnGameBg = createMenuButton("GAME THEME (WOOD)");
        btnGameBg.setOnAction(e -> updateBackgroundImage("/gebeta/resources/game_bg.png"));

        Label musicLabel = new Label("Change Music Track:");
        musicLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Button btnMusic1 = createMenuButton("DEFAULT TRACK");
        btnMusic1.setOnAction(e -> playMenuMusic("/gebeta/resources/background_music.m4a"));

        Button btnMusic2 = createMenuButton("ETHIOPIAN INSTRUMENTAL");
        btnMusic2.setOnAction(e -> playMenuMusic("/gebeta/resources/Ethiopian_Instrumental_1(128k).m4a"));

        Button btnBack = createMenuButton("BACK");
        btnBack.setStyle("-fx-font-size: 14px; -fx-background-color: transparent; -fx-text-fill: gold;");
        btnBack.setOnAction(e -> showMainMenu());


        container.getChildren().addAll(label, bgLabel, btnMenuBg, btnGameBg, musicLabel, btnMusic1, btnMusic2, btnBack);
    }

    private void updateBackgroundImage(String path) {
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            bgView.setImage(img);
            currentBgPath = path;
            prefs.put("bg_path", path); // Saves to disk instantly
        } catch (Exception e) {
            this.setStyle("-fx-background-color: #3e2723;");
        }
    }

    private void playMenuMusic(String path) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        try {
            currentMusicPath = path;
            prefs.put("music_path", path); // Saves to disk instantly
            var res = getClass().getResource(path);
            if (res != null) {
                mediaPlayer = new MediaPlayer(new Media(res.toExternalForm()));
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.out.println("Music Load Error: " + e.getMessage());
        }
    }

    private void showModeSelection() {
        container.getChildren().clear();
        Label label = new Label("SELECT MODE");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        container.getChildren().add(label);

        Button btn1P = createMenuButton("ONE PLAYER (VS AI)");
        btn1P.setOnAction(e -> showDifficultySelection());

        Button btn2P = createMenuButton("TWO PLAYERS");
        btn2P.setOnAction(e -> { stopMusic(); onStartGame.accept(false, null); });

        Button btnBack = createMenuButton("BACK");
        btnBack.setOnAction(e -> showMainMenu());
        container.getChildren().addAll(btn1P, btn2P, btnBack);
    }

    private void showDifficultySelection() {
        container.getChildren().clear();
        Label label = new Label("CHOOSE DIFFICULTY");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        container.getChildren().add(label);

        for (Difficulty diff : Difficulty.values()) {
            Button dBtn = createMenuButton(diff.name());
            dBtn.setOnAction(e -> { stopMusic(); onStartGame.accept(true, diff); });
            container.getChildren().add(dBtn);
        }
        Button btnBack = createMenuButton("BACK");
        btnBack.setOnAction(e -> showModeSelection());
        container.getChildren().add(btnBack);
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(280);
        btn.setStyle("-fx-font-size: 18px; -fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-border-color: gold;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-font-size: 18px; -fx-background-color: rgba(218,165,32,0.5); -fx-text-fill: white; -fx-border-color: white;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-font-size: 18px; -fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-border-color: gold;"));
        return btn;
    }

    public void stopMusic() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}