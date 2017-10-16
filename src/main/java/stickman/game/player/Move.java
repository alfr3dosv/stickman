package stickman.game.player;

import stickman.core.collision.CollisionDetector;
import stickman.core.entity.*;

import stickman.game.enemy.Asterisck;
import static stickman.core.entity.Entity.Direction;

public class Move
{
    final long WAIT_NEXT = 50;
    private Player player;
    private CollisionDetector.Context context;
    private Thread threadSlowdown;
    private Thread threadJump;
    private Thread threadFall;
    public static enum JumpStatus {jump, doubleJump, waiting, complete}
    public JumpStatus jumpStatus = JumpStatus.complete;
    private int jump = 0;

    public Move(Player newPlayer) {
        player = newPlayer;
        threadSlowdown = new Thread(() -> {
            while(true) slowdown();
        });
        threadJump = new Thread(() -> {
            while(true) jump();
        });
        threadFall = new Thread(() -> {
            while (true) fall();
        });
    }

    public void init() {
        threadSlowdown.start();
        threadJump.start();
        threadFall.start();
    }

    public void up() {
        if (jumpStatus == JumpStatus.complete) {
            jumpStatus = JumpStatus.jump;
        } else if (jumpStatus == JumpStatus.jump) {
            jumpStatus = JumpStatus.doubleJump;
        }
    }

    public void down() {
        player.setDirection(Direction.DOWN);
        if(context.isClear(Direction.DOWN))
            player.setY(player.getY() - 1);
    }

    public void left(int i) {
        for(int j = 0; j < i; j++) {
            if (context.isClear(Direction.LEFT)) {
                player.setDirection(Direction.LEFT);
                player.setX(player.getX() - 1);
            } else {
                player.setDirection(Direction.NONE);
                player.resetSpeedX();
                break;
            }
        }
    }

    public void right(int i) {
        for(int j = 0; j < i; j++) {
            if (context.isClear(Direction.RIGHT)) {
                player.setDirection(Direction.RIGHT);
                player.setX(player.getX() + 1);
            } else {
                player.setDirection(Direction.NONE);
                player.resetSpeedX();
                break;
            }
        }
    }

    private void slowdown () {
        if (player.speed.x > 0) {
            right(player.speed.x--);
        } else if (player.speed.x < 0) {
            left(-player.speed.x++);
        }
        sleep(100);
    }

    public void jump() {
        long wait = WAIT_NEXT;
        int movesPerjump = 4;
//        if(jumpStatus == JumpStatus.doubleJump)
//            movesPerjump += 2;

        switch (jumpStatus) {
            case jump:
            case doubleJump:
                if (jump < movesPerjump) {
                    if (context.isClear(Direction.UP)) {
                        player.setDirection(Direction.UP);
                        player.setY(player.getY() + 1);
                        jump++;
                        wait = wait * jump;
                    } else {
                        resetJump();
                    }
                } else {
                    resetJump();
                }
                break;
            case waiting:
                while (context.isClear(Direction.DOWN)) {
                    sleep(WAIT_NEXT);
                }
                jumpStatus = JumpStatus.complete;
                break;
        }
        sleep(wait);
    }

    private void resetJump() {
        jump = 0;
        jumpStatus = JumpStatus.waiting;
    }

    public void fall() {
        if(jumpStatus != JumpStatus.jump && jumpStatus != JumpStatus.doubleJump)
        {
            if(context != null && context.isClear(Direction.DOWN))
                player.setY(player.getY() -1 );
        }
        sleep(WAIT_NEXT * 2);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setContex(CollisionDetector.Context context) {
        this.context = context;
    }
}

