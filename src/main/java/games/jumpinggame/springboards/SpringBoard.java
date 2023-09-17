package games.jumpinggame.springboards;

import games.utils.Direction;

import java.awt.*;

public class SpringBoard {
    private final Point position;
    private final boolean destroyOnTouch;

    private final boolean doesMove;

    private final boolean isJumpBoosted;

    private Direction sideWaysDirection = Direction.LEFT;

    public SpringBoard(Point position, boolean destroyOnTouch, boolean doesMove, boolean isJumpBoosted) {
        this.position = position;
        this.destroyOnTouch = destroyOnTouch;
        this.doesMove = doesMove;
        this.isJumpBoosted = isJumpBoosted;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isDestroyOnTouch() {
        return destroyOnTouch;
    }

    public boolean isDoesMove() {
        return doesMove;
    }

    public Direction getSideWaysDirection() {
        return sideWaysDirection;
    }

    public void setSideWaysDirection(Direction sideWaysDirection) {
        this.sideWaysDirection = sideWaysDirection;
    }

    public boolean isJumpBoosted() {
        return isJumpBoosted;
    }
}
