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
import java.lang.StringBuilder;

public class Level 
{
	List<char[][]> stages = new ArrayList<char[][]>();
	List<char[][]> assets = new ArrayList<char[][]>();
	List<Enemie> enemies = new ArrayList<Enemie>(); 
	//file
	private Properties settings = new Properties();
	private InputStream input = null;
	//enemie
	final int X = 0;
	final int Y = 1;
	final int DIR = 2;
	final int SPEED = 3;
	final int STEPS = 4;
	private String levelPath;
	public Level(){

	}
	public Level (String levelPath)
	{
		this.init(levelPath);
	}
	public char[][] loadText(String path)
	{
		char[][] resource;
		List<String> lines = new ArrayList();

		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			lines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println("ERR: Not found file");
			e.printStackTrace();
		}
		resource = new char[lines.size()][lines.get(0).toCharArray().length];
		for(int i=0; i< resource.length; i++)
		{
			String s = lines.remove(0);
			resource[i] = s.toCharArray();
		}
		return resource;
	}
	public void addStage(String path)
	{
		stages.add(loadText(path));
	}

	public void addAsset(String path)
	{
		assets.add(loadText(path));
	}

	public void init(String path)
	{
		/* levelPath
		 * hacemos un split sobre path, lo convertimos a string quitando el ultimo indice 
		 * ejemplo path= "assets/level_3/algo.propertie" levelPath = "assets/level_3" 
		 */
		StringBuilder builderPath = new StringBuilder();
		String[] text = path.split("/"); 
		for(int i=0; i<text.length-1; i++) {
		  builderPath.append(text[i]+"/");
		}
		this.levelPath = builderPath.toString();

		try {
			input = new FileInputStream(path);
			settings.load(input);
			//stages
			int s = 1;
			while( settings.getProperty("stage"+Integer.toString(s)) != null )
			{
				this.addStage( levelPath+settings.getProperty("stage"+Integer.toString(s)) );
				s++;
			}
			//enemies
			int e = 1;
			while( settings.getProperty("enemie"+Integer.toString(e)) != null )
			{
				System.out.println(settings.getProperty("enemie"+Integer.toString(e)) );
				try{
					Thread.sleep(500);
				}
				catch(Exception ex){

				}
				this.addEnemie( settings.getProperty("enemie"+Integer.toString(e)) );
				e++;
			}
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addEnemie(String values)
	{
		String[] data = values.split(",");
		Enemie enemie = new Enemie();
		enemie.setX( Integer.parseInt(data[X]) ) ;
		enemie.setY( Integer.parseInt(data[Y]) ) ;
		enemie.speed( Integer.parseInt(data[SPEED]) );
		enemie.setSteps( Integer.parseInt(data[STEPS]) );
		switch(data[DIR])
		{
			case "UP":enemie.dir = Entity.Direction.UP;
			break;
			case "DOWN":enemie.dir = Entity.Direction.DOWN;
			break;
			case "LEFT":enemie.dir = Entity.Direction.LEFT;
			break;
			case "RIGHT":enemie.dir = Entity.Direction.RIGHT;
			break;
			default:enemie.dir = Entity.Direction.NONE;
		}
		this.enemies.add(enemie);
	}
}