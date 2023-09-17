package games.tetris.board.squares.tetrominoes;

import java.awt.*;

public class ZShape extends Tetrominoe {
    public ZShape() {
        super(Color.ORANGE);
    }

    @Override
    public int[][] getShapeForm(int direction) {
        if (direction == 1 || direction == 3)
            return new int[][]{{-1, 0}, {0, 0}, {0, 1}, {1, 1}};
        else
            return new int[][]{{0, 1}, {0, 0}, {1, 0}, {1, -1}};

    }
}
