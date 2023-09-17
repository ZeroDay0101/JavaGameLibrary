package games.flappybird;

import games.utils.DefaultPanelImpl;
import games.utils.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class FlappyBirdGame extends DefaultPanelImpl {

    private final Stack<Column> columns = new Stack<>();
    private final Timer timer = new Timer();
    private final int birdHeight = 20;
    private final int birdWidth = 20;
    private final int jumpHeight = 60;
    private final int colWidth = 80;
    private final int colGapHeight = (int) (jumpHeight * 1.4);
    private final int colGapWidth = 320;
    private final int grassHeight = 40;
    Timer colisionCheckingTimer = new Timer();
    JLabel label = new JLabel();
    private int birdPositionY = frameHeight / 2;
    private Direction direction = Direction.DOWN;
    private int jumpProgress = 0;
    private int activeColumnIndexUp = 0;
    private int activeColumnIndexDown = 1;
    private boolean isGameOver = false;
    private int score = 0;

    public FlappyBirdGame() {
        super(700, 700, new FlowLayout(FlowLayout.CENTER), Color.decode("#05D5FF"));
    }

    @Override
    public void startGame() {
        super.startGame();

        spawnNewColumn();
        spawnNewColumn();
        spawnNewColumn();
        spawnNewColumn();
        spawnNewColumn();

        frame.setResizable(false);

        frameWidth = getSize().width;
        frameHeight = getSize().height;

        setUpTimer();
        startColisionCheckingTimer();


    }

    private void setUpTimer() {
        int delay = 8;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (direction == Direction.DOWN) {
                    birdPositionY += 2.8;
                } else {
                    birdPositionY -= 4;
                    jumpProgress += 4;
                    if (jumpProgress >= jumpHeight) {
                        direction = Direction.DOWN;
                        jumpProgress = 0;
                    }
                    ;
                }
                moveColumns();


                for (int i = columns.size() - 1; i >= 0; i--) {
                    if (columns.get(i).getPosition().x <= 0) {
                        columns.remove(i);
                        columns.remove(i - 1);

                        activeColumnIndexUp -= 2;
                        activeColumnIndexDown -= 2;

                        spawnNewColumn();
                        i--;
                    }
                }
                if (columns.get(activeColumnIndexUp).getPosition().x < (frameWidth / 2 - colWidth - birdWidth)) {
                    activeColumnIndexUp += 2;
                    activeColumnIndexDown += 2;

                    score++;
                }
                repaint();
            }
        }, 0, delay);
    }

    public void startColisionCheckingTimer() {
        colisionCheckingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isCollidingWithColumns())
                    isGameOver = true;
                if (isCollidingWithBorder())
                    isGameOver = true;

            }
        }, 0, 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            final BufferedImage image = ImageIO.read(new File("src\\main\\resources\\bird.png"));
            g.drawImage(image, frameWidth / 2, birdPositionY, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.setColor(Color.GREEN);
        for (Column column : columns) {
            if (column.getPosition().x == columns.get(activeColumnIndexUp).getPosition().x) {
                g.setColor(Color.PINK);
            }
            g.fillRect(column.getPosition().x, column.getPosition().y, colWidth, column.getColLength());
            g.setColor(Color.GREEN);
        }

        g.setColor(Color.GREEN);
        g.fillRect(0, frameHeight - grassHeight, frameWidth, grassHeight + 50); //+50 because it didnt render good idk why.

        g.setColor(Color.decode("#4C2303"));
        //  g.fillRect(0,frameHeight - 100,frameWidth,20);

        if (isGameOver && direction != null) {
            JButton button = new JButton("Retry");
            button.setBackground(Color.PINK);
            add(button);
            timer.cancel();
            colisionCheckingTimer.cancel();
            direction = null;
            repaint();
            revalidate();

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();

                    new FlappyBirdGame().startGame();
                }
            });
        }
        g.setFont(new Font("Serif", Font.PLAIN, 34));
        g.setColor(Color.RED);

        g.drawString(String.valueOf(score), frameWidth / 2 - g.getFontMetrics().stringWidth(String.valueOf(score)) / 2, 50);

    }

    private void spawnNewColumn() {
        final Random random = new Random();
        int colLength = random.nextInt((frameHeight - grassHeight) - colGapHeight - 40); //40 is a additional gap so first column won't take all space not leaving any place for 2nd column.
        int lastColPosX = columns.isEmpty() ? frameWidth : (columns.lastElement().getPosition().x) + colGapWidth;

        columns.add(new Column(new Point(lastColPosX, 0), colLength));

        columns.add(new Column(new Point(lastColPosX, colLength + colGapHeight), 600));
    }

    private void moveColumns() {
        for (Column column : columns)
            column.getPosition().x -= 1;
    }

    private boolean isCollidingWithColumns() {
        return columns.get(activeColumnIndexUp).getPosition().x - birdWidth <= frameWidth / 2 &&
                columns.get(activeColumnIndexUp).getPosition().x + colWidth > frameWidth / 2
                && (birdPositionY <= columns.get(activeColumnIndexUp).getColLength() || birdPositionY >= columns.get(activeColumnIndexDown).getPosition().y - birdHeight);
    }

    private boolean isCollidingWithBorder() {
        return birdPositionY > frameHeight - grassHeight - birdHeight || birdPositionY < 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        direction = Direction.UP;
    }
}
