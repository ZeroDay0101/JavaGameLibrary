package games.tetris.board;

public class GameState {
    private boolean isGameOver = false;
    private int linescleared;
    private int linesclearedinarow;
    private int linesdropped;
    private int level = 1;
    private int score;

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getLinescleared() {
        return linescleared;
    }

    public void increaseLinescleared(int linescleared) {
        this.linescleared += linescleared;
    }

    public int getLinesclearedinarow() {
        return linesclearedinarow;
    }

    public void setLinesclearedinarow(int linesclearedinarow) {
        this.linesclearedinarow = linesclearedinarow;
    }

    public void increaseLinesclearedinarow(int linesclearedinarow) {
        this.linesclearedinarow += linesclearedinarow;
    }

    public int getLinesdropped() {
        return linesdropped;
    }

    public void increaseLinesDropped(int increaseValue) {
        this.linesdropped += increaseValue;
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel(int incVal) {
        this.level += incVal;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }
}
