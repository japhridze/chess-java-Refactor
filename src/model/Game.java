package model;

import view.StartMenu;

import javax.swing.*;

public class Game implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    public Board getBoard() {
        return null;
    }

    public boolean isGameOver() {
        return false;
    }

    public boolean makeMove(String move) {
        return false;
    }

    public Color getWinner() {
        return null;
    }

    public void switchTurn() {

    }

    public Color getCurrentPlayer() {

        return null;
    }
}
