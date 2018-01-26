import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Decide {
    public static Route findBestRoute(Point start, Point end, Field field, ArrayList<MoveType> validMoves) {
//        //TODO: uwzglednic bramy
        Route route = new Route();
        boolean found = false;
        Point currentPoint;
        int level = 1;
        ArrayList<Point> gates = field.getGatesPositions();

        field.setFieldPoint(start, String.valueOf(0));
        Queue<Point> neighbours = new LinkedList<>();
        Point neighbour;

        for (MoveType move : validMoves) {
            neighbour = move.executeMove(start, field.getWidth());
            field.setFieldPoint(neighbour, String.valueOf(level));
            neighbours.add(neighbour);
            if (neighbour.equals(end)) {
                route.addPoint(neighbour);
            }
        }

        while (!found) {
            currentPoint = neighbours.poll();
            validMoves = field.getValidMoves(currentPoint);

            //sprawdzamy czy jest to brama
            if (currentPoint.equals(gates.get(0))) {
                validMoves.add(MoveType.LEFT);
            }else if (currentPoint.equals(gates.get(1))) {
                validMoves.add(MoveType.RIGHT);
            }

            level = Integer.parseInt(field.getField()[currentPoint.x][currentPoint.y]);
            for ( MoveType move : validMoves) {
                neighbour = move.executeMove(currentPoint, field.getWidth());
                if (neighbour.equals(end)) {
                    found = true;
                }
                if (!field.getField()[neighbour.x][neighbour.y].matches("\\d+")) {
                    field.setFieldPoint(neighbour, String.valueOf(level+1));
                    neighbours.add(neighbour);
                }
            }
        }

        //przepisz sciezke do route
        currentPoint = end;
        route.addPoint(end);
        level = Integer.parseInt(field.getField()[currentPoint.x][currentPoint.y]);

        while (!currentPoint.equals(start)) {
            validMoves = field.getValidMoves(currentPoint);
            for (MoveType move : validMoves) {
                neighbour = move.executeMove(currentPoint, field.getWidth());
                if (field.getField()[neighbour.x][neighbour.y].matches("\\d+") && level-1 == Integer.parseInt(field.getField()[neighbour.x][neighbour.y])) {
                    route.addPoint(neighbour);
                    level = Integer.parseInt(field.getField()[neighbour.x][neighbour.y]);
                    currentPoint = neighbour;
                    break;
                }
            }
        }

        return route;
    }

    public static Point selectBestSnipet(Field field) {
        ArrayList<Point> snippets = field.getSnippetPositions();
        Point playerPosition = field.getMyPosition();
        Point opponentPosition = field.getOpponentPosition();
        int mapWidth = field.getWidth();

        switch (snippets.size()) {
            case 0:
                return null;
            case 1:
                return snippets.get(0);
            default:
                Point bestSnippet;
                int playerDistance1 = minVectorLength(playerPosition, snippets.get(0), mapWidth);
                int playerDistance2 = minVectorLength(playerPosition, snippets.get(1), mapWidth);
                int opponentDistance1 = minVectorLength(opponentPosition, snippets.get(0), mapWidth);
                int opponentDistance2 = minVectorLength(opponentPosition, snippets.get(1), mapWidth);

                if (playerDistance1 < playerDistance2) {
                    if (opponentDistance1 < playerDistance1) {
                        bestSnippet = snippets.get(1);
                    }else {
                        bestSnippet = snippets.get(0);
                    }
                }else {
                    if (opponentDistance2 < playerDistance2) {
                        bestSnippet = snippets.get(0);
                    }else {
                        bestSnippet = snippets.get(1);
                    }
                }

                return bestSnippet;
//            default:
                //sortowaniedla wiekszej ilosci po graczu i przeciwniku

//                return null;

        }
    }

    private static int minVectorLength (Point start, Point end, int mapWidth) {
        int normal = (Math.abs(end.x - start.x)^2) + (Math.abs(end.y - start.y)^2);
        int withGate;

        if (end.x > start.x) {
            withGate = (Math.abs((end.x - mapWidth + 1) - start.x)^2) + (Math.abs(end.y - start.y)^2);
        }else {
            withGate = (Math.abs((end.x + mapWidth - 1) - start.x)^2) + (Math.abs(end.y - start.y)^2);
        }

        if (normal <= mapWidth) {
            return normal;
        }else {
            return withGate;
        }
    }
}
