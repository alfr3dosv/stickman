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

//    @Test public void testIsRightClear() {
//        makeCollisionDetector();
//        collisionDetector.update();
//        player.movement.moveRight();
//        thenPlayerCantMoveRight();
//    }
    @Test public void a() {

    }

    public void thenPlayerMoves() {
        //assertEquals(expected, player.position);
    }

    public void thenPlayerCantMoveRight() {
        Point expected = new Point(0,0);
        assertEquals(expected, player.position);
    }

    public void makeCollisionDetector() {
        makePlayer();
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        Image world = (Image) Resources.lookup("test/player/world");
        collisionDetector = new CollisionDetector(world, entities);
    }

    public void makePlayer() {
        Image i = (Image) Resources.lookup("test/player/player");
        player = new Player(i);
        player.position = new Point(0,0);
    }

}
