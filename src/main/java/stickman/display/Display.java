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
import stickman.entity.*;

public class Display
{
	public static Size size;
	public static final int SIZE_X = 70;
	public static final int SIZE_Y = 20;
	private final int WAIT_PER_FRAME = 50;
	private int frames=0;
	private int step;
	private long start_time;
	private Level level;
	private Scene scene = new Scene();
	private StringBuilder dialogs = new StringBuilder();
	private boolean isOver = false;
	private boolean SCENE_MODE = false;
	private Image frame;

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
		frame = new Image(size, source);
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
		char[][] source = frame.chars();
		for(int y = 0; y < size.y; y++) {
			frameToPrint.append(source[y]);
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

	public void draw(Image img, Point position) {
		if(!SCENE_MODE) {
			frame.draw(img, position);
		}
	}

	public void drawEnemies() {
		if(level != null) {
			for(Enemie enemie : level.enemies) {
				enemie.update();
				frame.draw(enemie.img, enemie.position);
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
		return frame.cloneChars(frame.chars());
	}

	public boolean isOver() {
		return isOver;
	}

	private void waitDialog() {
		Input.waitKeyPress();
	}
}
