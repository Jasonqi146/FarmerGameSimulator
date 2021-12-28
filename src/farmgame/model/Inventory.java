package farmgame.model;

import farmgame.ui.InventoryToolbar;
import farmgame.ui.ItemSellListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory implements ItemSellListener {
    public static final int INVENTORY_CAPACITY = 10;

    private InventoryToolbar toolbar;
    private ObservableList<Item> itemsList;
    private Player player;

    public Inventory(Player player, Item... items) {
        this.player = player;
        this.itemsList = FXCollections.observableArrayList(items);
    }

    public void setToolbar(InventoryToolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setItemsList(itemsList);
        toolbar.setItemSellListener(this);
    }

    public int getCapacityRemaining() {
        return INVENTORY_CAPACITY - itemsList.size();
    }

    public void giveItem(Item item) {
        if (getCapacityRemaining() <= 0) {
            throw new IllegalArgumentException("Cannot give item to full inventory");
        }
        itemsList.add(item);
    }

    public void takeItem(Item item) {
        if (!itemsList.remove(item)) {
            throw new IllegalStateException("Do not have item " + item + " to take from inventory");
        }
    }


    @Override
    public void sellItem(Item item) {
        final int saleAmount = (int) (item.getPrice() * player.getDifficulty().getSaleMultiplier());
        // plus or minus 10 coins according to detailed algorithm predicting market conditions
        final int variance = (int) (Math.random() * 21) - 10;

        this.player.addMoney(saleAmount + variance);
        this.itemsList.remove(item);
        this.player.getMarket().giveItem(item);
    }

    public Item getSelected() {
        return toolbar.getSelected();
    }
}
