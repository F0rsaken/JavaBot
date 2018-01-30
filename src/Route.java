import java.awt.*;
import java.util.ArrayList;

public class Route {
    private ArrayList<Point> points;

    public Route () {
        this.points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public void removePoint() {
        int i = this.points.size()-1;
        this.points.remove(i);
    }

    public Point extractPoint() {
        int i = this.points.size()-1;
        Point tmp = this.points.get(i);
        this.points.remove(i);
        return tmp;
    }

    public Point getNextPoint() {
        return this.points.get(this.getDistance() - 1);
    }

    public int getDistance() {
        return points.size();
    }

}
