package gebeta.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class StartMenuView extends StackPane {
    private MediaPlayer mediaPlayer;

    public StartMenuView(Runnable onStartGame, Runnable onShowInfo) {
        // 1. Background Image
        try {
            Image bg = new Image(getClass().getResourceAsStream("/gebeta/resources/menu_bg.png"));
            ImageView bgView = new ImageView(bg);
            bgView.setFitWidth(1100);
            bgView.setFitHeight(500);
            getChildren().add(bgView);
        } catch (Exception e) {
            this.setStyle("-fx-background-color: #3e2723;");
        }

        // 2. Buttons (Including Cultural Info)
        VBox menuBox = new VBox(20);
        menuBox.setAlignment(Pos.CENTER);

        Button btnStart = createMenuButton("START GAME");
        btnStart.setOnAction(e -> {
            stopMusic();
            onStartGame.run();
        });

        Button btnInfo = createMenuButton("CULTURAL INFO");
        btnInfo.setOnAction(e -> onShowInfo.run());

        Button btnExit = createMenuButton("EXIT APP");
        btnExit.setStyle("-fx-base: #c0392b; -fx-text-fill: white;");
        btnExit.setOnAction(e -> System.exit(0));

        menuBox.getChildren().addAll(btnStart, btnInfo, btnExit);
        getChildren().add(menuBox);

        playMenuMusic();
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(200);
        btn.setStyle("-fx-font-size: 18px; -fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-border-color: gold;");
        return btn;
    }

    private void playMenuMusic() {
        try {
            var res = getClass().getResource("/gebeta/resources/background_music.m4a");
            if (res != null) {
                mediaPlayer = new MediaPlayer(new Media(res.toExternalForm()));
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            }
        } catch (Exception ignored) {}
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}