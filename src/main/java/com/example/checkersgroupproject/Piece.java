package com.example.checkersgroupproject;
public class Piece {

  private Player player;
  private boolean isKing;

  public Piece(Player player) {
    this.player = player;
    this.isKing = false;
  }

  public String icon() {
    String color = (player == Player.RED) ? Utils.RED : Utils.BLUE;
    return Utils.color(color, (isKing) ? "k" : "o");
  }

  // valid move directions (1 is up)
  public int[] getDirections() {
    if (this.isKing) {
      return new int[] {-1, 1};
    }
    if (player == Player.RED) {
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
