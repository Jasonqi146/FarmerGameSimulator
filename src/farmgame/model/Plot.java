package farmgame.model;

import static farmgame.model.PlantGrowth.MATURE;
import static farmgame.model.PlantGrowth.SEED;

public class Plot {
    private int row;
    private int col;

    private static final int MAXWATER = 3;
    private static final int MINWATER = 0;

    private PlantType plantType;
    private PlantGrowth plantGrowth;
    private int waterLevel;

    public Plot(int row, int col) {
        this(row, col, null, null);
    }

    public Plot(int row, int col, PlantType plantType, PlantGrowth plantGrowth) {
        this.row = row;
        this.col = col;
        this.plantType = plantType;
        this.plantGrowth = plantGrowth;
        this.waterLevel = 0;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public boolean hasPlant() {
        return plantType != null;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public PlantGrowth getPlantGrowth() {
        return plantGrowth;
    }

    public Item takePlant() {
        Item plant = null;
        if (plantGrowth == MATURE) {
            plant = new Item(plantType.toString(), plantType.getValue());
        }
        plantGrowth = null;
        plantType = null;
        return plant;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void addWater() {
        this.waterLevel++;
        if (waterLevel > MAXWATER) {
            waterLevel = MAXWATER;
            if (this.hasPlant()) {
                plantGrowth = PlantGrowth.DEAD;
            }
        }
    }

    public void removeWater() {
        this.waterLevel--;
        if (waterLevel < MINWATER) {
            waterLevel = MINWATER;
            if (this.hasPlant()) {
                plantGrowth = PlantGrowth.DEAD;
            }
        }
    }

    public void nextDay() {
        if (this.hasPlant()) {
            plantGrowth = plantGrowth.next();
        }
    }

    public void setPlantGrowth(PlantGrowth plantGrowth) {
        this.plantGrowth = plantGrowth;
    }

    public void plantSeed(Seed seed) {
        if (hasPlant()) {
            throw new IllegalStateException("Cannot plant in occupied plot");
        }
        this.plantType = seed.getSeedType();
        this.plantGrowth = SEED;
    }
}
