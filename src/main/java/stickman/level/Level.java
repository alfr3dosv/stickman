package stickman.level;

import java.util.*;

import stickman.collision.CollisionDetector;
import stickman.entity.*;
import stickman.display.Display;
import stickman.player.*;
import stickman.enemy.*;
import stickman.resources.Resources;

public class Level
{
	public Image stage;
	public List<Entity> entities;

	public Level (Image newStage, List<Entity> newEntities) {
        stage = newStage;
        entities = newEntities;
	}

	public void play() {
		Player player = (Player) Resources.lookup("player");
        entities.add(player);
        Thread movementThread = new Thread(player.movement);
        movementThread.start();
        CollisionDetector cd = new CollisionDetector(stage, entities);
        Display display = new Display();
        while(true) {
		    cd.update();
			display.render(stage, entities);
			display.render(player.movement.debugText);
			display.print();
			display.sleep();
		}
	}

	private void updateEnemies() {
		for(Entity e : entities) {
			if(e instanceof Enemy) {
				((Enemy) e).update();
			}
		}
	}
}
