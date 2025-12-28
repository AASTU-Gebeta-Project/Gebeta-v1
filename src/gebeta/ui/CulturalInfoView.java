package gebeta.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class CulturalInfoView {

    private final VBox root = new VBox(30);
    private final ScrollPane scrollPane = new ScrollPane();

    public CulturalInfoView(Runnable backToHome) {

        /* ================= ROOT CONTENT ================= */
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2b1b17, #3e2723);"
        );

        /* ================= IMAGE ================= */
        ImageView imageView = new ImageView(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(
                                "/gebeta/resources/5cc9eb49a13f457cba8680a35c099d61_408_408.jfif"
                        )
                ))
        );
        imageView.setFitWidth(320);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        /* ================= TITLE ================= */
        Text title = new Text("GEBETA");
        title.setFill(Color.web("#f4c430"));
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 42));

        Text subtitle = new Text("Cultural Heritage of Ethiopia");
        subtitle.setFill(Color.web("#e0c097"));
        subtitle.setFont(Font.font("Georgia", FontPosture.ITALIC, 20));

        /* ================= BODY TEXT ================= */
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

        infoText.setFill(Color.web("#f5f0e6"));
        infoText.setFont(Font.font("Serif", 19));
        infoText.setWrappingWidth(850);
        infoText.setTextAlignment(TextAlignment.JUSTIFY);
        infoText.setLineSpacing(6);

        Text rulesTitle = new Text("RULES OF GEBETA");
        rulesTitle.setFill(Color.web("#f4c430"));
        rulesTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 34));
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

        rulesText.setFill(Color.web("#f5f0e6"));
        rulesText.setFont(Font.font("Serif", 18));
        rulesText.setWrappingWidth(850);
        rulesText.setLineSpacing(6);
        rulesText.setTextAlignment(TextAlignment.LEFT);


        /* ================= BUTTON ================= */
        Button btnBack = new Button("← Back to Home");
        btnBack.setFont(Font.font("Arial", 16));
        btnBack.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #f4c430;"
        );
        btnBack.setOnMouseEntered(e ->
                btnBack.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-text-fill: white;"
                )
        );
        btnBack.setOnMouseExited(e ->
                btnBack.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-text-fill: #f4c430;"
                )
        );
        btnBack.setOnAction(e -> backToHome.run());

        /* ================= ADD CONTENT ================= */
        root.getChildren().addAll(
                imageView,
                title,
                subtitle,
                infoText,
                rulesTitle,
                rulesText,
                btnBack
        );

        /* ================= SCROLLPANE (WHOLE SCENE) ================= */
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true); // allows mouse drag scrolling
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background: transparent;"
        );
    }

    public Parent getRoot() {
        return scrollPane;
    }
}
