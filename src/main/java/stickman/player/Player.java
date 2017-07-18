package stickman.player;

import java.util.*;
import java.io.*;
import stickman.entity.Entity;
import stickman.RawConsoleInput;
import stickman.input.Input;
import stickman.entity.*;

public class Player extends Entity implements Runnable
{
	public volatile State status = State.STATIC;
	private long jump_start=0; //contadoe entre espacios
	private final int JUMP_WAIT=100;
	private int jump_steps=2; //cuantos caracteres salta
	private int jump_step=0;
	private long fall_start=0;
    final int FALL_WAIT=50;
	private int fall_step=0;
	boolean CAPTURE_INPUT = true;
	private int speed_x;
	private boolean STAGE_KEY = false;
	public enum State {JUMPING, FALLING, WALKING, STATIC, PAUSED}

	public Player()	{
		char[] new_img_0 ={' ','o',' '};
		char[] new_img_1 = {'/','|','\\'};
		char[] new_img_2 = {'/',' ','\\'};
		char[][] new_img = new char [3][3];
		new_img[0] = new_img_0;
		new_img[1] = new_img_1;
		new_img[2] = new_img_2;
		this.img = new Image(3,3,new_img);
	}

	public void run() {
		while(!Input.stop) {
			captureInput();
			move();
		}
	}

	public void interrupt() {
		this.CAPTURE_INPUT = false;
	}

	public void init() {
		this.setX(0);
		this.setY(0);
		this.setAlive();
		this.STAGE_KEY = false;
	}

	private void move() {
		if(status == State.JUMPING) {
			jump();
		}
		switch (dir) {
			case DOWN:
				this.setY( (this.getY()+1));
			break;

			case LEFT:
				this.setX(this.getX()-1);
				if(speed_x>0) {
					speed_x = 0;
				}
				else if(speed_x>-3) {
					speed_x--;
				}
			break;

			case RIGHT:
				this.setX( (this.getX()+1) );
				if(speed_x<0) {
					speed_x = 0;
				}
				else if(speed_x<3) {
					speed_x++;
				}

			break;
		}
		if(status == State.STATIC) {
			speed_x = 0;
		}
	}

    public void captureInput() {
        char keyCode = Input.getKey();
        dir = Entity.Direction.NONE;
        switch( keyCode ) {
            case 'w':
                dir = Entity.Direction.UP;
                status = State.JUMPING;
                break;
            case 's':
                dir = Entity.Direction.DOWN;
                status = State.FALLING;
                break;
            case 'a':
                dir = Entity.Direction.LEFT;
                status = State.WALKING;
                break;
            case 'd':
                dir = Entity.Direction.RIGHT;
                status = State.WALKING;
                break;
            case ':':
                dir = Entity.Direction.NONE;
                status = State.PAUSED;
                break;
            default:
                dir = Entity.Direction.NONE;
        }
    }

	public void jump() {
	    if(jump_start <= 0) {
			jump_start = System.currentTimeMillis();
		}
		else if( (System.currentTimeMillis() - jump_start) >
				 (50+(JUMP_WAIT*jump_step)) )
		{
			if((jump_step < jump_steps)) {
				this.setY(this.getY()-1);
			}
			else{
				jump_steps=2;
				jump_step=-1;
				jump_start = 0;
				status = State.FALLING;
				//fall(1);
			}
			if(speed_x != 0) {
				if(speed_x>0) {
					this.setX(this.getX()+2+jump_step);
					speed_x--;
				}
				else if(speed_x<0) {
					this.setX(this.getX()-2-jump_step);
					speed_x++;
				}
			}
			jump_step++;
		}
		if(jump_steps == 2) {
		}
	}

	public void fall(int spaces) {
		while( (spaces > 0) &&
			   (status == State.FALLING) )
		{
		    if(fall_start == 0) {
				fall_start = System.currentTimeMillis();
			}
			else if( (System.currentTimeMillis() - fall_start) > FALL_WAIT ) {
				this.setY(this.getY()+1);
				if(fall_step<=2) {
					fall_step++;
				}
				if(speed_x != 0) {
					if(speed_x>0) {
						this.setX(this.getX()+1);
					}
					else if(speed_x<0) {
						this.setX(this.getX()-1);
					}
				}
				spaces--;
			}
		}
		status = State.STATIC;
		fall_start = 0;
		fall_step = 0;
	}

	public boolean hasKey() {
		return this.STAGE_KEY;
	}

	public void setKey(boolean hasKey) {
		this.STAGE_KEY = hasKey;
	}
}
