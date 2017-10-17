package stickman.game;
import stickman.game.storyline.Storyline;
import stickman.core.display.Display;
import stickman.core.keyboard.Keyboard;
import stickman.game.level.*;
import stickman.core.loader.Loader;

public class Game{
    Display display;
    boolean storyEnd = false;
    Storyline storyline;

    public Game(Storyline storyline) {
        this.storyline = storyline;
    }

    public void menu() {
        char op='0';
        while( (op != '1') && (op !='2' )) {
            printMenu();
            op = Keyboard.waitKeyPress();
        }

        switch(op) {
            case '1': start();
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public void printMenu() {
        Display.clean();
        System.out.println(Loader.lookup("banner/stickman").toString());
        System.out.println(Loader.lookup("banner/menu").toString());
        System.out.print("1) Start, 2)Exit \n :");
    }

    public void start() {
        Object next = Loader.lookup(storyline.getNext());
        while(next != null) {
            if (next instanceof Level)
                ((Level) next).play();
            else if (next instanceof Scene)
                ((Scene) next).play();
            next = Loader.lookup(storyline.getNext());
        }
        System.out.println(Loader.lookup("banner/dead").toString());;
    }
}
