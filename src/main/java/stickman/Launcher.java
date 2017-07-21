package stickman;

import java.util.*;
import stickman.resources.*;


public class Launcher {
    public static void main(String[] args) {
        Storyline story = new Storyline("/storyline.properties");
        Game game = new Game(story);
        game.start();
    }
}
