package games.tetris.gui;


import games.tetris.board.TetrisGame;
import games.tetris.board.squares.tetrominoes.LineShape;
import games.tetris.board.squares.tetrominoes.SquareShape;

import javax.swing.*;
import java.awt.*;

public class Info extends JPanel {

    private final int gapBetwenShapeAndPanel = 10;
    private final int panelWidth = 180;
    private final int shapeDrawingPanelWidth = 160;


    private final TetrisGame tetrisGame;


    public Info(TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
        this.setPreferredSize(new Dimension(panelWidth, 0));
        this.setBackground(new Color(0x0E0E0E));


        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTetrominoes(g);
    }

    public void paintTetrominoes(Graphics g) {
        int rectSize = 40;
        if (tetrisGame.currentTetrominoe == null)
            return;

        for (int j = 0; j < tetrisGame.getFutureshapes().size(); j++) {
            g.setColor(tetrisGame.getFutureshapes().get(j).getColor());
            for (int i = 0; i < tetrisGame.getFutureshapes().get(j).getShapeForm(1).length; i++) {
                if (tetrisGame.getFutureshapes().get(j) instanceof SquareShape || tetrisGame.getFutureshapes().get(j) instanceof LineShape) //Those tetrominoes have even width
                    g.fillRect(gapBetwenShapeAndPanel + shapeDrawingPanelWidth / 2 + tetrisGame.getFutureshapes().get(j).getShapeForm(1)[i][0] * rectSize, 100 + tetrisGame.getFutureshapes().get(j).getShapeForm(1)[i][1] * rectSize, rectSize, rectSize);
                else
                    g.fillRect(gapBetwenShapeAndPanel + shapeDrawingPanelWidth / 2 - rectSize / 2 + tetrisGame.getFutureshapes().get(j).getShapeForm(1)[i][0] * rectSize, 100 + tetrisGame.getFutureshapes().get(j).getShapeForm(1)[i][1] * rectSize, rectSize, rectSize);
            }
        }

        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Level: ", 10, (839 - 839 / 3) - 40);
        g.drawString(Integer.toString(tetrisGame.getGameState().getLevel()), panelWidth - 50, (839 - 839 / 3) - 40);
        g.drawString("Score: ", 10, 839 - 839 / 3);
        g.drawString(Integer.toString(tetrisGame.getGameState().getScore()), panelWidth - 50, 839 - 839 / 3);
        g.drawString("DroppedT ", 10, (839 - 839 / 3) + 40);
        g.drawString(Integer.toString(tetrisGame.getGameState().getLinesdropped()), panelWidth - 50, (839 - 839 / 3) + 40);
        g.drawString("ClearedT ", 10, (839 - 839 / 3) + 80);
        g.drawString(Integer.toString(tetrisGame.getGameState().getLinescleared()), panelWidth - 50, (839 - 839 / 3) + 80);

    }
}
