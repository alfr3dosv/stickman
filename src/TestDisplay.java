import java.util.Scanner;
public class TestDisplay
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		System.out.println("LOADING STAGE");
		disp.level.loadStage("/home/alfredo/progra/java/gitman/game/assets/stages/level_1/scene_1.txt");
		System.out.println("INIT DISPLAY");		
		while (true)
			disp.print();
	}
}
