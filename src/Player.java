import java.util.*;
import java.io.*;

public class Player implements Runnable
{
	public int y;
	public int x;
	public volatile DIRECTION dir = DIRECTION.NONE;
	private volatile boolean alive;
	public volatile char key;
	public volatile STATE status = STATE.STATIC;
	Scanner console;
	RawConsoleInput in;
	final int NEXT_KEY=20;
	long start_time;

	public Player()
	{
		alive = true;
		x = 0; 
		y = 0;
		console = new Scanner(System.in);
		in = new RawConsoleInput();
	}
	public void run()
	{
		while(status != STATE.PAUSED)
		{
			captureInput();
			move();
			try{
				in.resetConsoleMode();
				System.out.flush();
			}
			catch (Exception e){}
		}
		try{
			in.resetConsoleMode();
			console.next();
		}
		catch(Exception e){}
	}
	private void move()
	{
		switch (dir)
		{
			case UP: 
				setY( (getY()+1));
			break;
			
			case DOWN:
				setY(getY()-1);
			break;

			case LEFT: 
				setX(getX()-1);
			break;

			case RIGHT:
				setX( (getX()+1) );
			break;
		}
	}
	public char captureKey()
	{
		StringBuffer str = new StringBuffer();
		char key = 'f';
		try {
			if( (System.currentTimeMillis() - start_time) > NEXT_KEY)
			{
				key = (char)in.read(false);
				start_time = System.currentTimeMillis();
			}	
		} 
		catch(IOException ex){key='f';}  
		finally
		{
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
	       	default:
	       		dir = DIRECTION.NONE;
	     }
	 }
	public char[][] image()
	{
		char[][] img = new char[3][3];
		char[] img_0 = {' ','o',' '};
		char[] img_1 = {'/','|','\\'};
		char[] img_2 = {'/',' ','\\'};
		img[0] = img_0;
		img[1] = img_1;
		img[2] = img_2;
		return img;
	}

	public enum DIRECTION {UP, DOWN, LEFT, RIGHT, NONE}
	public enum STATE {JUMPING, FALLING, WALKING, STATIC, PAUSED}
	public static class Image{
		static final int SIZE_X = 3;
		static final int SIZE_Y = 3;
	}

	public void collisions(char[][] frame)
	{
		/* drawArea
		 * area donde se dibuja el jugador
		 */
		char[][] drawArea = new char[Image.SIZE_Y][Image.SIZE_X];
		for(int pos_y=0; pos_y<Image.SIZE_Y; pos_y++)
		{
			for(int pos_x=0; pos_x<Image.SIZE_X; pos_x++)
			{
				drawArea[pos_y][pos_x] = frame[getY()+pos_y][getX()+pos_x];
			}	
		}
		/* enemies
		 * commprueba que el area donde se va dibujar no haya enemigos
		 * en caso de que si mata al jugador y pausa el juego
		 */
		for( char[] caracteres: drawArea)
		{
			for( char caracter:caracteres)
			{				
				if(caracter == '*')
				{
				killPlayer();
				status = STATE.PAUSED;
				}
			}
		}
		//bottom
		if( getY() <= (frame.length+Image.SIZE_Y+1) )
		{
			for( char caracter: frame[getY()+1] )
			{
				if( (caracter == '-') )
				{
					setY(getY()-1);
					break;
				}
			}
		}
	}
	//setters y getters para x,y 
	public void setX(int x){
		if( (x < (Display.SIZE_X-Player.Image.SIZE_X)) && (x>=0) )
			this.x = x;
	}
	public void setY(int y){
		if( (y < (Display.SIZE_Y-Player.Image.SIZE_Y)) && (y>=0) )
			this.y = y;
	}
	public int getX(){return x;}
	public int getY(){return y;}
	
	//setters y getters para alive
	public boolean isAlive(){return this.alive;}
	private void killPlayer(){this.alive = false;}

}