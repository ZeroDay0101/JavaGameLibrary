package games.tictactoe;

import games.utils.DefaultPanelImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends DefaultPanelImpl {

    private final ArrayList<Point> x = new ArrayList<>();
    private final ArrayList<Point> o = new ArrayList<>();
    private boolean xTurn = true;
    private boolean isGameOver = false;

    private boolean wasGameOverMethodAlreadyExecuted = false;

    public TicTacToe() {
        super(600, 600, new FlowLayout(FlowLayout.CENTER), Color.BLACK);
    }

    @Override
    public void startGame() {
        super.startGame();

        frameHeight = getHeight();
        frameWidth = getWidth();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                Point toAdd = new Point(me.getX() / (frameWidth / 3) + 1, me.getY() / (frameHeight / 3) + 1);

                List<Point> tmp = new ArrayList<>(x);
                tmp.addAll(o);

                for (Point point : tmp)
                    if (toAdd.x == point.x && toAdd.y == point.y)
                        return;

                if (xTurn)
                    x.add(new Point(me.getX() / (frameWidth / 3) + 1, me.getY() / (frameHeight / 3) + 1));
                else
                    o.add(new Point(me.getX() / (frameWidth / 3) + 1, me.getY() / (frameHeight / 3) + 1));


                if (!isGameOver()) {
                    xTurn = !xTurn;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(0, frameHeight / 3, frameWidth, frameHeight / 3);
        g.drawLine(0, frameHeight / 3 * 2, frameWidth, frameHeight / 3 * 2);

        g.drawLine(frameWidth / 3, 0, frameWidth / 3, frameHeight);
        g.drawLine(frameWidth / 3 * 2, 0, frameWidth / 3 * 2, frameHeight);

        int width = frameWidth / 3;
        int height = frameHeight / 3;

        for (Point point : x) {
            g.drawLine(frameWidth / 3 * (point.x - 1), frameHeight / 3 * (point.y - 1), frameWidth / 3 * (point.x), frameHeight / 3 * (point.y));
            g.drawLine(frameWidth / 3 * (point.x), frameHeight / 3 * (point.y - 1), frameWidth / 3 * (point.x - 1), frameHeight / 3 * (point.y));
        }

        for (Point point : o)
            g.drawOval(((point.x * (frameWidth / 3)) - frameWidth / 3 / 2) - width / 2, ((point.y * (frameHeight / 3)) - frameHeight / 3 / 2) - height / 2, width, height);

        if (isGameOver) {
            String won = xTurn ? "X" : "O";

            g.setFont(new Font("Serif", Font.PLAIN, 34));
            g.setColor(Color.RED);

            g.drawString(won + " won!", frameWidth / 2 - g.getFontMetrics().stringWidth(won + " won!") / 2, frameHeight / 2);
            if (!wasGameOverMethodAlreadyExecuted) {
                JButton button = new JButton("Retry");
                button.setBackground(Color.PINK);
                add(button);

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();

                        new TicTacToe().startGame();
                    }
                });
            }

            wasGameOverMethodAlreadyExecuted = true;

            repaint();
            revalidate();
        }
    }

    private boolean isGameOver() {
        List<Point> list = xTurn ? x : o;

        int repetitions = 0;


        for (int y = 1; y <= 3; y++) {
            for (int x = 1; x <= 3; x++) {
                for (Point point : list) {
                    if (point.y == y && point.x == x) repetitions++;
                }
            }
            if (!isGameOver)
                isGameOver = repetitions == 3;

            repetitions = 0;
        }

        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                for (Point point : list) {
                    if (point.y == y && point.x == x) repetitions++;
                }
            }
            if (!isGameOver)
                isGameOver = repetitions == 3;

            repetitions = 0;
        }
        for (int z = 1; z <= 3; z++) {
            for (Point point : list) {
                if (point.y == z && point.x == z) {
                    repetitions++;
                }
            }
            if (!isGameOver)
                isGameOver = repetitions == 3;
        }
        repetitions = 0;

        for (int x = 1, y = 3; x <= 3; x++, y--) {
            for (Point point : list) {
                if (point.y == y && point.x == x) {
                    repetitions++;
                }
            }
            if (!isGameOver)
                isGameOver = repetitions == 3;


        }


        return isGameOver;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }


}
