/*
Mostrar informacion de los comandos de git
Guadar los datos del jugador pasar de nivel usando branch
Repositorios para cada mundo

*/
package com.getgitman.gitman.git;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class GitCommandLine 
{
    private JSONParser parser = new JSONParser();
	private JSONObject data = new JSONObject();
    private JSONArray commands = new JSONArray();
    
    public GitCommandLine(String path)
	{
        loadCommands(path);
    }

    private loadCommands(String path)
    {
		String file = new Scanner(new File("path")).useDelimiter("\\Z").next();
        data = parser.parse(file);
        commands = (JSONArray)obj;
    }

    public String interpreter(String[] args)
    {
        String msg = null;
        if( args[0].equals("git") ) {
            if( args[1] == null ){
                //regresa todos los comandos disponibles
                msg = "wip";
            }
        }
        else{
            msg = "Error";
        }
    }
    return msg;
}
