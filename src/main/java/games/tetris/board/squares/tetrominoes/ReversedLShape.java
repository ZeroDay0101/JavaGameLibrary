package games.tetris.board.squares.tetrominoes;

import java.awt.*;

public class ReversedLShape extends Tetrominoe {
    public ReversedLShape() {
        super(Color.ORANGE);
    }

    @Override
    public int[][] getShapeForm(int direction) {
        if (direction == 1)
            return new int[][]{{0, -2}, {0, -1}, {0, 0}, {-1, 0}};
        else if (direction == 2)
            return new int[][]{{-1, 0}, {0, 0}, {1, 0}, {-1, -1}};
        else if (direction == 3)
            return new int[][]{{0, -1}, {0, 0}, {0, 1}, {1, -1}};
        else
            return new int[][]{{2, 1}, {0, 0}, {1, 0}, {2, 0}};
    }
}
