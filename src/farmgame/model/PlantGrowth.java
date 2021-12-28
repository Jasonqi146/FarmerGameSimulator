package farmgame.model;

public enum PlantGrowth {
    SEED, IMMATURE_1, IMMATURE_2, MATURE, DEAD;

    public PlantGrowth next() {
        if (this == DEAD || this == MATURE) {
            return this;
        } else {
            return values()[ordinal() + 1];
        }
    }
}
