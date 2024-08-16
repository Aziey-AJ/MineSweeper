package com.azieyaj;

import java.util.Random;
import java.util.stream.IntStream;

public class MinesBoard {
    private final int size;
    private final int numMines;
    private final Cells[][] grid;

    public MinesBoard(int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        this.grid = new Cells[size][size];

        initializeGrid();
        placeMines();
        calculateAdjacentMines();
    }

    private void initializeGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cells();
            }
        }
    }

    private void placeMines() {
        MinesPlacer.placeMines(this.grid, this.numMines);
    }

    private void calculateAdjacentMines() {
        AdjacentMinesCalculator.calculateAdjacentMines(this.grid);
    }


    //To handle revealing cells and implements the flood-fill algorithm for empty cells
    public boolean reveal(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }

        Cells cell = grid[row][col];
        if (cell.isRevealed()) {
            return true;
        }

        cell.reveal();

        if (cell.isMine()) {
            return false;
        }

        if (cell.getAdjacentMines() == 0) {
            for (int i = Math.max(0, row - 1); i <= Math.min(size - 1, row + 1); i++) {
                for (int j = Math.max(0, col - 1); j <= Math.min(size - 1, col + 1); j++) {
                    reveal(i, j);
                }
            }
        }

        return true;
    }

    //To check if all non-mine cells are revealed
    public boolean isGameWon() {
        return IntStream.range(0, size)
                .allMatch(i -> IntStream.range(0, size)
                        .allMatch(j -> grid[i][j].isMine() || grid[i][j].isRevealed()));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("  " + IntStream.range(0, size)
                .mapToObj(String::valueOf)
                .reduce((a, b) -> a + " " + b)
                .orElse("") + "\n");

        for (int i = 0; i < size; i++) {
            result.append(i).append(" ");
            for (int j = 0; j < size; j++) {
                result.append(grid[i][j].toString()).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    //Getter methods
    public Cells getCell(int row, int col) {
        return grid[row][col];
    }

    public int getSize() {
        return size;
    }

    public int getNumMines() {
        return numMines;
    }

}

//Separate classes for MinesPlacer and AdjacentMinesCalculator follows the Single Responsibility Principle, for code organization
class MinesPlacer {
    public static void placeMines(Cells[][] grid, int numMines) {
        Random random = new Random();
        int size = grid.length;
        int minesPlaced = 0;

        while (minesPlaced < numMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);

            if (!grid[row][col].isMine()) {
                grid[row][col].setMine(true);
                minesPlaced++;
            }
        }
    }
}

class AdjacentMinesCalculator {
    public static void calculateAdjacentMines(Cells[][] grid) {
        int size = grid.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!grid[i][j].isMine()) {
                    grid[i][j].setAdjacentMines(countAdjacentMines(grid, i, j));
                }
            }
        }
    }

    private static int countAdjacentMines(Cells[][] grid, int row, int col) {
        int count = 0;
        int size = grid.length;
        for (int i = Math.max(0, row - 1); i <= Math.min(size - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(size - 1, col + 1); j++) {
                if (grid[i][j].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }
}
