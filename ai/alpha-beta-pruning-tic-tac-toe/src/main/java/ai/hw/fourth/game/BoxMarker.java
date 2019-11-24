package ai.hw.fourth.game;

public enum BoxMarker {

    X("X"),
    O("O"),
    EMPTY("_");

    private String boxMarker;

    BoxMarker(String boxMarker) {
        this.boxMarker = boxMarker;
    }

    @Override
    public String toString() {
        return boxMarker;
    }

    public BoxMarker getOppositeMove() {
        if (this.boxMarker.equals("X"))
        {
            return BoxMarker.O;
        } else if (this.boxMarker.equals("O")) {
            return BoxMarker.X;
        }

        return BoxMarker.EMPTY;
    }
}
