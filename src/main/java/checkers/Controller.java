package checkers;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Seth Nans, Gabriel Strickland, Thomas Powell, Sabella Malisher, Zachary McMillan
 * Date: 4/21/2025
 * Section: CSC 331
 * Program Purpose: Controls the main game loop for a two-player checkers game.
 * This class handles turns, determines available moves, processes player input, and tracks the winner.
 */

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


  /**
   * Constructs a new Controller.
   */
  public Controller() {
    board = new Board();
    turn = Player.BLACK;
    moves = new ArrayList<Move>();
  }

  /**
   * Runs after the program starts, and updates UI elements.
   * */
  @FXML
  private void initialize() {
      updateUI();
  }

  /**
   * Runs when a move is selected by the user.
   * Take the input, validates it, then updates state and UI.
   */
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

  /**
   * Updates all UI elements.
   * Ends the game when no valid moves are available.
   * */
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

  /**
   * Alternates between WHITE and BLACK.
   * */
  private void toggleTurn() {
    turn = (turn == Player.WHITE) ? Player.BLACK : Player.WHITE;
  }

  /**
   * Updates the UI to show the victor.
   * */
  @FXML
  private void handleVictor(Player player) {
    VictorLabel.setText(player.toString() + " wins!");
    Left.setVisible(false);
    VictorLabel.setVisible(true);
  }

  /**
   * Returns a formatted string listing all moves for the current player.
   * @return Formatted string
   * */
  private String listMoves() {
    String res = "";
    for (int i = 0; i < moves.size(); i++) {
        res += String.format("%2d. %s\n", i + 1, moves.get(i));
    }
    return res;
  }

  /**
   * Applies the selected move to the board. Moves the piece, removes captured
   * pieces, and promotes to king if a piece reaches the back row.
   * @param move The move to process
   */
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
