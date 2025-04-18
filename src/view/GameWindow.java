package view;

import model.Board;
import model.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow {
    private JFrame gameWindow;
    private Clock blackClock;
    private Clock whiteClock;
    private Timer timer;
    private Board board;

    public GameWindow() {
        int hh = 0, mm = 0, ss = 0;

        blackClock = new Clock(hh, mm, ss);
        whiteClock = new Clock(hh, mm, ss);

        gameWindow = new JFrame("Chess");

        try {
            Image icon = ImageIO.read(getClass().getResource("wp.png"));
            gameWindow.setIconImage(icon);
        } catch (Exception e) {
            System.out.println("model.Game file wp.png not found");
        }

        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20, 20));

        JPanel gameData = createGameDataPanel(null, null, hh, mm, ss);
        gameWindow.add(gameData, BorderLayout.NORTH);

        this.board = new Board();
        gameWindow.add(board, BorderLayout.CENTER);
        gameWindow.add(createControlButtons(), BorderLayout.SOUTH);

        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel createGameDataPanel(final String blackName, final String whiteName, final int hh, final int mm, final int ss) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 0, 0));

        JLabel whiteLabel = createCenteredLabel(whiteName);
        JLabel blackLabel = createCenteredLabel(blackName);
        panel.add(whiteLabel);
        panel.add(blackLabel);

        final JLabel whiteTimeLabel = createCenteredLabel(whiteClock.getTime());
        final JLabel blackTimeLabel = createCenteredLabel(blackClock.getTime());

        if (!(hh == 0 && mm == 0 && ss == 0)) {
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    updateClock(whiteName, blackName, whiteTimeLabel, blackTimeLabel);
                }
            });
            timer.start();
        } else {
            whiteTimeLabel.setText("Untimed game");
            blackTimeLabel.setText("Untimed game");
        }

        panel.add(whiteTimeLabel);
        panel.add(blackTimeLabel);
        panel.setPreferredSize(panel.getMinimumSize());

        return panel;
    }

    private void updateClock(String whiteName, String blackName, JLabel whiteTimeLabel, JLabel blackTimeLabel) {
        boolean whiteTurn = board.getTurn();

        if (whiteTurn) {
            whiteClock.decr();
            whiteTimeLabel.setText(whiteClock.getTime());

            if (whiteClock.outOfTime()) {
                handleTimeOut(blackName + " wins by time!", blackName);
            }
        } else {
            blackClock.decr();
            blackTimeLabel.setText(blackClock.getTime());

            if (blackClock.outOfTime()) {
                handleTimeOut(whiteName + " wins by time!", whiteName);
            }
        }
    }

    private void handleTimeOut(String message, String winner) {
        timer.stop();
        int result = JOptionPane.showConfirmDialog(
                gameWindow,
                message + "\nPlay a new game?\nChoosing \"No\" quits the game.",
                winner + " wins!",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            new GameWindow();
        }
        gameWindow.dispose();
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setSize(label.getMinimumSize());
        return label;
    }

    private JPanel createControlButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));

        JButton instructionsButton = new JButton("How to play");
        JButton newGameButton = new JButton("New game");
        JButton quitButton = new JButton("Quit");

        instructionsButton.addActionListener(e ->
                JOptionPane.showMessageDialog(gameWindow,
                        "Move the chess pieces on the board by clicking\n"
                                + "and dragging. The game will watch out for illegal\n"
                                + "moves. You can win either by your opponent running\n"
                                + "out of time or by checkmating your opponent.\n"
                                + "\nGood luck, hope you enjoy the game!",
                        "How to play",
                        JOptionPane.PLAIN_MESSAGE)
        );

        newGameButton.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to begin a new game?",
                    "Confirm new game", JOptionPane.YES_NO_OPTION
            );

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        });

        quitButton.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to quit?",
                    "Confirm quit", JOptionPane.YES_NO_OPTION
            );

            if (n == JOptionPane.YES_OPTION) {
                if (timer != null) timer.stop();
                gameWindow.dispose();
            }
        });

        panel.add(instructionsButton);
        panel.add(newGameButton);
        panel.add(quitButton);
        panel.setPreferredSize(panel.getMinimumSize());

        return panel;
    }

    public void checkmateOccurred(int colorCode) {
        if (timer != null) timer.stop();

        String winner = (colorCode == 0) ? "White" : "Black";
        int result = JOptionPane.showConfirmDialog(
                gameWindow,
                winner + " wins by checkmate! Set up a new game?\n" +
                        "Choosing \"No\" lets you look at the final situation.",
                winner + " wins!",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(new StartMenu());
            gameWindow.dispose();
        }
    }

    public void showBoard(Board board) { /* placeholder */ }

    public String getMoveInput(Color player) {
        return "e2e4"; // placeholder
    }

    public void showInvalidMove() {
        System.out.println("Invalid move. Try again.");
    }

    public void showWinner(Color winner) {
        System.out.println("Winner: " + winner);
    }
}
