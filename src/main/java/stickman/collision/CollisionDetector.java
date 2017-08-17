package stickman.collision;

import java.util.*;
import stickman.player.Player;
import stickman.entity.*;
import stickman.util.ImageDrawer;
import stickman.util.ImageOverlapping;


public class CollisionDetector {
    private Image base;
    private char[][] frame;
    char[][] drawArea;
    char[] bottom;
    char charToTest;
    List<Entity> entities;
    List<Hittable> hittables;
    private final char FLOOR = '-';

	public CollisionDetector(List<Entity> entities) {
        this.entities = entities;
        hittables = new ArrayList<>();
        for(Entity e : entities)
            if(e instanceof Hittable)
                hittables.add((Hittable) e);
        setCharToTest('*');
    }

    public void setCharToTest(char charToTest) {
        this.charToTest = charToTest;
    }

    public void setContext(Image context) {
	    base = context;
    }


    public void update() {
	    for(Hittable h : hittables) {
            findCollisions(h);
        }
    }

    private boolean findCollisions(Hittable h) {
        for (Entity e : entities)
            if (ImageOverlapping.isOverlapping((Entity) h, e)) {
                h.onHit((Object) e);
                return true;
            }
        return false;
    }

    public interface Hittable {
	    public void onHit(Object who);
    }
}
