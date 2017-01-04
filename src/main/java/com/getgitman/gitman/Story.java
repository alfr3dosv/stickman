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

        /* storyPath
         * hace un split sobre path, lo convierte a String quitando el ultimo indice 
         * ejemplo path= "assets/level_3/algo.propertie" storyPath = "assets/level_3" 
         */
		this.loadSettings(path);
		StringBuilder builderPath = new StringBuilder();
		String[] text = path.split("/");
		for(int i=0; i<text.length-1; i++)
        {
		  builderPath.append(text[i]+"/");
		}
		this.storyPath = builderPath.toString();
		int scenes = 1;   // numero escenas 
		while( settings().getProperty("scene"+Integer.toString(scenes)) != null )
		{
			this.addScene( storyPath+settings().getProperty("scene"+Integer.toString(scenes)) );
			scenes++;
		}
		
		int dlgs = 1; // numero de dialogos
		while( settings().getProperty("dialog"+Integer.toString(dlgs)) != null )
		{
			this.addDialog( settings().getProperty("dialog"+Integer.toString(dlgs) ));
			dlgs++;
		}
		int dByS = 1; // dialogs por escenas
		while( settings().getProperty("dialogs_scene"+Integer.toString(dByS)) != null )
		{
			this.syncDialogs( Integer.parseInt( settings().getProperty("dialogs_scene"+Integer.toString(dByS)) ) );
			dByS++;
		}
	}

	public void syncDialogs(int step)
    {
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
        //se leyeron todos los dialogos de esta escena
        else if( (dialog_counter >= next_dialog.get(0)) && (next_dialog.size()>=1) ) 
        { 
            next_dialog.remove(0);
            currentDialog="";   // reseteanos los dialogos, drawDialog() recibe ""
            dialog = "";
            if(scenes.size() > 1)
            {
                scenes.remove(0);
            }
        }
        else if( dialog_counter < dialogs.size() )
        {
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
        for(int y=0; y<Display.SIZE_Y; y++)
        {
            for(int x=0; x<Display.SIZE_X; x++)
            {
                image[y][x]=this.scenes.get(0)[y][x];              
            }
        }
        if(isOver)
        {
            image = null;
        }
        return image;
    }

    public List<Entity> getEntitys()
    {
        return null;
    }
}