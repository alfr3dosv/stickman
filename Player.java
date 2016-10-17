import java.util.*;
import java.awt.event.KeyEvent;

public class Player
{
	public int y;
	public int x;
	boolean ALIVE;

	public Player()
	{
		ALIVE = true;
		x = 0; 
		y = 0;
	}
	private void move(DIRECTION dir)
	{
		switch (dir)
		{
			case UP: 
			if(y < Display.SIZE_Y)
				y++;
			break;
			
			case DOWN:
			if(y > 0)
				y--;
			break;

			case LEFT: 
			if(x > 0)
				x--;
			break;

			case RIGHT:
			if(x < Display.SIZE_X)
				x++;
			break;
		}
	}
	public DIRECTION captureInput(java.awt.event.KeyEvent e) 
	{
    	int keyCode = e.getKeyCode();
	    DIRECTION dir_captured = DIRECTION.NONE;
	    switch( keyCode ) 
	    { 
	        case KeyEvent.VK_UP:
	            dir_captured = DIRECTION.UP;
	            break;
	        case KeyEvent.VK_DOWN:
	            dir_captured = DIRECTION.DOWN;
	            break;
	        case KeyEvent.VK_LEFT:
	            dir_captured = DIRECTION.LEFT;
	            break;
	        case KeyEvent.VK_RIGHT :
	            dir_captured = DIRECTION.RIGHT;
	            break;
	     }
	     return dir_captured;
	 }
	public enum DIRECTION {UP, DOWN, LEFT, RIGHT, NONE}
	public enum STATE {JUMPING, FALLING, WALKING, STATIC}

}