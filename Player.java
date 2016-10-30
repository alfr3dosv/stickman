import java.util.*;
import java.io.*;

public class Player implements Runnable
{
	public int y;
	public int x;
	public volatile DIRECTION dir;
	boolean ALIVE;
	public STATE status;
	Scanner console;
	RawConsoleInput in;
	public Player()
	{
		ALIVE = true;
		x = 0; 
		y = 0;
		console = new Scanner(System.in);
		in = new RawConsoleInput();
	}
	public void run()
	{
		char key = 'f';
		while(key != ':')
		{
			key = captureKey();
			if(key != 'f')
				System.out.println(key);
		}
		try{
			in.resetConsoleMode();
		}
		catch(Exception e)
		{

		}
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
	public char captureKey()
	{
		StringBuffer str = new StringBuffer();
		char key = 'f';
		try {
		    //Reader entrada = new InputStreamReader(System.in);
		    //while ((key=(char)entrada.read())!='\n'){
		    //    str.append(key);
		    //}
			key = (char)in.read(false);
		} 
		catch(IOException ex){key='f';}  
		finally
		{
			if(key != 'j' && key != 'k' && key != 'l' && key != 'h' && key != ':')
				key = 'f';
			return key;
		}
	} 
	public void captureInput() 
	{
    	char keyCode = captureKey();
	    dir = DIRECTION.NONE;
	    switch( keyCode ) 
	    { 
	        case 'j':
	            dir = DIRECTION.UP;
	            break;
	        case 'k':
	            dir = DIRECTION.DOWN;
	            break;
	        case 'h':
	            dir = DIRECTION.LEFT;
	            break;
	        case 'l':
	            dir = DIRECTION.RIGHT;
	            break;
	        case ':':
	        	dir = DIRECTION.NONE;
	        	status = STATE.PAUSED;
	        	break;

	     }
	 }
	public enum DIRECTION {UP, DOWN, LEFT, RIGHT, NONE}
	public enum STATE {JUMPING, FALLING, WALKING, STATIC, PAUSED}

}