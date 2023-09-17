package games.pong;

import java.awt.*;

public class Ball {
    private Point position = new Point();
    private Point velocity = new Point(2, 2);

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getVelocity() {
        return velocity;
    }

    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    public void setVelocityX(int velocity) {
        this.velocity.x = velocity;
    }

    public void setVelocityY(int velocity) {
        this.velocity.y = velocity;
    }
}
