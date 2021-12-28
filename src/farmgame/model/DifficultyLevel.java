package farmgame.model;

public enum DifficultyLevel {
    EASY(1000, 1.0f), MEDIUM(750, 0.75f), HARD(500, 0.5f);

    private int startingMoney;
    private float saleMultiplier;

    DifficultyLevel(int startingMoney, float saleMultiplier) {
        this.startingMoney = startingMoney;
        this.saleMultiplier = saleMultiplier;
    }

    public int getStartingMoney() {
        return startingMoney;
    }
    public float getSaleMultiplier() {
        return saleMultiplier;
    }

    public static DifficultyLevel fromString(String s) {
        switch (s) {
        case "Easy":
            return EASY;
        case "Medium":
            return MEDIUM;
        case "Hard":
            return HARD;
        default:
            throw new IllegalArgumentException(
                    "No difficulty level matching string: \"" + s + "\""
            );
        }
    }
}
