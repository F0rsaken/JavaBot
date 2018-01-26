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

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Point getNextPoint() {
        return this.points.get(this.points.size() - 1);
    }

    public Point extractPoint() {
        int i = this.points.size()-1;
        Point tmp = this.points.get(i);
        this.points.remove(i);
        return tmp;
    }

    public int getDistance() {
        return points.size();
    }

//    public boolean checkConnectionOnX (Point start, Point end, String[][] field) {
//        int x, y;
//        y = end.y;
//
//        Route tmp = new Route();
//        if (start.y < end.y) {
//            for (x = start.x; y >= start.y; y--) {
//                if (field[x][y].charAt(0) == 'x') {
//                    return false;
//                }else {
//                    tmp.addPoint(new Point(x, y));
//                }
//            }
//        }else {
//            for (x = start.x; y <= start.y ; y++) {
//                if (field[x][y].charAt(0) == 'x') {
//                    return false;
//                }else {
//                    tmp.addPoint(new Point(x, y));
//                }
//            }
//        }
//
//        this.points = tmp.getPoints();
//        return true;
//    }
//
//    public boolean checkConnectionOnY (Point start, Point end, String[][] field) {
//        int x, y;
//        x = end.x;
//
//        Route tmp = new Route();
//        if (start.x < end.x) {
//            for (y = start.y; x >=  start.x; x--) {
//                if (field[x][y].charAt(0) == 'x') {
//                    return false;
//                }else {
//                    this.addPoint(new Point(x, y));
//                }
//            }
//        }else {
//            for (y = start.y; x <= start.x; x++) {
//                if (field[x][y].charAt(0) == 'x') {
//                    return false;
//                }else {
//                    this.addPoint(new Point(x, y));
//                }
//            }
//        }

//        this.points = tmp.getPoints();
//        return true;
//    }
}
