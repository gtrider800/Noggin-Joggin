package com.example.noggin_joggin;

import java.util.List;
import android.widget.GridLayout;
import android.widget.ImageView;


public class ElectricLinkGame
{
    private final Current[][] grid;
    private final int rows;
    private final int cols;
    private int score;
    private final GridLayout gameGrid;

    public ElectricLinkGame(int rows, int cols, GridLayout gameGrid)
    {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Current[rows][cols];
        this.score = 0;
        this.gameGrid = gameGrid;
        initializeGrid();
    }

    private void initializeGrid()
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
               int direction = (i + j) % 11;
               grid[i][j] = new Current(direction);
            }
        }
    }

    public void rotateCurrent(int row, int col)
    {
        Current current = grid[row][col];
        if (current.canRotate())
        {
            current.rotate();
            checkConnections();
        }
    }

    public Current getCurrent(int row, int col)
    {
        return grid[row][col];
    }

    private void checkConnections()
    {
        score = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (isCurrentConnected(i, j))
                {
                    score++;
                }
            }
        }
    }

    public boolean isCurrentConnected(int row, int col)
    {
        Current current = grid[row][col];
        int direction = current.getDirection();

        if (row > 0 && isConnecting(direction, grid[row - 1][col].getDirection()))
        {
            return true;
        }

        if (row < rows - 1 && isConnecting(direction, grid[row + 1][col]. getDirection()))
        {
            return true;
        }

        if (col > 0 && isConnecting(direction, grid[row][col - 1].getDirection()))
        {
            return true;
        }
        return col < cols - 1 && isConnecting(direction, grid[row][col + 1].getDirection());
    }

    private boolean isConnecting(int direction, int directionToCheck)
    {
        switch (direction)
        {
            case 0: return (directionToCheck == 1 || directionToCheck == 3);
            case 1: return (directionToCheck == 0 || directionToCheck == 3);
            case 2: return true;
            case 3: return (directionToCheck == 1 || directionToCheck == 2);
            case 4:
            case 7:
            case 9:
                return (directionToCheck == 0 || directionToCheck == 2);
            case 5:
            case 10:
                return (directionToCheck == 0 || directionToCheck == 1);
            case 6: return (directionToCheck == 2 || directionToCheck == 1);
            case 8: return (directionToCheck == 2 || directionToCheck == 3);
            default: return false;
        }
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

    public int getPieceCount(int pieceType)
    {
        int count = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (grid[i][j].getDirection() == pieceType)
                {
                    count++;
                }
            }
        }
        return count;
    }

}
