package test;

import main.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    /**
     * A simple test double that plays from a fixed script of coordinates.
     */
    static class ScriptedPlayer extends Player {
        private final int[][] script;
        private int i = 0;

        ScriptedPlayer(Mark mark, int[][] script) {
            super(mark);
            this.script = script;
        }

        @Override
        public Move nextMove(Board board) {
            if (i >= script.length) throw new IllegalStateException("script exhausted");
            int r = script[i][0], c = script[i][1];
            i++;
            return new Move(r, c, mark);
        }
    }

    @Test
    void scriptedGameLeadsToXWin() {
        Board b = new Board(3);
        Player x = new ScriptedPlayer(Mark.X, new int[][]{{0, 0}, {0, 1}, {0, 2}});
        Player o = new ScriptedPlayer(Mark.O, new int[][]{{1, 0}, {1, 1}});

        // run a few plies manually to avoid printing
        b.place(x.nextMove(b)); // X (0,0)
        b.place(o.nextMove(b)); // O (1,0)
        b.place(x.nextMove(b)); // X (0,1)
        b.place(o.nextMove(b)); // O (1,1)
        b.place(x.nextMove(b)); // X (0,2)

        assertTrue(b.winner().isPresent());
        assertEquals(Mark.X, b.winner().get());
    }
}