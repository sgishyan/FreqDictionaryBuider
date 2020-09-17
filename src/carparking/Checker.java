package carparking;

import mobiloids.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suren on 4/19/19.
 */
public class Checker {
    class Solution {
        Solution(ArrayList<Move> moves) {
            this.moves = moves;

        }

        int numMoves() {
            return moves.size();
        }

        ArrayList<Move> moves;
    };

    ArrayList<Move> moves = new ArrayList<>();
    ArrayList<String> solutionMoves =new ArrayList<>();
    ArrayList<ArrayList<Move>> moveBuffers = new ArrayList<>();
    Map<Pair<Long,Long>, Integer> memo = new HashMap<>();


    Solution solve(GameBoard board) {
        if (board.solved()) {
            return new Solution(moves);
        }
        memo.clear();
        for (int i = 1; ; i++) {
            moves.add(new Move(0, 0));
            moveBuffers.add(new ArrayList<>());
            if (search(board, 0, i, -1)) {
                return new Solution(moves);
            }
        }
    }

    String getSolutionString() {
        StringBuilder sb = new StringBuilder();
        Collections.reverse(solutionMoves);
        for(String move : solutionMoves) {
            sb.append(move + " ");
        }
        return sb.toString().trim();
    }

    int countMoves(GameBoard board) {
        if (board.solved()) {
            return 0;
        }
        memo.clear();
        for (int i = 1; ; i++) {
            moves.add(new Move(0, 0));
            moveBuffers.add(new ArrayList<>());
            if (search(board, 0, i, -1)) {
                return i;
            }
        }
    }

    boolean search(GameBoard board, int depth, int maxDepth, int previousPiece) {
       // System.out.println(depth + "  " + maxDepth + " " + previousPiece);
        int height = maxDepth - depth;
        if (height == 0) {
            return board.solved();
        }

        Integer item = memo.get(board.key());
        if (item != null && item >= height) {
            return false;
        }
        memo.put(board.key(), height);

        // count occupied squares between primary piece and target
        long boardMask = board.mask();
        Figure primary = board.pieces.get(0);
        int i0 = primary.position + primary.size;
        int i1 = Constants.TARGET_ITEMS + primary.size - 1;
        int minMoves = 0;
        for (int i = i0; i <= i1; i++) {
           // System.out.println(1);
            long mask = (long)1 << i;
            if ((mask & boardMask) != 0) {
                minMoves++;
            }

        }
        if (minMoves >= height) {
            return false;
        }

        ArrayList<Move> moves = moveBuffers.get(depth);
        board.moves(moves);
        //System.out.println(1.5);
        for ( Move move : moves) {
         //   System.out.println(2);
            if (move.piece == previousPiece) {
                continue;
            }
            board.doMove(move);
            boolean solved = search(board, depth + 1, maxDepth, move.piece);
            board.undoMove(move);
            if (solved) {
                memo.put(board.key(), height - 1);
               // System.out.println(move.piece + "->" + move.steps);
                if (move.steps > 0) {
                    solutionMoves.add((char) ('A' + (move.piece)) + "+" + move.steps);
                }else{
                    solutionMoves.add((char) ('A' + (move.piece)) + "" + move.steps);
                }
               // moves.add(depth,move);
                //solutionMoves.add((char) ('A' + (move.piece)) + " " + move.steps);
                return true;
            }
        }
        return false;
    }

}
