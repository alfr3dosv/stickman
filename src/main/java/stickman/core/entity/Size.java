package stickman.core.entity;

public class Size {
    public int x;
    public int y;

    public Size(Size s) {
        this(s.x, s.y);
    }
    public Size(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public static boolean isValid(Size s) {
        return (s.x > 0) && (s.y > 0);
    }
}
