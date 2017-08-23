package stickman.player;
import stickman.enemy.*;
import stickman.entity.*;
import stickman.player.Player;
import stickman.input.Input;

import java.util.List;

import static stickman.entity.Entity.Direction;

public class Movement implements Runnable
{
    public Player player;
    private int jumps;
    private long jumpBegin;
    private long slowDownBegin;
    public String debugText = "";
    private List<Point> outside;

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
            sleep(20);
        }
    }

    public void onKeyPress(char key) {
        if (key == 'w') {
            moveUp();
        } else if (key == 's') {
            moveDown();
        } else if (key == 'a') {
            left();
            increaseSpeed(-1);
        } else if (key == 'd') { 
            right(1);
            increaseSpeed(1);
        } resetDirection();
    }

    public void moveUp() {
        player.dir = Direction.UP;
        if(jumps == 0)
            jumps++;
        jump();
        sleep(5);
    }

    private void moveDown() {
        player.dir = Direction.DOWN;
        player.setY(player.getY() - 1);
    }

    private void left(int i) {
        for(int j = 0; j < i; j++) {
            if (isRightClear()) {
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
            if (isRightClear()) {
                player.dir = Direction.RIGHT;
                player.setX(player.getX() + 1);
            } else {
                player.dir = Direction.NONE;
                resetSpeedX();
                break;
            }
        }
    }

    private void slowdown() {
        final long WAIT_MILLIS = 100;
        long elapsedTime = System.currentTimeMillis() - slowDownBegin;

        if(elapsedTime > WAIT_MILLIS) {
            if (player.speed.x > 0)
                //if(isClear(player.position.x + 1, player.position.y))
                if(isRightClear())
                    player.setX(player.getX() + (player.speed.x--));
            else if (player.speed.x < 0)
                if(isClear(player.position.x + 1, player.position.y))
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
        final int MAX_SPEED = 2;
        if((increase > 0 && player.speed.x < 0) || (increase < 0 && player.speed.x > 0))
            resetSpeedX();
        else if((player.speed.x > -MAX_SPEED) && (player.speed.x < MAX_SPEED))
            player.speed.x += increase;

    }

    private void  resetSpeedX() {
        player.speed.x = 0;
    }

    private void  resetDirection() {
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
        final int LEFT = 0;
        final int RIGHT = 2;
        final int TOP = 2;
        final int BOTTOM = 0;
        if(who instanceof Enemy) {
            System.out.println("Asterisck ");
        } else if(who instanceof Character) {
            char c = (Character) who;
            if(where.x >= RIGHT) {
                moveLeft();
                debugText = "LOCKED";
            }
        }
    }

    public void setOutside(List<Point> outside) {
        this.outside = outside;
    }

    private boolean isClear(int x, int y) {
        int width = x + player.img.size.x;
        int height = y + player.img.size.y;
        for(Point p : outside) {
            boolean isXInside = p.x >= x && p.x <= width;
            boolean isYInside = p.y >= y && p.y <= height;
            if(isXInside && isYInside)
                return false;
        }
        return true;
    }

    private boolean isRightClear() {
        int width = player.position.x + player.img.size.x;
        for(Point p : outside) {
//            boolean isXClear = p.x != (width + 1);
//            boolean isYClear = (player.position.y - 1 != p.y;
//            if(isXInside && isYInside)
//                return false;
            if(p.x == width + 2)
                return false;
        }
        return true;
    }
}

