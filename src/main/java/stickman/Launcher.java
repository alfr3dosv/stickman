package stickman;

import stickman.game.Game;
import stickman.game.storyline.Storyline;

public class Launcher {
    public static void main(String[] args) {
        Storyline story = new Storyline("/storyline.properties");
        Game game = new Game(story);
        game.menu();
    }
}
