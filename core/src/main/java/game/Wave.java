package game;

public class Wave {
    private int amount;
    private String type;  // "normal", "boss", dll
    private float delay;

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
    public float getDelay() {
        return delay;
    }

    public Wave(String type, int amount, float delay) {
        this.type = type;
        this.amount = amount;
        this.delay = delay;
    }
}
