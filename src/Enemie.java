public class Enemie{
	public int x, y;
	public long start_time=0;
	public DIRECTION dir;
	public int speed;
	public int step=0;
	public int steps;
	public void update(){
		if(start_time == 0){
			start_time = System.currentTimeMillis();
		}
		if( (System.currentTimeMillis() - start_time) > (1000/speed) )
		{
			switch(dir){
				case UP:
						y++;
						break;
				case DOWN:
						y--;
						break;
				case LEFT:
						x--;
						break;
				case RIGHT:
						x++;
						break;
			}
			start_time = System.currentTimeMillis();
			step++;
		}
		if(step == steps){
			step = 0;
			switch(dir){
				case UP:
						dir = DIRECTION.DOWN;
						break;
				case DOWN:
						dir = DIRECTION.UP;
						break;
				case LEFT:
						dir = DIRECTION.RIGHT;
						break;
				case RIGHT:
						dir = DIRECTION.LEFT;
						break;
			}
		}
	}
	public static class Image{
		static final int SIZE_X = 1;
		static final int SIZE_Y = 1;
	}
	public char[][] image(){
		char[][] image = new char[1][1];
		image[0][0] = '*';
		return image;
	}
	public enum DIRECTION {UP, DOWN, LEFT, RIGHT, NONE}
}