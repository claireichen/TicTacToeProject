package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmartAIPlayer extends Player {
    private final Random rnd = new Random();

    public SmartAIPlayer(Mark mark) { super(mark); }

    @Override
    public Move nextMove(Board board) {
        // 1) Try to win now
        Move win = findWinningMove(board, mark);
        if (win != null) return win;

        // 2) Block opponent's win
        Move block = findWinningMove(board, mark.opposite());
        if (block != null) return new Move(block.row(), block.col(), mark);

        // 3) Heuristic
        List<int[]> empties = emptyCells(board);
        int bestScore = Integer.MIN_VALUE;
        List<int[]> picks = new ArrayList<>();

        for (int[] rc : empties) {
            int score = heuristicScore(board, rc[0], rc[1]);
            if (score > bestScore) {
                bestScore = score;
                picks.clear();
                picks.add(rc);
            } else if (score == bestScore) {
                picks.add(rc);
            }
        }

        int[] choice = picks.get(rnd.nextInt(picks.size()));
        return new Move(choice[0], choice[1], mark);
    }

    private Move findWinningMove(Board b, Mark target) {
        for (int[] rc : emptyCells(b)) {
            if (wouldWinAfterPlacing(b, target, rc[0], rc[1])) {
                return new Move(rc[0], rc[1], mark);
            }
        }
        return null;
    }

    private List<int[]> emptyCells(Board b) {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < b.size(); r++)
            for (int c = 0; c < b.size(); c++)
                if (b.getCell(r, c) == Mark.EMPTY)
                    cells.add(new int[]{r, c});
        return cells;
    }

    // True if placing 'm' at (r,c) would complete a K-in-a-row line that includes (r,c).
    private boolean wouldWinAfterPlacing(Board b, Mark m, int r, int c) {
        if (b.getCell(r, c) != Mark.EMPTY) return false;
        int n = b.size(), k = b.winCondition();
        int[][] dirs = {{1,0},{0,1},{1,1},{1,-1}};

        for (int[] d : dirs) {
            int dr = d[0], dc = d[1];
            // Consider every window of length k along this direction that *could* include (r,c)
            for (int offset = 0; offset < k; offset++) {
                int r0 = r - offset * dr;
                int c0 = c - offset * dc;
                int rEnd = r0 + (k - 1) * dr;
                int cEnd = c0 + (k - 1) * dc;

                if (r0 < 0 || r0 >= n || c0 < 0 || c0 >= n) continue;
                if (rEnd < 0 || rEnd >= n || cEnd < 0 || cEnd >= n) continue;

                boolean ok = true;
                for (int i = 0; i < k; i++) {
                    int rr = r0 + i * dr, cc = c0 + i * dc;
                    if (rr == r && cc == c) continue;        // we place here
                    if (b.getCell(rr, cc) != m) { ok = false; break; }
                }
                if (ok) return true;
            }
        }
        return false;
    }

    private int heuristicScore(Board b, int r, int c) {
        int n = b.size();
        // center (odd n only)
        if (n % 2 == 1 && r == n / 2 && c == n / 2) return 3;
        // corners
        boolean corner = (r == 0 || r == n - 1) && (c == 0 || c == n - 1);
        if (corner) return 2;
        // sides
        return 1;
    }
}
