public class TestPlayerOnDisplay
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		disp.level.loadStage("/home/alfredo/progra/java/gitman/game/assets/stages/level_1/stage_1.pre");
		Player player = new Player();
		Thread input = new Thread(player);
		input.start();
		System.out.print("x:"+player.x+" y:"+player.y);

		while( (player.status != Player.STATE.PAUSED) && (player.isAlive()==true) )
		{	
			disp.draw();
			player.collisions(disp.getFrame());
			disp.draw(player.image(), player.y, player.x);	
			disp.print();
		}
	}
}