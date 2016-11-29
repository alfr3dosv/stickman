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

public class Game extends FilesInput{
	public static void main(String[] args)
	{
		switch( menu() ){
			case '1': play("storyline.properties", false);
				break;
			case 2:

				break;
			case 3:
				break;
		}
	} 
	public static int menu()
	{
		RawConsoleInput input = new RawConsoleInput();
		char op='0';
		while( (op != '1') && (op !='2' ) && (op !='3' ))
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
		return op;
	}
	public static void printBanner(){
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
		System.out.print("1) New Game, 2) Load, 3)Exit \nOption:");
	}

	public static void play(String path, boolean newGame)
	{
		int step_counter=0;
		//input
		String input_while_paused = null;
		Properties storyline = FilesInput.loadProperties("storyline.properties");
		//storyline
		List<String> levelPaths = new ArrayList<String>();
		List<String> storyPaths = new ArrayList<String>();
		List<String> storySteps = new ArrayList<String>();		
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
		//Player
		Player player = new Player();
		Thread input = new Thread(player);
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
				disp = new Display( "assets/" + storyPath + "/" + storyPath + ".properties", true );
			}
			else { //modo normal
				String levelPath = levelPaths.get(Integer.valueOf(index)-1);
				disp = new Display( "assets/" + levelPath + "/" + levelPath + ".properties");
			}
			player.init();
			System.out.flush();
			while( (player.status != Player.State.PAUSED) && !player.hasKey() && player.isAlive() && !disp.isOver() )
			{	
				disp.draw();
				disp.drawEnemies();
				player.collisions(disp.getFrame());
				disp.draw(player.img.get(), player.getY(), player.getX());	
				disp.print();
			}
			// el jugador entro a la consola
			if(player.status == Player.State.PAUSED){

			}
			// el jugador murio 
			else if( !player.isAlive() ){
				printDeadBanner();
			}
			// el jugador consiguio la llave
			else if( player.hasKey() ){
				step_counter++;
			}
			// se acabo la parte de historia
			else if(disp.isOver() ){
				step_counter++;
			}
		}
		//final del juego
		input.interrupt();
		printEndBanner();
	}
	public static void printDeadBanner()
	{
		Display.clean();
		System.out.print("\n\n\n\n\n\n\n\n");
		System.out.println("\t\t  ____ ____ ____ ____   ");
		System.out.println("\t\t ||D |||E |||A |||D ||");
		System.out.println("\t\t ||__|||__|||__|||__||");
		System.out.println("\t\t |/__\\|/__\\|/__\\|/__\\| ");
		sleep(3000);
	}	
	public static void printEndBanner()
	{
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