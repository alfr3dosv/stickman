package stickman.core.entity;

import java.util.ArrayList;
import java.util.List;

public class Image
{
    public char[][] img;
    public List<String> lines = new ArrayList<String>();
    public Size size;
    public int spaces;

    public Image(char c) {
        this(new char[][] {{c}});
    }

    public Image(Image i) {
        this(i.img);
    }

    public Image(Size newSize, char[][] img)
    {
        size = newSize;
        this.img = cloneChars(img);
    }

    public Image(Size newSize)
    {
        size = newSize;
        char[][] previous = new char[size.y][size.x];
        img = cloneChars(previous);
    }

    public Image(char[][] matrix) {
        this(charsToList(matrix));
    }

    public Image(List<String> lines)
    {
        int max = 0;
        for(String s: lines){
            if(s.length() > max)
                max = s.length();
        }
        size = new Size(max, lines.size());
        char[][] previous = new char[size.y][size.x];
        int i = 0;
        for(String s: lines) {
            previous[i++] = s.toCharArray();
        }
        img = cloneChars(previous);
    }

    private static List<String> charsToList(char[][] matrix) {
        List<String> lines = new ArrayList<>();
        for (char[] c : matrix)
            lines.add(new String(c));
        return lines;
    }

    public Image clone() {
        return new Image(size, cloneChars(img));
    }

    private char[][] cloneChars(char[][] source) {
        char[][] newImg = new char[size.y][size.x];
        for(int y = 0; y < size.y; y++) {
            if(y < source.length)
                newImg[y] = cloneCharsArray(source[y]);
            else  //bigger than source
                newImg[y] = cloneCharsArray(new char[size.x]);
        }
        return newImg;
    }
    private char[] cloneCharsArray(char[] charsToCopy) {
        char[] chars = new char[size.x];
        for(int i = 0; i < size.x; i++) {
            if(i >= charsToCopy.length)
                chars[i] = ' ';
            else if(charsToCopy[i] == '\0')
                chars[i] = ' ';
            else
                chars[i] = charsToCopy[i];
        }
        return chars;
    }

    public String toString() {
        if(isEmpty(this))
            return " ";
        StringBuilder str = new StringBuilder();
        for(int y = 0; y < size.y; y++) {
            str.append(img[y]);
            str.append("\n");
        }
        return str.toString();
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

    public boolean isEmpty(Image i) {
        return i.size.x == 0 && i.size.y == 0;
    }
}
