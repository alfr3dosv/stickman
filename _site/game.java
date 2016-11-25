import java.util.Scanner;
public class game
{
	public static void main(String[] args)
	{
		int SIZE_X = 70;
		int SIZE_Y = 20;
		int frames=1;
		final int wait = 16; 
		char[][] frame = new char[SIZE_Y][SIZE_X];
		for(int y=0; y<SIZE_Y; y++)
			for(int x=0; x<SIZE_X; x++)
				frame[y][x]='0';	
		long start = System.currentTimeMillis();
		while (true)
		{
	//		System.out.println("Time:" + (System.currentTimeMillis() - start) );
			if( (System.currentTimeMillis() - start) > wait)
			{		
				clean();
				System.out.println("Frame" + frames++);
				for(int y=0; y<SIZE_Y; y++)
				{
					for(int x=0; x<SIZE_X; x++)
						System.out.print(frame[y][x]);
					System.out.print("\n");
				}
				start = System.currentTimeMillis();
			}
		}
	}
	public static void clean()
	{
    final String ANSI_CLS = "\u001b[2J";
    final String ANSI_HOME = "\u001b[H";
    System.out.print(ANSI_CLS + ANSI_HOME);
    System.out.flush();
	}

}
