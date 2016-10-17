public class Player()
{
	int y;
	int x;
	boolean ALIVE;
	enum 
	public Player()
	{
		ALIVE = true;
		x, y = 0;
	}
	private void move(DIRECTION dir)
	{
		switch (dir)
		{
			case UP: y++;
					 break;
			case UP: y++;
					 break;
			case UP: y++;
					 break;					 
					 
		}
	}
	public void captureInput()
	{

	}
	public enum DIRECTION {UP, DOWN, LEFT, RIGHT}
	public enum STATE {JUMPING, FALLING, WALKING}
}