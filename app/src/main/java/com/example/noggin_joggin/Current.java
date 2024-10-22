package com.example.noggin_joggin;

import java.util.ArrayList;
import java.util.List;

public class Current
{
    private int direction;
    private int usageCount;
    private final int maxUsage;

    public Current()
    {
        this.direction = 0;
        this.usageCount = 0;
        this.maxUsage = 5;
    }

    public Current(int direction)
    {
        this.direction = direction;
        this.usageCount = 0;
        this.maxUsage = 5;
    }

    public int getDirection()
    {
        return direction;
    }

    public int getUsageCount()
    {
        return usageCount;
    }

    public int getMaxUsage()
    {
        return maxUsage;
    }

    public boolean canRotate()
    {
        return usageCount < maxUsage;
    }

    public void rotate()
    {
        if (canRotate())
        {
            direction = (direction + 1) % 11;
            usageCount++;
        }

    }

    public List<Position> getConnectedPositions(Current[][] grid, int row, int col)
    {
        List<Position> connectedPositions = new ArrayList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        dfs(grid, row, col, connectedPositions, visited);
        return connectedPositions;
    }

    private void dfs(Current[][] grid,int row, int col, List<Position> connectedPositions, boolean[][] visited)
    {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || visited[row][col])
        {
            return;
        }

        visited[row][col] = true;
        connectedPositions.add(new Position(row, col));

        int direction = grid[row][col].getDirection();

        if (direction == 0)
        {
            dfs(grid, row - 1, col, connectedPositions, visited);
        }
        else if (direction == 1)
        {
            dfs(grid, row, col + 1, connectedPositions, visited);
        }
        else if (direction == 2)
        {
            dfs(grid, row + 1, col, connectedPositions, visited);
        }
        else if (direction == 3)
        {
            dfs(grid, row, col - 1, connectedPositions, visited);
        }
        else if (direction == 4)
        {
            dfs(grid, row, col + 1, connectedPositions, visited);
        }
        else if (direction == 5)
        {
            dfs(grid, row - 1, col, connectedPositions, visited);
        }
    }
}
