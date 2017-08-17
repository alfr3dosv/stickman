package stickman.player;

import java.util.*;
import java.io.*;
import stickman.entity.Entity;
import stickman.RawConsoleInput;
import stickman.input.Input;
import stickman.entity.*;
import stickman.collision.*;

public class Player extends Entity implements CollisionDetector.Hittable
{
	private boolean STAGE_KEY = false;
	Movement movement;

	public Player(Image imageOfPlayer)	{
		img = imageOfPlayer;
		init();
		movement = new Movement(this);
	}

	public void init() {
		setX(0);
		setY(0);
		setAlive();
		STAGE_KEY = false;
	}

    @Override
    public void onHit(Object who) {
        if (who instanceof Enemie) {

        } else if (who instanceof ) {

        }
    }

    public boolean hasKey() {
		return this.STAGE_KEY;
	}

	public void setKey(boolean hasKey) {
		this.STAGE_KEY = hasKey;
	}
}
