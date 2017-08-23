package stickman.resources;

import java.util.*;
import stickman.entity.*;
import stickman.level.*;
import stickman.enemy.*;

public class LoadLevel
{
    private static final String levelsPath = "/level/";

    public static Level load(String folderName) {
        String pathToFolder = levelsPath + folderName;
        String pathToProperties = pathToFolder + "/" + folderName + ".properties";
        Properties properties = ReadFile.loadProperties(pathToProperties);
        String pathToStage = pathToFolder + "/" + properties.getProperty("stage");
        Image stage = ReadFile.loadImage(pathToStage);
        List<Enemy> enemies = loadEnemies(properties);
        List<Entity> entities = new ArrayList<Entity>();
        for(Enemy e : enemies)
            entities.add((Entity) e);
        return new Level(stage, entities);
    }

    private static List<Enemy> loadEnemies(Properties levelProperties) {
        Integer i = 1;
        List<Enemy> enemies = new ArrayList<Enemy>();
        while(levelProperties.getProperty("Enemy" + i.toString()) != null) {
            Enemy e = parseEnemie(levelProperties.getProperty("Enemy" + i.toString()));
            enemies.add(e);
            i++;
        }
        return enemies;
    }

    private static Enemy parseEnemie(String values) {
        final int X = 0;
        final int Y = 1;
        final int DIR = 2;
        final int SPEED = 3;
        final int STEPS = 4;
        String[] data = values.split(",");
        Enemy Enemy = new Enemy();
        Enemy.setX(Integer.parseInt(data[X]));
        Enemy.setY(Integer.parseInt(data[Y]));
        Enemy.speed(Integer.parseInt(data[SPEED]));
        Enemy.setSteps(Integer.parseInt(data[STEPS]));
        switch(data[DIR]) {
            case "UP":
                Enemy.dir = Entity.Direction.UP;
                break;
            case "DOWN":
                Enemy.dir = Entity.Direction.DOWN;
                break;
            case "LEFT":
                Enemy.dir = Entity.Direction.LEFT;
                break;
            case "RIGHT":
                Enemy.dir = Entity.Direction.RIGHT;
                break;
            default:
                Enemy.dir = Entity.Direction.NONE;
        }
        return Enemy;
    }
}
