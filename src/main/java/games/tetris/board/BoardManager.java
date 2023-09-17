package games.tetris.board;


import games.tetris.board.squares.Square;
import games.tetris.board.squares.tetrominoes.Tetrominoe;

public class BoardManager {
    private final TetrisGame tetrisGame;


    public BoardManager(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
    }

    public void addTetrominoe() {
        tetrisGame.currentTetrominoe = tetrisGame.getFutureshapes().get(0);
        tetrisGame.getFutureshapes().remove(0);
        tetrisGame.refillTetrominoeBag();
        tetrisGame.getGameState().increaseLinesDropped(1);
    }

    public void removeTetrominoe(Tetrominoe tetrominoe) {
        for (int i = 0; i < tetrominoe.getSquaresList().length; i++) {
            tetrisGame.getBoard()[tetrominoe.getSquaresList()[i].getRow()][tetrominoe.getSquaresList()[i].getCol()] = new Square(tetrominoe.getSquaresList()[i].getColor());
        }
    }

    public void startGame() {
        tetrisGame.refillTetrominoeBag();
        addTetrominoe();
        javax.swing.Timer timer = new javax.swing.Timer(10, tetrisGame.new GameCycle());
        timer.start();
    }

}
