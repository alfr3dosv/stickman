package stickman.level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuilder;
import stickman.files.*;
import stickman.enemie.Enemie;
import stickman.entity.Entity;

public class Level extends ReadFile
{
	public List<char[][]> stages = new ArrayList<char[][]>();
	public List<Enemie> enemies = new ArrayList<Enemie>();
	private String levelPath;
	private Properties levelProperties;

	public Level() {
	}

	public Level (String filePath) {
		levelPath = getLevelPath(filePath);
		levelProperties = ReadFile.loadProperties(filePath);
		stages = loadStage();
		enemies = loadEnemies();
	}

	private String getLevelPath (String filePath) {
		StringBuilder levelPath = new StringBuilder();
		String[] text = filePath.split("/");
		for(int i=0; i<text.length-1; i++) {
			levelPath.append(text[i]+"/");
		}
		return levelPath.toString();
	}

	public List<char[][]> loadStage() {
		List<char[][]> stages = new ArrayList<char[][]>();
		String pathToStage = levelPath+levelProperties.getProperty("stage1");
		stages.add(ReadFile.loadText(pathToStage));
		return stages;
	}

	public List<Enemie> loadEnemies() {
		Integer i = 1;
		List<Enemie> enemies = new ArrayList<Enemie>();
		while(levelProperties.getProperty("enemie"+i.toString()) != null) {
			Enemie e = getEnemie(levelProperties.getProperty("enemie"+i.toString()));
			enemies.add(e);
			i++;
		}
		return enemies;
	}

	public Enemie getEnemie(String values) {
		final int X = 0;
		final int Y = 1;
		final int DIR = 2;
		final int SPEED = 3;
		final int STEPS = 4;
		String[] data = values.split(",");
		Enemie enemie = new Enemie();
		enemie.setX(Integer.parseInt(data[X]));
		enemie.setY(Integer.parseInt(data[Y]));
		enemie.speed(Integer.parseInt(data[SPEED]));
		enemie.setSteps(Integer.parseInt(data[STEPS]));
		switch(data[DIR]) {
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
		return enemie;
	}
}
