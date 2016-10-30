
public class testPlayer 
{
	public static void main(String[] args)
	{
		Player player = new Player();
		Thread input = new Thread(player);
		input.start();
		while(true)
		{
//			System.out.println(player.dir);
			//System.out.print("x:"+player.x+" y:"+player.y);
		}
		//input.interrupt();
	}
}