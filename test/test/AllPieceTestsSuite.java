package test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        PawnTest.class,
        RookTest.class,
        QueenTest.class,
        BishopTest.class,
        KnightTest.class,
        KingTest.class,
        EnPassantTest.class,
        CheckDetectionTest.class,
        CheckmateDetectionTest.class,
        PawnPromotionTest.class

})
public class AllPieceTestsSuite {
}
