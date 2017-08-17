package stickman.level;

import java.util.*;
import stickman.entity.*;
import stickman.display.Display;
import stickman.player.*;
import stickman.enemie.*;
import stickman.resources.Resources;

public class Level
{
	public Image stage;
	public List<Entity> entities;
	public c

	public Level (Image newStage, List<Entity> newEntities) {
        stage = newStage;
        entities = newEntities;
	}

	public void play() {
		Player player = (Player) Resources.lookup("player");
		Movement movement = new Movement(player);
		Thread movementThread = new Thread(movement);
		movementThread.start();
		Display display = new Display();
		entities.add(player);
		while(true) {
			display.render(stage, entities);
			display.print();
			display.sleep();
		}
	}

	private void updateEnemies() {
		for(Entity e : entities) {
			if(e instanceof Enemie) {
				((Enemie) e).update();
				collisions.
			}
		}
	}
}
