public class TestStory
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		disp.level.addStage("/home/alfredo/progra/java/gitman/game/assets/stages/level_1/stage_1.txt");
		Player player = new Player();
		Thread input = new Thread(player);
		input.start();
		System.out.print("x:"+player.getX()+" y:"+player.getY());

		while( (player.status != Player.State.PAUSED)  )
		{	
			disp.draw();
			player.collisions(disp.getFrame());
			disp.draw(player.img.get(), player.getY(), player.getX());	
			disp.print();
		}
	}
}