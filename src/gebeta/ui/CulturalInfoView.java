package gebeta.ui;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CulturalInfoView {
    private final VBox root = new VBox(20);

    public CulturalInfoView(Runnable backToHome) {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        root.setStyle("-fx-background-color: #3e2723;");

        Label title = new Label("GEBETA: CULTURAL HERITAGE");
        title.setStyle("-fx-text-fill: gold; -fx-font-size: 32; -fx-font-weight: bold;");

        // The Cultural Text Content
        Text infoText = new Text(
                "Gebeta  is a cultural game played by two teams. Players pick stones " +
                        "in the holes and place one at each hole to put more in the opponent's house to win.\n\n" +
                        "Historically, the game has deep-rooted origins beginning from the birth of Christ in Ethiopia. " +
                        "It progressed during the Middle Ages, where kings and judges played it around the palace. " +
                        "Today, this historical game continues to be played and expanded throughout the country.\n\n" +
                        "Known globally as Mancala, game experts consider it among the best games in the world. " +
                        "It is widespread across Africa, India, Southeast Asia, and parts of the Americas."
        );
        infoText.setFill(Color.WHITE);
        infoText.setStyle("-fx-font-size: 18px;");
        infoText.setWrappingWidth(800);
        infoText.setTextAlignment(TextAlignment.CENTER);

        // ScrollPane in case text is too long for small screens
        ScrollPane scrollPane = new ScrollPane(infoText);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(300);

        // Back to Home Button
        Button btnBack = new Button("BACK TO HOME");
        btnBack.setPrefWidth(200);
        btnBack.setStyle("-fx-font-size: 18px; -fx-background-color: rgba(0,0,0,0.7); -fx-text-fill: white; -fx-border-color: gold;");
        btnBack.setOnAction(e -> backToHome.run());

        root.getChildren().addAll(title, scrollPane, btnBack);
    }

    public Parent getRoot() { return root; }
}