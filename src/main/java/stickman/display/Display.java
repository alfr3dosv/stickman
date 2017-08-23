package stickman.display;

import java.util.*;
import stickman.entity.*;
import stickman.player.Player;
import stickman.resources.Resources;
import stickman.util.ImageDrawer;

public class Display
{
    private static final int WAIT_PER_FRAME = 50;
    public static Size size;
	private long startTime;
    private String text;
    private Image base;
    private ImageDrawer frame;
    private Player player;

	public Display() {
        frame = new ImageDrawer();
		size = new Size(70,20);
		startTime = System.currentTimeMillis();
		text = "";
		player = (Player) Resources.lookup("player");
	}

	public void print() {
		clean();
		System.out.print(frame.cut(size, getViewPoint()).toString());
		System.out.println(text);
		startTime = System.currentTimeMillis();
	}

    public void render(Image destination, String dialog) {
	    base = destination.clone();
        frame.setOutput(base);
        text = dialog;
	}

	public void render(Image destination, List<Entity> entities) {
	    base = destination.clone();
        frame.setOutput(base);
        for(Entity e : entities)
			frame.draw(e.img, e.position);
	}

	public void render(String text) {
		this.text = text;
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

	private Point getViewPoint() {
		int halfWidth = size.x/2;
		int halfHeight = size.y/2;
		int x = player.position.x + 35;
		Point pos = new Point(0,0);
		if(player.position.x > halfWidth)
			pos.x = player.position.x - halfWidth;
		if(player.position.y > halfHeight)
			pos.y = player.position.y - halfHeight;
		return pos;
	}

}
