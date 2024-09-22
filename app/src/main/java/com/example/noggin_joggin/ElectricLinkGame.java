package com.example.noggin_joggin;

public class ElectricLinkGame
{
    private final Current[][] grid;
    private final int rows;
    private final int cols;
    private int score;

    public ElectricLinkGame(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Current[rows][cols];
        this.score = 0;
        initializeGrid();
    }

    private void initializeGrid()
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = new Current();
            }
        }
    }

    public void rotateCurrent(int row, int col)
    {
        grid[row][col].rotate();
        checkConnections();
    }

    private void checkConnections()
    {

    }

    public Current getCurrent(int row, int col)
    {
        return grid[row][col];
    }

    public int getScore()
    {
        return score;
    }

    public void resetGame()
    {
        initializeGrid();
        score = 0;
    }
}
