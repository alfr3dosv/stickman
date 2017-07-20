package stickman.level;

import java.util.*;

import stickman.entity.*;
import stickman.enemie.Enemie;

public class Level
{
	public Image stage;
	public List<Enemie> enemies;

	public Level (Image newStage, List<Enemie> newEnemies) {
        stage = newStage;
        enemies = newEnemies;
	}

	public void play() {
		;
	}
}
