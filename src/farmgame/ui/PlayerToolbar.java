package farmgame.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import static javafx.scene.text.Font.font;

public class PlayerToolbar extends HBox {
    private Label labelMoney;
    private Label labelDay;
    private Button nextDayButton;

    private NextDayListener nextDayListener;

    public PlayerToolbar() {
        // Initialize HBox constants for toolbar
        this.setPadding(new Insets(5));
        this.setSpacing(17);

        // Make descriptive labels for money and day and button for next day
        labelMoney = new Label();
        labelMoney.setFont(font(14));
        labelDay = new Label();
        labelDay.setFont(font(14));
        nextDayButton = new Button("Next Day");

        nextDayButton.setOnAction(event -> {
            if (nextDayListener != null) {
                nextDayListener.nextDay();
            }
        });

        // Add labels to toolbar
        this.getChildren().addAll(labelMoney, labelDay, nextDayButton);

        setAlignment(Pos.TOP_CENTER);
    }

    public void setMoney(int money) {
        labelMoney.setText("Money: $" + money);
    }

    public void setDay(int day) {
        labelDay.setText("Day " + day);
    }

    public void setNextDayListener(NextDayListener nextDayListener) {
        this.nextDayListener = nextDayListener;
    }
}
