package stickman.core.entity;

public class Template extends Entity {
    public Template(Image image) {
        this.setImage(image);
        this.setPosition(new Point(0,0));
    }

    public Template(Image image, Point position) {
        this.setImage(image);
        this.setPosition(position);
    }
}
