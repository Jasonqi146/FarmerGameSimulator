package farmgame.model;
import farmgame.ui.NextDayListener;
import farmgame.ui.PlayerToolbar;
import javafx.application.Platform;

public class Player implements NextDayListener {
    private int day;
    private int money;

    // Related UI elements
    private PlayerToolbar toolbar;

    private DifficultyLevel difficulty;
    private Inventory inventory;
    private Market market;
    private Farm farm;

    public Player(DifficultyLevel difficulty, PlantType startingPlantType) {
        this.difficulty = difficulty;
        this.inventory = new Inventory(this, new Seed(startingPlantType));
        this.market = new Market(this);
        this.farm = new Farm(this);

        this.day = 1;
        this.money = difficulty.getStartingMoney();
    }

    public void setToolbar(PlayerToolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setMoney(money);
        toolbar.setDay(day);
        toolbar.setNextDayListener(this);
    }

    //change the money due to selling from inventory
    public void addMoney(int moneyToAdd) {
        money = (money + moneyToAdd);
        toolbar.setMoney(money);
    }

    @Override
    public void nextDay() {
        day++;
        farm.nextDay();
        if (toolbar != null) {
            Platform.runLater(() -> toolbar.setDay(day));
        }
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public int getMoney() {
        return money;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Market getMarket() {
        return market;
    }

    public Farm getFarm() {
        return farm;
    }
}
