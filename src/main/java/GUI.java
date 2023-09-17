import games.Game;
import games.crosstheroad.CrossTheRoadGame;
import games.flappybird.FlappyBirdGame;
import games.jumpinggame.JumpingGame;
import games.pong.PongGame;
import games.snake.SnakeGame;
import games.tetris.board.TetrisGame;
import games.tictactoe.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUI extends JFrame {
    private final JPanel jPanel = new Menu();

    private int frameWidth = 400;
    private int frameHeight = 800;


    public GUI() {

        this.setBounds(600, 200, frameWidth, frameHeight);

        jPanel.setBackground(Color.BLACK);
        jPanel.setLayout(new GridLayout(0, 1));
        jPanel.setVisible(true);
        addKeyListener((KeyListener) jPanel);
        this.add(jPanel);


        this.setVisible(true);
    }

    private class Menu extends JPanel implements KeyListener {

        private final ArrayList<GameInfo> games = new ArrayList<>();

        private int gameNum = 0;
        private int selectedGame = 1;



        public Menu() {

        }

        private void addGame(GameInfo game) {
            games.add(game);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            games.clear();
            gameNum = 0;

            g.drawLine(frameWidth / 2, 0, frameWidth / 2, frameHeight);

            g.setColor(Color.RED);
            g.drawString("Chose a game to play", (frameWidth / 2) - (g.getFontMetrics().stringWidth("Chose a game to play") / 2), 20);

            g.setColor(Color.RED);

            addGame(new GameInfo("Snake", new SnakeGame()));
            addGame(new GameInfo("Pong", new PongGame()));
            addGame(new GameInfo("FlappyBird", new FlappyBirdGame()));
            addGame(new GameInfo("TicTacToe", new TicTacToe()));
            addGame(new GameInfo("JumpingGame", new JumpingGame()));
            addGame(new GameInfo("Tetris", new TetrisGame()));



            gameNum = games.size();

            drawGames(g);

            //Draw title
            g.drawRect(0, (int) (frameHeight * (0.1 * (selectedGame)) - 25), frameWidth, 40);


        }

        private void drawGames(Graphics g) {
            for (int i = 1; i <= games.size(); i++)
                g.drawString(games.get(i - 1).getName(), 10, (int) (frameHeight * (0.1 * i)));
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (selectedGame == gameNum)
                    selectedGame = 0;
                selectedGame++;
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                if (selectedGame == 1)
                    selectedGame = gameNum + 1;
                selectedGame--;
                repaint();
            } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                games.get(selectedGame - 1).getGame().startGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class GameInfo {
        private final String name;
        private final Game game;

        public GameInfo(String name, Game game) {
            this.name = name;
            this.game = game;
        }

        public String getName() {
            return name;
        }

        public Game getGame() {
            return game;
        }
    }

}

