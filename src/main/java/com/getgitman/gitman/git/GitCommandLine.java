/*
Mostrar informacion de los comandos de git
Guadar los datos del jugador pasar de nivel usando branch
Repositorios para cada mundo

*/
package com.getgitman.gitman.git;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class GitCommandLine 
{
    private JSONParser parser = new JSONParser();
	private Object data = new JSONObject();
    private JSONArray commands = new JSONArray();
    
    public GitCommandLine(String path)
	{
        loadCommands(path);
    }

    private void loadCommands(String path)
    {
        String file = null;
        try{
            System.out.println("PATH:"+path);
            file = new Scanner(new File(path)).useDelimiter("\\Z").next();
            data = parser.parse(file);
        } 
        catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
            e.printStackTrace();
        }
        catch(org.json.simple.parser.ParseException e){
            System.out.println("Error al parsear");
            e.printStackTrace();            
        }
        finally{
            commands = (JSONArray)data;
        }
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
        return msg;
    }
}
