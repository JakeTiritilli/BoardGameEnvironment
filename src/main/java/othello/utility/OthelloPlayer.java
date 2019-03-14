package othello.utility;

public enum OthelloPlayer {
    BLACK, WHITE;

    private OthelloPlayer opposite;

    static{
        BLACK.opposite=WHITE;
        WHITE.opposite=BLACK;
    }

    public OthelloPlayer getOppositeColor(){
        return opposite;
    }
}
