package com.example.noggin_joggin;

public class Current
{
    private int direction;

    public Current()
    {
        this.direction = 0;
    }

    public Current(int Direction)
    {
        this.direction = Direction;
    }

    public int getDirection()
    {
        return direction;
    }

    public void rotate()
    {
        direction = (direction + 1) % 11;
    }
}
