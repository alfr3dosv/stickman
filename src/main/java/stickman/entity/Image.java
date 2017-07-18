package stickman.entity;

import stickman.display.Display;

public class Image
{
    private char[][] img;
    public Size size;

    public Image(Size newSize, char[][] img)
    {
        size = newSize;
        this.img = cloneChars(img);
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
        char[][] newImg = new char[size.y][size.x];
        for(int y=0; y<size.y; y++) {
            for(int x=0; x<size.x; x++) {
                newImg[y][x] = img[y][x];
            }
        }
        return new Image(size, img);
    }

    public char[][] cloneChars(char[][] source) {
        char[][] newImg = new char[size.y][size.x];
        for(int y=0; y<size.y; y++) {
            for(int x=0; x<size.x; x++) {
                newImg[y][x] = source[y][x];
            }
        }
        return newImg;
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
