package stickman.core.entity;

public class Entity
{
	private Point position = new Point(0,0);
	private volatile boolean alive = true;
	private Image img;
	public Speed speed = new Speed(0,0);
	private volatile Direction dir = Entity.Direction.NONE;

	public Entity() {
        setX(0);
        setY(0);
        speed.x = 0;
        speed.y = 0;
        setAlive();
    }

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		if(position != null) {
			this.position = new Point(position);
		}
	}

	public Image getImage() {
		return img;
	}

	public void setImage(Image img) {
		this.img = img;
	}

	public Direction getDirection() {
		return dir;
	}

	public void setDirection(Direction dir) {
		this.dir = dir;
	}

	public enum Direction {NONE, UP, DOWN, LEFT, RIGHT}

	public void setX(int x) {
		//if((x < (Display.SIZE_X - img.size.x)) & (x >= 0))
			position.x = x;
	}

	public void setY(int y) {
		//if((y < (Display.SIZE_Y - img.size.y)) && (y >= 0))
			position.y = y;
	}

	public int getX() {
		return getPosition().x;
	}

	public int getY() {
		return getPosition().y;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void kill() {
		this.alive = false;
	}

	public void setAlive() {
		this.alive = true;
	}
}
