package checkers;
import java.util.ArrayList;

/**
 * @author Seth Nans, Gabriel Strickland, Thomas Powell, Sabella Malisher, Zachary McMillan
 * Date: 4/21/2025
 * Section: CSC 331
 * Program Purpose: Represents the game board for the checkers game.
 * Handles the setup of the board, move validation, and displays the board.
 */

public class Board { 

  // Composition: Board has a grid of Pieces.
  Piece[][] grid = new Piece[8][8];

  /**
   * Constructs a Board object and sets up pieces.
   */
  Board() {
    setup();
  }

  /**
   * Returns the piece at the specified coordinates.
   * Used outside this class.
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @return The Piece at the location, or null if empty
   */
  public Piece get(int x, int y) {
    return grid[y][x];
  }

  /**
   * Sets a piece at the specified coordinates.
   * Used outside this class.
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param piece The Piece to place
   */
  public void set(int x, int y, Piece piece) {
    grid[y][x] = piece;
  }

  /**
   * Returns a list of valid moves for the player.
   * Takes into account movement and forced captures.
   * @param player The player whose moves are to be retrieved
   * @return List of valid move objects
   */
  public ArrayList<Move> getValidMoves(Player player) {
    ArrayList<Move> res = new ArrayList<>(); 
    for (int y = 0; y < 8; y++) {
        for (int x = 0; x < 8; x++) {
            Piece piece = grid[y][x];
            if (piece != null && piece.getPlayer() == player) {
                res.addAll(getPieceValidMoves(piece, x, y));
            }
        }
    }
  // taking is forced
  if (res.stream().anyMatch(m -> m.isTake())) {
      res.removeIf(m -> !m.isTake());
  }
    return res;
  }

  /**
   * Returns all valid moves for a piece at a given location.
   * @param piece The piece to check
   * @param x The x-coordinate of the piece
   * @param y The y-coordinate of the piece
   * @return List of valid move objects
   */
  private ArrayList<Move> getPieceValidMoves(Piece piece, int x, int y) {
      ArrayList<Move> moves = new ArrayList<>();
      int[] directions = piece.getDirections();
      for (int direction : directions) {
        addNormalMove(x, y, direction, -1, moves);
        addNormalMove(x, y, direction, 1, moves);
        addJumpMove(x, y, direction, -1, moves);
        addJumpMove(x, y, direction, 1, moves);
      }
      return moves;
  }

  /**
   * Adds a non-capturing move to the list if valid.
   * @param x The current x-position
   * @param y The current y-position
   * @param direction The vertical direction
   * @param dx The horizontal direction
   * @param moves List to append the move to
   */
  private void addNormalMove(int x, int y, int direction, int dx, ArrayList<Move> moves) {
      int newX = x + dx;
      int newY = y + direction;
      // if move is in bounds and the square is open
      if (isInBounds(newX, newY) && grid[newY][newX] == null) {
          // move contains the old and new positions
          moves.add(new Move(x, y, newX, newY));
      }
  }


  /**
   * Adds a jump (capturing) move to the list if valid.
   * @param x The current x-position
   * @param y The current y-position
   * @param direction The vertical direction
   * @param dx The horizontal direction
   * @param moves List to append the move to
   */
  private void addJumpMove(int x, int y, int direction, int dx, ArrayList<Move> moves) {
      // midX / midY are the square being skipped
      int newX = x + 2 * dx;
      int newY = y + 2 * direction;
      // this location may or may not be an opposing piece
      int midX = x + dx;
      int midY = y + direction;
      // if in bounds and the square is open
      if (isInBounds(newX, newY) && grid[newY][newX] == null) {
          Piece midPiece = grid[midY][midX];
          // and if theres an opposing piece in betweeen
          if (midPiece != null && midPiece.getPlayer() != grid[y][x].getPlayer()) {
              // if selected, the piece there will be deleted by processMove()
              moves.add(new Move(x, y, newX, newY, midX, midY));
          }
      }
  }

  /**
   * Checks to see if given coordinates are within board bounds.
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @return True if coordinate is in bound, false otherwise
   */
  private boolean isInBounds(int x, int y) {
      return x >= 0 && x < 8 && y >= 0 && y < 8;
  }

  /**
   * Returns a formatted string representation of the current board.
   * @return A String that displays the board layout
   */
  public String show() {
    String res = "";
    for (int y = 7; y >= 0; y--) {
        res += String.format("%2d ", y + 1);
        for (int x = 0; x < 8; x++) {
            String blank = ((x + y) % 2 == 0) ? ". " : "  ";
            Piece piece = grid[y][x];
            res += piece == null ? blank : piece.icon()+" ";
        }
        res += "\n";
    }
    res += "  ";
    for (int x = 1; x <= 8; x++) {
        res += String.format("%2d", x);
    }
    return res;
  }

  /**
   * Initializes the board with a default layout of WHITE and BLACK pieces.
   */
  public void setup() {
    grid[0][0] = new Piece(Player.WHITE);
    grid[0][2] = new Piece(Player.WHITE);
    grid[0][4] = new Piece(Player.WHITE);
    grid[0][6] = new Piece(Player.WHITE);
    grid[1][1] = new Piece(Player.WHITE);
    grid[1][3] = new Piece(Player.WHITE);
    grid[1][5] = new Piece(Player.WHITE);
    grid[1][7] = new Piece(Player.WHITE);
    grid[2][0] = new Piece(Player.WHITE);
    grid[2][2] = new Piece(Player.WHITE);
    grid[2][4] = new Piece(Player.WHITE);
    grid[2][6] = new Piece(Player.WHITE);
    grid[5][1] = new Piece(Player.BLACK);
    grid[5][3] = new Piece(Player.BLACK);
    grid[5][5] = new Piece(Player.BLACK);
    grid[5][7] = new Piece(Player.BLACK);
    grid[6][0] = new Piece(Player.BLACK);
    grid[6][2] = new Piece(Player.BLACK);
    grid[6][4] = new Piece(Player.BLACK);
    grid[6][6] = new Piece(Player.BLACK);
    grid[7][1] = new Piece(Player.BLACK);
    grid[7][3] = new Piece(Player.BLACK);
    grid[7][5] = new Piece(Player.BLACK);
    grid[7][7] = new Piece(Player.BLACK);
  }
}
