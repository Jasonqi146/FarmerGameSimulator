package farmgame.ui;

import farmgame.model.Player;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FarmStage extends Stage {

    private PlayerToolbar playerToolbar;
    private InventoryToolbar inventoryToolbar;
    private MarketToolbar marketToolbar;
    private FarmCanvas farmCanvas;
    private ActionToolbar actionToolbar;
    public FarmStage(Player player)  {
        // The main layout of our UI
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(0, 5, 0, 5));

        // Add in the HBox toolbar at the top of the screen and VBox toolbars at the sides
        playerToolbar = new PlayerToolbar();
        inventoryToolbar = new InventoryToolbar();
        marketToolbar = new MarketToolbar();
        farmCanvas = new FarmCanvas();
        actionToolbar = new ActionToolbar();
        // Designate areas of BorderPane to different panes
        border.setTop(playerToolbar);
        border.setCenter(farmCanvas);
        border.setLeft(inventoryToolbar);
        border.setRight(marketToolbar);
        border.setBottom(actionToolbar);
        Scene scene = new Scene(border);
        this.setScene(scene);
    }

    public PlayerToolbar getPlayerToolbar() {
        return playerToolbar;
    }

    public InventoryToolbar getInventoryToolbar() {
        return inventoryToolbar;
    }

    public MarketToolbar getMarketToolbar() {
        return marketToolbar;
    }

    public FarmCanvas getFarmCanvas() {
        return farmCanvas;
    }

    public ActionToolbar getActionToolbar() {
        return actionToolbar;
    }
}