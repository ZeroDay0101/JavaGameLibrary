package games.tetris.board.squares.tetrominoes;

import java.awt.*;

public class LineShape extends Tetrominoe {


    public LineShape() {
        super(new Color(0x1ADAB0));
    }

    @Override
    public int[][] getShapeForm(int direction) {
        if (direction == 1 || direction == 3)
            return new int[][]{{-2, 0}, {-1, 0}, {0, 0}, {1, 0}};
        else
            return new int[][]{{0, -1}, {0, 0}, {0, 1}, {0, 2}};
    }
}
