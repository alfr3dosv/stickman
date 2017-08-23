package stickman.util;

import java.util.*;
import stickman.entity.*;
import stickman.util.imagetools.ImageCut;
import stickman.util.imagetools.ImageDraw;
import stickman.util.imagetools.ImageOverlap;
import stickman.util.imagetools.ImageRaw;

public class ImageUtil {

    private static interface ImageBuilder {
        public Image getImage();
    }

    public static class ImageOperator {
        Image image;

        public ImageOperator(Image i) {
            image = i;
        }

        public ImageCut cut(int width, int height) {
            return new ImageCut(image, new Size(width, height));
        }

        public ImageCut cut(Size size) {
            return new ImageCut(image, size);
        }

        public ImageDraw draw(Image toDraw) {
            return new ImageDraw(image, toDraw);
        }

        public ImageOverlap findOverlaps(Image against) {
            return new ImageOverlap(image, against);
        }
        public ImageRaw raw() {
            return new ImageRaw(image);
        }
    }

    public interface Element {
        public Point getPosition();
        public void set(char c);
        public char value();
    }

    public static ImageOperator from(Image i) {
        return new ImageOperator(i);
    }
}
