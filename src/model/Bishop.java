package model;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(int color, Square initialSquare, String imagePath) {
        super(color, initialSquare, imagePath);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        Square[][] squares = board.getSquareArray();
        int currentX = getPosition().getXNum();
        int currentY = getPosition().getYNum();

        return getDiagonalOccupations(squares, currentX, currentY);
    }
}
