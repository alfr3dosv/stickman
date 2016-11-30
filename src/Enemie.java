public class Enemie extends Entity{
	private long start_time=0;
	private int speed;
	private int step=0;
	private int steps;
	
	public Enemie(){
		//image
		char[][] new_image = new char[1][1];
		new_image[0][0] = '*';
		this.img = new Image(1,1,new_image);
	}
	public void update(){
		//primera vez
		if(start_time == 0){
			start_time = System.currentTimeMillis();
		}
		else
		{
			if( (System.currentTimeMillis() - start_time) > 
				(1000/speed()) && 
				(step() <getSteps()) )
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
				step(step()+1);
			}
			if(step() == getSteps() ){
				step(0);
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
		//System.out.println(this.dir+" "+this.getY()+" "+this.getX()+" step:"+step); //debug
		}
	}
	//getters
	public int step(){return step;}
	public int speed(){return speed;}
	public int getSteps(){ return steps;}
	//setters
	public void step(int step){this.step = step;}	
	public void speed(int speed)
	{
		if(speed > 0)
			this.speed = speed;
	}
	public void setSteps(int steps) 
	{
		this.steps = steps;
	}
}