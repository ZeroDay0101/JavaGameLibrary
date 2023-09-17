package games.snake;

import games.utils.DefaultPanelImpl;
import games.utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeGame extends DefaultPanelImpl {
    //Frame,panel code

    private final Stack<Point> snakePartsPositions = new Stack<>();
    private final int snakeRectSize = 10;
    private final Color snakeColor = Color.GREEN;
    private final Color appleColor = Color.RED;
    private final int snakeStartLength = 3;
    //Game code
    private Timer timer = new Timer();
    private int score = 0;
    private Direction direction = Direction.RIGHT;
    private Direction queuedDirection = Direction.RIGHT;
    private Point applePosition;

    private boolean isGameOver = false;

    public SnakeGame() {
        super(600, 600, new FlowLayout(FlowLayout.CENTER), Color.BLACK);
    }


    @Override
    public void startGame() {
        super.startGame();
        spawnNewApple();
        snakePartsPositions.add(new Point(150, 150));

        for (int i = 0; i < snakeStartLength; i++) {
            addTail();
        }

        setUpTimer();
    }

    private void setUpTimer() {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                for (int i = snakePartsPositions.size() - 1; i > 0; i--) {
                    snakePartsPositions.set(i, snakePartsPositions.get(i - 1));
                }
                direction = queuedDirection;

                if (direction == Direction.RIGHT)
                    snakePartsPositions.set(0, new Point(snakePartsPositions.firstElement().x + snakeRectSize, snakePartsPositions.firstElement().y));
                else if (direction == Direction.LEFT)
                    snakePartsPositions.set(0, new Point(snakePartsPositions.firstElement().x - snakeRectSize, snakePartsPositions.firstElement().y));
                else if (direction == Direction.UP)
                    snakePartsPositions.set(0, new Point(snakePartsPositions.firstElement().x, snakePartsPositions.firstElement().y - snakeRectSize));
                else if (direction == Direction.DOWN)
                    snakePartsPositions.set(0, new Point(snakePartsPositions.firstElement().x, snakePartsPositions.firstElement().y + snakeRectSize));

                if (isSnakeAtApplePosition()) {
                    addTail();
                    spawnNewApple();
                    score++;
                }
                if (isSnakeCollidingWithItsOwnTail()) {
                    isGameOver = true;
                }
                if (isSnakeCollidingWithBorder()) {
                    isGameOver = true;
                }


                frame.repaint();

            }
        }, 0, 50);
    }

    private boolean isSnakeAtApplePosition() {
        return snakePartsPositions.firstElement().x == applePosition.x && snakePartsPositions.firstElement().y == applePosition.y;
    }

    private boolean isSnakeCollidingWithItsOwnTail() {
        for (int i = 1; i < snakePartsPositions.size(); i++) {
            if (snakePartsPositions.get(i).x == snakePartsPositions.get(0).x && snakePartsPositions.get(i).y == snakePartsPositions.get(0).y)
                return true;
        }
        return false;
    }

    private boolean isSnakeCollidingWithBorder() {
        return (snakePartsPositions.get(0).x > frameWidth || snakePartsPositions.get(0).x < 0) || (snakePartsPositions.get(0).y > frameHeight || snakePartsPositions.get(0).y < 0);
    }

    public void spawnNewApple() {
        Random random = new Random();
        int x = random.nextInt(frameWidth), y = random.nextInt(frameHeight);
        while (x % 20 != 0 || x < 10 || x > 550)
            x = random.nextInt(frameWidth);
        while (y % 20 != 0 || y < 10 || y > 550)
            y = random.nextInt(frameHeight);

        applePosition = new Point(x, y);
    }

    public void addTail() {
        snakePartsPositions.add(new Point(snakePartsPositions.lastElement().x - snakeRectSize, snakePartsPositions.lastElement().y));
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && direction != Direction.DOWN)
            queuedDirection = Direction.UP;
        else if (e.getKeyCode() == KeyEvent.VK_S && direction != Direction.UP)
            queuedDirection = Direction.DOWN;
        else if (e.getKeyCode() == KeyEvent.VK_A && direction != Direction.RIGHT)
            queuedDirection = Direction.LEFT;
        else if (e.getKeyCode() == KeyEvent.VK_D && direction != Direction.LEFT)
            queuedDirection = Direction.RIGHT;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(snakeColor);

        for (Point point : snakePartsPositions) {
            if (point == snakePartsPositions.get(0)) g.setColor(Color.CYAN);
            g.fillRect(point.x, point.y, snakeRectSize, snakeRectSize);
            g.setColor(snakeColor);
        }
        g.setColor(appleColor);

        g.fillRect(applePosition.x, applePosition.y, snakeRectSize, snakeRectSize);

        g.setFont(new Font("Serif", Font.PLAIN, 34));
        g.setColor(Color.RED);

        g.drawString(String.valueOf(score), frameWidth / 2 - g.getFontMetrics().stringWidth(String.valueOf(score)) / 2, 50);

        if (isGameOver) {
            g.setFont(new Font("Serif", Font.PLAIN, 34));
            g.setColor(Color.RED);
            g.drawString("GameOver", frameWidth / 2 - g.getFontMetrics().stringWidth("GameOver") / 2, frameHeight / 2);

            if (timer != null) {
                JButton button = new JButton("Retry");
                button.setBackground(Color.PINK);
                add(button);
                timer.cancel();
                timer = null;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();

                        new SnakeGame().startGame();
                    }
                });

                super.repaint();
                super.revalidate();
            }
        }
    }
}
