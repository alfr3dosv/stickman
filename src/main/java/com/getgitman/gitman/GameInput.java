package com.getgitman.gitman;
import java.util.*;
import java.io.*;

public class GameInput implements Runnable 
{
    private final int NEXT_KEY=20;  // intervalo de tiempo entre teclas
    private boolean CAPTURE_INPUT=true;
    private int times=0;
    private RawConsoleInput rawInput = new RawConsoleInput();
    private volatile char key;
    private char last_key;
    private long start_time;    // contador de tiempo entre teclas
    public GameInput()
    {

    }
    public void run()
    {
        while(CAPTURE_INPUT)
        {
            captureKey();
            try
            {
                rawInput.resetConsoleMode();
                System.out.flush();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            rawInput.resetConsoleMode();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void interrupt()
    {
        this.CAPTURE_INPUT = false;
    }

    public char captureKey()
    {
        key = 'f';
        try 
        {
            if( (System.currentTimeMillis() - start_time) > NEXT_KEY)
            {
                key = (char)rawInput.read(false);
                start_time = System.currentTimeMillis();
            }   
        } 
        catch(IOException ex)
        {
            key='f';
        }  
        finally
        {
            return key;
        }
    } 

    public char getKey()
    {
        return this.key;
    }

    public int rawRead(boolean wait)
    {
        int keyCode = 0;
        try
        {
            keyCode = rawInput.read(wait);
            System.out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return keyCode;
    }
}