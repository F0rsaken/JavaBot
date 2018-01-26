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

    public Point executeMove (Point point, int fieldWidth) {
        switch (this) {
            case UP:
                return new Point(point.x, point.y-1);
            case DOWN:
                return new Point(point.x, point.y+1);
            case LEFT:
                if (point.x-1 < 0) return new Point(fieldWidth - 1, point.y);
                return new Point(point.x-1, point.y);
            case RIGHT:
                if (point.x+1 == fieldWidth) return new Point(0, point.y);
                return new Point(point.x+1, point.y);
            default:
                return new Point(point.x, point.y);
        }
    }
}
