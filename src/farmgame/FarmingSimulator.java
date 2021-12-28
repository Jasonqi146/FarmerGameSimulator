package farmgame;

import farmgame.model.*;
import farmgame.ui.ConfigurationStage;
import farmgame.ui.FarmStage;
import farmgame.ui.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class FarmingSimulator extends Application {
    // View/UI classes
    private WelcomeStage welcomeStage;
    private ConfigurationStage configurationStage;
    private FarmStage farmStage;
    // Model classes
    private Player player;

    @Override
    public void start(Stage stage) {
        welcomeStage = new WelcomeStage(this);
        configurationStage = new ConfigurationStage(this);
        welcomeStage.show();
    }


    public void startGame() {
        // This method doesn't make sense if called in any other state
        if (welcomeStage.isShowing()) {
            welcomeStage.close();
            configurationStage.show();
        } else {
            throw new IllegalStateException(
                    "Cannot call startGame() except when welcome stage is open"
            );
        }
    }

    public void finishConfiguration(DifficultyLevel difficultyLevel, PlantType startingPlantType) {
        // This method doesn't make sense if called in any other state
        if (configurationStage.isShowing()) {
            configurationStage.close();

            // Initialize model classes
            this.player = new Player(difficultyLevel, startingPlantType);
            Inventory inventory = player.getInventory();
            Market market = player.getMarket();
            Farm farm = player.getFarm();

            // Initialize the farm stage here so that we can get player and its methods
            farmStage = new FarmStage(player);
            farmStage.show();

            // Bind model classes to appropriate views
            player.setToolbar(farmStage.getPlayerToolbar());
            inventory.setToolbar(farmStage.getInventoryToolbar());
            market.setToolbar(farmStage.getMarketToolbar());
            farm.setUiElements(farmStage.getFarmCanvas(), farmStage.getActionToolbar());
        } else {
            throw new IllegalStateException(
                    "Cannot call finishConfiguration() except when configuration stage is open"
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}