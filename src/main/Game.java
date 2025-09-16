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
                printBoard();
                return;
            }
            if (board.isFull()) {
                System.out.println("Draw!");
                printBoard();
                return;
            }

            try {
                Move mv = current.nextMove(board);
                board.place(mv);
                swapPlayers(); // normal advance
            } catch (Undo ur) {
                int requested = ur.count();
                int undone = 0;
                for (int i = 0; i < requested; i++) {
                    if (!board.canUndo()) {
                        if (undone == 0) System.out.println("Nothing to undo.");
                        break;
                    }
                    board.undo();     // flips Board's turn
                    swapPlayers();    // keep Game's current/other aligned with Board
                    undone++;
                }
                if (undone > 0) System.out.println("Undid " + undone + " move" + (undone > 1 ? "s" : "") + ".");
                // loop continues with updated state/turn
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid move: " + ex.getMessage());
                // same player tries again
            }
        }
    }

    private void swapPlayers() { Player tmp = current; current = other; other = tmp; }

    /** Prints empty cells as 1..N*N, aligned for any board size. */
    private void printBoard() {
        int n = board.size();
        int maxNum = n * n;
        int width = Integer.toString(maxNum).length(); // width for alignment

        System.out.println();
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                Mark m = board.getCell(r, c);
                String cell;
                if (m == Mark.EMPTY) {
                    int cellNum = r * n + c + 1;
                    cell = String.format("%" + width + "d", cellNum);
                } else {
                    String symbol = (m == Mark.X) ? "X" : "O";
                    cell = String.format("%" + width + "s", symbol);
                }
                sb.append(cell);
                if (c < n - 1) sb.append(" | ");
            }
            System.out.println(sb);

            if (r < n - 1) {
                // separator like ---+---+--- with dynamic width
                String seg = "-".repeat(width);
                String sep = (seg + "-+-").repeat(n - 1) + seg;
                System.out.println(sep);
            }
        }
        System.out.println("Turn: " + board.currentTurn());
        System.out.println("(enter a cell number, or 'u' / 'undo [n]' to undo)");
        System.out.println();
    }
}
