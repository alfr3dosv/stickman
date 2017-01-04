package com.getgitman.gitman;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuilder;
import com.getgitman.*;

public class Level extends FilesInput implements Displayable
{
	public List<char[][]> stages = new ArrayList<char[][]>();
	private List<char[][]> assets = new ArrayList<char[][]>();
	public List<Entity> entitys = new ArrayList<Entity>(); 
	private String levelPath;

	public Level(){

	}
	public Level (String levelPath)
	{
		this.init(levelPath);
	}
	public void addStage(String path)
	{
		stages.add(this.loadText(path));
	}

	public void addAsset(String path)
	{
		assets.add(this.loadText(path));
	}

	public void init(String path)
	{
		this.loadSettings(path);
		/* levelPath
		 * hacemos un split sobre path, lo convertimos a string quitando el ultimo indice 
		 * ejemplo path= "assets/level_3/algo.propertie" levelPath = "assets/level_3" 
		 */
		StringBuilder builderPath = new StringBuilder();
		String[] text = path.split("/"); 
		for(int i=0; i<text.length-1; i++) 
        {
		  builderPath.append(text[i]+"/");
		}
		this.levelPath = builderPath.toString();
		int stages = 1;   // numero de stages
		while( settings().getProperty("stage"+Integer.toString(stages)) != null )
		{
			this.addStage( levelPath+settings().getProperty("stage"+Integer.toString(stages)) );
			stages++;
		}
		int entys = 1;    // numero de entitys
		while (settings().getProperty("enemie"+Integer.toString(entys)) != null )
		{
			this.addEnemie( settings().getProperty("enemie"+Integer.toString(entys)) );
			entys++;
		}
		
	}

	public void addEnemie(String values)
	{
		final byte X = 0;
		final byte Y = 1;
		final byte DIR = 2;
		final byte SPEED = 3;
		final byte STEPS = 4;
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
		this.entitys.add(enemie);
	}

    public void addEntity(Entity enti)
    {
        this.entitys.add(enti);
    }

    public void update()
    {
        for(Entity en : entitys)
        {
            en.update();
        }
    }
    public char[][] getImage()
    {
        char[][] image = new char[Display.SIZE_Y][Display.SIZE_X];
        for(int y=0; y<Display.SIZE_Y; y++)
        {
            for(int x=0; x<Display.SIZE_X; x++)
            {
                image[y][x]=this.stages.get(0)[y][x];              
            }
        }
        return image;
    }

    public String getDialogs(){
        return null;
    }
    
    public List<Entity> getEntitys()
    {
        return entitys;
    }
}