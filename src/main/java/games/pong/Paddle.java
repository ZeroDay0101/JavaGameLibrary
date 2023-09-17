package games.pong;

import games.utils.Direction;

import java.awt.*;

public class Paddle {
    private final Point position;
    private Color paddleColor;

    private Direction direction;

    public Paddle(Point position) {
        this.position = position;
    }

    public Paddle(Point position, Color paddleColor) {
        this.position = position;
        this.paddleColor = paddleColor;
    }

    public Point getPosition() {
        return position;
    }

    public Color getPaddleColor() {
        return paddleColor;
    }

    public void setPaddleColor(Color paddleColor) {
        this.paddleColor = paddleColor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
