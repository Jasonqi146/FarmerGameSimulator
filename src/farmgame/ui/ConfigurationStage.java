package farmgame.ui;

import farmgame.FarmingSimulator;
import farmgame.model.DifficultyLevel;
import farmgame.model.PlantType;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.scene.text.Font.font;

public class ConfigurationStage extends Stage {
    public ConfigurationStage(FarmingSimulator game) {
        VBox root = new VBox();
        root.setPadding(new Insets(5, 5, 0, 5));

        TextField fieldName = new TextField();
        fieldName.setFont(font(20));
        fieldName.setPromptText("Enter your name");
        fieldName.setMaxWidth(272);

        Label errorLabel = new Label("Invalid User Name");
        String errorStyle = "-fx-text-fill:red";
        String successStyle = "-fx-text-fill:green";
        errorLabel.setStyle(errorStyle);
        errorLabel.setFont(font(20));
        errorLabel.setPadding(new Insets(0, 0, 10, 0));

        Button btnFinish = new Button("Finish configuration");

        fieldName.setOnKeyReleased(event -> {
            if (isValidName(fieldName.getText())) {
                errorLabel.setText("Valid");
                errorLabel.setStyle(successStyle);
                btnFinish.setDisable(false);
            } else {
                errorLabel.setText("Invalid User Name");
                errorLabel.setStyle(errorStyle);
                btnFinish.setDisable(true);
            }
        });

        VBox dropdowns = new VBox();
        ComboBox<String> comboDifficulty = new ComboBox<>(FXCollections.observableArrayList(
                "Easy", "Medium", "Hard")
        );
        comboDifficulty.setStyle("-fx-font-size: 20px;");
        comboDifficulty.getSelectionModel().select(0);

        ComboBox<String> comboStartingSeed = new ComboBox<>(FXCollections.observableArrayList(
                "Tomato", "Corn", "Wheat")
        );
        comboStartingSeed.setStyle("-fx-font-size: 20px;");
        comboStartingSeed.getSelectionModel().select(0);
        dropdowns.getChildren().addAll(comboDifficulty, comboStartingSeed);
        dropdowns.setPadding(new Insets(0, 0, 10, 0));

        btnFinish.setFont(font(20));
        btnFinish.setOnAction(event -> {
            DifficultyLevel diff = DifficultyLevel.fromString(comboDifficulty.getValue());
            PlantType plantType = PlantType.fromString(comboStartingSeed.getValue());
            game.finishConfiguration(diff, plantType);
        });
        btnFinish.setDisable(true);

        root.getChildren().addAll(fieldName, errorLabel, dropdowns, btnFinish);
        Scene scene = new Scene(root);
        this.setScene(scene);
    }

    public boolean isValidName(String s) {
        String regex = "[A-Za-z\\s]+";
        return s.matches(regex) && !s.matches("[\\s]+");
    }
}
