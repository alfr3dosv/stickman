package game;
import java.util.ArrayList;
import java.util.List;
import game.entity.Entity;
import game.display.Display;
import game.player.Player;
import game.input.Input;
import game.player.Collisions;
import game.files.Storyline;
import game.files.Banner;
import game.level.*;

public class Game{
    Display display;
    Player player = new Player();
    Thread input = new Thread(player);
    boolean storyEnd = false;
    Collisions collisions = new Collisions(player);
    Storyline storyline = new Storyline("/storyline.properties");

    public void menu() {
        char op='0';
        while( (op != '1') && (op !='2' )) {
            printMenu();
            op = Input.waitKeyPress();
        }
        switch(op) {
            case '1': start("storylineFile.properties", false);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public void printMenu() {
        Display.clean();
        Banner.print("gitman");
        Banner.print("menu");
        System.out.print("1) Start demo, 2)Exit \nOption:");
    }

    public void start(String path, boolean newGame) {
        input.start();
        loadNext();
        do {
            player.init();
            while(!player.hasKey() && player.isAlive() && !display.isOver()) {
                display.update();;
                collisions.test(display.getFrame());
                display.draw(player.img.get(), player.getY(), player.getX());
                display.print();
            }
            // el jugador entro a la consola
            if(player.status == Player.State.PAUSED) {
                System.out.println("No disponible en demo");
                player.status = Player.State.STATIC;
            }
            else if(!player.isAlive()) {
                printDeadBanner();
            }
            else if(player.hasKey()) {
                loadNext();
            }
            else if(display.isOver()) {
                loadNext();
            }
        } while(!storyEnd);
        //final del juego
        input.interrupt();
        printEndBanner();
    }

    private void loadNext() {
        Object next = storyline.getNext();
        if(next instanceof Level) {
            display = new Display((Level) next);
        }
        else if(next instanceof Scene) {
            display = new Display((Scene) next);
        }
        else {
            storyEnd = true;
        }
    }

    public void printDeadBanner() {
        Display.clean();
        Banner.print("dead");
        sleep(3000);
    }

    public void printEndBanner() {
        Display.clean();
        Banner.print("end");
        sleep(3000);
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
