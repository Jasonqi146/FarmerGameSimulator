package farmgame.ui;

import farmgame.FarmingSimulator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.scene.text.Font.font;

public class WelcomeStage extends Stage {
    public WelcomeStage(FarmingSimulator game) {
        VBox root = new VBox();
        root.setPadding(new Insets(5, 5, 0, 5));

        Label lblWelcome = new Label("Welcome to Team 15's\nFarming Simulator!");
        lblWelcome.setFont(font(30));
        lblWelcome.setPadding(new Insets(0, 0, 10, 0));

        Button btnStart = new Button("Start");
        btnStart.setFont(font(20));
        btnStart.setOnAction(event -> game.startGame());

        root.getChildren().addAll(lblWelcome, btnStart);
        Scene scene = new Scene(root);
        this.setScene(scene);
    }
}
