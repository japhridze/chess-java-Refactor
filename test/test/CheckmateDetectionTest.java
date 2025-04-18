package test;

import model.Board;
import model.CheckmateDetector;
import model.King;
import model.Queen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckmateDetectionTest {

    @Test
    void testSimpleCheckmateScenario() {
        Board board = new Board();

        King blackKing = new King(0, board.getSquareArray()[0][0], "bking.png");
        Queen whiteQueen = new Queen(1, board.getSquareArray()[1][1], "wqueen.png");
        King whiteKing = new King(1, board.getSquareArray()[2][0], "wking.png"); // Controls escape squares

        board.getSquareArray()[0][0].put(blackKing);
        board.getSquareArray()[1][1].put(whiteQueen);
        board.getSquareArray()[2][0].put(whiteKing);

        board.Bpieces.add(blackKing);
        board.Wpieces.add(whiteQueen);
        board.Wpieces.add(whiteKing);

        CheckmateDetector detector = new CheckmateDetector(board, board.Wpieces, board.Bpieces, whiteKing, blackKing);
        assertFalse(detector.blackCheckMated());
    }


}
