package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * Component of the Chess game that detects check mates in the game.
 *
 * @author Jussi Lundstedt
 *
 */
public class CheckmateDetector {
    private Board board;
    private LinkedList<Piece> whitePieces;
    private LinkedList<Piece> blackPieces;
    private LinkedList<Square> movableSquares;
    private final LinkedList<Square> allSquares;
    private King blackKing;
    private King whiteKing;
    private HashMap<Square,List<Piece>> whiteMoves;
    private HashMap<Square,List<Piece>> blackMoves;

    /**
     * Constructs a new instance of model.CheckmateDetector on a given board. By
     * convention should be called when the board is in its initial state.
     *
     * @param board The board which the detector monitors
     * @param whitePieces White pieces on the board.
     * @param blackPieces Black pieces on the board.
     * @param whiteKing model.Piece object representing the white king
     * @param blackKing model.Piece object representing the black king
     */
    public CheckmateDetector(Board board, LinkedList<Piece> whitePieces,
                             LinkedList<Piece> blackPieces, King whiteKing, King blackKing) {
        this.board = board;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.blackKing = blackKing;
        this.whiteKing = whiteKing;

        // Initialize other fields
        allSquares = new LinkedList<Square>();
        movableSquares = new LinkedList<Square>();
        whiteMoves = new HashMap<Square,List<Piece>>();
        blackMoves = new HashMap<Square,List<Piece>>();

        Square[][] brd = board.getSquareArray();

        // add all squares to squares list and as hashmap keys
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                allSquares.add(brd[y][x]);
                whiteMoves.put(brd[y][x], new LinkedList<Piece>());
                blackMoves.put(brd[y][x], new LinkedList<Piece>());
            }
        }

        // update situation
        update();
    }

    /**
     * Updates the object with the current situation of the game.
     */


    //I extracted jelper methods clearMaps
    private void clearMaps() {
        whiteMoves.values().forEach(List::clear);
        blackMoves.values().forEach(List::clear);
        movableSquares.clear();
    }

    public void update() {
        // Iterators through pieces
        clearMaps();
        updateMoves(whitePieces, whiteMoves);
        updateMoves(blackPieces, blackMoves);

    }

    //I extracted helper method updateMoves
    private void updateMoves(LinkedList<Piece> pieces, Map<Square, List<Piece>> moveMap) {
        Iterator<Piece> iterator = pieces.iterator();
        while (iterator.hasNext()) {
            Piece piece = iterator.next();
            if (piece instanceof King) continue;
            if (piece.getPosition() == null) {
                iterator.remove();
                continue;
            }
            for (Square square : piece.getLegalMoves(board)) {
                moveMap.get(square).add(piece);
            }
        }
    }
    /**
     * Checks if the black king is threatened
     * @return boolean representing whether the black king is in check.
     */
    public boolean blackInCheck() {
        update();
        Square sq = blackKing.getPosition();
        if (whiteMoves.get(sq).isEmpty()) {
            movableSquares.addAll(allSquares);
            return false;
        } else return true;
    }

    /**
     * Checks if the white king is threatened
     * @return boolean representing whether the white king is in check.
     */
    public boolean whiteInCheck() {
        update();
        Square sq = whiteKing.getPosition();
        if (blackMoves.get(sq).isEmpty()) {
            movableSquares.addAll(allSquares);
            return false;
        } else return true;
    }

    /**
     * Checks whether black is in .checkmate
     * @return boolean representing if black player is checkmated.
     */
    public boolean blackCheckMated() {
        boolean checkmate = true;
        // Check if black is in check
        if (!this.blackInCheck()) return false;

        // If yes, check if king can evade
        if (canEvade(whiteMoves, blackKing)) checkmate = false;

        // If no, check if threat can be captured
        List<Piece> threats = whiteMoves.get(blackKing.getPosition());
        if (canCapture(blackMoves, threats, blackKing)) checkmate = false;

        // If no, check if threat can be blocked
        if (canBlock(threats, blackMoves, blackKing)) checkmate = false;

        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }

    /**
     * Checks whether white is in checkmate.
     * @return boolean representing if white player is checkmated.
     */
    public boolean whiteCheckMated() {
        boolean checkmate = true;
        // Check if white is in check
        if (!this.whiteInCheck()) return false;

        // If yes, check if king can evade
        if (canEvade(blackMoves, whiteKing)) checkmate = false;

        // If no, check if threat can be captured
        List<Piece> threats = blackMoves.get(whiteKing.getPosition());
        if (canCapture(whiteMoves, threats, whiteKing)) checkmate = false;

        // If no, check if threat can be blocked
        if (canBlock(threats, whiteMoves, whiteKing)) checkmate = false;

        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }

    /*
     * Helper method to determine if the king can evade the check.
     * Gives a false positive if the king can capture the checking piece.
     */
    private boolean canEvade(Map<Square,List<Piece>> tMoves, King tKing) {
        boolean evade = false;
        List<Square> kingsMoves = tKing.getLegalMoves(board);
        Iterator<Square> iterator = kingsMoves.iterator();

        // If king is not threatened at some square, it can evade
        while (iterator.hasNext()) {
            Square sq = iterator.next();
            if (!testMove(tKing, sq)) continue;
            if (tMoves.get(sq).isEmpty()) {
                movableSquares.add(sq);
                evade = true;
            }
        }

        return evade;
    }

    /*
     * Helper method to determine if the threatening piece can be captured.
     */
    private boolean canCapture(Map<Square,List<Piece>> poss,
            List<Piece> threats, King k) {

        boolean capture = false;
        if (threats.size() == 1) {
            Square sq = threats.get(0).getPosition();

            if (k.getLegalMoves(board).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(k, sq)) {
                    capture = true;
                }
            }

            List<Piece> caps = poss.get(sq);
            ConcurrentLinkedDeque<Piece> capturers = new ConcurrentLinkedDeque<Piece>();
            capturers.addAll(caps);

            if (!capturers.isEmpty()) {
                movableSquares.add(sq);
                for (Piece p : capturers) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }

        return capture;
    }

    /*
     * Helper method to determine if check can be blocked by a piece.
     */

    private boolean canBlock(List<Piece> threats, Map<Square, List<Piece>> blockers, King king) {
        if (threats.size() != 1) return false;
        Square threatPos = threats.get(0).getPosition();
        Square kingPos = king.getPosition();
        Square[][] grid = board.getSquareArray();

        return tryBlockLine(threatPos, kingPos, blockers, grid, threats.get(0));
    }


    // I extraxted helper method tryBlockLine
    private boolean tryBlockLine(Square threat, Square king, Map<Square, List<Piece>> blockers,
                                 Square[][] grid, Piece threatPiece) {
        boolean blocked = false;
        int dx = Integer.compare(threat.getXNum(), king.getXNum());
        int dy = Integer.compare(threat.getYNum(), king.getYNum());
        int x = king.getXNum() + dx;
        int y = king.getYNum() + dy;

        while (x != threat.getXNum() || y != threat.getYNum()) {
            Square square = grid[y][x];
            for (Piece blocker : blockers.get(square)) {
                if (testMove(blocker, square)) {
                    movableSquares.add(square);
                    blocked = true;
                }
            }
            x += dx;
            y += dy;
        }

        return blocked && (threatPiece instanceof Queen || threatPiece instanceof Bishop || threatPiece instanceof Rook);
    }

    /**
     * Method to get a list of allowable squares that the player can move.
     * Defaults to all squares, but limits available squares if player is in
     * check.
     * @param b boolean representing whether it's white player's turn (if yes,
     * true)
     * @return List of squares that the player can move into.
     */
    public List<Square> getAllowableSquares(boolean b) {
        movableSquares.clear(); // I replace removalALL with .clear() for reduce reduntant
        if (whiteInCheck()) {
            whiteCheckMated();
        } else if (blackInCheck()) {
            blackCheckMated();
        }
        return movableSquares;
    }

    /**
     * Tests a move a player is about to make to prevent making an illegal move
     * that puts the player in check.
     * @param p model.Piece moved
     * @param sq model.Square to which p is about to move
     * @return false if move would cause a check
     */
    public boolean testMove(Piece p, Square sq) {
        Piece c = sq.getOccupyingPiece();

        boolean movetest = true;
        Square init = p.getPosition();

        p.move(sq);
        update();

        if (p.getColor() == 0 && blackInCheck()) movetest = false;
        else if (p.getColor() == 1 && whiteInCheck()) movetest = false;

        p.move(init);
        if (c != null) sq.put(c);

        update();

        movableSquares.addAll(allSquares);
        return movetest;
    }

}
