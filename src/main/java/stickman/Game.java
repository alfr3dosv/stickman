package stickman;
import stickman.display.Display;
import stickman.input.Input;
import stickman.resources.*;
import stickman.level.*;

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
            op = Input.waitKeyPress();
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
        System.out.print("1) Start demo, 2)Exit \nOption:");
    }

    public void start() {
        Object next = Resources.lookup(storyline.getNext());
        while(next != null) {
            if (next instanceof Level)
                ((Level) next).play();
            else if (next instanceof Scene)
                ((Scene) next).play();
            next = Resources.lookup(storyline.getNext());
        }
        System.out.println(Resources.lookup("banner/dead").toString());;
    }
}
