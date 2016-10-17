import java.util.Scanner;
public class runner
{
	public static void main(String[] args)
	{
		Display disp = new Display();
		disp.loadStage("/home/alfredo/progra/java/gitman/game/assets/stages/story_1/before.txt");
		while (true)
			disp.print();
	}
}
