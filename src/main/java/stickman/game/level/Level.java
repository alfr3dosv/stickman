package stickman.game.level;

import java.util.*;

import stickman.core.entity.Point;
import stickman.game.enemy.Asterisck;
import stickman.core.collision.CollisionDetector;
import stickman.core.entity.Entity;
import stickman.core.entity.Image;
import stickman.core.display.Display;
import stickman.game.player.*;
import stickman.core.loader.Loader;

public class Level
{
	private Image stage;
	private List<Entity> entities;
	private Point startPoint;

	public Level (Image newStage, List<Entity> newEntities) {
        stage = newStage;
        entities = newEntities;
	}

	public void setPlayerPosition(Point position) {
		startPoint = position;
	}

	public void play() {
		Player player = (Player) Loader.lookup("player");
		player.reset();
		player.setPosition(startPoint);
        entities.add(player);
        Thread movementThread = new Thread(player);
        movementThread.start();
        CollisionDetector cd = new CollisionDetector(stage, entities);
        Display display = new Display();

        while(!player.hasKey()) {
        	updateEnemies();
		    cd.update();
			display.render(stage, entities);
			display.print();
			display.sleep();
			if( !player.isAlive() ) {
			    Display.clean();
				System.out.println(Loader.lookup("banner/dead"));
				Display.sleep(1000);
				player.reset();
				player.setPosition(startPoint);
				display.sleep(1000);
			}
		}
	}

	private void updateEnemies() {
		for(Entity e : entities) {
			if(e instanceof Asterisck) {
				((Asterisck) e).update();
			}
		}
	}
}
