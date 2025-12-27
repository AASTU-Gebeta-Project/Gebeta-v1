package gebeta.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;
import javafx.scene.shape.Rectangle;

public class PitView extends StackPane {
    private final int pitIndex;
    private final FlowPane stonesPane = new FlowPane();
    private final Random random = new Random();
    private final Color[] stoneColors = {Color.DARKGOLDENROD, Color.DARKGOLDENROD, Color.DARKGOLDENROD, Color.DARKGOLDENROD};

    public PitView(int pitIndex, int stones, boolean isStore) {
        this.pitIndex = pitIndex;

        if (isStore) {
            // Restore Rectangle for Stores
            Rectangle storeRect = new Rectangle(75, 180);
            storeRect.setFill(Color.web("#5d4037"));
            storeRect.setArcWidth(20);
            storeRect.setArcHeight(20);
            storeRect.setStroke(Color.WHITE);
            storeRect.setStrokeWidth(4);
            getChildren().add(storeRect);
        } else {

            Circle pitCircle = new Circle(40);
            pitCircle.setFill(Color.web("#5d4037"));
            pitCircle.setStroke(Color.WHITE);
            pitCircle.setStrokeWidth(2);
            getChildren().add(pitCircle);
        }

        stonesPane.setAlignment(Pos.CENTER);
        stonesPane.setHgap(2);
        stonesPane.setVgap(2);

        stonesPane.setMaxSize(isStore ? 65 : 75, isStore ? 165 : 75);

        getChildren().add(stonesPane);
        animateToCount(stones);
    }

    public void animateToCount(int newCount) {
        stonesPane.getChildren().clear();
        for (int i = 0; i < newCount; i++) {
            Circle stone = new Circle(6, stoneColors[random.nextInt(stoneColors.length)]);
            stone.setStroke(Color.BLACK);
            stonesPane.getChildren().add(stone);
        }
    }

    public int getPitIndex() { return pitIndex; }
}