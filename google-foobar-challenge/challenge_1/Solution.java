/**
 * Google foobar challenge 1 - Braille translation. Help visually-impaired minions to navigate on
 * Commander Lambda's spaceship.
 */
public class Solution {

  /**
   * Binary Braille translation of an input string.
   *
   * <p>Each character of the input string gets translated into 6 dots of the Braille system. The bumps
   * and absence of bumps of the Braille system dots are represented by a string of 1's and 0's,
   * respectively.
   *
   * @param s Input String consisting of letters and spaces only.
   * @return Returns string with binary Braille translation of input string.
   */
  public static String solution(String s) {
    // Build binary Braille output iteratively with a StringBuilder.
    StringBuilder outputBraille = new StringBuilder();

    // Binary Braille patterns of the alphabet (a-z).
    String[] brailleAlphabet = {
        "100000", "110000", "100100", "100110", "100010", "110100", "110110", "110010", "010100",
        "010110", "101000", "111000", "101100", "101110", "101010", "111100", "111110", "111010",
        "011100", "011110", "101001", "111001", "010111", "101101", "101111", "101011"
    };

    // Split up the input string into an array of its individual characters
    char[] inputCharArray = s.toCharArray();

    // Translate each input character based on its ASCII values into the matching Braille character.
    for (int inputChar : inputCharArray) {
      if (inputChar >= 65 && inputChar <= 90) {
        // Case: A-Z, append Braille capitalization mark and matching Braille character.
        outputBraille.append("000001");
        outputBraille.append(brailleAlphabet[inputChar - 65]);
      } else if (inputChar >= 97 && inputChar <= 122) {
        // Case: a-z, append matching binary Braille character.
        outputBraille.append(brailleAlphabet[inputChar - 97]);
      } else if (inputChar == 32) {
        // Case: space, append matching binary Braille space character.
        outputBraille.append("000000");
      } else {
        // Invalid character in input string detected.
        throw new IllegalArgumentException("Input string s contains illegal characters."
            + " Only letters and spaces are allowed on the space station!");
      }
    }

    return outputBraille.toString();
  }

}
