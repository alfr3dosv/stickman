package stickman.player;
import stickman.entity.Entity;
import stickman.player.Player;
import stickman.input.Input;

public class Movement implements Runnable
{
    private Speed speed;
    public Player player;

    private class Speed
    {
        int x = 0;
        int y = 0;
    }

    public Movement(Player newPlayer) {
        player = newPlayer;
        speed = new Speed();
    }

    public void run() {
        while(true) {
            char key = Input.getKey();
             onKeyPress(key);
        }
    }

    public void onKeyPress(char key) {
        if (key == 'w') {
            player.dir = Entity.Direction.UP;
        } else if (key == 's') {
            player.dir = Entity.Direction.DOWN;
        } else if (key == 'a') {
            player.dir = Entity.Direction.LEFT;
        } else if (key == 'd') {
            player.dir = Entity.Direction.RIGHT;
        }
        move();
        resetDirection();
    }

    private void move() {
        switch (player.dir) {
            case UP:
                jump();
                break;

            case DOWN:
                player.setY(player.getY() - 1);
                break;

            case LEFT:
                player.setX(player.getX() - 1);
                increaseSpeed(-1);
                break;

            case RIGHT:
                player.setX(player.getX() + 1);
                increaseSpeed(1);
                break;
        }
    }

    private void jump() {
        final long WAIT_MILLIS = 100;
        sleep(WAIT_MILLIS);
        player.setY(player.getY() + 1);

        if(speed.x <= 1 && speed.x >= -1) {
            player.setX(player.getX() + speed.x);
            resetSpeedX();
        }
    }

    private void increaseSpeed(int increase) {
        if((increase > 0 && speed.x < 0) || (increase < 0 && speed.x > 0))
            resetSpeedX();
        else if((speed.x > -3) && (speed.x < 3))
            speed.x += increase;

    }

    private void  resetSpeedX() {
        speed.x = 0;
    }

    private void resetDirection() {
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

