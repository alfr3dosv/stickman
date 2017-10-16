package stickman.game.player;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import stickman.core.collision.CollisionDetector;
import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.core.entity.Size;
import stickman.core.loader.Loader;

public class MovementTest {
    Player player;
    CollisionDetector collisionDetector;
    Image world;
    Thread thread;
//
//    @Test public void testCantMoveRight() {
//        makeWorld();
//        makeCollisionDetector();
//        collisionDetector.update();
//        player.move.right(1);
//        thenPlayerCantMove();
//    }
//
//    @Test public void testMoveRight() {
//        makeEmptyWorld();
//        makeCollisionDetector();
//        collisionDetector.update();
//        player.move.right(1);
//        thenPlayerMoveRight();
//    }
//
//    @Test public void testCantMoveLeft() {
//        makeWorld();
//        makeCollisionDetector();
//        collisionDetector.update();
//        player.move.left(1);
//        thenPlayerCantMove();
//    }
//
//    @Test public void testMoveLeft() {
//        makeEmptyWorld();
//        makeCollisionDetector();
//        collisionDetector.update();
//        player.move.left(1);
//        thenPlayerMoveLeft();
//    }

//    @Test public void testCantMoveUp() {
//        makeWorld();
//        makeCollisionDetector();
//        makeThread();
//        collisionDetector.update();
//        player.movement.up();
//        sleep(200);
//        thenPlayerCantMove();
//    }


//    @Test public void testMoveUp() {
//        makeEmptyWorld();
//        makeCollisionDetector();
//        makeThread();
//        collisionDetector.update();
//        player.movement.up();
//        sleep(200);
//        thenPlayerMoveUp();
//    }

    public void thenPlayerMoveRight() {
        Point expected = new Point(1,1);
        assertEquals(expected, player.getPosition());
    }

    public void thenPlayerMoveLeft() {
        Point expected = new Point(-1,1);
        assertEquals(expected, player.getPosition());
    }

    public void thenPlayerMoveUp() {
        Point expected = new Point(0,4);
        assertEquals(expected, player.getPosition());
    }

    public void thenPlayerCantMove() {
        Point expected = new Point(0,1);
        assertEquals(expected, player.getPosition());
    }

    public void makeWorld() {
        world = (Image) Loader.lookup("test/player/world");
    }

    public void makeEmptyWorld() {
        world = new Image(new Size(20,20));
    }

    public void makeCollisionDetector() {
        makePlayer();
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        collisionDetector = new CollisionDetector(world, entities);
    }

    public void makePlayer() {
        Image i = (Image) Loader.lookup("test/player/player");
        player = new Player(i);
        player.setX(0);
        player.setY(1);
    }

    public void makeThread() {
        thread = new Thread(player);
        thread.start();
    }

    public void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
