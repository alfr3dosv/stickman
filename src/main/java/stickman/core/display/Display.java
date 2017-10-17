package stickman.core.display;

import java.util.*;

import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.core.entity.Size;
import stickman.game.player.Player;
import stickman.core.loader.Loader;
import stickman.util.ImageUtil;

public class Display
{
    private static final int WAIT_PER_FRAME = 50;
    public static Size size;
	private long startTime;
    private String text;
    private Image base;
    private Image frame;
    private Player player;

	public Display() {
		size = new Size(70,20);
        frame = new Image(size);
		startTime = System.currentTimeMillis();
		text = "";
		player = (Player) Loader.lookup("player");
	}

	public void print() {
		clean();
		frame = ImageUtil.from(frame)
				.cut(size)
				.where(getViewPoint())
				.getImage();
		System.out.print(frame.toString());
		System.out.println(text);
		startTime = System.currentTimeMillis();
	}

    public void render(Image destination, String dialog) {
		text = dialog;
	    base = destination.clone();
	    frame = base;
	}

	public void render(Image destination, List<Entity> entities) {
	    base = destination.clone();
	    frame = base;
        for(Entity e : entities)
			frame = ImageUtil.from(frame)
					.draw(e.getImage())
					.where(e.getPosition())
					.getImage();
	}

	public void render(Image base, Entity e) {
		frame = ImageUtil.from(base)
				.draw(e.getImage())
				.where(e.getPosition())
				.getImage();
	}

	public void render(Image base) {
		frame = base;
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
	    sleep(WAIT_PER_FRAME);
	}

    public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Point getViewPoint() {
		int halfWidth = size.x/2;
		int halfHeight = size.y/2;
		int x = player.getPosition().x + 35;
		Point pos = new Point(0,0);
		if(player.getPosition().x > halfWidth)
			pos.x = player.getPosition().x - halfWidth;
		if(player.getPosition().y > halfHeight)
			pos.y = player.getPosition().y - halfHeight;
		return pos;
	}

}
