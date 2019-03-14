package othello.utility;

public class OthelloPiece {
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
