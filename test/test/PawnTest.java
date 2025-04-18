package test;

import model.Board;
import model.Pawn;
import model.Square;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PawnTest {

    @Test
    void testInitialDoubleMoveAllowed() {
        Board board = new Board();
        Pawn pawn = new Pawn(0, board.getSquareArray()[1][0], null);

        board.getSquareArray()[1][0].put(pawn);

        List<Square> legalMoves = pawn.getLegalMoves(board);
        assertTrue(legalMoves.contains(board.getSquareArray()[3][0]));
    }

    @Test
    void testBlockedForwardMove() {
        Board board = new Board();
        Pawn pawn = new Pawn(0, board.getSquareArray()[1][0], "bpawn.png");
        board.getSquareArray()[1][0].put(pawn);

        // Block the square in front
        board.getSquareArray()[2][0].put(new Pawn(1, board.getSquareArray()[2][0], "wpawn.png"));

        List<Square> legalMoves = pawn.getLegalMoves(board);
        assertFalse(legalMoves.contains(board.getSquareArray()[2][0]));
    }
}
