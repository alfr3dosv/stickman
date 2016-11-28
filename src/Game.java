import java.util.Scanner;

public class Game{
	public static void main(String[] args)
	{
		switch( menu() ){
			case 1:
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
				System.out.println(op);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
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
		System.out.print("1) New Game, 2) Load, 3)Salir \nOption:");
	}
}