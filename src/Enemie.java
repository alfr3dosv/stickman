public class Enemie extends Entity{
	public long start_time=0;
	public int speed;
	public int step=0;
	public int steps;
	
	public Enemie(){
		//image
		char[][] new_image = new char[1][1];
		new_image[0][0] = '*';
		this.img = new Image(1,1,new_image);
	}
	public void update(){
		if(start_time == 0){
			start_time = System.currentTimeMillis();
		}
		else
		{
			if( (System.currentTimeMillis() - start_time) > (1000/speed) && (step <steps) )
			{
				switch(dir){
					case UP:
							this.setY(getY()+1);
							break;
					case DOWN:
							this.setY(getY()-1);
							break;
					case LEFT:
							this.setX(getX()-1);
							break;
					case RIGHT:
							this.setX(getX()+1);
							break;
				}
				start_time = System.currentTimeMillis();
				step++;
			}
			if(step == steps){
				step = 0;
				switch(dir){
					case UP:
							dir = Entity.Direction.DOWN;
							break;
					case DOWN:
							dir = Entity.Direction.UP;
							break;
					case LEFT:
							dir = Entity.Direction.RIGHT;
							break;
					case RIGHT:
							dir = Entity.Direction.LEFT;
							break;
				}
			}
		//debug System.out.println(this.dir+" "+this.getY()+" "+this.getX()+" step:"+step);
		}
	}
}