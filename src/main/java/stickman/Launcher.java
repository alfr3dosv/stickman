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
        Storyline s = new Storyline("/storyline.properties");
        System.out.println((Resources.lookup("banner/end")).toString());
        Player x = (Player) Resources.lookup("player");
        if(Resources.lookup("player") == null)
            System.out.println("Fallo");
        else {
            System.out.println(x.img.toString());
            System.out.println(x.img.size.x);
            System.out.println(x.img.size.y);
        }
        Display d = new Display();
        Image i = new Image(new Size(20,70));

        d.render(i, x);
        while(true) {
            d.print();
        }
    }
}
