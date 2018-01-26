import java.awt.*;
import java.util.ArrayList;

public class Bot {

    private String character;
    private Route plannedRoute = new Route();
    private Point currentTarget;
    private Point myPosition;

    private Bot() {
        this.character = "bixie";
    }

    public String getCharacter() {
        return this.character;
    }

    /**
     * Does a move action. Edit this to make your bot smarter.
     * @param state The current state of the game
     * @return A Move object
     */
    public Move doMove(BotState state) {
        this.myPosition = state.getField().getMyPosition();
        ArrayList<MoveType> validMoves = state.getField().getValidMoves(myPosition);

        if (validMoves.size() <= 0) {
            // sprawdzic gdzie jest bug i wybrac lepszy(mniej gro¼ny)
            return new Move(); // No valid moves, pass
        }

        //sprawdzanie, czy jest kod w lepszej lokalizacji
        Point lastTarget = currentTarget;
        this.currentTarget = Decide.selectBestSnipet(state.getField());
        //null, jezeli nie ma kodow do zebrania
        if (this.currentTarget == null) {
            //zakopac to w field
            this.currentTarget = new Point(state.getField().getWidth() / 2, state.getField().getHeight() / 2);
            this.plannedRoute = Decide.findBestRoute(
                    myPosition,
                    currentTarget,
                    state.getField(),
                    validMoves
            );
            //usuwanie powtorzonego punktu
            this.plannedRoute.removePoint();
        }else if (!currentTarget.equals(lastTarget)) {
            this.plannedRoute = Decide.findBestRoute(
                    myPosition,
                    currentTarget,
                    state.getField(),
                    validMoves
            );
            //usuwanie powtorzonego punktu
            this.plannedRoute.removePoint();
        }

        //obsluga min i robakow

        return new Move(
                getMoveType(
                        myPosition,
                        plannedRoute.extractPoint()
                )
        );
    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new Bot());
        parser.run();
    }

    private MoveType getMoveType (Point start, Point end) {
        if (start.x == end.x) {
            if (end.y - start.y > 0) {
                return MoveType.DOWN;
            }else {
                return MoveType.UP;
            }
        }else {
            if (end.x - start.x > 0) {
                return MoveType.RIGHT;
            }else {
                return MoveType.LEFT;
            }
        }
    }
}
