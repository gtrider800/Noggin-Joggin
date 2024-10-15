package com.example.noggin_joggin;

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
}
