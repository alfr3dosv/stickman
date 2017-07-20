package stickman.level;

import java.util.*;
import stickman.entity.*;

public class Scene
{
	public List<char[][]> scenes = new ArrayList<char[][]>();
	private List<String> dialogs = new ArrayList<String>();
	private List<Integer> next_dialog = new ArrayList<Integer>();
	public boolean isOver = false;

	public Scene(HashMap<Image, List<String>> scenesAndDialogs) {
        HashMap <Image, List<String>> dialogsPerScene = scenesAndDialogs;
		isOver = false;
	}

	public void play() {
		;
	}
}
