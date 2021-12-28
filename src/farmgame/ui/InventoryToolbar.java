package farmgame.ui;

import farmgame.model.Item;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.layout.VBox;

import static farmgame.model.Inventory.INVENTORY_CAPACITY;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontWeight.BOLD;

public class InventoryToolbar extends VBox {
    private static final int LIST_CELL_HEIGHT = 24;

    private ListView<Item> listItems;

    private ItemSellListener itemSellListener;

    public InventoryToolbar() {
        this.setPadding(new Insets(5));
        this.setSpacing(2);

        Label labelInventory = new Label("Inventory");
        labelInventory.setFont(font(null, BOLD, 14));

        // Sell button
        Button sellButton = new Button("Sell");
        sellButton.setDisable(true);

        sellButton.setOnAction(actionEvent -> {
            if (this.itemSellListener != null) {
                Item item = listItems.getSelectionModel().getSelectedItem();
                itemSellListener.sellItem(item);
            }
        });

        listItems = new ListView<>();
        // Add extra 0.1 cells of padding
        listItems.setMaxHeight(LIST_CELL_HEIGHT * (INVENTORY_CAPACITY + 0.1));

        listItems.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldVal, newVal) -> sellButton.setDisable(newVal == null)
        );

        this.getChildren().addAll(labelInventory, listItems, sellButton);
    }

    public void setItemSellListener(ItemSellListener itemSellListener) {
        this.itemSellListener = itemSellListener;
    }

    public void setItemsList(ObservableList<Item> items) {
        listItems.setItems(items);
    }

    public Item getSelected() {
        return listItems.getSelectionModel().getSelectedItem();
    }
}
