package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        Board board = new Board(3);
        Scanner in = new Scanner(System.in);
        Player human = new HumanPlayer(Mark.X, in);
        while (true) {
            System.out.println("Press 1 to play against the Computer, Press 2 to play against another person on the same computer.");
            System.out.print("Enter your choice: ");

            try {
                int menuChoice = in.nextInt();
                if(menuChoice == 1) {
                    System.out.println("Starting Player vs Computer Game:");
                    Player ai = new SmartAIPlayer(Mark.O);
                    new Game(board, human, ai).run();
                    break;

                } else if(menuChoice == 2) {
                    System.out.println("Starting Player vs Player Game:");
                    Player human2 = new HumanPlayer(Mark.O, in);
                    new Game(board, human, human2).run();
                    break;

                } else {
                    System.out.println("Invalid input! Please enter either 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric value only (1 or 2)!");
                in.nextLine();
            }
        }
    }
}
