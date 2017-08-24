package stickman.player;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import stickman.player.Player;
import stickman.collision.CollisionDetector;
import stickman.resources.Resources;
import stickman.entity.*;

public class MovementTest {
    public Player player;
    public CollisionDetector collisionDetector;
    public Image world;
    public Thread thread;

    @Test public void testCantMoveRight() {
        makeWorld();
        makeCollisionDetector();
        collisionDetector.update();
        player.movement.right(1);
        thenPlayerCantMove();
    }

    @Test public void testMoveRight() {
        makeEmptyWorld();
        makeCollisionDetector();
        collisionDetector.update();
        player.movement.right(1);
        thenPlayerMoveRight();
    }

    @Test public void testCantMoveLeft() {
        makeWorld();
        makeCollisionDetector();
        collisionDetector.update();
        player.movement.left(1);
        thenPlayerCantMove();
    }

    @Test public void testMoveLeft() {
        makeEmptyWorld();
        makeCollisionDetector();
        collisionDetector.update();
        player.movement.left(1);
        thenPlayerMoveLeft();
    }

    @Test public void testCantMoveUp() {
        makeWorld();
        makeCollisionDetector();
        makeThread();
        collisionDetector.update();
        player.movement.up();
        sleep(200);
        thenPlayerCantMove();
    }


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
        assertEquals(expected, player.position);
    }

    public void thenPlayerMoveLeft() {
        Point expected = new Point(-1,1);
        assertEquals(expected, player.position);
    }

    public void thenPlayerMoveUp() {
        Point expected = new Point(0,4);
        assertEquals(expected, player.position);
    }

    public void thenPlayerCantMove() {
        Point expected = new Point(0,1);
        assertEquals(expected, player.position);
    }

    public void makeWorld() {
        world = (Image) Resources.lookup("test/player/world");
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
        Image i = (Image) Resources.lookup("test/player/player");
        player = new Player(i);
        player.setX(0);
        player.setY(1);
    }

    public void makeThread() {
        thread = new Thread(player.movement);
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
