package games.tetris.board.squares.tetrominoes;

import java.awt.*;

public class SquareShape extends Tetrominoe {
    public SquareShape() {
        super(Color.YELLOW);
    }

    @Override
    public int[][] getShapeForm(int direction) {
        return new int[][]{{-1, -1}, {-1, 0}, {0, 0}, {0, -1}};
    }
}
