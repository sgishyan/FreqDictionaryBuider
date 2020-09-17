package carparking;

import java.util.Collections;

/**
 * Created by suren on 4/19/19.
 */
public class Main {
    public static void main(String[] args) {
        GameBoard board = new GameBoard("GBB.L.GHI.LMGHIAAMCCCK.M..JKDDEEJFF.");
        System.out.println(board.toString());
        Checker solver = new Checker();
        Checker.Solution solution = solver.solve(board);
        Collections.reverse(solver.solutionMoves);

        for(String move : solver.solutionMoves) {
            System.out.println(move);
        }
    }
}
