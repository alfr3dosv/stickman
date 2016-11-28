public class TestLevel
{
	public static void main(String[] args)
	{
		Display disp = new Display("assets/level_1/level_1.properties");
		Player player = new Player();
		Thread input = new Thread(player);
		input.start();
		System.out.print("x:"+player.getX()+" y:"+player.getY());

		while( (player.status != Player.State.PAUSED) && !player.hasKey() )
		{	
			disp.draw();
			disp.drawEnemies();
			player.collisions(disp.getFrame());
			disp.draw(player.img.get(), player.getY(), player.getX());	
			disp.print();
		}
	}
}