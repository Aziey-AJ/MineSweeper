package com.azieyaj;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MinesweeperGame {
    private final MinesBoard board;
    private final Scanner scanner;
	private static final Logger LOGGER = Logger.getLogger(MinesweeperGame.class.getName());

    public MinesweeperGame(int size, int numMines) {
        this.board = new MinesBoard(size, numMines);
        this.scanner = new Scanner(System.in);
    }


    public void play() {
        while (true) {
            System.out.println(board);
            //Prompt user input for number row and column chosen
            System.out.print("Enter row and column (space-separated): ");
            String input = scanner.nextLine();
            
            try {
                String[] coordinates = parseInput(input);
                int row = Integer.parseInt(coordinates[0]);
                int col = Integer.parseInt(coordinates[1]);

                if (!isValidCoordinate(row, col)) {
                    System.out.println("Invalid coordinates. Please enter values within the board size.");
                    continue;
                }

                if (!board.reveal(row, col)) {
                    System.out.println("Game Over! You hit a mine.");
                    LOGGER.info("Game ended. Player hit a mine.");
                    return;
                }

                if (board.isGameWon()) {
                    System.out.println("Congratulations! You've won!");
                    LOGGER.info("Game won by player.");
                    return;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                LOGGER.log(Level.WARNING, "Invalid input: {0}", input);
            }
        }
    }

    private String[] parseInput(String input) throws IllegalArgumentException {
        String[] coordinates = input.split(" ");
        if (coordinates.length != 2) {
            throw new IllegalArgumentException("Invalid input! Please enter two integers separated by a space.");
        }
        return coordinates;
    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
    }

    public MinesweeperGame(int size, int numMines, Scanner scanner) {
        this.board = new MinesBoard(size, numMines);
        this.scanner = scanner;
    }
}