package main;

import java.util.Optional;

public class Game {
    private final Board board;
    private Player current;
    private Player other;

    public Game(Board board, Player p1, Player p2) {
        this.board = board;
        this.current = p1;
        this.other = p2;
    }

    public void run() {
        while (true) {
            printBoard();
            Optional<Mark> w = board.winner();
            if (w.isPresent()) {
                System.out.println("Winner: " + w.get());
                printBoard(); return;
            }
            if (board.isFull()) {
                System.out.println("Draw!");
                printBoard(); return;
            }

            Move mv = current.nextMove(board);
            try {
                board.place(mv);
                swapPlayers();
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid move: " + ex.getMessage());
                // same current player tries again
            }
        }
    }

    private void swapPlayers() { Player tmp = current; current = other; other = tmp; }

    private void printBoard() {
        int n = board.size();
        System.out.println();
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                Mark m = board.getCell(r, c);
                sb.append(switch (m) { case X -> "X"; case O -> "O"; default -> "."; });
                if (c < n - 1) sb.append(" | ");
            }
            System.out.println(sb);
            if (r < n - 1) System.out.println("--+".repeat(n-1) + "--");
        }
        System.out.println("Turn: " + board.currentTurn());
        System.out.println();
    }
}
