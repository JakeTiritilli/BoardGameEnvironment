package othello.utility;

import othello.models.Othello;

import java.util.ArrayList;

public class ValidMoveFinder {
    public static ArrayList<Integer[]> getValidMoves(Integer row, Integer col){
        ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
        checkValidMoves(row, col, moves);
        return moves;
    }
    public static ArrayList<Integer[]> getFlips(Integer row, Integer col){
        ArrayList<Integer[]> flips = new ArrayList<Integer[]>();
        ArrayList<Integer[]> potential = new ArrayList<Integer[]>();
        checkToFlip(row, col, flips, potential);
        return flips;
    }

    private static void checkValidMoves(Integer r, Integer c, ArrayList<Integer[]> validMoves){
        int row = r;
        int col = c;
        for (Integer[] dir: Directions.getDirections()) {
            if (inBoundNext(row,col,dir)) {
                if (Othello.getBoard()[row+dir[0]][col+dir[1]] != Othello.getTurn() && Othello.getBoard()[row+dir[0]][col+dir[1]] != 0) {
                    while (inBoundNext(row, col, dir) && Othello.getBoard()[row+dir[0]][col+dir[1]] != Othello.getTurn()) {
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


    private static void checkToFlip(Integer r, Integer c, ArrayList<Integer[]> flips, ArrayList<Integer[]> potentialFlips){
        int row = r;
        int col = c;
        for (Integer[] dir: Directions.getDirections()) {
            if (inBoundNext(row,col,dir)) {
                if (Othello.getBoard()[row+dir[0]][col+dir[1]] != Othello.getTurn() && Othello.getBoard()[row+dir[0]][col+dir[1]] != 0) {
                    while (inBoundNext(row, col, dir)) {
                        if (Othello.getBoard()[row+dir[0]][col+dir[1]] != Othello.getTurn())
                            potentialFlips.add(new Integer[]{row+dir[0],col+dir[1]});
                        else if( Othello.getBoard()[row+dir[0]][col+dir[1]] == Othello.getTurn()){
                            for (Integer[] f: potentialFlips) {
                                flips.add(f);
                            }
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
