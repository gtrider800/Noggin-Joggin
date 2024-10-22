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
                    List<Position> connectedPositions = grid[i][j].getConnectedPositions(grid, i, j);
                    for (Position pos : connectedPositions)
                    {
                        ImageView currentView = (ImageView) gameGrid.getChildAt(pos.row * cols + pos.col);
                        currentView.setImageResource(R.drawable.current_flow);
                    }
                }
            }
        }
    }

    public boolean isCurrentConnected(int row, int col)
    {
        Current current = grid[row][col];
        int direction = current.getDirection();

        if (row > 0 && isConnecting(direction, grid[row -1][col].getDirection(), 0))
        {
            return true;
        }

        if (row < rows - 1 && isConnecting(direction, grid[row + 1][col].getDirection(), 2))
        {
            return true;
        }

        if (col > 0 && isConnecting(direction, grid[row][col - 1].getDirection(), 3))
        {
            return true;
        }

        return col < cols - 1 && isConnecting(direction, grid[row][col + 1].getDirection(), 1);
    }

    private boolean isConnecting(int fromDirection, int toDirection, int directionToCheck)
    {
        switch (directionToCheck)
        {
            case 0: return fromDirection == 2 && toDirection == 0;
            case 1: return fromDirection == 3 && toDirection == 1;
            case 2: return fromDirection == 0 && toDirection == 2;
            case 3: return fromDirection == 1 && toDirection == 3;
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
