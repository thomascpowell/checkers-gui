package checkers;

/**
 * @author Seth Nans, Gabriel Strickland, Thomas Powell, Sabella Malisher, Zachary McMillan
 * Date: 4/21/2025
 * Section: CSC 331
 * Program Purpose: A class for individual game pieces including movement rules and state.
 */

public class Piece {

  private Player player;
  private boolean isKing;

  public Piece(Player player) {
    this.player = player;
    this.isKing = false;
  }

  /**
   * Returns the character icon used to display the piece.
   * @return A string representing the piece, either a normal or king piece
   */
  public String icon() {
    int index = (player == Player.WHITE) ? 0 : 2;
    String[] icons = {"⛀", "⛁", "⛂", "⛃"};
    if (isKing) {
      index += 1;
    }
    return icons[index];
  }

  /**
   * Returns the directions a piece can move in.
   * Kings can move forward and backward; normal pieces move forward.
   * @return An array of movement directions
   */
  public int[] getDirections() {
    if (this.isKing) {
      return new int[] {-1, 1};
    }
    if (player == Player.WHITE) {
      return new int[] {1};
    }
    return new int[] {-1};
  }

  /**
   * Promotes a piece to a king, allowing it to move forward and backwards.
   */
  public void promote() {
    this.isKing = true;
  }

  /**
   * Returns the player who owns this piece.
   * @return The player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Checks if the piece is a king.
   * @return True if the piece is a king, false otherwise
   */
  public boolean isKing() {
    return isKing;
  }
}
