package game;
import java.util.ArrayList;
import java.util.List;
import game.entity.Entity;
import game.display.Display;
import game.player.Player;
import game.input.Input;
import game.player.Collisions;
import game.files.Storyline;
import game.level.*;

public class Game{
    Display display = new Display();
    Player player = new Player();
    Thread input = new Thread(player);
    boolean storyEnd = false;
    Collisions collisions = new Collisions(player);
    Storyline storyline;

    public void menu()
    {
        char op='0';
        while( (op != '1') && (op !='2' )) {
            printBanner();
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

    public void printBanner(){
        //hardcoded banner
        Display.clean();
        System.out.println();
        System.out.println();
        System.out.println("    ######   #### ######## ##     ##    ###    ##    ## ");
        System.out.println("   ##    ##   ##     ##    ###   ###   ## ##   ###   ## ");
        System.out.println("   ##         ##     ##    #### ####  ##   ##  ####  ## ");
        System.out.println("   ##   ####  ##     ##    ## ### ## ##     ## ## ## ## ");
        System.out.println("   ##    ##   ##     ##    ##     ## ######### ##  #### ");
        System.out.println("   ##    ##   ##     ##    ##     ## ##     ## ##   ### ");
        System.out.println("    ######   ####    ##    ##     ## ##     ## ##    ## ");
    }

    public void printMenu()
    {
        //hardcoded
        System.out.println();
        System.out.println();
        System.out.println("         _   _________       __   _________    __  _________");
        System.out.println("        / | / / ____/ |     / /  / ____/   |  /  |/  / ____/");
        System.out.println("       /  |/ / __/  | | /| / /  / / __/ /| | / /|_/ / __/   ");
        System.out.println("      / /|  / /___  | |/ |/ /  / /_/ / ___ |/ /  / / /___   ");
        System.out.println("     /__ |_/_____/_____/|____  \\____/_/  |_/_/  /_/_____/   ");
        System.out.println("     / /   / __ \\/   |  / __ \\                              ");
        System.out.println("    / /   / / / / /| | / / / /                              ");
        System.out.println("   / /___/ /_/ / ___ |/ /_/ /                               ");
        System.out.println("  /_____/\\____/_/  |_/_____/  ");
        System.out.print("1) Start demo, 2)Exit \nOption:");
    }

    public void start(String path, boolean newGame)
    {
        init();
        loadNext();
        do
        {
            player.init();
            while( !player.hasKey() && player.isAlive() && !display.isOver() )
            {
                display.draw();
                display.drawEnemies();
                collisions.test(display.getFrame());
                display.draw(player.img.get(), player.getY(), player.getX());
                display.print();
            }
            // el jugador entro a la consola
            if(player.status == Player.State.PAUSED){
                System.out.println("No disponible en demo");
                player.status = Player.State.STATIC;
            }
            else if( !player.isAlive() ){
                printDeadBanner();
            }
            else if( player.hasKey() ){
                loadNext();
            }
            else if(display.isOver() ){
                loadNext();
            }
        } while(!storyEnd);
        //final del juego
        input.interrupt();
        printEndBanner();
    }

    public void init() {
        storyline = new Storyline("/storyline.properties");
        input.start();
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


    public void printDeadBanner()
    {
        //hardcoded banner
        Display.clean();
        System.out.print("\n\n\n\n\n\n\n");
        System.out.println("\t\t  ____ ____ ____ ____   ");
        System.out.println("\t\t ||D |||E |||A |||D ||");
        System.out.println("\t\t ||__|||__|||__|||__||");
        System.out.println("\t\t |/__\\|/__\\|/__\\|/__\\| ");
        System.out.print("\n\n\n\n\n\n\n");
        sleep(3000);
    }

    public void printEndBanner()
    {
        System.out.print("\n\n\n\n\n\n\n");
        Display.clean();
        System.out.print("\n\n");
        System.out.println("  ____________________         ");
        System.out.println(" < The demo ends here >        ");
        System.out.println("  --------------------         ");
        System.out.println("         \\   ^__^              ");
        System.out.println("          \\  (oo)\\_______      ");
        System.out.println("             (__)\\       )\\/\\  ");
        System.out.println("                 ||----w |     ");
        System.out.println("                 ||     ||     ");
        System.out.print("\n\n");
        sleep(3000);
    }

    public void sleep(int time){
        try{
            Thread.sleep(time);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
