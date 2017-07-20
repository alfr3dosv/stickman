package stickman.display;

import java.util.*;
import stickman.entity.*;
import stickman.util.ImageDrawer;

public class Display
{
	public static Size size;
	public static final int SIZE_X = 70;
	public static final int SIZE_Y = 20;
	private static final int WAIT_PER_FRAME = 50;
	private long startTime;
    private String text;
    private ImageDrawer frame;

	public Display() {
        frame = new ImageDrawer();
		size = new Size(70,20);
		startTime = System.currentTimeMillis();
		text = "";
	}

	public void print() {
		clean();
		System.out.print(frame.getImage().toString());
		System.out.print(text);
		startTime = System.currentTimeMillis();
	}

    public void render(Image base, String dialog) {
        frame.setOutput(base);
        text = dialog;
	}

	public void render(Image base, Entity[] entitys) {
        frame.setOutput(base);
        for(Entity e : entitys)
			frame.draw(e.img, e.position);
	}

	public void render(Image base, Entity e) {
		frame.setOutput(base);
		frame.draw(e.img, e.position);
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

	public static void sleep() {
		try {
			Thread.sleep(WAIT_PER_FRAME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
