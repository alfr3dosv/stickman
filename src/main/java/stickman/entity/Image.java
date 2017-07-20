package stickman.entity;

import java.util.ArrayList;
import java.util.List;
import stickman.display.Display;

public class Image
{
    public char[][] img;
    public List<String> lines = new ArrayList<String>();
    public Size size;
    public int spaces;

    public Image(Size newSize, char[][] img)
    {
        size = newSize;
        this.img = cloneChars(img);
    }

    public Image(List<String> lines)
    {
        int max = 0;
        for(String s: lines){
            if(s.length() > max)
                max = s.length();
        }
        size = new Size(max, lines.size());
        img = new char[size.y][size.x];
        int i = 0;
        for(String s: lines) {
            s = fillSpaces(s, size.x - s.length());
            img[i++] = s.toCharArray();
        }
    }

    public String fillSpaces(String line, int spacesToFill) {
        for(int i = 0; i < spaces; i++)
            line += " ";
        return line;
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

    public char[][] getChars() {
        return this.img;
    }

    public Image clone() {
        char[][] newImg = new char[size.y][size.x];
        for(int y=0; y < size.y; y++) {
            for(int x=0; x < size.x; x++) {
                newImg[y][x] = img[y][x];
            }
        }
        return new Image(size, img);
    }

    public char[][] cloneChars(char[][] source) {
        char[][] newImg = new char[size.y][size.x];
        for(int y = 0; y < size.y; y++) {
            for(int x = 0; x < size.x; x++) {
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
            for(int x = 0; x < size.x; x++)
                img[sourcePosition.y + y][sourcePosition.x + x] = sourceChars[y][x];
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int y = 0; y < size.y; y++) {
            str.append(img[y]);
            str.append("\n");
        }
        return str.toString();
    }
}
