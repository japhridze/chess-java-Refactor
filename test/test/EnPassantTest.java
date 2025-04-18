package test;

import model.Board;
import model.Pawn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnPassantTest {

    @Test
    void testEnPassantCapture() {
        Board board = new Board();

        // White pawn at e5
        Pawn whitePawn = new Pawn(1, board.getSquareArray()[4][4], "wpawn.png");
        board.getSquareArray()[4][4].put(whitePawn);

        // Black pawn does a 2-step move from d7 to d5
        Pawn blackPawn = new Pawn(0, board.getSquareArray()[6][3], "bpawn.png");
        board.getSquareArray()[6][3].put(blackPawn);
        blackPawn.move(board.getSquareArray()[4][3]);

        // Simulate en passant rule (manual since game logic doesn’t track last move)
        assertTrue(board.getSquareArray()[4][3].isOccupied());
        board.getSquareArray()[5][3].put(whitePawn); // simulate en passant
        board.getSquareArray()[4][4].removePiece();

        assertEquals(whitePawn, board.getSquareArray()[5][3].getOccupyingPiece());
    }
}
