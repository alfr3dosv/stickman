package stickman.util;

import java.util.Arrays;

import stickman.entity.Entity;
import stickman.entity.Image;
import stickman.entity.Size;
import stickman.entity.Point;

public class ImageDrawer {
    private static int spaces;
    private static Image source;
    private static Point whereToDraw;
    private static Point whereToCut;
    private static Image origin;
    private static Point position = new Point(0,0);

    public void setOutput(Image output) {
        origin = output;
    }

    public void draw(Image newSource, Point where) {
        source = newSource.clone();
        whereToDraw = where;
        char[][] sourceChars = source.getChars();
        int y = whereToDraw.y + 1;
        for (int i = sourceChars.length-1; i >= 0 ; i--) {
            drawLine(sourceChars[i], y);
            y++;
        }
    }

    private void drawLine(char[] line, int y) {
        int spaces = line.length;
        char[][] originChars = origin.getChars();
        int offsetX = whereToDraw.x;
        int offsetY = origin.size.y;
        while(spaces > origin.size.x)
            spaces--;
        for(int x = 0; x < spaces; x++) {
            int relativeX = x + offsetX;
            int relativeY = offsetY - y;
            if(isInsideFrame(relativeX, relativeY))
                originChars[relativeY][relativeX] = line[x];
        }
    }

    private boolean isInsideFrame(int x, int y) {
        boolean isXValid = (x < origin.size.x) && (x > position.x);
        boolean isYValid = (y < origin.size.y) && (y >= position.y);
        return isXValid && isYValid;
    }

    public Image cut(Size size, Point where) {
        whereToCut = where;
        Image imageCut = new Image(size);
        char[][] imgChars = new char[size.y][size.x];
        int y = whereToCut.y + 1;
        for (int i = imgChars.length - 1; i >= 0; i--) {
            imgChars[i] = cutLine(size.x, y);
            y++;
        }
        return new Image(size, imgChars);
    }

    private char[] cutLine(int spaces, int y) {
        char[] line = new char[spaces];
        char[][] originChars = origin.getChars();
        int offsetX = whereToCut.x;
        int offsetY = origin.size.y;
        while(spaces > origin.size.x)
            spaces--;

        for(int x = 0; x < spaces; x++) {
            int relativeX = x + offsetX;
            int relativeY = offsetY - y;
            if(isInsideFrame(relativeX, relativeY))
                line[x] = originChars[relativeY][relativeX];
        }
        return line;
    }
    
    public Image getImage() {
        return origin;
    }
}
