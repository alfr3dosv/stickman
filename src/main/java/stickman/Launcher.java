package stickman;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.*;
import stickman.collision.*;
import stickman.resources.*;


public class Launcher {
    public static void main(String[] args) {
        Storyline story = new Storyline("/storyline.properties");
        Game game = new Game(story);
        game.start();
    }
}
