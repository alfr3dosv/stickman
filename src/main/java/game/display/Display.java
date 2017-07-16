package game.display;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.StringBuilder;
import java.io.IOException;
import game.level.*;
import game.enemie.Enemie;
import game.input.Input;

public class Display
{
	public static final int SIZE_X = 70;
	public static final int SIZE_Y = 20;
	private final int WAIT_PER_FRAME = 50;
	private int frames=0;
	private char[][] frame;
	private int step;
	private long start_time;
	Level level;//public para testing
	private Scene scene = new Scene();
	//historia
	private StringBuilder dialogs = null;
	private boolean isOver = false;//SOLO EN MODO HOSTORIA el display termino una serie de escenas y dialogos
	private boolean SCENE_MODE = false;

	public Display(Level level) {
		init();
		this.level = level;
	}

	public Display(Scene scene) {
		init();
		this.scene = scene;
		SCENE_MODE = true;
	}

	public void init() {
		step = 0;
		start_time = System.currentTimeMillis();
	}

	public void update()
	{
		cloneFrame();
		drawEnemies();
	}

	public void print()
	{
		if( this.scene.isOver && SCENE_MODE){
			isOver = true;
		}
		long elapsedTime = System.currentTimeMillis() - start_time;
		if(elapsedTime > WAIT_PER_FRAME ) {
			clean();
			printFrame();
			if(SCENE_MODE)
				printDialogs();
			start_time = System.currentTimeMillis();
		}
	}

	private void printFrame() {
		StringBuilder frameToPrint = new StringBuilder();
		for(int y=0; y<SIZE_Y; y++) {
			frameToPrint.append(frame[y]);
			frameToPrint.append("\n");
		}
		System.out.print(frameToPrint.toString());
	}

	private void printDialogs() {
		drawDialog(scene.getDialog());
		if(dialogs != null)
			System.out.print(dialogs.toString());
		waitDialog();
	}

	public void cloneFrame() {
		frame = new char[SIZE_Y][SIZE_X];
		char[][] source;
		if(SCENE_MODE)
			source = scene.scenes.get(0);
		else
			source = level.stages.get(0);
		for(int y=0; y<SIZE_Y; y++) {
			for(int x=0; x<SIZE_X; x++) {
				frame[y][x] = source[y][x];
			}
		}
	}

	public void draw(char[][] asset, int y, int x)
	{
		if(!SCENE_MODE){
			char[][] before = new char[asset.length][asset[0].length];
			for(int pos_y=0; pos_y<asset.length; pos_y++)
			{
				for(int pos_x=0; pos_x<asset[0].length; pos_x++){
					before[pos_y][pos_x] = frame[y+pos_y][x+pos_x];
					frame[y+pos_y][x+pos_x]=asset[pos_y][pos_x];
				}
					// inveritdo frame[SIZE_Y-(y+pos_y)-1][SIZE_X-(x+pos_x)-1]=asset[pos_y][pos_x];
			}
		}
	}

	public void drawEnemies(){
		//Actualizando la posicion de los enemigos
		if(level != null)
			for(Enemie enemie : level.enemies){
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
 		if (os.indexOf("win") >= 0){
 			try{
            	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 			}
 			catch(IOException e){
 				//es muy noche
 			}
 			catch(InterruptedException e){
 				//es muy noche
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
		for(int y=0; y<SIZE_Y; y++){
			for(int x=0; x<SIZE_X; x++){
				frame[y][x]=this.frame[y][x];
			}
		}
		return frame;
	}
	public boolean isOver()
	{
		return isOver;
	}
	private void waitDialog()
	{
		Input.waitKeyPress();
	}
}