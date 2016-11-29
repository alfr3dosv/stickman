import java.util.ArrayList;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.StringBuilder;
import java.io.IOException;

public class Display
{
	public static final int SIZE_X = 70;
	public static final int SIZE_Y = 20;
	private final int WAIT_PER_FRAME = 50; 
	private int frames=0;
	private char[][] frame;
	private int step;
	private long start_time;
	Level level = new Level();//public para testing
	private Story story = new Story();
	//historia
	StringBuilder dialogs = null;
	private boolean isOver = false;//SOLO EN MODO HOSTORIA el display termino una serie de escenas y dialogos
	private boolean STORY_MODE = false;
	/* Modos
	 * STORY_MODE = false, modo normal: el jugador puede moverse e interactuar
	 * STORY_MODE = true, modo historia: 
	 * se imprimen varias escenes con sus dialogos, el jugador no puede interactuar  
	 */
	
	RawConsoleInput console_in = new RawConsoleInput();
	public Display()
	{
		step = 0;
		start_time = System.currentTimeMillis();	
	}
	public Display(String levelPath)
	{
		this.init(levelPath);
	}
	public Display(String storyPath, boolean mode)
	{
		step = 0;
		start_time = System.currentTimeMillis();
		story = new Story(storyPath);
		STORY_MODE=true;	
	}
	public void init(String levelPath)
	{		
		step = 0;
		start_time = System.currentTimeMillis();
		level = new Level(levelPath);
	}

	public void print()
	{
		if( this.story.isOver && STORY_MODE)
		{
			isOver = true;
		}
		/* Si el tiempo actual - tiempo inicial > intevalo
		 * imprime el siguiente frame
		 */
		else if( (System.currentTimeMillis() - start_time) > WAIT_PER_FRAME)
		{		
			update();
			start_time = System.currentTimeMillis();		
		}
	}

	private void update()
	{
		/* Limpia la pantalla
		 * Imprime el frame
		 */

		clean();
		//armando el frame
		StringBuilder newFrame = new StringBuilder();
		for(int y=0; y<SIZE_Y; y++)
		{
			newFrame.append(frame[y]);
			newFrame.append("\n");
		}
		//impresion
        System.out.print(newFrame.toString());

        //dialogos modo historia
        if(STORY_MODE == true){
        	drawDialog( story.getDialog() );
        	System.out.flush();
        	if(dialogs != null)
        		System.out.print(dialogs.toString());
        	waitDialog();
        }
	}

	//testing
	public void fillFrame(char[][] frameToFill)//no usar 
	{
		frameToFill = new char[SIZE_Y][SIZE_X];
		for(int y=0; y<SIZE_Y; y++)
			for(int x=0; x<SIZE_X; x++)
				frameToFill[y][x]='0';	
	}


	//limpia el frame
	public void draw(){
		//deep copy del array
		frame = new char[SIZE_Y][SIZE_X];
		if(STORY_MODE)//modo historia
		{
			for(int y=0; y<SIZE_Y; y++)
				for(int x=0; x<SIZE_X; x++)
					frame[y][x]=story.scenes.get(0)[y][x];				
		}
		else//modo normal
		{
			for(int y=0; y<SIZE_Y; y++)
				for(int x=0; x<SIZE_X; x++)
					frame[y][x]=level.stages.get(0)[y][x];	
		}
	}
	public void draw(char[][] asset, int y, int x)
	{
		if(!STORY_MODE){
			char[][] before = new char[asset.length][asset[0].length];
			for(int pos_y=0; pos_y<asset.length; pos_y++)
			{
				for(int pos_x=0; pos_x<asset[0].length; pos_x++)
				{
					before[pos_y][pos_x] = frame[y+pos_y][x+pos_x];
					frame[y+pos_y][x+pos_x]=asset[pos_y][pos_x];
				}	
					// inveritdo frame[SIZE_Y-(y+pos_y)-1][SIZE_X-(x+pos_x)-1]=asset[pos_y][pos_x];
			}
		}
	}

	public void drawEnemies(){
		//Actualizando la posicion de los enemigos
		for(Enemie enemie : level.enemies)
		{
			enemie.update();
			this.draw( enemie.img.get(), enemie.getY(), enemie.getX() );
		}
	}

	public void drawDialog(String dialog)
	{ 
		/* Agrega texto a dialogs
		 * la variable dialogs se imprime despues del frame
		 * Para limpiar los dialogos enviar in string vacio
		 */
		if(dialogs == null){
			dialogs = new StringBuilder();
		}
		dialogs.append(dialog+"\n");
		if(dialog == ""){
		 	dialogs = null;
		}	
	}

	public static void clean()
	{
		/* 
	     * Devuelve el cursor a la parte superior
	     */
		String os = System.getProperty("os.name").toLowerCase();
	    //Windows
 		if (os.contains("Windows")){
 			try{
            	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 			}
 			catch(IOException e){
 				//es muy noche
 			}
 			catch(InterruptedException e){
 				
 			}
 		}
	    // Linux, Mac ANSI ESCAPES
		else{
		    System.out.print("\033[2J\033[;H");
		    System.out.flush();
		}
	}

	public char[][] getFrame()
	{
		char[][] frame = new char[SIZE_Y][SIZE_X];
		//deep copy
		for(int y=0; y<SIZE_Y; y++)
			for(int x=0; x<SIZE_X; x++)
				frame[y][x]=this.frame[y][x];
		return frame;
	}
	public boolean isOver(){
		return isOver;
	}
	public void waitDialog()
	{
		try {
			console_in.read(true);	
		} 
		catch(Exception ex){}  
	}
}