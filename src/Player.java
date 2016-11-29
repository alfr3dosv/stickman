import java.util.*;
import java.io.*;

public class Player extends Entity implements Runnable
{
	public volatile State status = State.STATIC;
	//input
	int times=0;
	Scanner console;
	RawConsoleInput in;
	public volatile char key;
	char last_key;
	long start_time;// contador de tiempo entre teclas
	final int NEXT_KEY=20;//intervalo de tiempo entre teclas
	//jumping
	long jump_start=0;
	final int JUMP_WAIT=100;
	int jump_steps=1;
	int jump_step=0;
	//falling
	private long fall_start=0;
    final int FALL_WAIT=50;
	private int fall_step=0;
	private int speed_x;
	/* STAGE_KEY
	 * Si el jugador encontro la llave del stage pasa al siguiente stage o nivel
	 */ 
	private boolean STAGE_KEY = false;

	public Player()
	{
		in = new RawConsoleInput();
		//Image
		char[] new_img_0 ={' ','o',' '};
		char[] new_img_1 = {'/','|','\\'};
		char[] new_img_2 = {'/',' ','\\'};
		char[][] new_img = new char [3][3];
		new_img[0] = new_img_0;
		new_img[1] = new_img_1;
		new_img[2] = new_img_2;
		this.img = new Image(3,3,new_img);

	}
	public void run()
	{
		while(status != State.PAUSED)
		{
			captureInput();
			move();
			try{
				in.resetConsoleMode();
				System.out.flush();
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		try{
			in.resetConsoleMode();
			console.next();
		}
		catch(Exception e){}
	}
	public void init(){
		//position
		this.setX(0);
		this.setY(0);
		//life
		this.setAlive();
	}
	private void move()
	{
		if(status == State.JUMPING){
			jump();
		}	
		switch (dir)
		{
			case DOWN: 
				this.setY( (this.getY()+1));
			break;
		
			case LEFT:
				this.setX(this.getX()-1);
				if(speed_x>0){
					speed_x = 0;
				}
				else if(speed_x>-3){
					speed_x--;
				}
			break;

			case RIGHT:
				this.setX( (this.getX()+1) );
				if(speed_x<0){
					speed_x = 0;
				}
				else if(speed_x<3){
					speed_x++;
				}
				
			break;
		}
		if(status == State.STATIC){
			speed_x = 0;
		}
	}
	public char captureKey()
	{
		StringBuffer str = new StringBuffer();
		char key = 'f';
		try {
			if( (System.currentTimeMillis() - start_time) > NEXT_KEY)
			{
				char new_key = (char)in.read(false);
				//Evita que dejen presionada alguna tecla
				if(last_key != new_key || times > 2){
					key = new_key;
					last_key = key;
					times = 0;
				}
				else{
					times++;
				}
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
	    dir = Entity.Direction.NONE;
		    switch( keyCode ) 
		    { 
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

	public enum State {JUMPING, FALLING, WALKING, STATIC, PAUSED}

	public void collisions(char[][] frame)
	{
		/* drawArea
		 * area donde se dibuja el jugador
		 */
		char[][] drawArea = new char[this.img.SIZE_Y][this.img.SIZE_X];
		for(int pos_y=0; pos_y<this.img.SIZE_Y; pos_y++)
		{
			for(int pos_x=0; pos_x<this.img.SIZE_X; pos_x++)
			{
				drawArea[pos_y][pos_x] = frame[this.getY()+pos_y][this.getX()+pos_x];
			}	
		}
		/* bootm
		 * area debajo del jugador
		 */
		char[] bottom = new char[this.img.SIZE_X];
		for(int pos_x=0; pos_x<this.img.SIZE_X; pos_x++)
		{
			bottom[pos_x]= frame[this.getY()+this.img.SIZE_Y][this.getX()+pos_x];
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
				this.kill();
				//status = State.PAUSED;
				}
			}
		}
		//keys
		for( char[] caracteres: drawArea)
		{
			for( char caracter:caracteres)
			{				
				if(caracter == 'K')
				{
				STAGE_KEY = true;
				}
			}
		}
		/* bottom
		 * si se encuentra un '-' debajo del drawArea hay piso
		 * logicamente no puede atravesar el piso, lo subimos
		 */
		if( (this.getY() <= (frame.length-this.img.SIZE_Y+1)))//evitamos out of ranges 
		{
			boolean PISO = false;

			for( char caracter: drawArea[2])
			{				
				if(caracter == '-')
				{
					this.setY(this.getY()-1);//sube
					status = State.STATIC;
					break;
				}
			}
			for(char caracter: bottom){
				if(caracter == '-'){
					PISO = true;
				}
			}
			//caso en que no hay
			if( (PISO == false) && (status != State.JUMPING) ) 
			{
				//System.out.println("FALLING"); //debug
				status = State.FALLING;
				fall(1);
			}
			
		}
	}
	public void jump()
	{	
	    if(jump_start <= 0){
			jump_start = System.currentTimeMillis();
		}
		else if( (System.currentTimeMillis() - jump_start) > (50+(JUMP_WAIT*jump_step)) ) 
		{
			
			if((jump_step < jump_steps)){
				this.setY(this.getY()-1);
			}
			else{
				jump_steps=2;
				jump_step=-1;
				jump_start = 0;
				status = State.FALLING;
				//fall(1);
			}
			if(speed_x != 0){
				if(speed_x>0){
					this.setX(this.getX()+2+jump_step);
					speed_x--;		
				}
				else if(speed_x<0){
					this.setX(this.getX()-2-jump_step);
					speed_x++;		
				}
			}
			jump_step++;
		}
		if(jump_steps == 2){
		}
	}
	public void fall(int spaces)
	{
		while( (spaces > 0) && (status == State.FALLING) )
		{
		    if(fall_start == 0){
				fall_start = System.currentTimeMillis();
			}
			else if( (System.currentTimeMillis() - fall_start) > FALL_WAIT ) 
			{
				this.setY(this.getY()+1);
				if(fall_step<=2){
					fall_step++;
				}
				if(speed_x != 0){
					if(speed_x>0){
						this.setX(this.getX()+1);
					}
					else if(speed_x<0){
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
	//getter para key
	public boolean hasKey(){
		return this.STAGE_KEY;
	}
}