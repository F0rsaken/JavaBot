import java.awt.*;
import java.util.ArrayList;

public class Field {
    protected final String EMTPY_FIELD = ".";
    protected final String BLOCKED_FIELD = "x";

    private String myId;
    private String opponentId;
    private int width;
    private int height;

    private String[][] field;
    private Point myPosition;
    private Point opponentPosition;
    private ArrayList<Point> enemyPositions;
    private ArrayList<Point> snippetPositions;
    private ArrayList<Point> bombPositions;
    private ArrayList<Point> tickingBombPositions;
    private ArrayList<Point> gatesPositions;

    public Field() {
        this.enemyPositions = new ArrayList<>();
        this.snippetPositions = new ArrayList<>();
        this.bombPositions = new ArrayList<>();
        this.tickingBombPositions = new ArrayList<>();
        this.gatesPositions = new ArrayList<>();
    }

    /**
     * Initializes field
     * @throws Exception: exception
     */
    public void initField() throws Exception {
        try {
            this.field = new String[this.width][this.height];
        } catch (Exception e) {
            throw new Exception("Error: trying to initialize field while field "
                    + "settings have not been parsed yet.");
        }
        clearField();
    }

    /**
     * Clears the field
     */
    public void clearField() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.field[x][y] = "";
            }
        }

        this.myPosition = null;
        this.opponentPosition = null;
        this.enemyPositions.clear();
        this.snippetPositions.clear();
        this.bombPositions.clear();
        this.tickingBombPositions.clear();
    }

    /**
     * Parses input string from the engine and stores it in
     * this.field. Also stores several interesting points.
     * @param input String input from the engine
     */
    public void parseFromString(String input) {
        clearField();

        String[] cells = input.split(",");
        int x = 0;
        int y = 0;

        for (String cellString : cells) {
            this.field[x][y] = cellString;

            for (String cellPart : cellString.split(";")) {
                switch (cellPart.charAt(0)) {
                    case 'P':
                        parsePlayerCell(cellPart.charAt(1), x, y);
                        break;
                    case 'S':
                        break;
                    case 'G':
                        parseGateCell (cellPart.charAt(1), x, y);
                        break;
                    case 'E':
                        parseEnemyCell(cellPart.charAt(1), x, y);
                        break;
                    case 'B':
                        parseBombCell(cellPart, x, y);
                        break;
                    case 'C':
                        parseSnippetCell(x, y);
                        break;
                }
            }

            if (++x == this.width) {
                x = 0;
                y++;
            }
        }
    }

    private void parseGateCell (char direction, int x, int y) {
        if (direction == 'l') {
            this.gatesPositions.add(0, new Point(x, y));
        }else if (direction == 'r') {
            this.gatesPositions.add(1, new Point(x, y));
        }
    }

    /**
     * Stores the position of one of the players, given by the id
     * @param id Player ID
     * @param x X-position
     * @param y Y-position
     */
    private void parsePlayerCell(char id, int x, int y) {
        if (id == this.myId.charAt(0)) {
            this.myPosition = new Point(x, y);
        } else if (id == this.opponentId.charAt(0)) {
            this.opponentPosition = new Point(x, y);
        }
    }

    /**
     * Stores the position of an enemy. The type of enemy AI
     * is also given, but not stored in the starterbot.
     * @param type Type of enemy AI
     * @param x X-position
     * @param y Y-position
     */
    private void parseEnemyCell(char type, int x, int y) {
        this.enemyPositions.add(new Point(x, y));
    }

    /**
     * Stores the position of a bomb that can be collected or is
     * about to explode. The amount of ticks is not stored
     * in this starterbot.
     * @param cell The string that represents a bomb, if only 1 letter it
     *             can be collected, otherwise it will contain a number
     *             2 - 5, that means it's ticking to explode in that amount
     *             of rounds.
     * @param x X-position
     * @param y Y-position
     */
    private void parseBombCell(String cell, int x, int y) {
        if (cell.length() <= 1) {
            this.bombPositions.add(new Point(x, y));
        } else {
            this.tickingBombPositions.add(new Point(x, y));
        }
    }

    /**
     * Stores the position of a snippet
     * @param x X-position
     * @param y Y-position
     */
    private void parseSnippetCell(int x, int y) {
        this.snippetPositions.add(new Point(x, y));
    }

    public ArrayList<MoveType> getValidMoves(Point position) {
        ArrayList<MoveType> moves = new ArrayList<>();
        int x = position.x;
        int y = position.y;

        if (isPointValid(x, y-1)) {
            moves.add(MoveType.UP);
        }
        if (isPointValid(x, y+1)) {
            moves.add(MoveType.DOWN);
        }
        if (isPointValid(x-1, y)) {
            moves.add(MoveType.LEFT);
        }
        if (isPointValid(x+1, y)) {
            moves.add(MoveType.RIGHT);
        }

        return moves;
    }

    public boolean isPointValid(int x, int y) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            switch (this.field[x][y].charAt(0)) {
                case 'x':
                    return false;
                case 'E':
                    return false;
                default:
                    return true;
            }
        }else {
            return false;
        }
    }

    public void setFieldPoint(Point point, String value) {
        this.field[point.x][point.y] = value;
    }

    public void setMyId(int id) {
        this.myId = id + "";
    }

    public void setOpponentId(int id) {
        this.opponentId = id + "";
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Point getMyPosition() {
        return this.myPosition;
    }

    public Point getOpponentPosition() {
        return this.opponentPosition;
    }

    public ArrayList<Point> getEnemyPositions() {
        return this.enemyPositions;
    }

    public ArrayList<Point> getSnippetPositions() {
        return this.snippetPositions;
    }

    public ArrayList<Point> getBombPositions() {
        return this.bombPositions;
    }

    public ArrayList<Point> getTickingBombPositions() {
        return this.tickingBombPositions;
    }

    public ArrayList<Point> getGatesPositions() {
        return this.gatesPositions;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String[][] getField() {
        return field;
    }

    public boolean isBombGonnaHit(Point bomb, Point player) {
        int x, y;
        if (bomb.x == player.x) {
            x = player.x;
            if (bomb.y > player.y) {
                for (y = player.y; y < bomb.y; y++) {
                    if (this.field[x][y].charAt(0) == 'x') {
                        return false;
                    }
                }
                return true;
            }else {
                for (y = bomb.y; y < player.y; y++) {
                    if (this.field[x][y].charAt(0) == 'x') {
                        return false;
                    }
                }
                return true;
            }
        }else {
            y = player.y;
            if (bomb.x > player.x) {
                for (x = player.x; x < bomb.x; x++) {
                    if (this.field[x][y].charAt(0) == 'x') {
                        return false;
                    }
                }
                return true;
            }else {
                for (x = bomb.x; x < player.x; x++) {
                    if (this.field[x][y].charAt(0) == 'x') {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    public boolean isBugNearby(Point playerPos) {
        int nearMaxDistance = 8; // odleglosc miedzy punktami oddalonymi o maximum dx = 2 i dy = 2
        int currentDistance;

        for (Point enemyPos : this.enemyPositions) {
            currentDistance = ((enemyPos.x - playerPos.x)^2) + ((enemyPos.y - playerPos.y)^2);
            if (currentDistance <= nearMaxDistance) {
                return true;
            }
        }

        return false;
    }

    public Point getCenter() {
        int x = (this.width-1)/2;
        int y = this.height/2;
        return new Point(x, y);
    }

}
