import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuilder;

public class Story extends FilesInput
{
	List<char[][]> scenes = new ArrayList<char[][]>();
	List<String> dialogs = new ArrayList<String>();
	List<Integer> next_dialog = new ArrayList<Integer>();

	public boolean isOver = false;
	//enemie
	final int X = 0;
	final int Y = 1;
	private int dialog_counter=0;
	private int scene_counter=1;
	private int scenes_total=1;
	private String storyPath;

	public Story(){

	}
	public Story(String storyPath)
	{
		this.init(storyPath);
	}
	public void addScene(String path)
	{
		scenes.add(this.loadText(path));
	}

	public void init(String path)
	{
		//loading settings
		this.loadSettings(path);
		/* storyPath
		 * hacemos un split sobre path, lo convertimos a string quitando el ultimo indice 
		 * ejemplo path= "assets/level_3/algo.propertie" storyPath = "assets/level_3" 
		 */
		StringBuilder builderPath = new StringBuilder();
		String[] text = path.split("/"); 
		for(int i=0; i<text.length-1; i++) {
		  builderPath.append(text[i]+"/");
		}
		this.storyPath = builderPath.toString();
		//scenes
		int s = 1;
		while( settings().getProperty("scene"+Integer.toString(s)) != null )
		{
			this.addScene( storyPath+settings().getProperty("scene"+Integer.toString(s)) );
			s++;
		}
		//dialogs
		int d = 1;
		while( settings().getProperty("dialog"+Integer.toString(d)) != null )
		{
			this.addDialog( settings().getProperty("dialog"+Integer.toString(d)) );
			d++;
		}
		//dialogs by scene
		int dByS = 1;
		while( settings().getProperty("dialogs_scene"+Integer.toString(dByS)) != null )
		{
			this.syncDialogs( Integer.parseInt( settings().getProperty("dialogs_scene"+Integer.toString(dByS)) ) );
			dByS++;
		}
	}
	public void syncDialogs(int step){
		next_dialog.add(step);
	}
	public void addDialog(String dialog)
	{
		this.dialogs.add(dialog);
	}
	public String getDialog(){
		String output="";
		if( (next_dialog.size() < 1) || 
			(dialog_counter >= dialogs.size()) )
		{
			isOver=true;
		}
		else if( (dialog_counter >= next_dialog.get(0)) && 
				 (next_dialog.size()>=1) ) 
		//se leyeron todos los dialogos de esta escena
		{ 
			next_dialog.remove(0);
			output = ""; //reseteanos los dialogos, drawDialog() recibe ""
			if(scenes.size() > 1)
				scenes.remove(0);
		}
		else if( dialog_counter < dialogs.size() )
			output = dialogs.get(dialog_counter++);
		return output;
	}
}