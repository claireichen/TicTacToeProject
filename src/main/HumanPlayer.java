package main;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner in;

    public HumanPlayer(Mark mark, Scanner in) {
        super(mark);
        this.in = in;
    }

    @Override
    public Move nextMove(Board board) {
        int n = board.size() * board.size();

        while (true) {
            System.out.print(mark + " choose a cell [1-" + n + "]: ");
            String line = in.nextLine().trim();

            if (line.startsWith("u")) {
                int count = 1;
                String digits = line.replaceAll("[^0-9]", "");
                if (!digits.isEmpty()) count = Integer.parseInt(digits);
                throw new Undo(count);
            }
            int num;
            try {
                num = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 and " + n + ".");
                continue;
            }

            if (num < 1 || num > n) {
                System.out.println("Out of range. Try 1.." + n + ".");
                continue;
            }

            int idx = num - 1;
            int r = idx / board.size();
            int c = idx % board.size();

            if (board.getCell(r, c) != Mark.EMPTY) {
                System.out.println("That cell is already taken. Try again.");
                continue;
            }

            return new Move(r, c, mark);
        }
    }
}