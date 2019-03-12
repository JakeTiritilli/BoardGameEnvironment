package othello.utility;

import othello.models.Othello;

import java.util.ArrayList;

public class ValidMoveFinder {
    public static ArrayList<Integer[]> getValidMoves(Integer turn){
        ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
        for (int i = 0; i < 7; i++){
            for (int j = 0; i < 7; j++){
                checkValidMoves(i, j, moves, turn);
            }
        }
        return moves;
    }
    public static ArrayList<Integer[]> getFlips(Integer row, Integer col, Integer turn){
        ArrayList<Integer[]> flips = new ArrayList<Integer[]>();
        ArrayList<Integer[]> potential = new ArrayList<Integer[]>();
        checkToFlip(row, col, flips, potential, turn);
        return flips;
    }

    private static void checkValidMoves(Integer r, Integer c, ArrayList<Integer[]> validMoves, Integer turn){
        int row = r;
        int col = c;
        for (Integer[] dir: Directions.getDirections()) {
            if (inBoundNext(row,col,dir)) {
                if (!(Othello.getBoard()[row + dir[0]][col + dir[1]].equals(turn)) && !(Othello.getBoard()[row + dir[0]][col + dir[1]].equals(0))) {
                    while (inBoundNext(row, col, dir) && !Othello.getBoard()[row + dir[0]][col + dir[1]].equals(turn)) {
                        if (Othello.getBoard()[row+dir[0]][col+dir[1]] == 0) {
                            validMoves.add(new Integer[]{row+dir[0],col+dir[1]});
                            break;
                        }
                        row+=dir[0];
                        col+=dir[1];
                    }
                }
            }
        }
    }


    private static void checkToFlip(Integer r, Integer c, ArrayList<Integer[]> flips, ArrayList<Integer[]> potentialFlips, Integer turn){
        int row = r;
        int col = c;
        for (Integer[] dir: Directions.getDirections()) {
            if (inBoundNext(row,col,dir)) {
                if (!Othello.getBoard()[row + dir[0]][col + dir[1]].equals(turn) && Othello.getBoard()[row+dir[0]][col+dir[1]] != 0) {
                    while (inBoundNext(row, col, dir)) {
                        if (!Othello.getBoard()[row + dir[0]][col + dir[1]].equals(turn))
                            potentialFlips.add(new Integer[]{row+dir[0],col+dir[1]});
                        else if(Othello.getBoard()[row + dir[0]][col + dir[1]].equals(turn)){
                            flips.addAll(potentialFlips);
                            potentialFlips.clear();
                            break;
                        }
                        row+=dir[0];
                        col+=dir[0];

                    }
                }
            }
        }
    }




    private static boolean inBoundNext(Integer row, Integer col, Integer[] dir){
        if(0 <= row+dir[0] && row+dir[0] <= 7 && 0 <= col+dir[1] && col+dir[1] <= 7){
            return true;
        }
        return false;
    }
}
