public class TestEnemie
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		disp.level.addStage("assets/level_1/stage_1.txt");
		Enemie enemie = new Enemie();
		enemie.setX(5);
		enemie.setY(5);
		enemie.speed(1);
		enemie.setSteps(50);
		enemie.dir = Entity.Direction.UP;

		while( true)
		{	
			enemie.update();
			disp.draw();
			disp.draw(enemie.img.get(), enemie.getY(), enemie.getX());	
			disp.print();
		}
	}
}