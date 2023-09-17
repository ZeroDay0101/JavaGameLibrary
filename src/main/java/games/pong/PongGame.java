package games.pong;

import games.utils.DefaultPanelImpl;
import games.utils.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PongGame extends DefaultPanelImpl {

    private final int paddleWidth = 20;
    private final int paddleHeight = 70;
    private final int paddleSpeed = 5;
    private Ball ball = new Ball();
    private Timer timer = new Timer();
    private ArrayList<Point> trackOfABall = new ArrayList<>(); //Just a little "debug" feature measuring track of a ball. Not rendered in a real game.
    private int bounceCount = 0;
    private Paddle paddleRed = new Paddle(new Point(frameWidth - paddleWidth, frameHeight / 2));
    private Paddle paddleBlue = new Paddle(new Point(0, frameHeight / 2));
    private int blueScore = 0;
    private int redScore = 0;


    public PongGame() {
        super(900, 600, new BorderLayout(), Color.BLACK);
    }

    @Override
    public void startGame() {
        super.startGame();
        blueScore = 0;
        redScore = 0;
        bounceCount = 0;

        ball = new Ball();

        paddleRed = new Paddle(new Point(getSize().width - paddleWidth, frameHeight / 2));
        paddleBlue = new Paddle(new Point(0, frameHeight / 2));
        if (timer != null) {
            timer.cancel();
            timer = new Timer();
        }
        startTimer();

        throwBall();
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.CYAN);

        g.drawOval(ball.getPosition().x, ball.getPosition().y, 20, 20);

        //Render track of a ball
//        for (Point pos1 : trackOfABall)
//            g.drawRect(pos1.x, pos1.y, 1, 1);

        g.setColor(paddleBlue.getPaddleColor());
        g.drawRect(paddleBlue.getPosition().x, paddleBlue.getPosition().y, paddleWidth, paddleHeight);

        g.setColor(paddleRed.getPaddleColor());
        g.drawRect(paddleRed.getPosition().x, paddleRed.getPosition().y, paddleWidth, paddleHeight);

        g.setFont(new Font("Serif", Font.PLAIN, 34));

        g.setColor(Color.BLUE);
        g.drawString(String.valueOf(blueScore), frameWidth - frameWidth / 3 - g.getFontMetrics().stringWidth(String.valueOf(blueScore)) / 2, 50);

        g.setColor(Color.RED);
        g.drawString(String.valueOf(redScore), frameWidth / 3 - g.getFontMetrics().stringWidth(String.valueOf(redScore)) / 2, 50);

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {
            paddleBlue.setDirection(Direction.UP);
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            paddleBlue.setDirection(Direction.DOWN);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            paddleRed.setDirection(Direction.UP);
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddleRed.setDirection(Direction.DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            paddleBlue.setDirection(null);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            paddleRed.setDirection(null);
        }

    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (ball.getPosition().x >= paddleBlue.getPosition().x && ball.getPosition().x <= paddleBlue.getPosition().x + paddleWidth && ball.getPosition().y >= paddleBlue.getPosition().y && ball.getPosition().y <= paddleBlue.getPosition().y + paddleHeight) {
                    ball.getPosition().x = (paddleBlue.getPosition().x + paddleWidth);
                    if (bounceCount % 4 == 0) {
                        if (ball.getVelocity().y >= 0)
                            ball.getVelocity().y++;
                        else
                            ball.getVelocity().y--;

                        if (ball.getVelocity().x >= 0)
                            ball.getVelocity().x++;
                        else
                            ball.getVelocity().x--;
                    }

                    ball.setVelocityX(-ball.getVelocity().x);


                    bounceCount++;
                }
                if (ball.getPosition().x >= paddleRed.getPosition().x && ball.getPosition().x <= paddleRed.getPosition().x + paddleWidth && ball.getPosition().y >= paddleRed.getPosition().y && ball.getPosition().y <= paddleRed.getPosition().y + paddleHeight) {
                    ball.getPosition().x = paddleRed.getPosition().x;
                    if (bounceCount % 4 == 0) {
                        if (ball.getVelocity().y >= 0)
                            ball.getVelocity().y++;
                        else
                            ball.getVelocity().y--;


                        if (ball.getVelocity().x >= 0)
                            ball.getVelocity().x++;
                        else
                            ball.getVelocity().x--;
                    }

                    ball.setVelocityX(-ball.getVelocity().x);

                    bounceCount++;
                }

                if (ball.getPosition().x > frameWidth || ball.getPosition().x < 0) {
                    addPointAndResetGame();
                }

                if (ball.getPosition().y > frameHeight || ball.getPosition().y < 0) {
                    ball.setVelocityY(-ball.getVelocity().y);
                    bounceCount++;
                }

                ball.setPosition(new Point(ball.getPosition().x + ball.getVelocity().x, ball.getPosition().y + ball.getVelocity().y));

//                if (trackOfABall.size() >= 30)
//                    trackOfABall.remove(trackOfABall.get(0));

                // trackOfABall.add(ball.getPosition());

                movePaddles();

                repaint();
            }
        }, 0, 7);
    }

    private void movePaddles() {
        if (paddleBlue.getDirection() == Direction.UP && paddleBlue.getPosition().y > 0) {
            paddleBlue.getPosition().y -= paddleSpeed;
        } else if (paddleBlue.getDirection() == Direction.DOWN && paddleBlue.getPosition().y < frameHeight - paddleHeight) {
            paddleBlue.getPosition().y += paddleSpeed;
        }
        if (paddleRed.getDirection() == Direction.UP && paddleRed.getPosition().y > 0) {
            paddleRed.getPosition().y -= paddleSpeed;
        } else if (paddleRed.getDirection() == Direction.DOWN && paddleRed.getPosition().y < frameHeight - paddleHeight) {
            paddleRed.getPosition().y += paddleSpeed;
        }


    }

    private void addPointAndResetGame() {
        if (ball.getPosition().y < getSize().width / 2)
            blueScore++;
        else
            redScore++;

        bounceCount = 0;
        ball = new Ball();
        throwBall();
    }

    private void throwBall() {
        Random random = new Random();

        int isNegativeX = random.nextBoolean() ? 1 : -1;
        int isNegativeY = random.nextBoolean() ? 1 : -1;


        int x = frameWidth / 2;
        int y = random.nextInt(frameHeight);

        ball.setPosition(new Point(x, y));

        ball.setVelocity(new Point(ball.getVelocity().x * isNegativeX, ball.getVelocity().y * isNegativeY));
    }


}
