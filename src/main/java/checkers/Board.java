package checkers;
import java.util.ArrayList;

public class Board { 

  Piece[][] grid = new Piece[8][8];

  Board() {
    setup();
  }

  public Piece get(int x, int y) {
    return grid[y][x];
  }

  public void set(int x, int y, Piece piece) {
    grid[y][x] = piece;
  }

  // returns a list of valid moves
  public ArrayList<Move> getValidMoves(Player player) {
    ArrayList<Move> res = new ArrayList<>(); 
    for (int y = 0; y < 8; y++) {
        for (int x = 0; x < 8; x++) {
            Piece piece = grid[y][x];
            if (piece != null && piece.getPlayer() == player) {
                // Add valid moves for this piece
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

  private void addNormalMove(int x, int y, int direction, int dx, ArrayList<Move> moves) {
      // dx = shift in x
      // direction = forward / backwards
      int newX = x + dx;
      int newY = y + direction;
      // if move is in bounds and the square is open
      if (isInBounds(newX, newY) && grid[newY][newX] == null) {
          // move contains the old and new positions
          moves.add(new Move(x, y, newX, newY));
      }
  }

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
          // and if theres an opposing piece there
          if (midPiece != null && midPiece.getPlayer() != grid[y][x].getPlayer()) {
              // if the move is selected, the piece there will be deleted
              moves.add(new Move(x, y, newX, newY, midX, midY));
          }
      }
  }

  // checks if coordiates are on the board
  private boolean isInBounds(int x, int y) {
      return x >= 0 && x < 8 && y >= 0 && y < 8;
  }

  // returns the board
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

  // terrible.
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
