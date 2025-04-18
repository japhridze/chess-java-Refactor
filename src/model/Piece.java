package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage img;

    private static final int BOARD_SIZE = 8;

    public Piece(int color, Square initialSquare, String imagePath) {
        this.color = color;
        this.currentSquare = initialSquare;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(imagePath));
            }
        } catch (IOException e) {
            System.err.println("Image load error: " + imagePath + " → " + e.getMessage());
        }
    }

    public boolean move(Square targetSquare) {
        Piece occupyingPiece = targetSquare.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == this.color) return false;
            targetSquare.capture(this);
        }

        currentSquare.removePiece();
        this.currentSquare = targetSquare;
        currentSquare.put(this);
        return true;
    }

    public Square getPosition() {
        return currentSquare;
    }

    public void setPosition(Square square) {
        this.currentSquare = square;
    }

    public int getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYAbove = 0;
        int lastYBelow = BOARD_SIZE - 1;
        int lastXLeft = 0;
        int lastXRight = BOARD_SIZE - 1;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                lastYAbove = (board[i][x].getOccupyingPiece().getColor() != this.color) ? i : i + 1;
            }
        }

        for (int i = BOARD_SIZE - 1; i > y; i--) {
            if (board[i][x].isOccupied()) {
                lastYBelow = (board[i][x].getOccupyingPiece().getColor() != this.color) ? i : i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                lastXLeft = (board[y][i].getOccupyingPiece().getColor() != this.color) ? i : i + 1;
            }
        }

        for (int i = BOARD_SIZE - 1; i > x; i--) {
            if (board[y][i].isOccupied()) {
                lastXRight = (board[y][i].getOccupyingPiece().getColor() != this.color) ? i : i - 1;
            }
        }

        return new int[]{lastYAbove, lastYBelow, lastXLeft, lastXRight};
    }

    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        List<Square> diagonalSquares = new LinkedList<>();
        addDiagonal(board, diagonalSquares, x, y, -1, -1); // NW
        addDiagonal(board, diagonalSquares, x, y, -1, 1);  // SW
        addDiagonal(board, diagonalSquares, x, y, 1, 1);   // SE
        addDiagonal(board, diagonalSquares, x, y, 1, -1);  // NE
        return diagonalSquares;
    }

    private void addDiagonal(Square[][] board, List<Square> result, int startX, int startY, int dx, int dy) {
        int x = startX + dx;
        int y = startY + dy;

        while (isValidSquare(x, y)) {
            Square square = board[y][x];
            if (square.isOccupied()) {
                if (square.getOccupyingPiece().getColor() != this.color) {
                    result.add(square);
                }
                break;
            } else {
                result.add(square);
            }
            x += dx;
            y += dy;
        }
    }

    private boolean isValidSquare(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    // Abstract method to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board board);
}
