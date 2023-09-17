package games.jumpinggame;

import games.jumpinggame.springboards.SpringBoard;
import games.utils.DefaultPanelImpl;
import games.utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public final class JumpingGame extends DefaultPanelImpl {
    private final int jumpHeight = 300;
    private final int boostedJumpHeight = jumpHeight * 2;
    private final int minBoardWidth = 60;
    private final int springBoardHeight = 20;
    private final int shrinkBoardPx = 5;
    private final int characterWidth = 20;
    private final int characterHeight = 20;
    private final ArrayList<SpringBoard> springBoards = new ArrayList<>();
    private Point characterPosition = new Point(frameWidth / 2, 0);
    private int springBoardWidth = 110;
    private int springBoardSideWaysMovePerPx = 1;
    private Timer timer = new Timer();
    private boolean alreadyShrinked = false;

    private int jumpProgress = 0;
    private Direction characterDirection = Direction.UP;

    private Direction characterDirectionSideWays = null;

    private boolean isJumpBoosted = false;

    private int jumpPxPerTick = 2;
    private int score = 0;
    private SpringBoard furthestDownBoard;

    private boolean isGameOver = false;

    private SpringBoard removeWhenMovedDown = null;

    public JumpingGame() {
        super(600, 700, new FlowLayout(), Color.BLACK);
    }

    @Override
    public void startGame() {
        super.startGame();
        characterPosition = new Point(getSize().width / 2, 0);
        generateSpringBoard();


//       springBoards.add(new SpringBoard(new Point(0,frameHeight - springBoardHeight * 3 ), true, false));
//       furthestDownBoard = springBoards.get(springBoards.size() - 1);
//       springBoards.add(new SpringBoard(new Point(frameWidth - springBoardWidth,springBoards.get(springBoards.size() -1).getPosition().y - (jumpHeight - springBoardHeight - characterHeight)), true, true));


        setUpTimer();
    }

    private void setUpTimer() {

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (characterDirection != Direction.UP) {
                    for (SpringBoard springBoard : springBoards) {
                        if (characterPosition.x + characterWidth >= springBoard.getPosition().x && characterPosition.x <= springBoard.getPosition().x + springBoardWidth && characterPosition.y >= springBoard.getPosition().y && characterPosition.y <= springBoard.getPosition().y + springBoardHeight) {
                            characterDirection = Direction.UP;
                            if (springBoard != furthestDownBoard) {
                                score++;
                                if (alreadyShrinked) alreadyShrinked = false;
                            }
                            if (springBoard.isDestroyOnTouch())
                                removeWhenMovedDown = springBoard;
                            if (springBoard.isJumpBoosted())
                                isJumpBoosted = true;

                            furthestDownBoard = springBoard;
                            break;
                        }
                    }
                }


                if (score % 10 == 0 && score != 0 && !alreadyShrinked && (springBoardWidth - shrinkBoardPx >= minBoardWidth)) {
                    alreadyShrinked = true;
                    springBoardWidth -= 5;
                    int maxSpringBoardMovePerPx = 2;
                    if (score % 50 == 0 && springBoardSideWaysMovePerPx < maxSpringBoardMovePerPx)
                        springBoardSideWaysMovePerPx++;
                }


                //Move all boards down
                if (furthestDownBoard.getPosition().y < frameHeight - 90) {
                    int pxMovingPerTick = 5;
                    for (SpringBoard springBoard : springBoards) {
                        springBoard.getPosition().setLocation(new Point(springBoard.getPosition().x, springBoard.getPosition().y + pxMovingPerTick));
                    }
                    characterPosition.y += pxMovingPerTick;
                } else { //Move character down or up
                    if (removeWhenMovedDown != null) {
                        springBoards.remove(removeWhenMovedDown);
                        generateSpringBoard();
                        removeWhenMovedDown = null;
                    }
                    if (characterDirection == Direction.DOWN)
                        characterPosition.y += jumpPxPerTick;
                    else {
                        characterPosition.y -= jumpPxPerTick;

                        jumpProgress += jumpPxPerTick;

                        if ((isJumpBoosted && jumpProgress >= boostedJumpHeight) || !isJumpBoosted && jumpProgress >= jumpHeight) {
                            characterDirection = Direction.DOWN;
                            isJumpBoosted = false;
                            jumpProgress = 0;
                        }
                    }
                }


                if (characterDirectionSideWays != null) {
                    int sideWaysMovePerPx = 3;
                    if (characterDirectionSideWays == Direction.LEFT)
                        characterPosition.x -= sideWaysMovePerPx;
//                        characterPosition.x -= Math.ceil((float) (600 - springBoardWidth) / ((double) jumpHeight / jumpPxPerTick));
                    else
//                        characterPosition.x += Math.ceil((float) (600 - springBoardWidth) / ((double) jumpHeight / jumpPxPerTick));
                        characterPosition.x += sideWaysMovePerPx;
                }
                for (SpringBoard springBoard : springBoards) {
                    if (springBoard.isDoesMove()) {
                        if (springBoard.getPosition().x + springBoardWidth >= frameWidth && springBoard.getPosition().x + springBoardWidth <= frameWidth + springBoardSideWaysMovePerPx)
                            springBoard.setSideWaysDirection(Direction.LEFT);
                        else if (springBoard.getPosition().x <= 0 && springBoard.getPosition().x >= -springBoardSideWaysMovePerPx)
                            springBoard.setSideWaysDirection(Direction.RIGHT);

                        if (springBoard.getSideWaysDirection() == Direction.RIGHT)
                            springBoard.getPosition().x += springBoardSideWaysMovePerPx;
                        else
                            springBoard.getPosition().x -= springBoardSideWaysMovePerPx;

                    }

                }

                isGameOver = isGameOver();
                repaint();

                removeSpringBoardsOutOfBoundsAndAddNewOnes();
            }
        }, 0, 3);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && jumpProgress < jumpHeight)
            characterDirection = Direction.UP;

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);

        if (e.getKeyChar() == 'a')
            characterDirectionSideWays = Direction.LEFT;
        else if (e.getKeyChar() == 'd')
            characterDirectionSideWays = Direction.RIGHT;

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyChar() == 'a')
            characterDirectionSideWays = null;
        else if (e.getKeyChar() == 'd')
            characterDirectionSideWays = null;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.CYAN);
        g.fillRect(characterPosition.x, characterPosition.y, characterWidth, characterHeight);

        for (int i = springBoards.size() - 1; i >= 0; i--) {
            SpringBoard springBoard = springBoards.get(i);
            if (springBoard == furthestDownBoard)
                g.setColor(Color.PINK);
            if (springBoard.isDestroyOnTouch()) {
                g.setColor(Color.WHITE);
                g.drawOval(springBoard.getPosition().x, springBoard.getPosition().y, springBoardWidth, springBoardHeight);
            } else if (springBoard.isJumpBoosted()) {
                g.setColor(Color.GREEN);
                g.drawRect(springBoard.getPosition().x, springBoard.getPosition().y, springBoardWidth, springBoardHeight);
            } else
                g.drawRect(springBoard.getPosition().x, springBoard.getPosition().y, springBoardWidth, springBoardHeight);

            g.setColor(Color.CYAN);
        }

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

                        new JumpingGame().startGame();
                    }
                });

                super.repaint();
                super.revalidate();
            }
        }
    }

    private void generateSpringBoard() {
        Random random = new Random();
        int rep = 0;
        while (rep == 0 || springBoards.size() < 5) {
            int randWidth = random.nextInt(frameWidth - springBoardWidth);
            int randHeight = ThreadLocalRandom.current().nextInt(jumpHeight / 2, (jumpHeight - springBoardHeight - characterHeight));
            int isMoving = random.nextInt(Math.max(8 - (score / 12), 1));
            int isDestroyOnTouch = random.nextInt(Math.max(5 - score / 7, 1));
            int isJumpBoosted = random.nextInt(9);
            if (springBoards.isEmpty()) {
                springBoards.add(new SpringBoard(new Point(randWidth, frameHeight - jumpHeight), false, false, false));
                furthestDownBoard = springBoards.get(0);
            } else
                springBoards.add(new SpringBoard(new Point(randWidth, springBoards.get(springBoards.size() - 1).getPosition().y - randHeight), isDestroyOnTouch == 0, isMoving == 0, isJumpBoosted == 0));
            rep++;
        }
    }

    private void removeSpringBoardsOutOfBoundsAndAddNewOnes() {
        for (int i = springBoards.size() - 1; i >= 0; i--) {
            SpringBoard springBoard = springBoards.get(i);

            if (springBoard.getPosition().y > frameHeight) {
                springBoards.remove(springBoard);
                generateSpringBoard();
            }
        }
    }

    private boolean isGameOver() {
        return characterPosition.y > frameHeight;
    }
}
