package test;

import model.Bishop;
import model.Board;
import model.Square;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BishopTest {

    @Test
    void testBishopMovesDiagonally() {
        Board board = new Board();
        Bishop bishop = new Bishop(1, board.getSquareArray()[4][4], "wbishop.png");
        board.getSquareArray()[4][4].put(bishop);

        List<Square> moves = bishop.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquareArray()[3][3]));
        assertTrue(moves.contains(board.getSquareArray()[5][5]));
    }
}
