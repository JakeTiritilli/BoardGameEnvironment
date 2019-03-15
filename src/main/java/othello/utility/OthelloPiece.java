package othello.utility;

import boardgamekit.utility.GamePiece;

public class OthelloPiece implements GamePiece {
    public OthelloPlayer color;

    public OthelloPiece(OthelloPlayer c){
        color = c;
    }

    public String toString(){
        if(color == OthelloPlayer.WHITE){
            return " W ";
        }
        else{
            return " B ";
        }
    }
}
