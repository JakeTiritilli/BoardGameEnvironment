package othello.utility;

import othello.models.Othello;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ValidMoveFinder {
    public static ArrayList<Integer[]> getValidMoves(OthelloPlayer turn){
        ArrayList<Integer[]> moves = new ArrayList<Integer[]>();
        for (int i = 0; i <= 7; i++){
            for (int j = 0; j <= 7; j++){
                //System.out.println("Row: " + i + " Col: " + j);
                if (Othello.gameboard[i][j] != null && Othello.gameboard[i][j].color.equals(turn)){
                    checkValidMoves(i, j, moves, turn);
                }
            }
        }
        ArrayList<Integer[]> nonDuplicateMoves = (ArrayList<Integer[]>) moves.stream().distinct().collect(Collectors.toList());
        return nonDuplicateMoves;
    }
    public static ArrayList<Integer[]> getFlips(Integer row, Integer col, OthelloPlayer turn){
        ArrayList<Integer[]> flips = new ArrayList<Integer[]>();
        ArrayList<Integer[]> potential = new ArrayList<Integer[]>();
        checkToFlip(row, col, flips, potential, turn);
        return flips;
    }

    private static void checkValidMoves(Integer r, Integer c, ArrayList<Integer[]> validMoves, OthelloPlayer turn){

        for (Integer[] dir: Directions.getDirections()) {
            int row = r;
            int col = c;
            if (inBoundNext(row,col,dir)) {
                if ((Othello.getBoard()[row + dir[0]][col + dir[1]]!=null) &&!(Othello.getBoard()[row + dir[0]][col + dir[1]].color.equals(turn)))  {
                    while (inBoundNext(row, col, dir)) {
                        if (Othello.getBoard()[row+dir[0]][col+dir[1]] == null) {
                            validMoves.add(new Integer[]{row+dir[0], col+dir[1]});
                            break;
                        }
                        else if (Othello.getBoard()[row + dir[0]][col + dir[1]].color.equals(turn)){
                            break;
                        }
                        row+=dir[0];
                        col+=dir[1];
                    }
                }
            }
        }
    }

    private static void checkToFlip(Integer r, Integer c, ArrayList<Integer[]> flips, ArrayList<Integer[]> potentialFlips, OthelloPlayer turn){

        for (Integer[] dir: Directions.getDirections()) {
            int row = r;
            int col = c;
            System.out.println("Direction: "+dir[0]+" "+dir[1]);
            if (inBoundNext(row,col,dir)) {
                if ((Othello.getBoard()[row + dir[0]][col + dir[1]]!=null) &&!(Othello.getBoard()[row + dir[0]][col + dir[1]].color.equals(turn))) {
                    while (inBoundNext(row, col, dir)) {
                        if(Othello.getBoard()[row + dir[0]][col + dir[1]] == null){
                            potentialFlips.clear();
                            break;
                        }
                        if (Othello.getBoard()[row + dir[0]][col + dir[1]].color.equals(turn.getOppositeColor())) {
                            System.out.println("Adding Potential Flip");
                            potentialFlips.add(new Integer[]{row + dir[0], col + dir[1]});
                        }
                        else if(Othello.getBoard()[row + dir[0]][col + dir[1]].color.equals(turn)){
                            flips.addAll(potentialFlips);
                            potentialFlips.clear();
                            System.out.println("Clearing and adding to Flip");
                            break;
                        }
                        row+=dir[0];
                        col+=dir[1];
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
