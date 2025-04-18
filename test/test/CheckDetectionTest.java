package test;

import model.Board;
import model.CheckmateDetector;
import model.King;
import model.Queen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckDetectionTest {

    @Test
    void testBlackKingInCheck() {
        Board board = new Board();
        King blackKing = new King(0, board.getSquareArray()[0][4], "bking.png");
        board.getSquareArray()[0][4].put(blackKing);

        Queen whiteQueen = new Queen(1, board.getSquareArray()[1][4], "wqueen.png");
        board.getSquareArray()[1][4].put(whiteQueen);

        CheckmateDetector detector = new CheckmateDetector(board, board.Wpieces, board.Bpieces, null, blackKing);
        assertFalse(detector.blackInCheck());
    }
}
