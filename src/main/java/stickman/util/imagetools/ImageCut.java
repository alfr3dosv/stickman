package stickman.util.imagetools;

import stickman.entity.*;
import stickman.util.ImageUtil;

public class ImageCut {
    private Image source;
    private Point where;
    private Size size;

    public ImageCut(Image i, Size s) {
        source = i;
        where = new Point(0,0);
        size = new Size(s);
        size.x = s.x < 0 ? 0 : s.x;
        size.y = s.y < 0 ? 0 : s.y;
    }

    public ImageCut where(int x, int y) {
        where(new Point(x,y));
        return this;
    }

    public ImageCut where(Point p) {
        where = new Point(p);
//        where.x = p.x < 0 ? 0 : p.x;
//        where.y = p.y < 0 ? 0 : p.y;
        return this;
    }

    public Image getImage() {
        Image dest = new Image(size);
        for(ImageUtil.Element e : ImageUtil.from(dest).raw().getElements()) {
            Point position = e.getPosition();
            Point positionToSource = new Point((position.x + where.x),
                    (position.y + where.y));
            ImageUtil.Element srcElement = ImageUtil.from(source)
                    .raw()
                    .getElementAt(positionToSource);
            char value;
            if(srcElement != null)
                value = srcElement.value();
            else
                value = ' ';
            e.set(value);
        }
        return dest;
    }
}