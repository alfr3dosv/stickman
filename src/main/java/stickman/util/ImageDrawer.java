package stickman.util;

import java.util.Arrays;
import stickman.entity.Image;
import stickman.entity.Size;
import stickman.entity.Point;

public class ImageDrawer {
    private static int spaces;
    private static Image source;
    private static Point whereToDraw;
    private static Image destination;
    private static Point position = new Point(0,0);

    public void setOutput(Image output) {
        destination = output;
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
        spaces = line.length;
        char[][] destinationChars = destination.getChars();
        int offsetX = whereToDraw.x;
        int offsetY = destination.size.y;
        int relativeX = 0;
        int relativeY = 0;
        while(spaces > destination.size.x)
            spaces--;
        for(int x = 0; x < spaces; x++) {
            relativeX = x + offsetX;
            relativeY = offsetY - y;
            if(isInsideFrame(relativeX, relativeY))
                destinationChars[relativeY][relativeX] = line[x];
        }
    }

    private boolean isInsideFrame(int x, int y) {
        boolean isXValid = (x < destination.size.x) && (x > 0);
        boolean isYValid = (y < destination.size.y) && (y >= 0);
        return isXValid && isYValid;
    }

    public Image getImage() {
        return destination;
    }
    // public void cut(Size size, Point where) {
    //     source = newSource.clone();
    //     whereToDraw = where;
    //     char[][] sourceChars = source.getChars();
    //     int y = whereToDraw.y + 1;
    //     for (int i = sourceChars.length-1; i >= 0 ; i--) {
    //         drawLine(sourceChars[i], y);
    //         y++;
    //     }
    // }
    //
    // private String cutLine(char[] line, int y) {
    //     spaces = line.length;
    //     char[][] destinationChars = destination.getChars();
    //     int offsetX = whereToDraw.x;
    //     int offsetY = destination.size.y;
    //     int relativeX = 0;
    //     int relativeY = 0;
    //     while(spaces > destination.size.x)
    //         spaces--;
    //     for(int x = 0; x < spaces; x++) {
    //         relativeX = x + offsetX;
    //         relativeY = offsetY - y;
    //         if(isInsideFrame(relativeX, relativeY))
    //             destinationChars[relativeY][relativeX] = line[x];
    //     }
    // }
}
