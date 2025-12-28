package gebeta.ui;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.util.Objects;

public class CulturalInfoView {

    private final VBox root = new VBox(32);
    private final ScrollPane scrollPane = new ScrollPane();
    private final StackPane stackRoot = new StackPane();

    public CulturalInfoView(Runnable backToHome) {

        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(50));
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0f2027, #203a43, #2c5364);" +
                        "-fx-background-radius: 25;" +
                        "-fx-effect: innershadow(gaussian, rgba(0,0,0,0.7), 35, 0.3, 0, 0);"
        );

        ImageView imageView = new ImageView(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(
                                "/gebeta/resources/5cc9eb49a13f457cba8680a35c099d61_408_408.jfif"
                        )
                ))
        );
        imageView.setFitWidth(460);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setStyle(
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 20, 0.3, 0, 6);"
        );

        Text title = new Text("GEBETA");
        title.setFill(Color.web("#f4c430"));
        title.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 46));
        title.setTextAlignment(TextAlignment.CENTER);

        Text subtitle = new Text("Cultural Heritage of Ethiopia");
        subtitle.setFill(Color.web("#e6c89c"));
        subtitle.setFont(Font.font("Georgia", FontPosture.ITALIC, 22));
        subtitle.setTextAlignment(TextAlignment.CENTER);

        Text infoText = new Text(
                "Gebeta is a traditional Ethiopian strategy board game played by two players. "
                        + "Stones are picked from holes and distributed across the board with precision and foresight, "
                        + "aiming to collect more stones than the opponent.\n\n"

                        + "With origins dating back thousands of years, Gebeta was played by kings, elders, and judges "
                        + "as a symbol of intelligence, patience, and wisdom. It was not merely a game, but a cultural "
                        + "expression of strategy and leadership.\n\n"

                        + "Today, Gebeta remains a living heritage, passed from generation to generation. "
                        + "Globally known as Mancala, it is respected as one of the world’s oldest and most strategic games, "
                        + "played across Africa, Asia, and the Americas."
        );
        infoText.setFill(Color.web("#f5efe6"));
        infoText.setFont(Font.font("Serif", 20));
        infoText.setWrappingWidth(860);
        infoText.setLineSpacing(7);
        infoText.setTextAlignment(TextAlignment.JUSTIFY);

        Text rulesTitle = new Text("RULES OF GEBETA");
        rulesTitle.setFill(Color.web("#f4c430"));
        rulesTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 36));
        rulesTitle.setTextAlignment(TextAlignment.CENTER);

        Text rulesText = new Text(
                "GAME COMPONENTS\n\n" +

                        "1. Small Pits\n" +
                        "• The board contains twelve (12) small pits.\n" +
                        "• Each player owns six pits.\n" +
                        "• These pits hold the stones (seeds).\n\n" +

                        "2. Mancalas (Stores)\n" +
                        "• There are two large stores called Mancalas.\n" +
                        "• Each player owns one Mancala on their right-hand side.\n" +
                        "• Mancalas are used to collect captured stones.\n\n" +

                        "3. Stones\n" +
                        "• The game uses forty-eight (48) stones.\n" +
                        "• Each of the 12 small pits starts with 4 stones.\n\n" +

                        "OBJECTIVE OF THE GAME\n\n" +
                        "The objective is to collect more stones in your own Mancala than your opponent.\n\n" +

                        "BASIC GAMEPLAY RULES\n\n" +
                        "1. Player 1 starts the game.\n" +
                        "2. Choose one of your six pits that contains stones.\n" +
                        "3. Remove all stones from the selected pit.\n" +
                        "4. Distribute stones counter-clockwise, one per pit.\n" +
                        "5. Place stones into all pits except your opponent’s Mancala.\n\n" +

                        "EXTRA TURN RULE\n\n" +
                        "• If the last stone lands in your Mancala, you earn an extra turn.\n\n" +

                        "CAPTURE RULE\n\n" +
                        "• If the last stone lands in an empty pit on your side\n" +
                        "• And the opposite pit has stones\n" +
                        "• You capture all those stones into your Mancala.\n\n" +

                        "END OF GAME\n\n" +
                        "• The game ends when one player’s six pits are empty.\n" +
                        "• The opponent collects remaining stones into their Mancala.\n\n" +

                        "WINNER\n\n" +
                        "• The player with more stones in their Mancala wins.\n" +
                        "• Equal stones result in a draw."
        );
        rulesText.setFill(Color.web("#f1ebe2"));
        rulesText.setFont(Font.font("Serif", 19));
        rulesText.setWrappingWidth(860);
        rulesText.setLineSpacing(7);
        rulesText.setTextAlignment(TextAlignment.LEFT);

        Button btnBack = new Button("← Back to Home");
        btnBack.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        btnBack.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #f4c430;" +
                        "-fx-border-width: 2;" +
                        "-fx-text-fill: #f4c430;" +
                        "-fx-padding: 10 30;" +
                        "-fx-background-radius: 30;" +
                        "-fx-border-radius: 30;"
        );

        btnBack.setOnMouseEntered(e ->
                btnBack.setStyle(
                        "-fx-background-color: #f4c430;" +
                                "-fx-text-fill: #3a241d;" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 10 30;" +
                                "-fx-background-radius: 30;"
                )
        );

        btnBack.setOnMouseExited(e ->
                btnBack.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-border-color: #f4c430;" +
                                "-fx-border-width: 2;" +
                                "-fx-text-fill: #f4c430;" +
                                "-fx-padding: 10 30;" +
                                "-fx-background-radius: 30;" +
                                "-fx-border-radius: 30;"
                )
        );

        btnBack.setOnAction(e -> backToHome.run());

        root.getChildren().addAll(
                imageView,
                title,
                subtitle,
                infoText,
                rulesTitle,
                rulesText
        );

        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> scrollPane.setVvalue(scrollPane.getVmin()));

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(550), root);
        slideUp.setFromY(80);
        slideUp.setToY(0);
        slideUp.play();

        stackRoot.getChildren().addAll(scrollPane, btnBack);
        stackRoot.setAlignment(btnBack, Pos.BOTTOM_LEFT);
        StackPane.setMargin(btnBack, new Insets(0, 20, 20, 0));
    }

    public Parent getRoot() {
        return stackRoot;
    }
}
