package stickman.level;

import java.util.*;
import stickman.files.*;
import stickman.entity.*;
import stickman.enemie.Enemie;

public class Level extends ReadFile
{
	public Image stage;
	public List<Enemie> enemies;

	public Level (Image newStage, List<Enemie> newEnemies) {
        stage = newStage;
        enemies = newEnemies;
	}
}
