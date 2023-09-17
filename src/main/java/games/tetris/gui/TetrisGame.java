package games.tetris.gui;


import games.utils.DefaultPanelImpl;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TetrisGame extends DefaultPanelImpl {
    private final games.tetris.board.TetrisGame tetrisGame;

    public TetrisGame(games.tetris.board.TetrisGame tetrisGame) {
        super(420 + 180, 839, new BorderLayout(), Color.BLACK);
        this.tetrisGame = tetrisGame;
        frame.add(new Info(tetrisGame), BorderLayout.EAST);
    }

    @Override
    public void startGame() {
        super.startGame();

        tetrisGame.getBoardManager().startGame();
    }

    public games.tetris.board.TetrisGame getBoard() {
        return tetrisGame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (tetrisGame.getGameState().isGameOver())
            return;
        if (e.getKeyCode() == KeyEvent.VK_A) {
            if (tetrisGame.canTetrominoeMove(tetrisGame.getCurrentTetrominoe(), -1, 0))
                tetrisGame.getCurrentTetrominoe().move(-1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            if (tetrisGame.canTetrominoeMove(tetrisGame.getCurrentTetrominoe(), 1, 0))
                tetrisGame.getCurrentTetrominoe().move(1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_W)
            tetrisGame.getCurrentTetrominoe().rotate(tetrisGame.getBoard());
        else if (e.getKeyCode() == KeyEvent.VK_S)
            if (tetrisGame.canTetrominoeMove(tetrisGame.getCurrentTetrominoe(), 0, 1))
                tetrisGame.getCurrentTetrominoe().move(0, 1);
            else return;
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            while (tetrisGame.canTetrominoeMove(tetrisGame.getCurrentTetrominoe(), 0, 1)) {
                tetrisGame.getCurrentTetrominoe().move(0, 1);
            }
            tetrisGame.getBoardManager().removeTetrominoe(tetrisGame.getCurrentTetrominoe());
            tetrisGame.getBoardManager().addTetrominoe();
            frame.repaint();
        }


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLines(g);
        drawShapes(g);
        drawShadowShapes(g);
    }

    private void drawLines(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < 400; i += 400 / 10) {
            g2.drawLine(i, 0, i, 800);
        }
        for (int i = 0; i < 800; i += 400 / 10) {
            g2.drawLine(0, i, 400, i);
        }

    }

    private void drawShapes(Graphics g) {
        if (getBoard().getCurrentTetrominoe() != null) {
            g.setColor(getBoard().getCurrentTetrominoe().getColor());
            for (int i = 0; i < getBoard().getCurrentTetrominoe().getSquaresList().length; i++)
                g.fillRect(getBoard().getCurrentTetrominoe().getSquaresList()[i].getRow() * 40, getBoard().getCurrentTetrominoe().getSquaresList()[i].getCol() * 40, 39, 39);

        }

        for (int i = 0; i < tetrisGame.getBoard().length; i++) {
            for (int j = 0; j < tetrisGame.getBoard()[i].length; j++) {
                if (tetrisGame.getBoard()[i][j] != null) {
                    g.setColor(tetrisGame.getBoard()[i][j].getColor());
                    g.fillRect(i * 40, j * 40, 39, 39);
                }
            }
        }
        repaint();
    }

    public void drawShadowShapes(Graphics g) {
        if (getBoard().getCurrentTetrominoe() == null)
            return;
        int z = 1;
        while (tetrisGame.canTetrominoeMove(tetrisGame.getCurrentTetrominoe(), 0, z)) {
            z++;
        }
        z--;

        if (getBoard().getCurrentTetrominoe() != null) {
            g.setColor(getBoard().getCurrentTetrominoe().getColor());
            for (int i = 0; i < getBoard().getCurrentTetrominoe().getSquaresList().length; i++)
                g.drawRect(getBoard().getCurrentTetrominoe().getSquaresList()[i].getRow() * 40, (getBoard().getCurrentTetrominoe().getSquaresList()[i].getCol() + z) * 40, 38, 38);
        }
        repaint();
    }

}
