package stickman.entity;

public class Template extends Entity {
    public Template(Image image) {
        this.img = image;
        this.position = new Point(0,0);
    }

    public Template(Image image, Point position) {
        this.img = image;
        this.position = position;
    }
}
