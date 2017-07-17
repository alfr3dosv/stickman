package stickman.player;

import stickman.player.Player;

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
        drawArea = new char[player.img.SIZE_Y][player.img.SIZE_X];
        for(int posY=0; posY<player.img.SIZE_Y; posY++) {
			for(int posX=0; posX<player.img.SIZE_X; posX++) {
				drawArea[posY][posX] = frame[player.getY()+posY][player.getX()+posX];
			}
        }
        bottom = new char[player.img.SIZE_X];
        for(int posX=0; posX<player.img.SIZE_X; posX++) {
            bottom[posX]= frame[player.getY()+player.img.SIZE_Y][player.getX()+posX];
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
		//keys
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
		if(  player.getY() <=
		    (frame.length-player.img.SIZE_Y+1) )
		{
			boolean PISO = false;
			for( char caracter: drawArea[2]) {
				if(caracter == '-') {
					player.setY(player.getY()-1);//sube
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
