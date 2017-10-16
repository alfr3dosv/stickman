package stickman.core.loader;

import java.util.*;

import stickman.core.entity.Point;
import stickman.game.enemy.Asterisck;
import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.game.key.Key;
import stickman.game.level.*;

public class LoadLevel
{
    private static final String levelsPath = "/level/";

    public Level load(String folderName) {
        String pathToFolder = levelsPath + folderName;
        String pathToProperties = pathToFolder + "/" + folderName + ".properties";
        Properties properties = ReadFile.loadProperties(pathToProperties);

        String pathToStage = pathToFolder + "/" + properties.getProperty("stage");
        Image stage = ReadFile.loadImage(pathToStage);
        List<Asterisck> enemies = loadEnemies(properties);
        Point playerPosition = parsePlayerPosition(properties);

        List<Entity> entities = new ArrayList<Entity>();
        for(Asterisck e : enemies)
            entities.add((Entity) e);
        entities.add((Entity) parseKey(properties));

        Level level = new Level (stage, entities);
        level.setPlayerPosition(playerPosition);
        return level;
    }

    private static List<Asterisck> loadEnemies(Properties levelProperties) {
        Integer i = 1;
        List<Asterisck> enemies = new ArrayList<Asterisck>();
        while(levelProperties.getProperty("Asterisck" + i.toString()) != null) {
            Asterisck e = parseEnemie(levelProperties.getProperty("Asterisck" + i.toString()));
            enemies.add(e);
            i++;
        }
        return enemies;
    }

    private static Asterisck parseEnemie(String values) {
        final int X = 0;
        final int Y = 1;
        final int DIR = 2;
        final int SPEED = 3;
        final int STEPS = 4;
        String[] data = values.split(",");
        Asterisck Asterisck = new Asterisck();
        Asterisck.setX(Integer.parseInt(data[X]));
        Asterisck.setY(Integer.parseInt(data[Y]));
        Asterisck.speed(Integer.parseInt(data[SPEED]));
        Asterisck.setSteps(Integer.parseInt(data[STEPS]));
        switch(data[DIR]) {
            case "UP":
                Asterisck.setDirection(Entity.Direction.UP);
                break;
            case "DOWN":
                Asterisck.setDirection(Entity.Direction.DOWN);
                break;
            case "LEFT":
                Asterisck.setDirection(Entity.Direction.LEFT);
                break;
            case "RIGHT":
                Asterisck.setDirection(Entity.Direction.RIGHT);
                break;
            default:
                Asterisck.setDirection(Entity.Direction.NONE);
        }
        return Asterisck;
    }

    private Key parseKey(Properties prop) {
        final int X = 0;
        final int Y = 1;
        final int IMAGE = 2;
        String[] params = new String[3];
        int index = 0;
        if(prop.getProperty("key") != null) {
            try {
                String text = prop.getProperty("key");
                for (String param : text.split(",")) {
                    params[index] = param;
                    index++;
                }
            } catch (Exception e){
              throw new RuntimeException("expected key:int x, int y", e);
            }
        } else {
            throw new RuntimeException("properties has no 'key'");
        }
        Point position = new Point( Integer.parseInt(params[X]),
                Integer.parseInt(params[Y]));
        Key key = new Key();
        key.setPosition(position);
        return key;
    }

    private Point parsePlayerPosition(Properties prop) {
        int x, y;
        if(prop.getProperty("player") != null) {
            try {
                String text = prop.getProperty("player");
                String params[] = text.split(",");
                x = Integer.parseInt(params[0]);
                y = Integer.parseInt(params[1]);
            } catch (Exception e){
              throw new RuntimeException("expected player:int x,int y", e);
            }
        } else {
            x = 1;
            y = 1;
        }
        return new Point(x,y);
    }
}
