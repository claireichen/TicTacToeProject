package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAIPlayer extends Player {
    private final Random rnd = new Random();

    public RandomAIPlayer(Mark mark) { super(mark); }

    @Override
    public Move nextMove(Board board) {
        List<int[]> empties = new ArrayList<>();
        for (int r = 0; r < board.size(); r++)
            for (int c = 0; c < board.size(); c++)
                if (board.getCell(r, c) == Mark.EMPTY)
                    empties.add(new int[]{r, c});
        if (empties.isEmpty()) throw new IllegalStateException("No moves available");
        int[] pick = empties.get(rnd.nextInt(empties.size()));
        return new Move(pick[0], pick[1], mark);
    }
}
