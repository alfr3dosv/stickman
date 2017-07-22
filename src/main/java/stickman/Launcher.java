package stickman;

import java.util.*;
import stickman.resources.*;
import stickman.player.*;


public class Launcher {
    public static void main(String[] args) {
        Storyline story = new Storyline("/storyline.properties");
        Game game = new Game(story);
        game.start();
//        Player p = (Player) Resources.lookup("player");
//        Movement movement = new Movement(p);
//        movement.run();
    }
}
