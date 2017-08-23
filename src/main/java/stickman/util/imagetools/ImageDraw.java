package stickman.util.imagetools;

import stickman.entity.*;
import stickman.util.ImageUtil;

public class ImageDraw {
    private Image destination;
    private Image source;
    private Point where;

    public ImageDraw(Image dest, Image src) {
        destination = new Image(dest);
        where = new Point(0,0);
        source = new Image(src);
    }

    public ImageDraw where(Point where) {
        this.where = new Point(where);
        return this;
    }

    public ImageDraw where(int x, int y) {
        this.where = new Point(x, y);
        return this;
    }

    public Image getImage() {
        for(ImageUtil.Element sourceElement : ImageUtil.from(source).raw().getElements())
        {
            Point destinationPosition = sourceElement.getPosition();
            destinationPosition.x += where.x;
            destinationPosition.y += where.y;
            ImageUtil.Element destinationElement =
                    ImageUtil.from(destination)
                            .raw()
                            .getElementAt(destinationPosition);
            if(destinationElement != null) {
                if(sourceElement.value() != ' ')
                    destinationElement.set(sourceElement.value());
            }
        }
        return destination;
    }
}
