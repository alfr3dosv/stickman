package game.entity;
import game.Display;

public abstract class Entity
{
	//enitity posicion
	private int y=0;
	private int x=0;
	//entity life
	private volatile boolean alive = true;
	//enitity picture
	public Image img;
	//enitity direction
	public volatile Direction dir = Entity.Direction.NONE;
	public enum Direction {UP, DOWN, LEFT, RIGHT, NONE}

	public class Image
	{
		private char[][] img;
		public int SIZE_X;
		public int SIZE_Y;
		public Image(int SIZE_X, int SIZE_Y)
		{
			this.init(SIZE_Y, SIZE_X);
		}
		public Image(int SIZE_X, int SIZE_Y, char[][] img )
		{
			this.init(SIZE_Y, SIZE_X);
			this.img = img;  	
		}
		public void init(int SIZE_X, int SIZE_Y)
		{
			//no negativos
			this.SIZE_Y = SIZE_Y;
			this.SIZE_X = SIZE_X;
			this.img = new char[SIZE_Y][SIZE_X];
		}
		public char[][] get()
		{
			return this.img;
		}
	}
	
	//setters y getters para x,y 
	public void setX(int x){
		if( (x < (Display.SIZE_X-this.img.SIZE_X)) && 
			(x>=0) )
		{
			this.x = x;
		}
	}
	public void setY(int y){
		if( (y < (Display.SIZE_Y-this.img.SIZE_Y)) && 
			(y>=0) )
		{
			this.y = y;
		}
	}
	public int getX(){return x;}
	public int getY(){return y;}

	//setters y getters para alive
	public boolean isAlive(){return this.alive;}
	public void kill(){this.alive = false;}
	public void setAlive(){this.alive = true;}
}