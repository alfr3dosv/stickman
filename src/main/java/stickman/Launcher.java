package stickman;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.*;

import stickman.entity.Image;
import stickman.entity.Point;
import stickman.entity.Size;
import stickman.resources.*;
import stickman.player.*;
import stickman.util.ImageDrawer;

import javax.swing.text.Position;


public class Launcher {
    public static void main(String[] args) {
        Storyline story = new Storyline("/storyline.properties");
        Game game = new Game(story);
        game.start();
//        Player p = (Player) Resources.lookup("player");
//        p.position = new Point(50,12);
//        Image base = ReadFile.loadImage("/level/caminar/stage_1.txt");
//        //Image playerView = frame.cut()
//        ImageDrawer playerView = new ImageDrawer();
//        playerView.setOutput(base);
//        playerView.draw(p.img, p.position);
//        Image result = playerView.cut(new Size(70, 20), viewPosition(p.position));
//        System.out.print(result.toString());
    }
}
