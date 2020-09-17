package carparking;

import mobiloids.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suren on 4/19/19.
 */
public class GameBoard {
    long horizontalMask;
    long verticalMask;
    ArrayList<Figure> pieces;

    long mask() {
        return horizontalMask | verticalMask;
    }


    public GameBoard() {
        horizontalMask = 0;
        verticalMask = 0;
    }


    public GameBoard(String desc){
        horizontalMask = 0;
        verticalMask = 0;

        if (desc.length() != Constants.TOTAL_ITEMS) {
            System.out.println("Wrong string size");
            throw new RuntimeException();
        }

        Map<Character, ArrayList<Integer>> positions = new HashMap<>();
        for (int i = 0; i < desc.length(); i++) {
            char label = desc.charAt(i);
            if (label == '.' || label == 'o') {
                continue;
            }
            if (positions.containsKey(label)) {
                positions.get(label).add(i);
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                positions.put(label, list);
            }
        }

        ArrayList<Character> labels = new ArrayList<>(positions.size());
        labels.addAll(positions.keySet());

        Collections.sort(labels);

        pieces = new ArrayList<>(labels.size());

        for (char label : labels) {
            ArrayList<Integer> ps = positions.get(label);

            if (ps.size() < Constants.LOW_SIZE) {
                throw new RuntimeException("piece size < LOW_SIZE");
            }
            if (ps.size() > Constants.HIGH_SIZE) {
                throw new RuntimeException("piece size > HIGH_SIZE");
            }

            //Wall support
            if (label == 'x') {
                for (int i = 0; i < ps.size(); i++) {
                    addPiece(new Figure(ps.get(i), 1, 0));
                }
                continue;
            }
            int stride = ps.get(1) - ps.get(0);
            if (stride != Constants.H && stride != Constants.V) {
                throw new RuntimeException("invalid piece shape");
            }
            for (int i = 2; i < ps.size(); i++) {
                if (ps.get(i) - ps.get(i-1) != stride) {
                    throw new RuntimeException("invalid piece shape");
                }
            }
            addPiece(new Figure(ps.get(0), ps.size(), stride));
         }
    }

    Pair<Long, Long> key() {
        return new Pair<>(horizontalMask, verticalMask);
    }

    boolean solved() {
        return pieces.get(0).position == Constants.TARGET_ITEMS;
    }

    void addPiece(Figure piece) {
        pieces.add(piece);
        if (piece.stride == Constants.H) {
            horizontalMask |= piece.mask;
        } else {
            verticalMask |= piece.mask;
        }
    }

    void popPiece() {
        Figure piece = pieces.get(pieces.size() - 1);
        if (piece.stride == Constants.H) {
            horizontalMask &= ~piece.mask;
        } else {
            verticalMask &= ~piece.mask;
        }
        pieces.remove(pieces.size() - 1);
    }

    void removePiece(int i) {
        Figure piece = pieces.get(i);
        if (piece.stride == Constants.H) {
            horizontalMask &= ~piece.mask;
        } else {
            verticalMask &= ~piece.mask;
        }
        pieces.remove(i);
    }

    void doMove(int i, int steps) {
        Figure piece = pieces.get(i);
        if (piece.stride == Constants.H) {
            horizontalMask &= ~piece.mask;
            piece.move(steps);
            horizontalMask |= piece.mask;
        } else {
            verticalMask &= ~piece.mask;
            piece.move(steps);
            verticalMask |= piece.mask;
        }
    }

    void doMove(Move move) {
        doMove(move.piece, move.steps);
    }

    void undoMove(Move move) {
        doMove(move.piece, -move.steps);
    }

