package checkers;

/**
 * @author Seth Nans, Gabriel Strickland, Thomas Powell, Sabella Malisher, Zachary McMillan
 * Date: 4/21/2025
 * Section: CSC 331
 * Program Purpose: Stores information relating to a possible move in the game
 */

public class Move {

  // information about the movement itself
  private int startX, startY, endX, endY;

  // information about taking, if applicable
  private boolean isTake;
  private int takeX, takeY;

  /**
   * Constructor for a normal move.
   * @param startX The starting x-coordinate of the move
   * @param startY The starting y-coordinate of the move
   * @param endX The ending x-coordinate of the move
   * @param endY the ending y-coordinate of the move
   */
  public Move(int startX, int startY, int endX, int endY) {
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.isTake = false;
  }

  /**
   * Constructor for a take move.
   * @param startX The starting x-coordinate of the move
   * @param startY The starting y-coordinate of the move
   * @param endX The ending x-coordinate of the move
   * @param endY The ending y-coordinate of the move
   * @param takeX The x-coordinate of the taken piece
   * @param takeY The y-coordinate of the taken piece
   */
  public Move(int startX, int startY, int endX, int endY, int takeX, int takeY) {
    this(startX, startY, endX, endY);
    this.isTake = true;
    this.takeX = takeX;
    this.takeY = takeY;
  }


  /**
   * Gets the starting x-coordinate.
   * @return the starting x-position
   */
  public int getStartX() {
      return startX;
  }

  /**
   * Gets the starting y-coordinate.
   * @return the starting y-position
   */
  public int getStartY() {
      return startY;
  }

  /**
   * Gets the ending x-coordinate.
   * @return the ending x-position
   */
  public int getEndX() {
      return endX;
  }

  /**
   * Gets the ending y-coordinate.
   * @return the ending y-position
   */
  public int getEndY() {
      return endY;
  }

  /**
   * Checks if the move is a capturing move.
   * @return true if the move involves taking a piece
   */
  public boolean isTake() {
      return isTake;
  }

  /**
   * Gets the x-coordinate of the captured piece.
   * @return the x-position of the taken piece
   */
  public int getTakeX() {
      return takeX;
  }

  /**
   * Gets the y-coordinate of the captured piece.
   * @return the y-position of the taken piece
   */
  public int getTakeY() {
      return takeY;
  }

  /**
   * Returns a formatted string representing the move.
   * Includes capture information if applicable.
   * @return a string describing the move
   */
  @Override
  public String toString() {
      String moveStr = String.format("(%d,%d) to (%d,%d)", startX + 1, startY + 1, endX + 1, endY + 1);
      if (isTake) {
          moveStr += String.format(" takes (%d,%d)", takeX + 1, takeY + 1);
      }
      return moveStr;
  }
}
