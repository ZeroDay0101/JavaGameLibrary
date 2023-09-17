package games.tetris.gui;


import games.tetris.board.TetrisGame;

import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {

    public GameOver(JFrame frame) {
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        JButton retry = new JButton("Retry");
        retry.setBackground(Color.BLACK);
        retry.setForeground(Color.RED);
        this.setLayout(new GridBagLayout());
        this.add(retry, new GridBagConstraints());

        retry.addActionListener(e -> {
            //Restart game
            frame.dispose();
            new TetrisGame().startGame();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Game Over", 420 / 2 - 50, 100);
    }
}