    void moves(ArrayList<Move> moves){
        moves.clear();
        long boardMask = mask();
        for (int i = 0; i < pieces.size(); i++) {
          //  System.out.println("i = " + i );
            Figure piece = pieces.get(i);
            if (piece.fixed()) {
                continue;
            }
            if (piece.stride == Constants.H) {
                // reverse / left (negative steps)
                if ((piece.mask & Constants.LEFT) == 0) {

                    long mask = (piece.mask >> Constants.H) & ~piece.mask;
                    int steps = -1;
                    while ((boardMask & mask) == 0) {
                       // System.out.println("PM = " +piece.mask);
                        moves.add(new Move(i, steps));
                        if ((mask & Constants.LEFT) != 0) {
                            break;
                        }
                        mask >>= Constants.H;
                        steps--;
                       // System.out.println(boardMask + " " + mask);
                    }
                }
                // forward / right (positive steps)
                if ((piece.mask & Constants.RIGHT) == 0) {
                    long mask = (piece.mask << Constants.H) & ~piece.mask;
                    int steps = 1;
                    while ((boardMask & mask) == 0) {
                        moves.add(new Move(i, steps));
                        if ((mask & Constants.RIGHT) != 0) {
                            break;
                        }
                        mask <<= Constants.H;
                        steps++;
                    }
                }
            } else {
                // reverse / up (negative steps)
                if ((piece.mask & Constants.TOP) == 0) {
                    long mask = (piece.mask >> Constants.V) & ~piece.mask;
                    int steps = -1;
                    while ((boardMask & mask) == 0) {
                        moves.add(new Move(i, steps));
                        if ((mask & Constants.TOP) != 0) {
                            break;
                        }
                        mask >>= Constants.V;
                        steps--;
                    }
                }
                // forward / down (positive steps)
                if ((piece.mask & Constants.BOTTOM) == 0) {
                    long mask = (piece.mask << Constants.V) & ~piece.mask;
                    int steps = 1;
                    while ((boardMask & mask) == 0) {
                        moves.add(new Move(i, steps));
                        if ((mask & Constants.BOTTOM) != 0) {
                            break;
                        }
                        mask <<= Constants.V;
                        steps++;
                    }
                }
            }
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(Constants.TOTAL_ITEMS);
        for (int i = 0; i < Constants.TOTAL_ITEMS; i++) {
            s.append(".");
        }
         for (int i = 0; i < pieces.size(); i++) {
            Figure piece = pieces.get(i);
            char c = piece.fixed() ? 'x' : (char)('A' + i);
            int p = piece.position;
            for (int j = 0; j < piece.size; j++) {
                s.setCharAt(p,c);
                p += piece.stride;
            }
        }
        return s.toString();
    }

//    String string2D() {
//        StringBuilder s = new StringBuilder(Config.GAME_BOARD_SIZE * (Config.GAME_BOARD_SIZE + 1));
//
//        for (int y = 0; y < Config.GAME_BOARD_SIZE; y++) {
//             int p = y * (Config.GAME_BOARD_SIZE + 1) + GAME_BOARD_SIZE;
//            s[p] = '\n';
//        }
//        for (int i = 0; i < m_Pieces.size(); i++) {
//            const Piece &piece = m_Pieces[i];
//            const char c = piece.Fixed() ? 'x' : 'A' + i;
//            int stride = piece.Stride();
//            if (stride == V) {
//                stride++;
//            }
//            const int y = piece.Position() / GAME_BOARD_SIZE;
//            const int x = piece.Position() % GAME_BOARD_SIZE;
//            int p = y * (GAME_BOARD_SIZE + 1) + x;
//            for (int i = 0; i < piece.Size(); i++) {
//                s[p] = c;
//                p += stride;
//            }
//        }
//        return s;
//    }

//    std::ostream& operator<<(std::ostream &stream, const Board &board) {
//        return stream << board.String();
//    }

//    bool operator<(const Board &b1, const Board &b2) {
//
//    }

//    @Override
//    public int compareTo(Board o) {
//        if (horizontalMask == o.horizontalMask) {
//            if(verticalMask - o.verticalMask);
//        }
//        return b1.HorzMask() < b2.HorzMask();
//    }
}
