import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Display
{
	final int SIZE_X = 70;
	final int SIZE_Y = 20;
	final int WAIT_PER_FRAME = 16; 
	int frames=0;
	char[][] frame;
	int step;
	long start_time;
	Level level;	
	
	public Display()
	{
		step = 0;
		start_time = System.currentTimeMillis();		
	}

	public void print()
	{
		/* Si el tiempo actual - tiempo inicial > intevalo
		 * imprime el siguiente frame
		 */
		if( (System.currentTimeMillis() - start_time) > WAIT_PER_FRAME)
		{		
			update();
			start_time = System.currentTimeMillis();		
		}
	}

	private void update()
	{
		/* Carga el mapa en el frame 
		 * Limpia la pantalla
		 * Imprime el frame
		 */
		frame = level.stages.get(step);
		clean();
		System.out.println("Frame" + frames++);
		for(int y=0; y<SIZE_Y; y++)
		{
			for(int x=0; x<SIZE_X; x++)
				System.out.print(frame[y][x]);
			System.out.print("\n");
		}
	}

	public void fillFrame(char[][] frameToFill)
	{
		frameToFill = new char[SIZE_Y][SIZE_X];
		for(int y=0; y<SIZE_Y; y++)
			for(int x=0; x<SIZE_X; x++)
				frameToFill[y][x]='0';	
	}


	private void clean()
	{
		/* 
	     * Debuelve el cursor a la parte superior
	     */
	    final String ANSI_CLS = "\u001b[2J";
	    final String ANSI_HOME = "\u001b[H";
	    System.out.print(ANSI_CLS + ANSI_HOME);
	    System.out.flush();
	}

	public void loadStage(String path)
	{
		char[][] stage = new char[SIZE_Y][SIZE_X];
		List<String> lines = new ArrayList();

		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			lines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int y=0; y<SIZE_Y; y++)
		{
			String s = lines.remove(0);
			stage[y] = s.toCharArray();
		}
		//level.stages.add(stage);
	}
}