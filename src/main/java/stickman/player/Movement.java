package stickman.player;
import stickman.enemy.*;
import stickman.entity.*;
import stickman.player.Player;
import stickman.input.Input;
import stickman.util.ImageUtil;

import java.util.List;

import static stickman.entity.Entity.Direction;

public class Movement implements Runnable
{
    final long WAIT_NEXT = 50;
    private Player player;
    private Image context;
    private Thread threadSlowdown;
    private Thread threadJump;
    private Thread threadFall;
    private enum JumpStatus {jump, doubleJump, waiting, complete}
    private JumpStatus jumpStatus = JumpStatus.complete;
    private int jump = 0;

    public Movement(Player newPlayer) {
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

    public void run() {
        threadSlowdown.start();
        threadJump.start();
        threadFall.start();
        while(true) {
            char key = Input.getKey();
            onKeyPress(key);
            sleep(10);
        }
    }

    public void onKeyPress(char key) {
        if (key == 'w') {
            up();
        } else if (key == 's') {
            down();
        } else if (key == 'a') {
            if(jumpStatus == JumpStatus.jump || jumpStatus != JumpStatus.doubleJump) {
                left(2);
                increaseSpeed(-1);
            }
            else {
                left(1);
                increaseSpeed(-1);
            }
        } else if (key == 'd') {
            if(jumpStatus == JumpStatus.jump || jumpStatus != JumpStatus.doubleJump) {
                right(2);
                increaseSpeed(1);
            }
            else {
                right(1);
                increaseSpeed(1);
            }
        }
        player.dir = Direction.NONE;
    }

    public void up() {
        if (jumpStatus == JumpStatus.complete) {
            jumpStatus = JumpStatus.jump;
        } else if (jumpStatus == JumpStatus.jump) {
            jumpStatus = JumpStatus.doubleJump;
        }
    }

    public void down() {
        player.dir = Direction.DOWN;
        if(isClear(Direction.DOWN))
            player.setY(player.getY() - 1);
    }

    public void left(int i) {
        for(int j = 0; j < i; j++) {
            if (isClear(Direction.LEFT)) {
                player.dir = Direction.LEFT;
                player.setX(player.getX() - 1);
            } else {
                player.dir = Direction.NONE;
                resetSpeedX();
                break;
            }
        }
    }

    public void right(int i) {
        for(int j = 0; j < i; j++) {
            if (isClear(Direction.RIGHT)) {
                player.dir = Direction.RIGHT;
                player.setX(player.getX() + 1);
            } else {
                player.dir = Direction.NONE;
                resetSpeedX();
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
        sleep(WAIT_NEXT);
    }

    public void jump() {
        long wait = WAIT_NEXT * 2;
        int movesPerjump = 4;
//        if(jumpStatus == JumpStatus.doubleJump)
//            movesPerjump += 2;

        switch (jumpStatus) {
            case jump:
            case doubleJump:
                if (jump < movesPerjump) {
                    if (isClear(Direction.UP)) {
                        player.dir = Direction.UP;
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
                while (isClear(Direction.DOWN)) {
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
            if(isClear(Direction.DOWN))
                player.setY(player.getY() -1 );
        }
        sleep(WAIT_NEXT * 2);
    }

    private void increaseSpeed(int increase) {
        final int MAX_SPEED = 4;
        if((player.speed.x > -MAX_SPEED) && (player.speed.x < MAX_SPEED))
            player.speed.x += increase;
        else
            resetSpeedX();
    }

    private void  resetSpeedX() {
        player.speed.x = 0;
        player.dir = Direction.NONE;
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleHit(Object who, Point where) {
        if(who instanceof Enemy) {
            System.out.println("Asterisck ");
        } else if(who instanceof Character) {
        }
    }

    public void setContex(Image context) {
        this.context = context;
    }

    private boolean isClear(Direction direction) {
        final int OFFSET = 1;
        Point origin = player.position;
        Size size = player.img.size;
        int x = origin.x + 1;
        int y = origin.y;

        if(direction == Direction.RIGHT)
            x = origin.x + size.x + OFFSET;
        else if (direction == Direction.LEFT)
            x = origin.x;
        else if (direction == Direction.DOWN)
            y = origin.y - OFFSET;
        else if(direction == Direction.UP)
            y = origin.y + size.y;

        if (direction == Direction.UP || direction == Direction.DOWN) {
            for (; x < origin.x + size.x; x++) {
                if (!isClear(x, y)) {
                    return false;
                }
            }
        }
        else if (direction == Direction.LEFT || direction == Direction.RIGHT) {
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
        ImageUtil.Element element = ImageUtil.from(context)
                .raw()
                .getElementAt(new Point(x,y));
        if(element != null)
            value = element.value();
        return value == ' ';
    }
}

