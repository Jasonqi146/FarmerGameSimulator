package farmgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import static javafx.scene.text.Font.font;

public class ActionToolbar extends VBox {
    private RadioButton waterButton;
    public ActionToolbar() {
        this.setPadding(new Insets(5));
        this.setSpacing(2);
        //Water Button
        this.waterButton = new RadioButton("Water");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill:red");
        errorLabel.setFont(font(20));
        errorLabel.setPadding(new Insets(0, 0, 10, 0));

        this.getChildren().addAll(waterButton);
        waterButton.isSelected();

        this.setAlignment(Pos.TOP_CENTER);
    }
    public boolean checkWater() {
        return waterButton.isSelected();
    }
}
