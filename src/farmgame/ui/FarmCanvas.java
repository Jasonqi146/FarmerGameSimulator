package farmgame.ui;

import farmgame.model.Plot;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static farmgame.model.Farm.NUM_COLS;
import static farmgame.model.Farm.NUM_ROWS;

public class FarmCanvas extends Canvas {
    private static final int PLOT_SIZE = 40;
    private static final int PLOT_SPACING = 10;

    private static final int WIDTH = (NUM_COLS * (PLOT_SIZE + PLOT_SPACING)) + PLOT_SPACING;
    private static final int HEIGHT = (NUM_ROWS * (PLOT_SIZE + PLOT_SPACING)) + PLOT_SPACING;

    private GraphicsContext gc;
    private static final Font FONT = Font.font(32); // makes text big enough to be seen

    // Array index = water level
    private static final Color[] PLOT_COLORS = {
            Color.rgb(80, 48, 13),
            Color.rgb(153, 204, 255),
            Color.rgb(51, 153, 255),
            Color.rgb(0, 102, 204)
    };
    private static final Color BG_COLOR = Color.rgb(0, 100, 0);
    private static final Color SEED_COLOR = Color.rgb(0, 100, 0);


    private PlotClickedListener plotClickedListener;

    public FarmCanvas() {
        super(WIDTH, HEIGHT);
        this.gc = getGraphicsContext2D();
        gc.setFont(FONT);
        gc.setFill(BG_COLOR);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void setPlotClickedListener(PlotClickedListener plotClickedListener,
                                       ActionToolbar actionToolbar) {
        this.plotClickedListener = plotClickedListener;
        this.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            int row = -1;
            int col = -1;
            while (x > 0) {
                if (x < PLOT_SPACING) {
                    return;
                }
                col++;
                x -= PLOT_SIZE + PLOT_SPACING;
            }
            while (y > 0) {
                if (y < PLOT_SPACING) {
                    return;
                }
                row++;
                y -= PLOT_SIZE + PLOT_SPACING;
            }

            PlotClickMode clickMode = PlotClickMode.NORMAL;
            if (actionToolbar.checkWater()) {
                clickMode = PlotClickMode.WATER;
            }
            plotClickedListener.plotClicked(row, col, clickMode);
        });
    }

    public void drawPlot(Plot plot) {
        final int xCoord = (plot.getCol() * (PLOT_SIZE + PLOT_SPACING)) + PLOT_SPACING;
        final int yCoord = (plot.getRow() * (PLOT_SIZE + PLOT_SPACING)) + PLOT_SPACING;
        final int middleOfPlotX = xCoord + PLOT_SIZE / 2;
        final int middleOfPlotY = yCoord + PLOT_SIZE / 2;

        gc.setFill(PLOT_COLORS[plot.getWaterLevel()]);
        gc.fillRect(xCoord, yCoord, PLOT_SIZE, PLOT_SIZE);

        if (plot.hasPlant()) {
            switch (plot.getPlantGrowth()) {
            case SEED:
                gc.setFill(SEED_COLOR);
                gc.fillRect(middleOfPlotX - 3, middleOfPlotY - 3, 6, 6);
                gc.setFill(plot.getPlantType().getColor());
                gc.fillRect(middleOfPlotX, middleOfPlotY, 3, 3);
                break;
            case IMMATURE_1:
            case IMMATURE_2:
                gc.setFill(plot.getPlantType().getColor());
                gc.fillRect(middleOfPlotX - 5, middleOfPlotY - 5, 10, 10);
                gc.setFill(SEED_COLOR);
                gc.fillRect(middleOfPlotX - 3, middleOfPlotY - 3, 6, 6);
                break;
            case MATURE:
                gc.setFill(plot.getPlantType().getColor());
                gc.fillRect(middleOfPlotX - 8, middleOfPlotY - 8, 16, 16);
                gc.setFill(SEED_COLOR);
                gc.fillRect(middleOfPlotX - 3, middleOfPlotY - 3, 6, 6);
                break;
            case DEAD:
                gc.setFill(Color.rgb(0, 0, 0));
                gc.fillRect(middleOfPlotX - 8, middleOfPlotY - 8, 16, 16);
                gc.setStroke(Color.rgb(255, 255, 0));
                gc.strokeLine(middleOfPlotX - 8, middleOfPlotY - 8,
                        middleOfPlotX - 8 + 16, middleOfPlotY - 8 + 16);
                gc.strokeLine(middleOfPlotX - 8 + 16, middleOfPlotY - 8,
                        middleOfPlotX - 8, middleOfPlotY - 8 + 16);
                break;
            default:
                throw new IllegalStateException(
                    "No renderer defined for seed type " + plot.getPlantGrowth()
                );
            }
        }
    }
}
