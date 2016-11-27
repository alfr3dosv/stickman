public class TestEnemie
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		disp.level.loadStage("/home/alfredo/progra/java/gitman/game/assets/stages/level_1/stage_1.txt");
		Enemie enemie = new Enemie();
		enemie.x = 5;
		enemie.y = 5;
		enemie.speed=10;
		enemie.steps=10;
		enemie.dir = Enemie.DIRECTION.UP;

		while( true)
		{	
			enemie.update();
			disp.draw();
			disp.draw(enemie.image(), enemie.y, enemie.x);	
			disp.print();
		}
	}
}