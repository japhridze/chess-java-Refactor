package test;

import model.Board;
import model.Pawn;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PawnPromotionTest {

    @Test
    void testPawnCanReachLastRank() {
        Board board = new Board();
        Pawn pawn = new Pawn(1, board.getSquareArray()[1][0], "wpawn.png");
        board.getSquareArray()[1][0].put(pawn);

        pawn.move(board.getSquareArray()[0][0]); // Move to last rank
        assertEquals(board.getSquareArray()[0][0].getOccupyingPiece(), pawn);
    }
}
