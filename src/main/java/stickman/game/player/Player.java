package stickman.game.player;

import stickman.core.collision.CollisionDetector;
import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.core.keyboard.Keyboard;
import stickman.game.enemy.Asterisck;
import stickman.game.key.Key;

import static stickman.game.player.Move.JumpStatus;

public class Player extends Entity implements CollisionDetector.Hittable, CollisionDetector.Aware, Runnable
{
    private boolean key = false;
    public Move move;
    private CollisionDetector cd;

    public Player(Image imageOfPlayer) {
        super();
        setImage(imageOfPlayer);
        move = new Move(this);
        key = false;
    }

    public void run() {
        move.init();
        while(true) {
            char key = Keyboard.getKey();
            onKeyPress(key);
            sleep(50);
        }
    }

    public void reset() {
        setX(1);
        setY(1);
        key = false;
    }

    public void onKeyPress(char key) {
        if (key == 'w') {
            move.up();
        } else if (key == 's') {
            move.down();
        } else if (key == 'a') {
            if(move.jumpStatus == JumpStatus.jump || move.jumpStatus != JumpStatus.doubleJump) {
                move.left(1);
                increaseSpeed(-1);
            } else {
                move.left(2);
                //increaseSpeed(-1);
            }
        } else if (key == 'd') {
            if(move.jumpStatus == JumpStatus.jump || move.jumpStatus != JumpStatus.doubleJump) {
                move.right(2);
                increaseSpeed(1);
            } else {
                move.right(1);
                //increaseSpeed(1);
            }
        }
        setDirection(Direction.NONE);
    }

    private void increaseSpeed(int increase) {
        final int MAX_SPEED = 4;
        if ((speed.x > -MAX_SPEED) && (speed.x < MAX_SPEED))
            speed.x += increase;
        else
            resetSpeedX();
    }

    public void resetSpeedX() {
        speed.x = 0;
        setDirection(Direction.NONE);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean hasKey() {
        return key;
    }

    public void setKey(boolean hasKey) {
        key = hasKey;
    }

    @Override
    public void onHit(Object who, Point where) {
        if(who instanceof Key) {
            key = true;
        } else if (who instanceof Asterisck) {
            kill();
        }
    }

    @Override
    public void setContext(CollisionDetector.Context cx) {
        move.setContex(cx);
    }
}
