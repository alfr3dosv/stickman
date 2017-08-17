package stickman.player;
import stickman.entity.*;
import stickman.player.Player;
import stickman.input.Input;

public class Movement implements Runnable
{
    public Player player;
    private int jumps;
    private long jumpBegin;
    private long slowDownBegin;

    public Movement(Player newPlayer) {
        player = newPlayer;

        jumps = 0;
        slowDownBegin = System.currentTimeMillis();
    }

    public void run() {
        while(true) {
            char key = Input.getKey();
             onKeyPress(key);
            jump();
            slowdown();
            //sleep(0);
        }
    }

    public void onKeyPress(char key) {
        if (key == 'w')
            moveUp();
        else if (key == 's')
            moveDown();
        else if (key == 'a')
            moveLeft();
        else if (key == 'd')
            moveRight();
        resetDirection();
    }

    public void moveUp() {
        player.dir = Entity.Direction.UP;
        if(jumps == 0)
            jumps++;
        jump();
        sleep(5);
    }

    private void moveDown() {
        player.dir = Entity.Direction.DOWN;
        player.setY(player.getY() - 1);
    }

    private void moveLeft() {
        player.dir = Entity.Direction.LEFT;
        player.setX(player.getX() - 1);
        increaseSpeed(-1);
    }

    private  void moveRight() {
        player.dir = Entity.Direction.RIGHT;
        player.setX(player.getX() + 1);
        increaseSpeed(1);
    }

    private void slowdown() {
        final long WAIT_MILLIS = 100;
        long elapsedTime = System.currentTimeMillis() - slowDownBegin;

        if(elapsedTime > WAIT_MILLIS) {
            if (player.speed.x > 0)
                player.setX(player.getX() + (player.speed.x--));
            else if (player.speed.x < 0)
                player.setX(player.getX() + (player.speed.x++));
            slowDownBegin = System.currentTimeMillis();
        }
    }

    private void jump() {
        final long WAIT_RESET = 500;
        final long WAIT_NEXT = 50;
        long elapsedTime = System.currentTimeMillis() - jumpBegin;

        if((jumps > 0 && jumps <= 3) && elapsedTime > WAIT_NEXT) {
            player.setY(player.getY() + 1);
            jumps++;
            jumpBegin = System.currentTimeMillis();
        }
        else if((jumps > 3 && jumps <= 5) && elapsedTime > WAIT_NEXT) {
            jumps++;
            jumpBegin = System.currentTimeMillis();
        }
        else if((jumps > 5 && jumps <= 8) && elapsedTime > WAIT_NEXT) {
            player.setY(player.getY() - 1);
            jumps++;
            jumpBegin = System.currentTimeMillis();
        }
        else if((jumps >= 9) && elapsedTime > WAIT_RESET) {
            jumps = 0;
        }

    }

    private void increaseSpeed(int increase) {
        if((increase > 0 && player.speed.x < 0) || (increase < 0 && player.speed.x > 0))
            resetSpeedX();
        else if((player.speed.x > -4) && (player.speed.x < 4))
            player.speed.x += increase;

    }

    private void  resetSpeedX() {
        player.speed.x = 0;
    }

    private void  resetDirection() {
        player.dir = Entity.Direction.NONE;
    }

    
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

