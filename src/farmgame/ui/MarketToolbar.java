package farmgame.ui;

import farmgame.model.Item;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import static farmgame.model.Market.NUM_OF_MARKET_ITEMS;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontWeight.BOLD;

public class MarketToolbar extends VBox {
    private static final int LIST_CELL_HEIGHT = 24;

    private ListView<Item> listItems;

    private ItemBuyListener itemBuyListener;

    public MarketToolbar() {
        this.setPadding(new Insets(5));
        this.setSpacing(2);

        Label labelMarket = new Label("Market");
        labelMarket.setFont(font(null, BOLD, 14));

        // Buy button
        Button buyButton = new Button("Buy");
        buyButton.setDisable(true);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill:red");
        errorLabel.setFont(font(20));
        errorLabel.setPadding(new Insets(0, 0, 10, 0));

        buyButton.setOnAction(actionEvent -> {
            if (this.itemBuyListener != null) {
                Item item = listItems.getSelectionModel().getSelectedItem();
                String err = itemBuyListener.buyItem(item);
                errorLabel.setText(err);
            }
        });


        listItems = new ListView<>();
        // Add extra 0.1 cells of padding
        listItems.setMaxHeight(LIST_CELL_HEIGHT * (NUM_OF_MARKET_ITEMS + 0.1));
        listItems.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldVal, newVal) -> buyButton.setDisable(newVal == null)
        );

        this.getChildren().addAll(labelMarket, listItems, buyButton, errorLabel);
    }

    public void setItemBuyListener(ItemBuyListener itemBuyListener) {
        this.itemBuyListener = itemBuyListener;
    }

    public void setItemsList(ObservableList<Item> items) {
        listItems.setItems(items);
    }
}
