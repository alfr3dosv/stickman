package stickman.resources;

import java.util.*;
import stickman.entity.*;
import stickman.level.*;
import stickman.enemie.*;
import stickman.files.ReadFile;

public class LoadLevel
{
    private static final String levelsPath = "/level/";

    public static Level load(String folderName) {
        String pathToFolder = levelsPath + "/" + folderName;
        String pathToProperties = pathToFolder + "/" + folderName + ".properties";
        Properties properties = ReadFile.loadProperties(pathToProperties);
        String pathToStage = pathToFolder + "/" + properties.getProperty("stage");
        Image stage = ReadFile.loadImage(pathToStage);
        List<Enemie> enemies = loadEnemies(properties);
        return new Level(stage, enemies);
    }

    private static List<Enemie> loadEnemies(Properties levelProperties) {
        Integer i = 1;
        List<Enemie> enemies = new ArrayList<Enemie>();
        while(levelProperties.getProperty("enemie"+i.toString()) != null) {
            Enemie e = parseEnemie(levelProperties.getProperty("enemie"+i.toString()));
            enemies.add(e);
            i++;
        }
        return enemies;
    }

    private static Enemie parseEnemie(String values) {
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
