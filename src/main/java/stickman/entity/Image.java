package stickman.entity;

import stickman.display.Display;

public class Image
{
    private char[][] img;
    public int SIZE_X;
    public int SIZE_Y;

    public Image(int SIZE_X, int SIZE_Y) {
        this.init(SIZE_Y, SIZE_X);
    }

    public Image(int SIZE_X, int SIZE_Y, char[][] img )
    {
        this.init(SIZE_Y, SIZE_X);
        this.img = img;
    }

    public void init(int SIZE_X, int SIZE_Y) {
        //no negativos
        this.SIZE_Y = SIZE_Y;
        this.SIZE_X = SIZE_X;
        this.img = new char[SIZE_Y][SIZE_X];
    }

    public char[][] get() {
        return this.img;
    }
}
