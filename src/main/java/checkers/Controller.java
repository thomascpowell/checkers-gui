package checkers;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

  private Board board;
  private Player turn;
  private ArrayList<Move> moves;

  // List of available moves
  @FXML private TextArea MoveList;

  // Users Move selelction
  @FXML private TextField MoveInput;

  // Move Button
  @FXML private Button MoveButton;

  // Move Label
  @FXML private Label MoveLabel;

  // Board Representation
  @FXML private Label BoardLabel;

  // Left half of the screen
  @FXML private AnchorPane Left;

  // Displays the victor
  @FXML private Label VictorLabel;

  public Controller() {
    board = new Board();
    turn = Player.BLACK;
    moves = new ArrayList<Move>();
  }

  @FXML
  private void initialize() {
      updateUI();
  }

  // runs when the user submits a move
  public void handleMove() {
    // read in a valid input
    int input;
    try {
      input = Integer.parseInt(MoveInput.getText()) -1;
      if (input < 0 || input >= moves.size()) {
        throw new Exception();
      }
    } catch (NumberFormatException e) {
      MoveLabel.setText("Please enter an integer.");
      return;
    } catch (Exception e) {
      MoveLabel.setText("Please enter a valid move.");
      return;
    }
    // match it to a move in the list
    Move move = moves.get(input);
    // modify the board
    processMove(move);
    // handle the case of a double jump
    if (move.isTake()) {
        moves = board.getValidMoves(turn); 
        // in the case of a double jump, only take moves are permitted
        // specifically, take moves by the piece that made the original capture
        ArrayList<Move> followUps = new ArrayList<>();
        for (Move m : moves) {
            if (m.getStartX() == move.getEndX() && m.getStartY() == move.getEndY() && m.isTake()) {
                followUps.add(m);
            }
        }
        if (followUps.size() != 0) {
            MoveLabel.setText(turn.toString() + " has another avaliable move.");
            return;
        }
    }
    // update the game state
    updateUI();
  }

  // updates the ui and moves list
  public void updateUI() {
    // update the board display
    BoardLabel.setText(board.show());
    // change the turn
    toggleTurn();
    // calculate moves available
    moves = board.getValidMoves(turn);
    // if the game has ended
    if (moves.isEmpty()) {
      toggleTurn();
      handleVictor(turn);
    }  
    // update the moves list
    MoveList.setText(listMoves());
    // update the move label
    MoveLabel.setText(turn.toString() + "'s Turn");
  }

  private void toggleTurn() {
    turn = (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
  }

  @FXML // displays the winner
  private void handleVictor(Player player) {
    VictorLabel.setText(player.toString() + " wins!");
    Left.setVisible(false);
    VictorLabel.setVisible(true);
  }

  private String listMoves() {
    String res = "";
    for (int i = 0; i < moves.size(); i++) {
        res += String.format("%2d. %s\n", i + 1, moves.get(i));
    }
    return res;
  }

  // modifies the board
  private void processMove(Move move) {
    Piece piece = board.get(move.getStartX(), move.getStartY());
    board.set(move.getStartX(), move.getStartY(), null);
    board.set(move.getEndX(), move.getEndY(), piece);
    if (move.isTake()) {
      board.set(move.getTakeX(), move.getTakeY(), null);
    }
    int backRank = (piece.getPlayer() == Player.WHITE) ? 7 : 0;
    if (move.getEndY() == backRank) {
      piece.promote();
    }
  }
}
