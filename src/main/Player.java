package main;

public abstract class Player {
    protected final Mark mark;

    protected Player(Mark mark) {
        if (mark == null || mark == Mark.EMPTY) throw new IllegalArgumentException("mark must be X or O");
        this.mark = mark;
    }

    /** Compute the next move for this player given the current board. */
    public abstract Move nextMove(Board board);
}
