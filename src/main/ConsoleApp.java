package main;

import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        Board board = new Board(3);
        Scanner in = new Scanner(System.in);
        Player human = new HumanPlayer(Mark.X, in);
        Player ai = new RandomAIPlayer(Mark.O);
        new Game(board, human, ai).run();
    }
}
