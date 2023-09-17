package games.tetris.board.squares.tetrominoes;

import java.awt.*;

public class TShape extends Tetrominoe {
    public TShape() {
        super(Color.ORANGE);
    }

    @Override
    public int[][] getShapeForm(int direction) {
        if (direction == 1)
            return new int[][]{{0, -1}, {-1, 0}, {0, 0}, {1, 0}};
        else if (direction == 2)
            return new int[][]{{0, -1}, {0, 0}, {0, 1}, {1, 0}};
        else if (direction == 3)
            return new int[][]{{0, 1}, {-1, 0}, {0, 0}, {1, 0}};
        else
            return new int[][]{{0, -1}, {0, 0}, {0, 1}, {-1, 0}};
    }
}
