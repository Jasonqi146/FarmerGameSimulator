package farmgame.model;

public class Item {
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    // Note that toString() is used to populate the list in the Inventory and Market
    @Override
    public String toString() {
        return String.format("%s ($%d)", name, price);
    }
}
