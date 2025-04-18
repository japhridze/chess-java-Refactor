package test;

import model.Board;
import model.Rook;
import model.Square;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    @Test
    void testRookMovesHorizontallyAndVertically() {
        Board board = new Board();

        // 🔁 Clear default pieces from board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.getSquareArray()[i][j].removePiece();
            }
        }

        // 🏰 Place rook in center
        Rook rook = new Rook(1, board.getSquareArray()[4][4], "/wrook.png");
        board.getSquareArray()[4][4].put(rook);

        List<Square> moves = rook.getLegalMoves(board);

        // ✅ Test for horizontal and vertical reach
        assertTrue(moves.contains(board.getSquareArray()[4][0])); // Horizontal left
        assertTrue(moves.contains(board.getSquareArray()[0][4])); // Vertical up
        assertTrue(moves.contains(board.getSquareArray()[7][4])); // Vertical down
    }

}
