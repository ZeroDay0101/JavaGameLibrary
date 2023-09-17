package games.utils;

import games.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Because most games implements very similar interfaces and the gui code implementation itself this is a template class that allow most games to quickly just inherit this code
 */
public abstract class DefaultPanelImpl extends JPanel implements Game, KeyListener {
    public final JFrame frame = new JFrame();
    private final LayoutManager layout;
    private final Color bgColor;
    protected int frameWidth;
    protected int frameHeight;
    private boolean isGameOver = false;


    public DefaultPanelImpl(int frameWidth, int frameHeight, LayoutManager layout, Color bgColor) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.layout = layout;
        this.bgColor = bgColor;
    }

    @Override
    public void startGame() {
        frame.setBounds(300, 200, frameWidth, frameHeight);
        frame.add(this);

        frame.addKeyListener(this);
        frame.setVisible(true);
        this.setBackground(bgColor);
        this.setLayout(layout);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public abstract void keyPressed(KeyEvent e);

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
