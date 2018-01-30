import java.awt.*;
import java.util.ArrayList;

public class Bot {

    private String character;
    private Route plannedRoute = new Route();
    private Point currentTarget;

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
        Point myPosition = state.getField().getMyPosition();
        ArrayList<MoveType> validMoves = state.getField().getValidMoves(myPosition);

        if (validMoves.size() <= 0) {
            return new Move();
        }

        //sprawdzanie, czy jest kod w lepszej lokalizacji
        Point lastTarget = currentTarget;
        this.currentTarget = Decide.selectBestSnipet(state.getField());

        if (!currentTarget.equals(lastTarget)) {
            this.plannedRoute = Decide.findBestRoute(
                    myPosition,
                    currentTarget,
                    state.getField(),
                    validMoves
            );
            //usuwanie powtorzonego punktu
            this.plannedRoute.removePoint();
        }

        MoveType avoid = Decide.checkExplodingBombs(state.getField(), plannedRoute.getNextPoint());
        if (avoid != null) {
            return new Move(avoid);
        }

        if (state.getField().isBugNearby(myPosition)) {
            this.plannedRoute = Decide.findBestRoute(
                    myPosition,
                    currentTarget,
                    state.getField(),
                    validMoves
            );
            //usuwanie powtorzonego punktu
            this.plannedRoute.removePoint();
        }

        if (state.getPlayers().get(state.getMyName()).getBombs() != 0 && state.getField().isBugNearby(myPosition)) {
            return new Move(
                    getMoveType(
                            myPosition,
                            plannedRoute.extractPoint()
                    ),
                    2
            );
        }

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
