package game.level;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuilder;
import game.files.*;

public class Scene extends ReadFile
{
	public List<char[][]> scenes = new ArrayList<char[][]>();
	private List<String> dialogs = new ArrayList<String>();
	private List<Integer> next_dialog = new ArrayList<Integer>();

	public boolean isOver = false;
	private int dialog_counter=0;
	private int scene_counter=1;
	private String scenesPath;

	public Scene(){
		isOver = true;
	}
	public Scene(String scenesPath)
	{
		this.init(scenesPath);
	}

	public void init(String path)
	{
		//loading settings
		this.loadSettings(path);
		/* scenesPath
		 * hacemos un split sobre path, lo convertimos a string quitando el ultimo indice
		 * ejemplo path= "assets/level_3/algo.propertie" scenesPath = "assets/level_3"
		 */
		StringBuilder builderPath = new StringBuilder();
		String[] text = path.split("/");
		for(int i=0; i<text.length-1; i++) {
		  builderPath.append(text[i]+"/");
		}
		this.scenesPath = builderPath.toString();
		scenes = loadScenes();
		dialogs = loadDialogs();
		next_dialog = syncDialogs();

	}

	private List<char[][]> loadScenes() {
		List<char[][]> scenesToLoad = new ArrayList<char[][]>();
		Integer i = 1;
		String sceneId = "scene" + i.toString();
		while( settings().getProperty(sceneId) != null )
		{
			String pathToScene = scenesPath + settings().getProperty(sceneId);
			scenesToLoad.add(this.loadText(pathToScene));
			i++;
			sceneId = "scene" + i.toString();
		}
		return scenesToLoad;
	}

	private List<String> loadDialogs() {
		List<String> dialogs = new ArrayList<String>();
		Integer i = 1;
		String dialogId = "dialog" + i.toString();
		while( settings().getProperty(dialogId) != null )
		{
			dialogs.add( settings().getProperty(dialogId));
			i++;
			dialogId = "dialog" + i.toString();
		}
		return dialogs;
	}

	private List<Integer> syncDialogs(){
		Integer i = 1;
		String key = "dialogs_scene"+ i.toString();
		List<Integer> nextDialog = new ArrayList<Integer>();
		while( settings().getProperty(key) != null )
		{
			int dialog = Integer.parseInt( settings().getProperty(key));
			nextDialog.add(dialog);
			i++;
			key = "dialogs_scene"+ i.toString();
		}
		return nextDialog;
	}

	public String getDialog(){
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
