import java.awt.*;

public class Move {
    private MoveType moveType = MoveType.PASS;
    private Integer bombTicks;

    public Move() {}

    public Move(MoveType moveType) {
        this.moveType = moveType;
        this.bombTicks = null;
    }

    public Move(MoveType moveType, int bombTicks) {
        this.moveType = moveType;
        this.bombTicks = bombTicks;
    }

    public String toString() {
        if (this.moveType == MoveType.PASS || this.bombTicks == null) {
            return this.moveType.toString();
        }

        return (this.moveType + ";drop_bomb " + this.bombTicks);
    }
}
