import java.util.stream.IntStream;

/**
 * Google foobar challenge 3 - Gearing Up for Destruction. Configure the LAMBCHOP doomsday device's
 * axial orientation gears fitting the complicated layout of beams and pipes.
 */
public class Solution {

  /**
   * Calculates radius of first gear with 1/2x rpm of the last gear in a given list of pegs.
   *
   * <p>Given a list of peg positions on a support beam, the ratio of all gears on the beam are
   * calculated so that the last gear rotates at twice the rate (in revolutions per minute) of the
   * first gear.
   *
   * <p>The utilized formula for that is derived from the following equations:
   * 1. radius_i + radius_j = position_j - position_i with i<j
   * 2. radius_first = 2 * radius_last
   *
   * @param pegs Ascending Integer list with 2-20 positions of pegs to fix gears on.
   * @return Returns [numerator, denominator] array representing the first gear's radius in its
   * simplest form. In case a configuration is not feasible [-1, -1] will be returned.
   */
  public static int[] solution(int[] pegs) {
    // Check that input restrictions for pegs are not violated.
    if (pegs.length < 2 || pegs.length > 20 || !isSorted(pegs) || IntStream.of(pegs)
        .anyMatch(value -> value < 1 || value > 10000)) {
      throw new IllegalArgumentException(
          "The given pegs array is not suitable for the LAMBCHOP doomsday device!"
              + " The list of pegs must be sorted in ascending order and contain at least 2 and"
              + " no more than 20 distinct positive integers, all between 1 and 10000 inclusive."
      );
    } else {
      // Initiate first gear radius with the negative distance between 0 and the first peg.
      int radius = -pegs[0];

      // Incorporate all distances between 1 and n-1 into the radius and change the +/- sign
      // depending on even and uneven positions in the peg order.
      for (int i = 1; i < pegs.length - 1; i++) {
        if (i % 2 == 0) {
          radius -= 2 * pegs[i];
        } else {
          radius += 2 * pegs[i];
        }
      }

      // Convert intermediate radius result into fraction to incorporate fraction factor.
      IntegerFraction radiusFirstGear;
      if (pegs.length % 2 == 1) {
        radius -= pegs[pegs.length - 1];
        radiusFirstGear = new IntegerFraction(2 * radius, 1);
      } else {
        radius += pegs[pegs.length - 1];
        radius *= 2;
        // Check if there is a smaller denominator.
        if (radius % 3 == 0) {
          radiusFirstGear = new IntegerFraction(radius / 3, 1);
        } else {
          radiusFirstGear = new IntegerFraction(radius, 3);
        }
      }

      // Check the feasibility of all radii by forward calculating beginning at the first peg.
      IntegerFraction radiusGear = radiusFirstGear;
      for (int i = 0; i < (pegs.length - 1); i++) {
        // Each gear radius should be at least greater than or equal to 1.
        if (radiusGear.getNumerator() / radiusGear.getDenominator() < 1) {
          return new int[]{-1, -1};
        }

        // Calculate next radius based on radius_i+1 = position_i+1 - position_i - radius_i.
        radiusGear = IntegerFraction.sub(new IntegerFraction(pegs[i + 1] - pegs[i], 1), radiusGear);
      }

      return new int[]{radiusFirstGear.getNumerator(), radiusFirstGear.getDenominator()};
    }
  }


  /**
   * Checks if an Integer array is sorted in ascending order.
   *
   * @param array Integer array that is checked for its order.
   * @return Returns true if the array is sorted in ascending order and false if not.
   */
  private static boolean isSorted(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] > array[i + 1]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Representation of integer fractions.
   */
  private static class IntegerFraction {

    private final int numerator;
    private final int denominator;

    /**
     * Integer fractions that are split into numerator and denominator.
     *
     * @param numerator Integer value for the mathematical numerator.
     * @param denominator Integer value for the mathematical denominator.
     */
    public IntegerFraction(int numerator, int denominator) {
      this.numerator = numerator;
      this.denominator = denominator;
    }

    /**
     * Subtraction of two given fractions based on difference = minuend - subtrahend.
     *
     * @param minuend IntegerFraction value that is subtracted from.
     * @param subtrahend IntegerFraction value that is subtracted.
     * @return Returns a new IntegerFraction that resembles the difference.
     */
    public static IntegerFraction sub(IntegerFraction minuend, IntegerFraction subtrahend) {
      return new IntegerFraction(
          minuend.getNumerator() * subtrahend.getDenominator()
              - subtrahend.getNumerator() * minuend.getDenominator(),
          minuend.getDenominator() * subtrahend.getDenominator()
      );
    }

    /**
     * Returns numerator of an Integer fraction.
     */
    public int getNumerator() {
      return numerator;
    }

    /**
     * Returns denominator of an Integer fraction.
     */
    public int getDenominator() {
      return denominator;
    }
  }
}
