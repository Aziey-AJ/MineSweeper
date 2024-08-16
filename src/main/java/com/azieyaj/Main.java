package com.azieyaj;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //To read user input
        Scanner scanner = new Scanner(System.in);
        int size, numMines;

        //while loop to handle the program to repeatedly ask for input until valid values are provided
        while (true) {
            try {
                //Prompt user input for grid size
                System.out.print("Enter the grid size: ");
                size = Integer.parseInt(scanner.nextLine());
                //Prompt user input for number of mines to insert
                System.out.print("Enter the number of mines: ");
                numMines = Integer.parseInt(scanner.nextLine());

                //To validate that the number of mines is less than the total number of cells
                if (numMines >= size * size) {
                    System.out.println("The number of mines must be less than the total number of cells.");
                    continue;
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integers.");
            }
        }

        //create MinesweeperGame instance to start playing
        MinesweeperGame game = new MinesweeperGame(size, numMines);
        game.play();

        scanner.close();
    }
}