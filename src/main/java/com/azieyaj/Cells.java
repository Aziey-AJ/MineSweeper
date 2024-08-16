package com.azieyaj;

public class Cells{
    private boolean isMine;
    private boolean isRevealed;
    private int adjacentMines;

    //Getter and setter methods
    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    @Override
    public String toString() {
        //if unrevealed cells, return .
        if (!isRevealed) {
            return ".";
        }
        //if mines, return *
        if (isMine) {
            return "*";
        }
        return adjacentMines > 0 ? String.valueOf(adjacentMines) : " ";
    }
}