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
    // 
	// public String getDialog() {
	// 	String output="";
	// 	if(!hasDialogs()) {
	// 		isOver=true;
	// 	}
	// 	else if(!sceneHasDialogs()) {
	// 		next_dialog.remove(0);
	// 		output = "";
	// 		if(scenes.size() > 1)
	// 			scenes.remove(0);
	// 	}
	// 	else if(sceneHasDialogs()) {
	// 		output = dialogs.get(dialog_counter++);
	// 	}
	// 	return output;
	// }
    //
	// private boolean hasDialogs () {
	// 	return !((next_dialog.size() < 1) || dialog_counter >= dialogs.size());
	// }
    //
	// private boolean sceneHasDialogs() {
	// 	return !(dialog_counter >= next_dialog.get(0) && next_dialog.size()>=1);
	// }
}
