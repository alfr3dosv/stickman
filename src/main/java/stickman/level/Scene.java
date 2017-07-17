package stickman.level;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuilder;
import stickman.files.*;

public class Scene
{
	public List<char[][]> scenes = new ArrayList<char[][]>();
	private List<String> dialogs = new ArrayList<String>();
	private List<Integer> next_dialog = new ArrayList<Integer>();

	public boolean isOver = false;
	private int dialog_counter=0;
	private int scene_counter=1;
	private String rootPath;
	private Properties sceneProperties;

	public Scene() {
		isOver = true;
	}

	public Scene(String pathOfFile) {
		sceneProperties = ReadFile.loadProperties(pathOfFile);
		rootPath = getRootPath(pathOfFile);
		scenes = loadScenes();
		dialogs = loadDialogs();
		next_dialog = syncDialogs();
	}

	public String getRootPath(String pathOfFile) {
		StringBuilder rootPath = new StringBuilder();
		String[] text = pathOfFile.split("/");
		for(int i=0; i<text.length-1; i++) {
		  rootPath.append(text[i]+"/");
		}
		return rootPath.toString();
	}

	private List<char[][]> loadScenes() {
		List<char[][]> scenesToLoad = new ArrayList<char[][]>();
		Integer i = 1;
		String sceneId = "scene" + i.toString();
		while(sceneProperties.getProperty(sceneId) != null) {
			String pathToScene = rootPath + sceneProperties.getProperty(sceneId);
			scenesToLoad.add(ReadFile.loadText(pathToScene));
			i++;
			sceneId = "scene" + i.toString();
		}
		return scenesToLoad;
	}

	private List<String> loadDialogs() {
		List<String> dialogs = new ArrayList<String>();
		Integer i = 1;
		String dialogId = "dialog" + i.toString();
		while(sceneProperties.getProperty(dialogId) != null) {
			dialogs.add( sceneProperties.getProperty(dialogId));
			i++;
			dialogId = "dialog" + i.toString();
		}
		return dialogs;
	}

	private List<Integer> syncDialogs(){
		Integer i = 1;
		String key = "dialogs_scene"+ i.toString();
		List<Integer> nextDialog = new ArrayList<Integer>();
		while(sceneProperties.getProperty(key) != null) {
			int dialog = Integer.parseInt( sceneProperties.getProperty(key));
			nextDialog.add(dialog);
			i++;
			key = "dialogs_scene"+ i.toString();
		}
		return nextDialog;
	}

	public String getDialog() {
		String output="";
		if(!hasDialogs()) {
			isOver=true;
		}
		else if(!sceneHasDialogs()) {
			next_dialog.remove(0);
			output = "";
			if(scenes.size() > 1)
				scenes.remove(0);
		}
		else if(sceneHasDialogs()) {
			output = dialogs.get(dialog_counter++);
		}
		return output;
	}

	private boolean hasDialogs () {
		return !((next_dialog.size() < 1) || dialog_counter >= dialogs.size());
	}

	private boolean sceneHasDialogs() {
		return !(dialog_counter >= next_dialog.get(0) && next_dialog.size()>=1);
	}
}
