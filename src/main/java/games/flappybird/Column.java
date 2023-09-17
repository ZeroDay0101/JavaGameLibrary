package games.flappybird;

import java.awt.*;

public class Column {
    private final Point position;
    private final int colLength;

    public Column(Point position, int length) {
        this.position = position;
        this.colLength = length;
    }

    public Point getPosition() {
        return position;
    }

    public int getColLength() {
        return colLength;
    }
}
