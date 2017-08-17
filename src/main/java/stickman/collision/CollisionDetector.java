package stickman.player;

import stickman.player.Player;
import stickman.entity.*;
import stickman.util.ImageDrawer;


public class CollisionDetector {
    private Image base;
    private char[][] frame;
    char[][] drawArea;
    char[] bottom;
    char charToTest;
    Player player;
    private final char FLOOR = '-';

	public CollisionDetector(Player player) {
        this.player = player;
        setCharToTest('*');
    }

    public void setCharToTest(char charToTest) {
        this.charToTest = charToTest;
    }

    public void setContext(Image context) {
	    base = context;
    }

    public void testPlayer() {
        ImageDrawer drawAreaCut = new ImageDrawer();
        Size drawAreaSize = new Size(player.img.size.x + 1, player.img.size.y + 1);
        Point drawArePosition = new Point(player.position.x -1, player.position.y -1 );
        Image drawArea = drawAreaCut.cut(drawAreaSize, drawArePosition);

        if(isOverFloor())
           player.movement.fall(1);
    }

    public boolean isOverFloor() {
	    for(char c : bottom)
	        if(c == FLOOR)
	            return true;
	    return false;
    }

    public interface Hitable {
	    public void onHit(Object who);
    }
}
