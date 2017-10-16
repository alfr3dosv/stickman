package stickman.util.imagetools;

import java.util.*;

import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.util.ImageUtil;

public class ImageOverlap {
    Image image;
    Image vs;

    public ImageOverlap(Image i, Image against) {
        image = new Image(i);
        vs = new Image(against);
    }

    public List<Point> getOverlaps () {
        List<Point> detections = new ArrayList<>();
        for(ImageUtil.Element e : ImageUtil.from(image).raw().getElements()) {
            ImageUtil.Element vsElement =
                    ImageUtil.from(vs).raw().getElementAt(e.getPosition());
            if(vsElement != null) {
                if(isOverlapping(e, vsElement))
                    detections.add(e.getPosition());
            }
        }
        return detections;
    }

    private boolean isOverlapping(ImageUtil.Element e, ImageUtil.Element e2) {
        char b1 = e.value();
        char b2 = e2.value();
        return !((b1 == ' ') || (b2 == ' '));
    }
}
