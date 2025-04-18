package test;

import model.Board;
import model.Knight;
import model.Square;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {

    @Test
    void testKnightLShapeMoves() {
        Board board = new Board();

        // ✅ Clear default pieces from board before placing test piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.getSquareArray()[i][j].removePiece();
            }
        }

        // ✅ Place knight manually in the center of the board
        Knight knight = new Knight(1, board.getSquareArray()[4][4], "/images/wknight.png");
        board.getSquareArray()[4][4].put(knight);

        // ✅ Run the test
        List<Square> legalMoves = knight.getLegalMoves(board);
        assertTrue(legalMoves.contains(board.getSquareArray()[6][5])); // example L-move
    }

}
