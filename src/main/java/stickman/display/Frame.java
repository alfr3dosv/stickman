package stickman.display;

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
        int y = imageToDrawPosition.y;
        for (int i = imageToDrawChars.length-1; i >= 0 ; i--) {
            drawLine(imageToDrawChars[i], y);
            y++;
        }
    }

    public void drawLine(char[] line, int y) {
        spaces = line.length;
        int offset = imageToDrawPosition.x;
        while(spaces > (Display.SIZE_X))
            spaces--;
        if(isInsideFrame())
            for(int x = 0; x < spaces; x++)
                img[size.y-y-1][x + offset] = line[x];
    }

    public boolean isInsideFrame() {
        boolean isXValid = (imageToDrawPosition.x < (Display.SIZE_X)) &&
                           (imageToDrawPosition.x > 0);
        boolean isYValid = (imageToDrawPosition.y < Display.SIZE_Y) &&
                           (imageToDrawPosition.y > 0);
        return isXValid && isYValid;
    }
}
