package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartMenu implements Runnable {
    @Override
    public void run() {
        JFrame startWindow = new JFrame("Chess");
        startWindow.setLocation(300, 100);
        startWindow.setResizable(false);
        startWindow.setSize(260, 240);

        Box root = Box.createVerticalBox();
        startWindow.add(root);

        root.add(createTitlePanel());
        JTextField blackInput = new JTextField("Black", 10);
        JTextField whiteInput = new JTextField("White", 10);
        root.add(createPlayerPanel("bp.png", blackInput));
        root.add(createPlayerPanel("wp.png", whiteInput, startWindow));

        JComboBox<String> hours = new JComboBox<>(new String[]{"0", "1", "2", "3"});
        JComboBox<String> minutes = buildMinuteSecondCombo();
        JComboBox<String> seconds = buildMinuteSecondCombo();

        root.add(createTimerPanel(hours, minutes, seconds));
        root.add(createButtonPanel(startWindow, blackInput, whiteInput, hours, minutes, seconds));
        root.add(Box.createGlue());

        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel title = new JPanel();
        JLabel label = new JLabel("Chess");
        title.add(label);
        return title;
    }

    private JPanel createPlayerPanel(String imageFile, JTextField inputField) {
        JPanel panel = new JPanel();
        JLabel pieceIcon = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource(imageFile));
            pieceIcon.setIcon(new ImageIcon(img));
            panel.add(pieceIcon);
        } catch (Exception e) {
            System.out.println("Missing image: " + imageFile);
        }
        panel.add(inputField);
        return panel;
    }

    private JPanel createPlayerPanel(String imageFile, JTextField inputField, JFrame frame) {
        JPanel panel = createPlayerPanel(imageFile, inputField);
        try {
            Image img = ImageIO.read(getClass().getResource(imageFile));
            frame.setIconImage(img);
        } catch (Exception e) {
            // already handled above
        }
        return panel;
    }

    private JComboBox<String> buildMinuteSecondCombo() {
        String[] values = new String[60];
        for (int i = 0; i < 60; i++) {
            values[i] = String.format("%02d", i);
        }
        JComboBox<String> combo = new JComboBox<>(values);
        combo.setMaximumSize(combo.getPreferredSize());
        return combo;
    }

    private Box createTimerPanel(JComboBox<String> hours, JComboBox<String> minutes, JComboBox<String> seconds) {
        Box timerBox = Box.createHorizontalBox();
        hours.setMaximumSize(hours.getPreferredSize());
        timerBox.add(hours);
        timerBox.add(Box.createHorizontalStrut(10));
        timerBox.add(minutes);
        timerBox.add(Box.createHorizontalStrut(10));
        timerBox.add(seconds);
        return timerBox;
    }

    private Box createButtonPanel(JFrame window, JTextField blackInput, JTextField whiteInput,
                                  JComboBox<String> hours, JComboBox<String> minutes, JComboBox<String> seconds) {
        Box buttonBox = Box.createHorizontalBox();

        JButton start = new JButton("Start");
        start.addActionListener((ActionEvent e) -> {
            String bn = blackInput.getText();
            String wn = whiteInput.getText();
            int hh = Integer.parseInt((String) hours.getSelectedItem());
            int mm = Integer.parseInt((String) minutes.getSelectedItem());
            int ss = Integer.parseInt((String) seconds.getSelectedItem());

            new GameWindow(); // You can pass values later if needed
            window.dispose();
        });

        JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e ->
                JOptionPane.showMessageDialog(window,
                        "To begin a new game, input player names\n" +
                                "next to the pieces. Set the clocks and\n" +
                                "click \"Start\". Setting the timer to all\n" +
                                "zeroes begins a new untimed game.",
                        "How to play",
                        JOptionPane.PLAIN_MESSAGE)
        );

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> window.dispose());

        buttonBox.add(start);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(instructions);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(quit);
        return buttonBox;
    }
}
