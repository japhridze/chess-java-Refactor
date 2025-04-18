package model;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JComponent {

    private final Board board;
    private final int color;
    private final int xNum;
    private final int yNum;

    private Piece occupyingPiece;
    private boolean displayPiece;

    public Square(Board board, int color, int xNum, int yNum) {
        this.board = board;
        this.color = color;
        this.xNum = xNum;
        this.yNum = yNum;
        this.displayPiece = true;

        setBorder(BorderFactory.createEmptyBorder());
    }

    public int getColor() {
        return this.color;
    }

    public int getXNum() {
        return this.xNum;
    }

    public int getYNum() {
        return this.yNum;
    }

    public Piece getOccupyingPiece() {
        return this.occupyingPiece;
    }

    public boolean isOccupied() {
        return this.occupyingPiece != null;
    }

    public void setDisplay(boolean display) {
        this.displayPiece = display;
    }

    public void put(Piece piece) {
        this.occupyingPiece = piece;
        piece.setPosition(this);
    }

    public Piece removePiece() {
        Piece removed = this.occupyingPiece;
        this.occupyingPiece = null;
        return removed;
    }

    public void capture(Piece attacker) {
        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == 0) {
                board.Bpieces.remove(occupyingPiece);
            } else {
                board.Wpieces.remove(occupyingPiece);
            }
        }
        this.occupyingPiece = attacker;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color tileColor = (color == 1) ? new Color(221, 192, 127) : new Color(101, 67, 33);
        g.setColor(tileColor);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        if (occupyingPiece != null && displayPiece) {
            occupyingPiece.draw(g);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }
}
