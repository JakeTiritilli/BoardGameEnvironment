package othello.utility;

import javafx.util.Pair;

public class Directions {
    public static final Integer[][] directions = new Integer[][]{
            //E      W       S       N      SE     SW       NE      NW
            {0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}

    };
    public static Integer[][] getDirections(){
        return directions;
    }
}
