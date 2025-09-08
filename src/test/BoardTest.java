package test;

import main.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test void emptyBoardHasNoWinnerAndNotFull() {
        Board b = new Board(3);
        assertTrue(b.winner().isEmpty());
        assertFalse(b.isFull());
    }

    @Test void placeRejectsOutOfBounds() {
        Board b = new Board(3);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> b.place(new Move(3, 0, Mark.X)));
        assertTrue(ex.getMessage().contains("out of bounds"));
    }

    @Test void placeRejectsWrongTurn() {
        Board b = new Board(3);
        // X goes first; trying O first should fail
        assertThrows(IllegalArgumentException.class,
                () -> b.place(new Move(0, 0, Mark.O)));
    }

    @Test void rowWinDetected() {
        Board b = new Board(3);
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(1,0, Mark.O));
        b.place(new Move(0,1, Mark.X));
        b.place(new Move(1,1, Mark.O));
        b.place(new Move(0,2, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test void colWinDetected() {
        Board b = new Board(3);
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(0,1, Mark.O));
        b.place(new Move(1,0, Mark.X));
        b.place(new Move(1,1, Mark.O));
        b.place(new Move(2,0, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test void diagWinDetected() {
        Board b = new Board(3);
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(0,1, Mark.O));
        b.place(new Move(1,1, Mark.X));
        b.place(new Move(0,2, Mark.O));
        b.place(new Move(2,2, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test void cannotPlaceOnOccupiedCell() {
        Board b = new Board(3);
        b.place(new Move(0,0, Mark.X));
        assertThrows(IllegalArgumentException.class, () -> b.place(new Move(0,0, Mark.O)));
    }

    @Test void resetClearsBoardAndResetsTurn() {
        Board b = new Board(3);
        b.place(new Move(0,0, Mark.X));
        b.reset();
        assertEquals(Mark.EMPTY, b.getCell(0,0));
        assertEquals(Mark.X, b.currentTurn());
    }
}
