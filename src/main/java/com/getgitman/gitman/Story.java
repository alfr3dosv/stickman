package com.getgitman.gitman;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.StringBuilder;

public class Story extends FilesInput implements Displayable
{
	public List<char[][]> scenes = new ArrayList<char[][]>();
	private List<String> dialogs = new ArrayList<String>();
	private List<Integer> next_dialog = new ArrayList<Integer>();

	public boolean isOver = false;
	private int dialog_counter=0;
	private int scene_counter=1;
	private String storyPath;
    private String currentDialog = "";

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
    
    public void update()
    {
        String dialog=null;
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
            //reseteanos los dialogos, drawDialog() recibe ""
            currentDialog="";
            dialog = "";
            if(scenes.size() > 1)
                scenes.remove(0);
        }
        else if( dialog_counter < dialogs.size() ){
            dialog = dialogs.get(dialog_counter++);
        }
        currentDialog+="\n";
        currentDialog+=dialog;  
    }

    public String getDialogs()
    {
        return currentDialog;
    }
    
    public char[][] getImage()
    {
        char[][] image = new char[Display.SIZE_Y][Display.SIZE_X];
        for(int y=0; y<Display.SIZE_Y; y++){
            for(int x=0; x<Display.SIZE_X; x++){
                image[y][x]=this.scenes.get(0)[y][x];              
            }
        }
        if(isOver){
            image = null;
        }
        return image;
    }
    public List<Entity> getEntitys(){
        return null;
    }
}