package checkers;

public class Piece {

  private Player player;
  private boolean isKing;

  public Piece(Player player) {
    this.player = player;
    this.isKing = false;
  }

  public String icon() {
    int index = (player == Player.WHITE) ? 0 : 2;
    String[] icons = {"⛀", "⛁", "⛂", "⛃"};
    if (isKing) {
      index += 1;
    }
    return icons[index];
  }

  // valid move directions (1 is up)
  public int[] getDirections() {
    if (this.isKing) {
      return new int[] {-1, 1};
    }
    if (player == Player.WHITE) {
      return new int[] {1};
    }
    return new int[] {-1};
  }

  public void promote() {
    this.isKing = true;
  }

  public Player getPlayer() {
    return player;
  }

  public boolean isKing() {
    return isKing;
  }
}
