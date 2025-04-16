package checkers;
// stores information relating to a possible move in the game

public class Move {

  // information about the movement itself
  private int startX, startY, endX, endY;

  // information about taking, if applicable
  private boolean isTake;
  private int takeX, takeY;

  // constructor for a normal move
  public Move(int startX, int startY, int endX, int endY) {
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.isTake = false;
  }

  // constructor for a take move
  public Move(int startX, int startY, int endX, int endY, int takeX, int takeY) {
    this(startX, startY, endX, endY);
    this.isTake = true;
    this.takeX = takeX;
    this.takeY = takeY;
  }

  public int getStartX() {
      return startX;
  }

  public int getStartY() {
      return startY;
  }

  public int getEndX() {
      return endX;
  }

  public int getEndY() {
      return endY;
  }

  public boolean isTake() {
      return isTake;
  }

  public int getTakeX() {
      return takeX;
  }

  public int getTakeY() {
      return takeY;
  }

  @Override
  public String toString() {
      String moveStr = String.format("(%d,%d) to (%d,%d)", startX + 1, startY + 1, endX + 1, endY + 1);
      if (isTake) {
          moveStr += String.format(" takes (%d,%d)", takeX + 1, takeY + 1);
      }
      return moveStr;
  }
}
