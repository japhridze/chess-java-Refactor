package model;



import java.util.LinkedList;
import java.util.List;

public class Knight extends Piece {

    public Knight(int color, Square initialSquare, String imagePath) {
        super(color, initialSquare, imagePath);
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        List<Square> legalMoves = new LinkedList<>();
        Square[][] squares = board.getSquareArray();

        int currentX = this.getPosition().getXNum();
        int currentY = this.getPosition().getYNum();

        int[][] moveOffsets = {
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };

        for (int[] offset : moveOffsets) {
            int newX = currentX + offset[0];
            int newY = currentY + offset[1];

            if (isValidSquare(newX, newY, squares)) {
                legalMoves.add(squares[newY][newX]);
            }
        }

        return legalMoves;
    }

    private boolean isValidSquare(int x, int y, Square[][] squares) {
        return x >= 0 && x < squares[0].length && y >= 0 && y < squares.length &&
                (!squares[y][x].isOccupied() || squares[y][x].getOccupyingPiece().getColor() != this.getColor());
    }

}
