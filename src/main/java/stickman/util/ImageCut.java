package stickman.util;

import stickman.entity.Entity;
import stickman.entity.Image;
import stickman.entity.Point;
import stickman.entity.Size;

public class ImageCut {
    private Entity base;
    private Entity source;
    private Image result;

    public ImageCut cut(Entity template, Entity source) {
        this.base = template;
        this.source = source;
        cutImage();
        return this;
    }

    private void cutImage() {
        char[][] imgChars = new char[base.img.size.y][base.img.size.x];
        char[][] sourceChars = source.img.getChars();
        for(int y = base.position.y + base.img.size.y; y > base.position.y; y--)
            for(int x = base.position.x; x <= base.position.x + base.img.size.x; x++)
                if(isInsideBase(x,y) && isInsideSource(x,y)) {
                    Point indexOfBase = indexForBase(x,y);
                    Point indexOfSource = indexForSource(x,y);
                    imgChars[indexOfBase.y][indexOfBase.x] = sourceChars[indexOfSource.y][indexOfSource.x];
                }
        imgChars = invertY(imgChars);
        result = new Image(base.img.size, imgChars);
    }

    private boolean isInsideBase(int x, int y) {
        boolean isXValid = (x < (base.position.x + base.img.size.x)) && (x >= base.position.x);
        boolean isYValid = (y < (base.position.y + base.img.size.y)) && (y >= base.position.y);
        return isXValid && isYValid;
    }

    private boolean isInsideSource(int x, int y) {
        boolean isXValid = (x < (source.position.x + source.img.size.x)) && (x >= source.position.x);
        boolean isYValid = (y < (source.position.y + source.img.size.y)) && (y >= source.position.y);
        return isXValid && isYValid;
    }

    private Point indexForSource(int x, int y) {
        x = x - source.position.x;
        y = y - source.position.y;
        return new Point(x, y);
    }

    private Point indexForBase(int x, int y) {
        x = x - base.position.x;
        y = y - base.position.y;
        return new Point(x, y);
    }

    private char[][] invertY(char[][] matrix) {
        char[][] matrixInverted = new char[base.img.size.y][base.img.size.x];
        char[] temp;
        int y = base.img.size.y - 1;
        for(char[] line : matrix) {
            matrixInverted[y--] = line;
        }
        return matrixInverted;
    }

    public Image getImage() {
        return result;
    }
}
