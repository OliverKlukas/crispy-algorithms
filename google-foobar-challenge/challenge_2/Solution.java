import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * Google foobar challenge 2 - Don't Get Volunteered. Finds the shortest number of moves to solve
 * the morning puzzle of the day in order to not get "volunteered" as a test subject for the
 * LAMBCHOP doomsday device.
 */
public class Solution {

  /**
   * Calculates the smallest amount of moves needed to reach a destination square.
   *
   * <p>The movement puzzle is restricted to be on a 8x8 playing field which only allows the
   * movement pattern of a chess knight ("L-shaped movements").
   *
   * @param src  Source square on the chessboard between 0 and 63 (inclusive).
   * @param dest Destination square on the chessboard between 0 and 63 (inclusive).
   * @return Returns the smallest number of moves it takes to reach the destination square.
   */
  public static int solution(int src, int dest) {
    // Hashset that contains the already considered squares to avoid duplicate evaluations.
    HashSet<Integer> consideredSquares = new HashSet<>();

    // Values utilized to compute knight movements in a (x,y)-coordinate system.
    int[][] knightMoves = {
        {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}
    };

    // Initialize a queue for the next possible squares with SquareTuples of (square, number of moves).
    ArrayDeque<SquareTuple> nextSquares = new ArrayDeque<>();
    nextSquares.add(new SquareTuple(src, 0));

    // Search for the path to the destination square as long there are still feasible alternatives.
    while (!nextSquares.isEmpty()) {
      // Dequeue first square of evaluation queue.
      SquareTuple evaluatedSquare = nextSquares.remove();

      // Check if destination square is reached.
      if (evaluatedSquare.getSquare() == dest) {
        return evaluatedSquare.getNumberMoves();
      } else {
        // Retrieve x and y coordinates based on current square for knight move calculation.
        int x = evaluatedSquare.getSquare() % 8;
        int y = evaluatedSquare.getSquare() / 8;

        // Calculate possible next squares based on current position and knight moves.
        for (int i = 0; i < 8; i++) {
          int nextX = x + knightMoves[i][0];
          int nextY = y + knightMoves[i][1];

          // Check if next move is within board boundaries.
          if (nextY >= 0 && nextY <= 7 && nextX >= 0 && nextX <= 7) {
            int possibleSquare = 8 * nextY + nextX;

            // Add square to queue and to the considered squares if not evaluated yet.
            if (!consideredSquares.contains(possibleSquare)) {
              nextSquares.add(
                  new SquareTuple(possibleSquare, evaluatedSquare.getNumberMoves() + 1)
              );
              consideredSquares.add(possibleSquare);
            }
          }
        }
      }
    }

    // Return infinity if computation led to no feasible path.
    return Integer.MAX_VALUE;
  }

  /**
   * Tuple representing a square on the chessboard floor and a relative amount of moves. Utilized to
   * persist the number of moves required to reach a square relative to an unspecified source square
   * without having to store the whole movement history.
   */
  private static class SquareTuple {

    private final int square;
    private final int numberMoves;

    /**
     * Construct a square tuple based on chessboard square and required amount of moves to reach
     * it.
     *
     * @param square      Number between 0 and 63 (inclusive) representing a square on the
     *                    chessboard.
     * @param numberMoves Number of moves required to reach the specified square from a relative
     *                    (not-specified) square on the board.
     */
    public SquareTuple(int square, int numberMoves) {
      this.square = square;
      this.numberMoves = numberMoves;
    }

    public int getSquare() {
      return this.square;
    }

    public int getNumberMoves() {
      return this.numberMoves;
    }
  }
}
