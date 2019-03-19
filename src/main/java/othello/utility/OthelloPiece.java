package othello.utility;

import boardgamekit.utility.GamePiece;

public class OthelloPiece extends GamePiece {
    public OthelloPlayer color;

    public OthelloPiece(OthelloPlayer c){
        color = c;
    }

    public String toString(){
        return color == OthelloPlayer.WHITE ? "W" : "B";
    }
}
