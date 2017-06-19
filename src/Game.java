package game;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;
import game.files.*;
import game.entity.Entity;

public class Game{
    private Properties storylineFile;
    List<String> levelsFilesPaths;
    List<String> scenesFilesPaths;
    Display display;
    Player player;
    Thread input;
    int currentStep = 0;
    boolean storyEnd = false;

    public void menu()
    {
        RawConsoleInput input = new RawConsoleInput();
        char op='0';
        while( (op != '1') && (op !='3' ))
        {
            printBanner();
            printMenu();
            try{
                op = (char)input.read(true);
                input.resetConsoleMode();
                input=null;
                System.out.println(op);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        switch(op){
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
        loadNextStep();
        do
        {
            player.init();
            while( !player.hasKey() && player.isAlive() && !display.isOver() ) 
            //&& (player.status != Player.State.PAUSED)) consola no disponible en demo
            {   
                display.draw();
                display.drawEnemies();
                player.collisions(display.getFrame());
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
                loadNextStep();
            }
            else if(display.isOver() ){
                loadNextStep();
            }
        } while(!storyEnd);
        //final del juego
        input.interrupt();
        printEndBanner();
    }

    public void init() {
        storylineFile = this.readStorylineFile();
        levelsFilesPaths = this.getLevelsFilesPaths();
        scenesFilesPaths = this.getScenesFilesPaths();
        currentStep = 0;
        player = new Player();
        display = new Display();
        input = new Thread(player);
        input.start();
    }

    private Properties readStorylineFile() {
        Properties file = ReadFile.loadProperties("storyline.properties");
        return file;
    }

    private List<String> getScenesFilesPaths() {
        List<String> filesPaths = new ArrayList<String>();
        for(int i = 1; 
            storylineFile.getProperty("story"+Integer.toString(i)) != null; i++)
        {
            String path = storylineFile.getProperty("story"+Integer.toString(i));
            filesPaths.add(path);
        }
        return filesPaths;
    }

    private List<String> getLevelsFilesPaths() {
        List<String> filesPaths = new ArrayList<String>();
        for(int i = 1;
            storylineFile.getProperty("level"+Integer.toString(i)) != null; i++) 
        {
            String path = storylineFile.getProperty("level"+Integer.toString(i));
            filesPaths.add(path);
        }
        return filesPaths;
    }

    private String getNextStep() {
        List<String> storylineSteps = new ArrayList<String>();
        for (String step: 
             storylineFile.getProperty("storyline").split(",") )
        {
            storylineSteps.add(step);
        }
        if(currentStep < storylineSteps.size()) {
            return storylineSteps.get(currentStep);
        }
        else {
            return null;
        } 

    }
    
    private void loadNextStep() {
        String step = getNextStep();
        String stepText = step.substring( 0,(step.length()-1) ); 
        boolean isAScene = stepText.equals("story");
        String fileName = null;
        if(step != null) {
            if(isAScene &&  scenesFilesPaths.size() > 0){ 
                fileName = scenesFilesPaths.remove(0);
            }
            else if(levelsFilesPaths.size() > 0) { 
                fileName = levelsFilesPaths.remove(0);
            }
            else {
                storyEnd = true;
            }
        }
        if (!storyEnd) {
            String path = "assets/" + fileName + "/" + fileName + ".properties";
            display = new Display(path, isAScene);
        }
        currentStep++;
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
