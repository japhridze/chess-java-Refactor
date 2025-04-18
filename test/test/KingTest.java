package test;

import model.Board;
import model.King;
import model.Square;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {

    @Test
    void testKingMovesOneSquareInAllDirections() {
        Board board = new Board();
        King king = new King(1, board.getSquareArray()[4][4], "wking.png");
        board.getSquareArray()[4][4].put(king);

        List<Square> moves = king.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquareArray()[3][4])); // Up
        assertTrue(moves.contains(board.getSquareArray()[5][4])); // Down
        assertTrue(moves.contains(board.getSquareArray()[4][3])); // Left
        assertTrue(moves.contains(board.getSquareArray()[4][5])); // Right
        assertTrue(moves.contains(board.getSquareArray()[3][3])); // NW
        assertTrue(moves.contains(board.getSquareArray()[3][5])); // NE
        assertTrue(moves.contains(board.getSquareArray()[5][3])); // SW
        assertTrue(moves.contains(board.getSquareArray()[5][5])); // SE
    }
}
