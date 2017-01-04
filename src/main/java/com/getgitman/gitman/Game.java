package com.getgitman.gitman;

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
import com.getgitman.gitman.*;
public class Game
{
    GameInput gameInput = new GameInput();
    Thread input = new Thread(gameInput);
    int step_counter=0;
    //input
    String input_while_paused = null;
    Properties storyline = FilesInput.loadProperties("storyline.properties");
    //storyline
    List<String> levelPaths = new ArrayList<String>();
    List<String> storyPaths = new ArrayList<String>();
    List<String> storySteps = new ArrayList<String>();  

    public void start()
	{
		switch( this.menu() ){
			case '1': 
                this.load("storyline.properties");
                this.play();
				break;
			case 2:
				break;
			case 3:
				break;
		}
	} 
	public int menu()
	{
		char op='0';
		while( (op != '1') && (op !='3' ))
		{
			printBanner();
			printMenu();
			op = (char)gameInput.rawRead(true);
		}
		return op;
	}
	public static void printBanner(){
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
	public static void printMenu()
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

	public void play()
	{
		//GitCommandLine git = new GitCommandLine("git.json");
		Player player = new Player(gameInput);
		input.start();
		while ( step_counter < storySteps.size() )
		{
			//paso en la historia, elimina el paso
			String step = storySteps.get(step_counter);
			//numero del nivel o historia
			String index = String.valueOf( step.toCharArray()[step.toCharArray().length-1] );
			// elimina el ultimo digito
			step= step.substring( 0,(step.length()-1) ); 
			//display
			Display disp;
			if(step.equals("story") ){ //modo historia
				String storyPath = storyPaths.get(Integer.valueOf(index)-1);
				Story story = new Story( "assets/" + storyPath + "/" + storyPath + ".properties");
                disp = new Display(story,gameInput);
			}
			else { //modo normal
				String levelPath = levelPaths.get(Integer.valueOf(index)-1);
				Level level = new Level( "assets/" + levelPath + "/" + levelPath + ".properties");
                level.addEntity(player);
                disp = new Display(level,gameInput);
			}
			player.init();
			System.out.flush();
			while( !player.hasKey() && player.isAlive() && !disp.isOver())
			{	
				disp.update();
                //console mode
                if( player.status == Player.State.PAUSED )
                {
                    input.interrupt();
                    String commands = "";
                    try{
                        RawConsoleInput in = new RawConsoleInput();
                        char txt = (char)in.read(false);
                        while(txt != '\n'){
                            commands += in;
                            txt = (char)in.read(false);
                        }
                        in.resetConsoleMode();
                    }
                    catch(Exception e){}
                    finally{
                        System.out.println(commands);
       //                 git.interpreter(commands.split(" "));
                    }
                }
			}
			// el jugador entro a la consola
			if(player.status == Player.State.PAUSED){
				System.out.println("No disponible en demo");
				player.status = Player.State.STATIC;
			}
			// el jugador murio 
			else if( !player.isAlive() ){
				printDeadBanner();
			}
			// el jugador consiguio la llave
			else if( player.hasKey() || disp.isOver() ){
				step_counter++;
			}
			// se acabo la parte de historia
		}
		//final del juego
		input.interrupt();
		printEndBanner();
    }
    public void load(String path)
    {
        //storyline 
        for (String step : storyline.getProperty("storyline").split(",") )
            storySteps.add(step);
        //level
        int l = 1;
        while( storyline.getProperty("level"+Integer.toString(l)) != null )
        {
            levelPaths.add( storyline.getProperty("level"+Integer.toString(l)) );
            l++;
        }
        //story
        int s = 1;
        while( storyline.getProperty("story"+Integer.toString(s)) != null )
        {
            storyPaths.add( storyline.getProperty("story"+Integer.toString(s)) );
            s++;
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
	public static void sleep(int time){
		try{
			Thread.sleep(time);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}