package stickman.collision;

import java.util.*;
import stickman.player.Player;
import stickman.entity.*;
import stickman.util.ImageDrawer;
import stickman.util.ImageOverlapping;
import stickman.util.ImageUtil;


public class CollisionDetector {
    private Image world;
    private List<Entity> entities;
    private List<Hittable> hittable;
    public static final char FLOOR = '-';
    public static final char WALL = '|';
    private static final char[] WORLD_CHARS = {FLOOR, WALL};

	public CollisionDetector(Image world, List<Entity> entities) {
	    this.entities = entities;
        hittable = new ArrayList<>();
        for(Entity e : entities)
            if(e instanceof Hittable)
                hittable.add((Hittable) e);
        setContext(world);
    }

    public void setContext(Image world) {
        this.world = world;
        for(Entity e : entities)
            if(e instanceof Aware)
                ((Aware) e).setContext(world);
    }


    public void update() {
	    for(Hittable h : hittable) {
            findCollisions(h);
        }
    }

    private void findCollisions(Hittable h) {
        // entities
	    for (Entity against : entities) {
            List<Point> where = new ArrayList<>();
            if(h != against) {
                Image src = fullSizeImage(h);
                Image intersection = ImageUtil.from(src)
                        .cut(against.img.size)
                        .where(against.position)
                        .getImage();
                where = ImageUtil.from(intersection)
                        .findOverlaps(against.img)
                        .getOverlaps();
                if(!where.isEmpty()) {
                    for(Point p : where)
                        h.onHit((Object) against, p);
                }
            }
        }
//        if(h instanceof Aware) {
//            List<Point> where = findAroundContext(h);
//            if (!where.isEmpty()) {
//                ((Aware) h).around(where);
//            }
//        }
        // world
//        List<Point> where = new ArrayList<>();
//        Entity e = (Entity) h;
//        Point p = new Point(e.position.x -1, e.position.y -1);
//        Image withBorders = addBorders(h);
//        Image intersection = ImageUtil.from(world)
//                .cut(e.img.size.x + 2, e.img.size.y + 2)
//                .where(p)
//                .getImage();
//        where = ImageUtil.from(intersection)
//                .findOverlaps(withBorders)
//                .getOverlaps();
//        if(!where.isEmpty()) {
//            char who = ImageUtil.from(intersection).raw().getElementAt(p).value();
//            ((Aware) h).around(where);
//            //h.o((Object) who, p);
//        }
    }

    public Image addBorders (Hittable h) {
        Entity e = (Entity) h;
        int width = e.img.size.x + 2;
        int height = e.img.size.y + 2;
        Point position = e.position;
        Image i = new Image(new Size(width, height));
        i = ImageUtil.from(i)
                .draw(e.img)
                .where(1,1)
                .getImage();
        return i;
    }

    public List<Point> findAroundContext(Hittable h) {
        List<Point> where = new ArrayList<>();
        Entity e = (Entity) h;
        Point p = new Point(e.position.x -1, e.position.y -1);
        Image intersection = ImageUtil.from(world)
                .cut(e.img.size.x + 2, e.img.size.y + 2)
                .where(p)
                .getImage();
        for(ImageUtil.Element element : ImageUtil.from(intersection).raw().getElements())
        {
            if(element.value() != ' ') {
                Point point = new Point(element.getPosition());
                point.x += e.position.x;
                point.y += e.position.y;
                where.add(point);
            }
        }
        return where;
    }

    private Image fullSizeImage (Hittable h) {
	    Entity e = (Entity) h;
        int width = e.position.x + e.img.size.x;
        int height = e.position.y + e.img.size.y;

        Image i =  new Image(new Size(width,height));
        i = ImageUtil.from(i)
                .draw(e.img)
                .where(e.position)
                .getImage();
        return i;
    }

    public interface Hittable {
	    public void onHit(Object who, Point where);
    }

    public interface Aware {
	    public void setContext(Image context);
    }
}