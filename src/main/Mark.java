package main;

public enum Mark {
    X, O, EMPTY;

    public Mark opposite() {
        return switch (this) {
            case X -> O;
            case O -> X;
            default -> EMPTY;
        };
    }
}