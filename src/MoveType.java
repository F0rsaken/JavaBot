import java.awt.*;

public enum MoveType {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    PASS;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public Point executeMove (Point point) {
        switch (this) {
            case UP:
                return new Point(point.x, point.y-1);
            case DOWN:
                return new Point(point.x, point.y+1);
            case LEFT:
                return new Point(point.x-1, point.y);
            case RIGHT:
                return new Point(point.x+1, point.y);
            default:
                return new Point(point.x, point.y);
        }
    }
}
