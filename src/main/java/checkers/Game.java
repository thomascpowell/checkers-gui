package checkers;
import java.util.ArrayList;

public class Game {
  private Board board;
  private Player victor;
  private Player turn;

  public Game() {
    board = new Board();
    turn = Player.RED;
    victor = null;
    while (victor == null) {
      turn();
    }
  }

  // driver
  public static void main(String[] args) {
    Game game = new Game();
    System.out.println("\n" + game.getVictor().toString() + " wins!");
    Utils.in.close();
  }

  // processes one turn
  private void turn() {
    System.out.println(board.show());
    ArrayList<Move> moves = board.getValidMoves(turn);
    // victor is the other player, if no moves are avaliable
    // i.e. no pieces remaining for current player
    if (moves.isEmpty()) {
      toggleTurn();
      victor = turn;
      return;
    }  
    Move move = getPlayerChoice(moves);
    processMove(move);
    // handle the case of a double jump
    // the second (or more) moves will be handled in the next iteration
    if (move.isTake()) {
        moves = board.getValidMoves(turn); 
        // in the case of a double jump, only take moves are permitted
        // specifically take moves by the piece that made the original capture
        ArrayList<Move> followUps = new ArrayList<>();
        for (Move m : moves) {
            if (m.getStartX() == move.getEndX() && m.getStartY() == move.getEndY() && m.isTake()) {
                followUps.add(m);
            }
        }
        if (followUps.size() != 0) {
            System.out.println(turn.toString() + " has another avaliable move.");
            return;
        }
    }
    toggleTurn();
  }

  private Move getPlayerChoice(ArrayList<Move> moves) {
    System.out.println("\nMoves: " + turn.toString());
    for (int i = 0; i < moves.size(); i++) {
        System.out.printf("%2d. %s\n", i + 1, moves.get(i));
    }
    return moves.get(Utils.getIntInRange("Choice: ", 1, moves.size())-1);
  }

  private void processMove(Move move) {
    // get the current piece
    Piece piece = board.get(move.getStartX(), move.getStartY());
    // delete current piece
    board.set(move.getStartX(), move.getStartY(), null);
    // put it where it should be
    board.set(move.getEndX(), move.getEndY(), piece);
    // delete taken piece if applicable
    if (move.isTake()) {
      board.set(move.getTakeX(), move.getTakeY(), null);
    }
    // promote to king if made it to back rank
    int backRank = (piece.getPlayer() == Player.RED) ? 7 : 0;
    if (move.getEndY() == backRank) {
      piece.promote();
    }

  }

  private void toggleTurn() {
    turn = (turn == Player.RED) ? Player.BLUE : Player.RED;
  }

  public Player getVictor() {
    return victor;
  }

}
