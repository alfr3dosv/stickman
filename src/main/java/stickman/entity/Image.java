package stickman.entity;

import stickman.display.Display;

public class Image
{
    private char[][] img;
    public int SIZE_X;
    public int SIZE_Y;
    public Size size;

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

    public Size getSize() {
        return size;
    }

    public char[][] chars() {
        return this.img;
    }

    public Image clone() {
        char[][] newImg = new char[SIZE_Y][SIZE_X];
        for(int y=0; y<SIZE_Y; y++) {
            for(int x=0; x<SIZE_X; x++) {
                newImg[y][x] = img[y][x];
            }
        }
        return new Image(SIZE_X, SIZE_Y, img);
    }

    public void draw(Image source, Point sourcePosition) {
        Image sourceCopy = source.clone();
        char[][] sourceChars = sourceCopy.chars();
        Size size = source.getSize();
        for(int y = 0; y < size.y; y++)
            for(int x=0; x < size.x; x++)
                img[sourcePosition.y + y][sourcePosition.x + x] = sourceChars[y][x];
    }
}
