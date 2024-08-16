package com.azieyaj;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MinesweeperTest {
    private MinesBoard board;

    @BeforeEach
    void setUp() {
        board = new MinesBoard(5, 5);
    }

    @Test
    public void testCellInitialization() {
        Cells cell = new Cells();
        Assertions.assertFalse(cell.isMine());
        Assertions.assertFalse(cell.isRevealed());
        Assertions.assertEquals(0, cell.getAdjacentMines());
    }

    @Test
    public void testCellReveal() {
        Cells cell = new Cells();
        cell.reveal();
        Assertions.assertTrue(cell.isRevealed());
    }

    @Test
    public void testCellToString() {
        Cells cell = new Cells();
        Assertions.assertEquals(".", cell.toString());

        cell.reveal();
        Assertions.assertEquals(" ", cell.toString());

        cell.setAdjacentMines(3);
        Assertions.assertEquals("3", cell.toString());

        cell.setMine(true);
        Assertions.assertEquals("*", cell.toString());
    }

    @Test
    public void testBoardInitialization() {
        MinesBoard board = new MinesBoard(5, 5);
        Assertions.assertEquals(5, board.getSize());
        Assertions.assertEquals(5, board.getNumMines());

        int mineCount = 0;
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i, j).isMine()) {
                    mineCount++;
                }
            }
        }
        Assertions.assertEquals(5, mineCount);
    }

    @Test
    public void testBoardReveal() {
        MinesBoard board = new MinesBoard(5, 5);
        board.getCell(0, 0).setMine(true);

        Assertions.assertFalse(board.reveal(0, 0));
        Assertions.assertTrue(board.reveal(1, 1));
        Assertions.assertTrue(board.getCell(1, 1).isRevealed());
    }

    @Test
    public void testGameWon() {
        MinesBoard board = new MinesBoard(3, 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board.getCell(i, j).isMine()) {
                    board.getCell(i, j).reveal();
                }
            }
        }
        Assertions.assertTrue(board.isGameWon());
    }

    @Test
    public void testRevealEmptyCell() {
        board.getCell(0, 0).setAdjacentMines(0);
        Assertions.assertTrue(board.reveal(0, 0));
        Assertions.assertTrue(board.getCell(0, 0).isRevealed());
    }

    @Test
    public void testRevealMine() {
        board.getCell(0, 0).setMine(true);
        Assertions.assertFalse(board.reveal(0, 0));
    }

    @Test
    public void testGameNotWonInitially() {
        Assertions.assertFalse(board.isGameWon());
    }

    @Test
    public void testEndToEndGamePlay() {
        String input = "0 0\n0 1\n0 2\n1 0\n1 1\n1 2\n2 0\n2 1\n2 2\n";
        Scanner mockScanner = new Scanner(input);
        MinesweeperGame game = new MinesweeperGame(3, 1, mockScanner);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        game.play();

        String output = outContent.toString();
        Assertions.assertTrue(output.contains("Game Over") || output.contains("Congratulations"));

        System.setOut(System.out); // Reset System.out
    }

}