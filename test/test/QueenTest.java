package test;

import model.Board;
import model.Queen;
import model.Square;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    @Test
    void testQueenMovesStraightAndDiagonal() {
        Board board = new Board();

        // Clear all default pieces before testing
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.getSquareArray()[i][j].removePiece();
            }
        }

        Queen queen = new Queen(1, board.getSquareArray()[4][4], "wqueen.png");
        board.getSquareArray()[4][4].put(queen);

        List<Square> moves = queen.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquareArray()[4][7])); // Horizontal
        assertTrue(moves.contains(board.getSquareArray()[0][4])); // Vertical
        assertTrue(moves.contains(board.getSquareArray()[7][7])); // Diagonal
    }

}
