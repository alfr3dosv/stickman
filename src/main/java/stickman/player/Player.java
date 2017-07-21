package stickman.player;

import java.util.*;
import java.io.*;
import stickman.entity.Entity;
import stickman.RawConsoleInput;
import stickman.input.Input;
import stickman.entity.*;

public class Player extends Entity
{
	private boolean STAGE_KEY = false;

	public Player(Image imageOfPlayer)	{
		img = imageOfPlayer;
		init();
	}

	public void init() {
		setX(0);
		setY(0);
		setAlive();
		STAGE_KEY = false;
	}

	public boolean hasKey() {
		return this.STAGE_KEY;
	}

	public void setKey(boolean hasKey) {
		this.STAGE_KEY = hasKey;
	}
}
