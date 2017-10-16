package stickman.util.imagetools;

import java.util.*;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.util.ImageUtil;

import java.util.List;

public class ImageRaw {
    private Image image;

    public ImageRaw(Image i) {
        image = i;
    }

    public ImageUtil.Element getElementAt(Point position) {
        if (position.y >= 0 && position.y < image.size.y)
            if (position.x >= 0 && position.x < image.size.x)
                return new RawElement(image, position);
        return null;
    }

    public List<ImageUtil.Element> getElements() {
        List<ImageUtil.Element> elements = new ArrayList<>();
        for(int y = 0; y < image.size.y; y++) {
            for(int x = 0; x < image.size.x; x++) {
                elements.add(new RawElement(image, x, y));
            }
        }
        return elements;
    }

    class RawElement implements ImageUtil.Element {
        Image image;
        Point position;

        public RawElement(Image i, int x, int y) {
            image = i;
            position = new Point(x, y);
        }

        public RawElement(Image i, Point position) {
            image = i;
            this.position = position;
        }

        public Point getPosition() {
            return new Point(position.x, position.y);
        }

        public void set(char value) {
            image.img[invertY(position.y)][position.x] = value;
        }

        public char value() {
            return image.img[invertY(position.y)][position.x];
        }

        public int invertY(int y) {
            return image.size.y - y - 1;
        }
    }
}