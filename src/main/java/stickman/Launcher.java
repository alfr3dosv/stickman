package stickman;

import stickman.resources.*;

public class Launcher {
    public static void main(String[] args) {
        //Game game = new Game();
        //game.menu();
        if((Resources.lookup("scene/tutorial")) == null) {
            System.out.println("Fallo");
        } else {
            System.out.println("Funciono");
        }
    }
}
