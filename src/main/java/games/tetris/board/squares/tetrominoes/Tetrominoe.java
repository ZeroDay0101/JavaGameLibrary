package games.tetris.board.squares.tetrominoes;


import games.tetris.board.squares.Square;

import java.awt.*;

public abstract class Tetrominoe {

    protected final Square[] squaresList = new Square[4];
    private final Color color;
    private int row = 5;
    private int rotationState = 1;
    private int col;

    public Tetrominoe(Color color) {

        this.color = color;

        col = Math.abs(getShapeForm(rotationState)[0][1]);
        //Set default spawning position and color
        for (int i = 0; i < getShapeForm(rotationState).length; i++)
            squaresList[i] = new Square(row + getShapeForm(rotationState)[i][0], col + getShapeForm(rotationState)[i][1], color);

    }

    public Color getColor() {
        return color;
    }

    public Square[] getSquaresList() {
        return squaresList;
    }

    public void move(int row, int col) {
        this.row += row;
        this.col += col;
        for (int i = 0; i < getSquaresList().length; i++) {
            getSquaresList()[i].setRow(getSquaresList()[i].getRow() + row);
            getSquaresList()[i].setCol(getSquaresList()[i].getCol() + col);
        }
    }

    public void rotate(Square[][] board) {
        int previousRot = rotationState;
        if (rotationState == 4)
            rotationState = 1;
        else
            rotationState++;

        for (int i = 0; i < 4; i++)
            if (row + getShapeForm(rotationState)[i][0] > 9 || row + getShapeForm(rotationState)[i][0] < 0 || col + getShapeForm(rotationState)[i][1] > 19 || col + getShapeForm(rotationState)[i][1] < 0 || board[row + getShapeForm(rotationState)[i][0]][col + getShapeForm(rotationState)[i][1]] != null) {
                rotationState = previousRot;
                return;
            }


        for (int i = 0; i < 4; i++)
            squaresList[i] = new Square(row + getShapeForm(rotationState)[i][0], col + getShapeForm(rotationState)[i][1], color);
    }

    public abstract int[][] getShapeForm(int direction);

}
