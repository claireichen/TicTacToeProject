package main;

public class Undo extends RuntimeException{
    private final int count;
    public Undo() { this(1); }
    public Undo(int count) {
        super("undo x" + count);
        if (count < 1) throw new IllegalArgumentException("count must be ≥ 1");
        this.count = count;
    }
    public int count() { return count; }
}
