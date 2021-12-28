package farmgame.model;

public class Seed extends Item {
    private PlantType seedType;
    public Seed(PlantType seedType) {
        super(seedType.toString() + " Seeds", 10);
        this.seedType = seedType;
    }

    public PlantType getSeedType() {
        return seedType;
    }
}
