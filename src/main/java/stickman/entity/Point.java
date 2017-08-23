package stickman.entity;

import stickman.display.Display;

public class Point {
    public int x;
    public int y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public static boolean isValid(Point p) {
        return p.x >= 0 && p.y >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o instanceof Point) {
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }
        return false;
    }
}
