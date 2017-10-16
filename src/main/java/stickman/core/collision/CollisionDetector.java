package stickman.core.collision;

import java.util.*;

import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.core.entity.Size;
import stickman.game.player.Player;
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
                ((Aware) e).setContext(new Context(e, world));
    }


    public void update() {
	    for(Hittable h : hittable) {
            findCollisions(h);
        }
    }

    private void findCollisions(Hittable h) {
	    for (Entity against : entities) {
            List<Point> where = new ArrayList<>();
            if(h != against) {
                Image src = fullSizeImage(h);
                Image intersection = ImageUtil.from(src)
                        .cut(against.getImage().size)
                        .where(against.getPosition())
                        .getImage();
                where = ImageUtil.from(intersection)
                        .findOverlaps(against.getImage())
                        .getOverlaps();
                if(!where.isEmpty()) {
                    for(Point p : where)
                        h.onHit((Object) against, p);
                }
            }
        }
    }

    public List<Point> findAroundContext(Hittable h) {
        List<Point> where = new ArrayList<>();
        Entity e = (Entity) h;
        Point p = new Point(e.getPosition().x -1, e.getPosition().y -1);
        Image intersection = ImageUtil.from(world)
                .cut(e.getImage().size.x + 2, e.getImage().size.y + 2)
                .where(p)
                .getImage();
        for(ImageUtil.Element element : ImageUtil.from(intersection).raw().getElements())
        {
            if(element.value() != ' ') {
                Point point = new Point(element.getPosition());
                point.x += e.getPosition().x;
                point.y += e.getPosition().y;
                where.add(point);
            }
        }
        return where;
    }

    private Image fullSizeImage (Hittable h) {
	    Entity e = (Entity) h;
	    Image i;
        int width = e.getPosition().x + e.getImage().size.x;
        int height = e.getPosition().y + e.getImage().size.y;
        i = new Image(new Size(width, height));
        i = ImageUtil.from(i)
                .draw(e.getImage())
                .where(e.getPosition())
                .getImage();
        return i;
    }

    public class Context {
        Entity entity;
        Image world;
        Entity.Direction direction;

        public Context(Entity e, Image i) {
            entity = e;
            world = i;
        }

        public boolean isClear(Entity.Direction direction) {
            final int OFFSET = 1;
            this.direction = direction;
            Point origin = entity.getPosition();
            Size size = entity.getImage().size;
            int x = origin.x;
            int y = origin.y;

            if (direction == Entity.Direction.RIGHT)
                x = origin.x + size.x;
            else if (direction == Entity.Direction.LEFT)
                x = origin.x - OFFSET;
            else if (direction == Entity.Direction.DOWN)
                y = origin.y - OFFSET;
            else if (direction == Entity.Direction.UP)
                y = origin.y + size.y;

            if (direction == Entity.Direction.UP || direction == Entity.Direction.DOWN) {
                for (; x < origin.x + size.x; x++) {
                    if (!isClear(x, y)) {
                        return false;
                    }
                }
            } else if (direction == Entity.Direction.LEFT || direction == Entity.Direction.RIGHT) {
                for (; y < origin.y + size.y; y++) {
                    if (!isClear(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isClear(int x, int y) {
            char value = ' ';
            ImageUtil.Element element = ImageUtil.from(world)
                    .raw()
                    .getElementAt(new Point(x, y));
            if (element != null)
                value = element.value();
            return value == ' ';
        }
    }

    public interface Hittable {
	    public void onHit(Object who, Point where);
    }

    public interface Aware {
	    public void setContext(Context context);
    }
}