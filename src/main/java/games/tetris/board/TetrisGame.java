package games.tetris.board;


import games.Game;
import games.tetris.board.squares.Square;
import games.tetris.board.squares.tetrominoes.*;
import games.tetris.gui.GameOver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class TetrisGame implements Game {
    private final BoardManager boardManager = new BoardManager(this);

    private final GameState gameState = new GameState();
    private final games.tetris.gui.TetrisGame frame = new games.tetris.gui.TetrisGame(this);
    private final Square[][] board = new Square[10][20];
    private final ArrayList<Tetrominoe> futureshapes = new ArrayList<>();
    private final ArrayList<Tetrominoe> bagofshapes = new ArrayList<>();
    public Tetrominoe currentTetrominoe = null;

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Square[][] getBoard() {
        return board;
    }

    public Tetrominoe getCurrentTetrominoe() {
        return currentTetrominoe;
    }

    public ArrayList<Tetrominoe> getFutureshapes() {
        return futureshapes;
    }

    @Override
    public void startGame() {
        frame.startGame();
    }


    public boolean canTetrominoeMove(Tetrominoe tetrominoe, int row, int col) {
        for (int i = 0; i < tetrominoe.getSquaresList().length; i++) {
            if (tetrominoe.getSquaresList()[i].getCol() + col > 19 || tetrominoe.getSquaresList()[i].getRow() + row > 9 || tetrominoe.getSquaresList()[i].getRow() + row < 0 || getBoard()[tetrominoe.getSquaresList()[i].getRow() + row][tetrominoe.getSquaresList()[i].getCol() + col] != null) {
                return false;
            }
        }
        return true;
    }


    private void checkSquareRows() {
        int columnToRemove = 0;

        loop:
        for (int i = getBoard()[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < getBoard().length; j++) {
                if (getBoard()[j][i] == null) break;
                if (j == 9) {
                    columnToRemove = i;
                    for (int k = 0; k < getBoard().length; k++)
                        getBoard()[k][i] = null;
                    break loop;
                }
            }
        }
        if (columnToRemove > 0) {
            gameState.increaseLinescleared(1);
            gameState.increaseLinesclearedinarow(1);
            for (int i = columnToRemove - 1; i >= 0; i--) {
                for (int j = 0; j < getBoard().length; j++) {
                    if (getBoard()[j][i] != null) {
                        getBoard()[j][i + 1] = getBoard()[j][i];
                        getBoard()[j][i] = null;
                    }
                }
            }
        } else {
            if (gameState.getLinesclearedinarow() == 1)
                gameState.increaseScore(100);
            else if (gameState.getLinesclearedinarow() == 2)
                gameState.increaseScore(300);
            else if (gameState.getLinesclearedinarow() == 3)
                gameState.increaseScore(500);
            else if (gameState.getLinesclearedinarow() >= 4)
                gameState.increaseScore(800);

            gameState.setLinesclearedinarow(0);
        }
        frame.repaint();

    }


    public void refillTetrominoeBag() {
        if (bagofshapes.isEmpty()) {
            bagofshapes.add(new LineShape());
            bagofshapes.add(new LShape());
            bagofshapes.add(new ReversedLShape());
            bagofshapes.add(new SquareShape());
            bagofshapes.add(new SShape());
            bagofshapes.add(new TShape());
            bagofshapes.add(new ZShape());
            Collections.shuffle(bagofshapes);
        }
        while (futureshapes.size() < 1) {
            futureshapes.add(bagofshapes.get(0));
            bagofshapes.remove(0);
        }
    }

    private void checkIfGameOver() {
        for (int i = 0; i < currentTetrominoe.getSquaresList().length; i++) {
            if (currentTetrominoe.getSquaresList()[i].getCol() <= 0)
                getGameState().setGameOver(true);
        }
    }


    public class GameCycle implements ActionListener {
        private int gameCycleTimes = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameState.isGameOver()) {
                frame.add(new GameOver(frame.frame), BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                return;
            }
            if (gameCycleTimes % (55 - 5 * getGameState().getLevel()) == 0) {
                if (canTetrominoeMove(currentTetrominoe, 0, 1)) {
                    currentTetrominoe.move(0, 1);
                } else {
                    checkIfGameOver();
                    boardManager.removeTetrominoe(currentTetrominoe);
                    boardManager.addTetrominoe();
                }
            }

            checkSquareRows();
            frame.repaint();
            gameCycleTimes++;
            if (getGameState().getLinescleared() >= getGameState().getLevel() * 15 && getGameState().getLevel() < 10)
                getGameState().increaseLevel(1);
        }
    }

    public class KeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (gameState.isGameOver())
                return;
            if (e.getKeyCode() == KeyEvent.VK_A) {
                if (canTetrominoeMove(currentTetrominoe, -1, 0))
                    currentTetrominoe.move(-1, 0);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                if (canTetrominoeMove(currentTetrominoe, 1, 0))
                    currentTetrominoe.move(1, 0);
            } else if (e.getKeyCode() == KeyEvent.VK_W)
                currentTetrominoe.rotate(getBoard());
            else if (e.getKeyCode() == KeyEvent.VK_S)
                if (canTetrominoeMove(currentTetrominoe, 0, 1))
                    currentTetrominoe.move(0, 1);
                else return;
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                while (canTetrominoeMove(currentTetrominoe, 0, 1)) {
                    currentTetrominoe.move(0, 1);
                }
                boardManager.removeTetrominoe(currentTetrominoe);
                boardManager.addTetrominoe();
                frame.repaint();
            }

            super.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
        }
    }

}

