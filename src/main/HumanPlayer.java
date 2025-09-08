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
        while (true) {
            System.out.print(mark + " enter row and col (0-" + (board.size()-1) + "): ");
            String line = in.nextLine().trim();
            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Please enter two integers.");
                continue;
            }
            try {
                int r = Integer.parseInt(parts[0]);
                int c = Integer.parseInt(parts[1]);
                return new Move(r, c, mark);
            } catch (NumberFormatException e) {
                System.out.println("Invalid numbers. Try again.");
            }
        }
    }
}
