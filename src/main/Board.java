package main;

import java.util.Optional;

public class Board {
    private final int size;
    private final Mark[][] grid;
    private Mark currentTurn;

    public Board(int size) {
        if (size < 3) throw new IllegalArgumentException("size must be ≥ 3");
        this.size = size;
        this.grid = new Mark[size][size];
        reset();
    }

    public int size() { return size; }

    public void reset() {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                grid[r][c] = Mark.EMPTY;
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
        // advance turn only if game not already won; harmless otherwise
        currentTurn = currentTurn.opposite();
    }

    public Mark currentTurn() { return currentTurn; }

    public Optional<Mark> winner() {
        // rows
        for (int r = 0; r < size; r++) {
            Mark m = lineWinner(r, 0, 0, 1);
            if (m != Mark.EMPTY) return Optional.of(m);
        }
        // cols
        for (int c = 0; c < size; c++) {
            Mark m = lineWinner(0, c, 1, 0);
            if (m != Mark.EMPTY) return Optional.of(m);
        }
        // diagonals
        Mark d1 = lineWinner(0, 0, 1, 1);
        if (d1 != Mark.EMPTY) return Optional.of(d1);
        Mark d2 = lineWinner(0, size - 1, 1, -1);
        if (d2 != Mark.EMPTY) return Optional.of(d2);

        return Optional.empty();
    }

    private Mark lineWinner(int r0, int c0, int dr, int dc) {
        Mark first = grid[r0][c0];
        if (first == Mark.EMPTY) return Mark.EMPTY;
        int r = r0, c = c0;
        for (int i = 1; i < size; i++) {
            r += dr; c += dc;
            if (grid[r][c] != first) return Mark.EMPTY;
        }
        return first;
    }

    private void checkBounds(int r, int c) {
        if (r < 0 || r >= size || c < 0 || c >= size)
            throw new IllegalArgumentException("out of bounds: (" + r + "," + c + ")");
    }
}

