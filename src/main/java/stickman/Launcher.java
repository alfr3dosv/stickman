package stickman;

import java.util.*;

import stickman.resources.Storyline;
import stickman.resources.*;
import stickman.player.Player;
import stickman.display.Display;
import stickman.entity.*;

public class Launcher {
    public static void main(String[] args) {
        //Game game = new Game();
        //game.menu();

        Player x = (Player) Resources.lookup("player");
//        if(Resources.lookup("player") == null)
//            System.out.println("Fallo");
//        else {
//            System.out.println(x.img.toString());
//            System.out.println(x.img.size.x);
//            System.out.println(x.img.size.y);
//        }
        Display d = new Display();
        Image i = ReadFile.loadImage("/level/caminar/stage_1.txt");
        Image i2 = x.img.clone();
        Image i3 = new Image(new Size(20,70));

        System.out.println("HI");
        d.render(i, x);
        while(true) {
            d.print();
        }
    }
}
