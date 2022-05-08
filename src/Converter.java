public class Converter {
    private final int centimetersPerStep;
    private final int caloriesPerStep;

    public Converter() {
        this.centimetersPerStep = 75;
        this.caloriesPerStep = 50;
    }

    public double stepsToKilometers(int steps) {
        return (double) steps * centimetersPerStep / 100 / 1000;
    }

    public int stepsToKilocalories(int steps) {
        return steps * caloriesPerStep / 1000;
    }
}
