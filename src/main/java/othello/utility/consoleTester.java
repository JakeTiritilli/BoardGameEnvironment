package othello.utility;

import othello.models.Othello;

import java.util.ArrayList;
import java.util.Scanner;

public class consoleTester {
    public static void main(String[] args){
        Othello game = new Othello();
        Scanner in = new Scanner(System.in);
        while (!game.endGame()){
            printBoard(game);

            ArrayList<Integer[]> moves = ValidMoveFinder.getValidMoves(game.getTurn());
            System.out.println("Available Moves Found");
            if (moves.size() > 0){
                boolean valid = false;
                System.out.println("Valid Moves:");
                for (Integer[] move : moves) {
                    System.out.println(move[0] +"  "+move[1]);
                }
                while (!valid) {
                    System.out.println("Please Enter a valid position");
                    Integer row = in.nextInt();
                    Integer col = in.nextInt();

                    for (Integer[] move : moves) {
                        if (move[0] == row && move[1] == col) {
                            game.makeMove(row, col);
                            valid = true;
                            break;
                        }
                    }
                }
            }
            else game.setCurrentPlayer(game.getTurn().getOppositeColor());
        }

    }

    public static void printBoard(Othello game){
        for (Integer i = 0; i < 8; i++){
            for (Integer j = 0; j < 8; j++){
                if (game.gameboard[i][j] == null){
                    System.out.print(" . ");
                }
                else{
                    System.out.print(game.gameboard[i][j].toString());
                }
            }
            System.out.println();
        }
    }
}
