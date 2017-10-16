package stickman.core.collision;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.game.enemy.Asterisck;
import stickman.game.player.Player;
import stickman.core.loader.Loader;

public class CollisionDetectorTest {
    public static CollisionDetector collisionDetector;
    public static Asterisck asterisck;
    public static Player player;
    public static Result result;
    public static Result expected;
    public static Image world;

//    @Test public void testEntityDetection() {
//        makeCollisionDetector();
//        collisionDetector.update();
//        thenEnemyHitPlayer();
//    }

//    @Test public void testWorldDetection() {
//        makeCollisionDetector();
//        collisionDetector.update();
//        thenWallHitPlayer();
//    }

    public void thenEnemyHitPlayer() {
        expected.where.add(new Point(0,0));
        assertTrue(result.who instanceof Asterisck);
        assertEquals(expected.where.size(), result.where.size());
        for(Point p : result.where)
            assertTrue(expected.where.contains(p));
    }

    public void thenWallHitPlayer() {
        expected.where.add(new Point(2,0));
        assertTrue(result.who instanceof Character);
        for(Point p : expected.where)
            assertTrue(result.where.contains(p));
    }

    public void makeCollisionDetector() {
        makeEnemy();
        makePlayer();
        makeWorld();
        List<Entity> entities = new ArrayList<>();
        entities.add(asterisck);
        entities.add(player);
        collisionDetector = new CollisionDetector(world, entities);
    }

    public void makeEnemy() {
        asterisck = new Asterisck();
        asterisck.setPosition(new Point(0,0));
    }

    public void makePlayer() {
        Image i = (Image) Loader.lookup("test/collision/player");
        player = new Player(i) {
            @Override
            public void onHit(Object who, Point where) {
                result.who = who;
                result.where.add(where);
            }
        };
    }

    public void makeWorld() {
        world = (Image) Loader.lookup("test/collision/world");
    }

    @Before public void init() {
        result = new Result();
        expected = new Result();
    }

    public class Result {
        public Result() {
            who = new Object();
            where = new ArrayList<>();
        }
        Object who;
        List<Point> where;
    }
}
