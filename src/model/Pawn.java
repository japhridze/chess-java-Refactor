package model;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
    private boolean wasMoved;

    private static final int BLACK = 0;
    private static final int WHITE = 1;

    public Pawn(int color, Square initialSquare, String imagePath) {
        super(color, initialSquare, imagePath);
    }

    @Override
    public boolean move(Square destination) {
        boolean result = super.move(destination);
        wasMoved = true;
        return result;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        LinkedList<Square> legalMoves = new LinkedList<>();

        int x = getPosition().getXNum();
        int y = getPosition().getYNum();
        int direction = (getColor() == BLACK) ? 1 : -1;

        addInitialDoubleMove(board, legalMoves, x, y, direction);
        addForwardMove(board, legalMoves, x, y, direction);
        addDiagonalCaptures(board, legalMoves, x, y, direction);

        return legalMoves;
    }

    private void addInitialDoubleMove(Board board, List<Square> moves, int x, int y, int direction) {
        Square[][] squares = board.getSquareArray();
        int firstStep = y + direction;
        int doubleStep = y + 2 * direction;

        if (!wasMoved && isValidSquare(x, doubleStep)
                && !squares[firstStep][x].isOccupied()
                && !squares[doubleStep][x].isOccupied()) {
            moves.add(squares[doubleStep][x]);
        }
    }

    private void addForwardMove(Board board, List<Square> moves, int x, int y, int direction) {
        Square[][] squares = board.getSquareArray();
        int nextY = y + direction;

        if (isValidSquare(x, nextY) && !squares[nextY][x].isOccupied()) {
            moves.add(squares[nextY][x]);
        }
    }

    private void addDiagonalCaptures(Board board, List<Square> moves, int x, int y, int direction) {
        Square[][] squares = board.getSquareArray();
        int nextY = y + direction;

        // Capture right
        if (isValidSquare(x + 1, nextY) && squares[nextY][x + 1].isOccupied()
                && squares[nextY][x + 1].getOccupyingPiece().getColor() != getColor()) {
            moves.add(squares[nextY][x + 1]);
        }

        // Capture left
        if (isValidSquare(x - 1, nextY) && squares[nextY][x - 1].isOccupied()
                && squares[nextY][x - 1].getOccupyingPiece().getColor() != getColor()) {
            moves.add(squares[nextY][x - 1]);
        }
    }

    private boolean isValidSquare(int x, int y) {
        return (x >= 0 && x < 8) && (y >= 0 && y < 8);
    }
}
