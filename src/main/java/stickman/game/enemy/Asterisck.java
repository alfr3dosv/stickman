package stickman.game.enemy;
import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.entity.Size;

public class Asterisck extends Entity implements Enemy {
	private long start_time=0;
	private int speed;
	private int step=0;
	private int steps;
	private int damage = 100;

	public Asterisck() {
		char[][] new_image = new char[1][1];
		new_image[0][0] = '*';
		this.setImage(new Image(new Size(1,1), new_image));
	}

	public void update() {
		if(start_time == 0) {
			start_time = System.currentTimeMillis();
		}
		else
		{
			if( (System.currentTimeMillis() - start_time) >
				(1000/speed()) &&
				(step() <getSteps()) )
			{
				switch(getDirection()) {
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

			if(step() == getSteps()) {
				step(0);
				switch(getDirection()) {
					case UP:
							setDirection(Direction.DOWN);
							break;
					case DOWN:
							setDirection(Direction.UP);
							break;
					case LEFT:
							setDirection(Direction.RIGHT);
							break;
					case RIGHT:
							setDirection(Direction.LEFT);
							break;
				}
			}
		}
	}

	public int step() {
		return step;
	}

	public int speed() {
		return speed;
	}

	public int getSteps() {
		return steps;
	}

	public void step(int step) {
		this.step = step;
	}

	public void speed(int speed) {
		if(speed > 0)
			this.speed = speed;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

    @Override
    public int getDamage() {
        return damage;
    }
}
