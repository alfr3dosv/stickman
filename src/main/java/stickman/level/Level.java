package stickman.level;

import java.util.*;

import stickman.entity.*;
import stickman.display.Display;
import stickman.player.Player;
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
		Display display = new Display();
		Player player = (Player) Resources.lookup("player");
		entities.add(player);
		while(true) {
			display.render(stage, entities);
			display.print();
			display.sleep();
		}
	}

}
