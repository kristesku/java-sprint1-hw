public class Converter {

  private static final int centimetersPerStep = 75;
  private static final int caloriesPerStep = 50;

  public static double stepsToKilometers(int steps) {
    return (double) steps * centimetersPerStep / 100000;
  }

  public static int stepsToKilocalories(int steps) {
    return steps * caloriesPerStep / 1000;
  }
}
