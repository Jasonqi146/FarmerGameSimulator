package farmgame.model;

import farmgame.ui.ItemBuyListener;
import farmgame.ui.MarketToolbar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Market implements ItemBuyListener {
    public static final int NUM_OF_MARKET_ITEMS = 10;

    private Player player;
    private MarketToolbar toolbar;
    private ObservableList<Item> itemsList;

    public Market(Player player) {
        this.player = player;
        itemsList = FXCollections.observableArrayList(
                new Seed(PlantType.TOMATO),
                new Seed(PlantType.TOMATO),
                new Seed(PlantType.TOMATO),
                new Seed(PlantType.CORN),
                new Seed(PlantType.CORN),
                new Seed(PlantType.CORN),
                new Seed(PlantType.WHEAT),
                new Seed(PlantType.WHEAT),
                new Seed(PlantType.WHEAT),
                new Item("Money Tree Seeds", 10000)
        );
    }

    public void setToolbar(MarketToolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setItemsList(itemsList);
        toolbar.setItemBuyListener(this);
    }

    @Override
    public String buyItem(Item item) {
        // plus or minus 10 coins according to detailed algorithm predicting market conditions
        final int variance = (int) (Math.random() * 21) - 10;
        final int buyPrice = item.getPrice() + variance;
        if (buyPrice > player.getMoney()) {
            return "Not Enough Money";
        } else if (player.getInventory().getCapacityRemaining() <= 0) {
            return "No Inventory Capacity";
        } else {
            itemsList.remove(item);
            player.getInventory().giveItem(item);
            player.addMoney(-buyPrice);
            return null;
        }
    }

    public void giveItem(Item item) {
        if (itemsList.size() < NUM_OF_MARKET_ITEMS) {
            itemsList.add(item);
        }
        // else: If the market's too big, then tough luck.
        // The sold item probably got lost in the bureaucracy somewhere.
    }

}
