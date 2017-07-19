package stickman.display;

import java.util.Arrays;
import stickman.entity.Image;
import stickman.entity.Size;
import stickman.entity.Point;

public class Frame extends Image {
    private int spaces;
    private Image imageToDraw;
    private Point imageToDrawPosition;
    private Point position = new Point(0,0);

    public Frame(Size newSize, char[][] img) {
        super(newSize, img);
    }

    public void draw(Image source, Point sourcePosition) {
        imageToDraw = source.clone();
        imageToDrawPosition = sourcePosition;
        char[][] imageToDrawChars = imageToDraw.chars();
        int y = imageToDrawPosition.y + 1;
        for (int i = imageToDrawChars.length-1; i >= 0 ; i--) {
            drawLine(imageToDrawChars[i], y);
            y++;
        }
    }

    public void drawLine(char[] line, int y) {
        spaces = line.length;
        int offsetX = imageToDrawPosition.x;
        int offsetY = size.y;
        int relativeX = 0;
        int relativeY = 0;
        while(spaces > (Display.SIZE_X))
            spaces--;
        for(int x = 0; x < spaces; x++) {
            relativeX = x + offsetX;
            relativeY = offsetY - y;
            if(isInsideFrame(relativeX, relativeY))
                img[relativeY][relativeX] = line[x];
        }
    }

    public boolean isInsideFrame(int x, int y) {
        boolean isXValid = (x < (Display.SIZE_X)) && (x > 0);
        boolean isYValid = (y < Display.SIZE_Y) && (y >= 0);
        return isXValid && isYValid;
    }
}
