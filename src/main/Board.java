package main;

import java.util.Optional;
import java.util.Deque;
import java.util.ArrayDeque;

public class Board {
    private final int size;
    private final int winCondition;
    private final Mark[][] grid;
    private final Deque<Move> history = new ArrayDeque<>();
    private Mark currentTurn;

    public Board() { this(3); }

    public Board(int size) {
        this(size, 3); // default to 3-in-a-row
    }

    public Board(int size, int winCondition) {
        if (size < 3) throw new IllegalArgumentException("size must be ≥ 3");
        if (winCondition < 3 || winCondition > size)
            throw new IllegalArgumentException("winCondition must be between 3 and size");
        this.size = size;
        this.winCondition = winCondition;
        this.grid = new Mark[size][size];
        reset();
    }

    public int size() { return size; }
    public int winCondition() { return winCondition; }

    public void reset() {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                grid[r][c] = Mark.EMPTY;
        history.clear();
        currentTurn = Mark.X;
    }

    public Mark getCell(int r, int c) {
        checkBounds(r, c);
        return grid[r][c];
    }

    public boolean isFull() {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (grid[r][c] == Mark.EMPTY) return false;
        return true;
    }

    /** Applies a move if valid; else throws IllegalArgumentException. */
    public void place(Move mv) {
        if (mv == null) throw new IllegalArgumentException("move is null");
        int r = mv.row(), c = mv.col();
        checkBounds(r, c);
        if (mv.mark() != currentTurn)
            throw new IllegalArgumentException("It's " + currentTurn + "'s turn");
        if (grid[r][c] != Mark.EMPTY)
            throw new IllegalArgumentException("cell not empty");

        grid[r][c] = mv.mark();
        history.push(mv);
        currentTurn = currentTurn.opposite();
    }

    /** Undo the last move and return it. */
    public Move undo() {
        if (history.isEmpty()) throw new IllegalStateException("nothing to undo");
        Move last = history.pop();
        grid[last.row()][last.col()] = Mark.EMPTY;
        currentTurn = currentTurn.opposite(); // revert turn
        return last;
    }

    public boolean canUndo() { return !history.isEmpty(); }

    public Mark currentTurn() { return currentTurn; }

    public Optional<Mark> winner() {
        final int k = winCondition;
        final int[][] dirs = { {0,1}, {1,0}, {1,1}, {1,-1} };

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Mark start = grid[r][c];
                if (start == Mark.EMPTY) continue;

                for (int[] d : dirs) {
                    int dr = d[0], dc = d[1];
                    // For K-in-a-row, only check windows that fit
                    if (!canFit(r, c, dr, dc, k)) continue;

                    boolean ok = true;
                    for (int i = 1; i < k; i++) {
                        int rr = r + i * dr, cc = c + i * dc;
                        if (grid[rr][cc] != start) { ok = false; break; }
                    }
                    if (ok) return Optional.of(start);
                }
            }
        }
        return Optional.empty();
    }

    private boolean canFit(int r, int c, int dr, int dc, int k) {
        int rEnd = r + (k - 1) * dr;
        int cEnd = c + (k - 1) * dc;
        return rEnd >= 0 && rEnd < size && cEnd >= 0 && cEnd < size;
    }

    private void checkBounds(int r, int c) {
        if (r < 0 || r >= size || c < 0 || c >= size)
            throw new IllegalArgumentException("out of bounds: (" + r + "," + c + ")");
    }
}

