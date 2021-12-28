package farmgame.ui;

import farmgame.model.Item;

public interface ItemBuyListener {
    String buyItem(Item item); // returns an error message to be displayed, if necessary
}
