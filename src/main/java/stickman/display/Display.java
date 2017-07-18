package stickman.display;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.StringBuilder;
import java.io.IOException;
import stickman.level.*;
import stickman.enemie.Enemie;
import stickman.input.Input;
import stickman.entity.Image;
import stickman.entity.Size;

public class Display
{
	public static Size size;
	public static final int SIZE_X = 70;
	public static final int SIZE_Y = 20;
	private final int WAIT_PER_FRAME = 50;
	private int frames=0;
	private char[][] frame;
	private int step;
	private long start_time;
	private Level level;
	private Scene scene = new Scene();
	private StringBuilder dialogs = new StringBuilder();
	private boolean isOver = false;
	private boolean SCENE_MODE = false;
	private Image frameImage;

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
		size = new Size(70,20);
		step = 0;
		start_time = System.currentTimeMillis();
	}

	public void update() {
		char[][] source;
		if(SCENE_MODE)
			source = scene.scenes.get(0);
		else
			source = level.stages.get(0);
		frame = cloneFrame(source);
		frameImage = new Image(size, source);
		drawEnemies();
	}

	public void print() {
		if( this.scene.isOver && SCENE_MODE) {
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
		for(int y=0; y<size.y; y++) {
			frameToPrint.append(frame[y]);
			frameToPrint.append("\n");
		}
		System.out.print(frameToPrint.toString());
	}

	private void printDialogs() {
		String dialog = scene.getDialog();
		dialogs.append(dialog + "\n");
		if(dialog == "")
			dialogs = new StringBuilder();
		else
			System.out.print(dialogs.toString());
		waitDialog();
	}

	public char[][] cloneFrame(char[][] source) {
		char[][] newFrame = new char[size.y][size.x];
		for(int y=0; y<size.y; y++) {
			for(int x=0; x<size.x; x++) {
				newFrame[y][x] = source[y][x];
			}
		}
		return newFrame;
	}

	public void draw(char[][] asset, int y, int x) {
		if(!SCENE_MODE) {
			char[][] before = new char[asset.length][asset[0].length];
			for(int pos_y=0; pos_y<asset.length; pos_y++) {
				for(int pos_x=0; pos_x<asset[0].length; pos_x++) {
					before[pos_y][pos_x] = frame[y+pos_y][x+pos_x];
					frame[y+pos_y][x+pos_x]=asset[pos_y][pos_x];
				}
			}
		}
	}

	public void drawEnemies() {
		if(level != null) {
			for(Enemie enemie : level.enemies) {
				enemie.update();
				this.draw( enemie.img.get(), enemie.getY(), enemie.getX() );
			}
		}
	}

	public static void clean() {
		String os = System.getProperty("os.name").toLowerCase();
 		if (os.indexOf("win") >= 0) {	//Windows
 			try {
            	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 			}
 			catch(Exception e) {
				throw new RuntimeException("[Windows] No se pudo limpiar la pantalla", e);
			}
 		}
		else {	// Linux, Mac ANSI ESCAPES
		    System.out.print("\033[2J\033[;H");
		    System.out.flush();
		}
	}

	public char[][] getFrame() {
		return cloneFrame(frame);
	}

	public boolean isOver() {
		return isOver;
	}

	private void waitDialog() {
		Input.waitKeyPress();
	}
}
