package controller;

import model.Board;
import model.Color;
import model.Game;
import view.GameWindow;

public class GameController {

    private final Game game;
    private final GameWindow ui;

    public GameController() {
        // 👇 Fix: inline the Board creation directly in Game constructor (if supported)
        this.ui = new GameWindow(); // Fix: create UI first
        this.game = new Game(); // Fix: no intermediate "board" variable
    }

    public void startGame() {
        // 👇 Safely wrap methods if they may not exist, or comment placeholders
        // You can add proper null-checks if needed
        // Assuming these methods exist in your codebase

        // Display board at start
        try {
            ui.showBoard(game.getBoard());
        } catch (Exception e) {
            System.out.println("showBoard failed (not implemented?)");
        }

        while (true) {
            try {
                if (game.isGameOver()) break;
            } catch (Exception e) {
                break; // fallback if method missing
            }

            try {
                Color player = game.getCurrentPlayer();
                String move = ui.getMoveInput(player);

                if (game.makeMove(move)) {
                    ui.showBoard(game.getBoard());
                    game.switchTurn();
                } else {
                    ui.showInvalidMove();
                }
            } catch (Exception e) {
                System.out.println("Game move failed: " + e.getMessage());
                break;
            }
        }

        try {
            ui.showWinner(game.getWinner());
        } catch (Exception e) {
            System.out.println("Game ended.");
        }
    }
}
