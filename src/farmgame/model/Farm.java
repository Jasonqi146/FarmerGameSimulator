package farmgame.model;

import farmgame.ui.ActionToolbar;
import farmgame.ui.PlotClickMode;
import farmgame.ui.FarmCanvas;
import farmgame.ui.PlotClickedListener;
import javafx.scene.control.Alert;

import static farmgame.model.PlantGrowth.DEAD;
import static farmgame.model.PlantGrowth.MATURE;
import static java.lang.String.format;

public class Farm implements PlotClickedListener {
    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 5;

    private static final double EVENT_PROBABILITY = 0.25;
    private static final double LOCUST_EAT_PROBABILITY = 0.50;

    private FarmCanvas canvas;
    private Plot[][] plots;
    private Player player;

    public Farm(Player player) {
        this.player = player;
        plots = new Plot[NUM_ROWS][NUM_COLS];
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int c = 0; c < NUM_COLS; c++) {
                PlantType plantType = null;
                PlantGrowth plantGrowth = null;
                if (Math.random() < 0.5) {
                    plantType = PlantType.values()[(int) (Math.random() * 3)];
                    plantGrowth = PlantGrowth.values()[(int) (Math.random() * 4)];
                }
                plots[r][c] = new Plot(r, c, plantType, plantGrowth);
            }
        }
    }

    public void setUiElements(FarmCanvas farmCanvas, ActionToolbar actionToolbar) {
        this.canvas = farmCanvas;
        for (Plot[] row : plots) {
            for (Plot plot : row) {
                canvas.drawPlot(plot);
            }
        }
        canvas.setPlotClickedListener(this, actionToolbar);
    }

    @Override
    public void plotClicked(int row, int col, PlotClickMode clickMode) {
        Plot plot;
        try {
            plot = plots[row][col];
        } catch (ArrayIndexOutOfBoundsException ignored) {
            throw new IllegalArgumentException(format("Plot (%d, %d) does not exist", row, col));
        }
        if (clickMode == PlotClickMode.WATER) {
            plot.addWater();
            canvas.drawPlot(plot);
        } else {
            if (plot.hasPlant()) {
                if (plot.getPlantGrowth() == MATURE) {
                    Inventory inventory = player.getInventory();
                    if (inventory.getCapacityRemaining() > 0) {
                        inventory.giveItem(plot.takePlant());
                        canvas.drawPlot(plot);
                    }
                }
                if (plot.getPlantGrowth() == DEAD) {
                    plot.takePlant();
                    canvas.drawPlot(plot);
                }
            } else {
                Inventory inventory = player.getInventory();
                Item selected = inventory.getSelected();
                if (selected instanceof Seed) {
                    inventory.takeItem(selected);
                    plot.plantSeed((Seed) selected);
                    canvas.drawPlot(plot);
                }
            }
        }
    }

    public void nextDay() {
        if (Math.random() < EVENT_PROBABILITY) {
            int whichEvent = (int) (Math.random() * 3);
            String eventTitle;
            String eventDesc;
            if (whichEvent == 0) {
                eventTitle = "Flood!";
                eventDesc =
                        "Your farm has been flooded and increased\nthe water level of all plots";
                for (Plot[] row : plots) {
                    for (Plot plot : row) {
                        plot.addWater();
                        plot.nextDay();
                        canvas.drawPlot(plot);
                    }
                }

            } else if (whichEvent == 1) {
                eventTitle = "Drought!";
                eventDesc = "A drought has occurred and decreased\nthe water level of all plots";
                for (Plot[] row : plots) {
                    for (Plot plot : row) {
                        plot.removeWater();
                        plot.removeWater();
                        plot.nextDay();
                        canvas.drawPlot(plot);
                    }
                }
            } else {
                eventTitle = "Locusts!";
                eventDesc = "A plague of locusts in your\nfarm has eaten your crops";
                for (Plot[] row : plots) {
                    for (Plot plot : row) {
                        if (plot.hasPlant()) {
                            if (Math.random() < LOCUST_EAT_PROBABILITY) {
                                plot.setPlantGrowth(PlantGrowth.DEAD);
                                canvas.drawPlot(plot);
                            }
                        }
                    }
                }

            }
            Alert eventAlert = new Alert(Alert.AlertType.ERROR);
            eventAlert.setTitle(eventTitle);
            eventAlert.setHeaderText(eventTitle);
            eventAlert.setContentText(eventDesc);
            eventAlert.showAndWait();
        } else {
            // No event
            for (Plot[] row : plots) {
                for (Plot plot : row) {
                    plot.removeWater();
                    plot.nextDay();
                    canvas.drawPlot(plot);
                }
            }
        }
    }
}
