package games.tetris.board.squares;

import java.awt.*;

public class Square {
    private final Color color;
    private int row;
    private int col;

    public Square(Color color) {
        this.color = color;
    }

    public Square(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Color getColor() {
        return color;
    }

}
