package stickman.player;

import stickman.player.Player;
import stickman.entity.Image;
import stickman.entity.Size;

public class Collisions {
    char[][] frame;
    char[][] drawArea;
    char[] bottom;
    char charToTest;
    Player player;

	public Collisions(Player player) {
        this.player = player;
        setCharToTest('*');
    }

    public void setCharToTest(char charToTest) {
        this.charToTest = charToTest;
    }

    public void test(char[][] frame) {
        setContext(frame);
        testHitInsideDrawArea();
        testHitUnderPlayer();
    }

    public void setContext(char[][] frame) {
        this.frame = frame;
        Size sizeOfPlayer = player.img.size;
        drawArea = new char[sizeOfPlayer.y][sizeOfPlayer.x];
        for(int y = 0; y < sizeOfPlayer.y; y++) {
			for(int x = 0; x < sizeOfPlayer.x; x++) {
				drawArea[y][x] = frame[player.getY() + y][player.getX() + x];
			}
        }
        bottom = new char[sizeOfPlayer.x];
        for(int x = 0; x < sizeOfPlayer.x; x++) {
            bottom[x]= frame[player.getY() + sizeOfPlayer.y][player.getX() + x];
        }
    }

    public void testHitInsideDrawArea () {
		for(char[] column: drawArea) {
			for(char charInsideDrawArea: column) {
				if(charInsideDrawArea == charToTest) {
		            player.kill();
				}
			}
		}
		for( char[] caracteres: drawArea) {
			for( char caracter:caracteres) {
				if(caracter == 'K'){
			        player.setKey(true);
				}
			}
		}
    }
    
    public void testHitUnderPlayer() {
		/* bottom
		 * si se encuentra un '-' debajo del drawArea hay piso
		 * logicamente no puede atravesar el piso, lo subimos
		 */
        Size sizeOfPlayer = player.img.size;
		if(  player.getY() <=
		    (frame.length - sizeOfPlayer.y + 1) )
		{
			boolean PISO = false;
			for( char caracter: drawArea[2]) {
				if(caracter == '-') {
					player.setY(player.getY() - 1);//sube
					player.status = Player.State.STATIC;
					break;
				}
			}
			for(char caracter: bottom) {
				if(caracter == '-') {
					PISO = true;
				}
			}
			//caso en que no hay superficie abajp
			if( (PISO == false) &&
				(player.status != Player.State.JUMPING) )
			{
				player.status = Player.State.FALLING;
				player.fall(1);
			}

		}
	}
}
