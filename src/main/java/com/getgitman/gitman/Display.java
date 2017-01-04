package com.getgitman.gitman;
import java.io.*;
import java.lang.StringBuilder;

public class Display
{
    public static final int SIZE_X = 70;
    public static final int SIZE_Y = 20;
    private final int WAIT_PER_FRAME = 50; 
    Displayable displayable;
    private char[][] frame;
    private long start_time;
    private StringBuilder dialogs;
    private GameInput gameInput;
    private boolean isOver = false;

    public Display(Displayable displayable, GameInput gameInput)
    {
        this.displayable = displayable;
        this.gameInput = gameInput;
    }

    public char[][] getFrame()
    {
        char[][] frame = new char[SIZE_Y][SIZE_X];
        //deep copy del framae actual
        for(int y=0; y<SIZE_Y; y++){
            for(int x=0; x<SIZE_X; x++){
                frame[y][x]=this.frame[y][x];
            }
        }
        return frame;
    }

    public void update()
    {
        if( (System.currentTimeMillis() - start_time) > WAIT_PER_FRAME )
        {       
            this.draw();
            start_time = System.currentTimeMillis();        
        }
    }
    public void draw()
    {
        this.clean();
        this.frame = displayable.getImage();
        if(this.frame == null){
            isOver = true;
        }
        else {
            drawEntitys();
            drawDialogs( displayable.getDialogs() );
            StringBuilder newFrame = new StringBuilder();
            for(int y=0; y<SIZE_Y; y++){
                newFrame.append(frame[y]);
                newFrame.append("\n");
            }
            newFrame.append(dialogs);
            //impresion
            System.out.print(newFrame.toString());
            if(dialogs != null)
            {
                gameInput.rawRead(true);
                Game.sleep(500);
            }
        }
        displayable.update();
    }
    public void draw(char[][] objectToDisplay, int y, int x)
    {
        char[][] before = new char[objectToDisplay.length][objectToDisplay[0].length];
        for(int pos_y=0; pos_y<objectToDisplay.length; pos_y++)
        {
            for(int pos_x=0; pos_x<objectToDisplay[0].length; pos_x++){
                before[pos_y][pos_x] = frame[y+pos_y][x+pos_x];
                frame[y+pos_y][x+pos_x]=objectToDisplay[pos_y][pos_x];
            }   
                // inveritdo frame[SIZE_Y-(y+pos_y)-1][SIZE_X-(x+pos_x)-1]=objectToDisplay[pos_y][pos_x];
        }
    }
    public void drawEntitys()
    {
        if( displayable.getEntitys() !=null ){
            for(Entity entity : displayable.getEntitys() ){
                this.draw( entity.img.get(), entity.getY(), entity.getX() );
            }
        }
    }
    public void drawDialogs(String dialog)
    { 
        dialogs = new StringBuilder();
        String dlgs = displayable.getDialogs();
        dialogs.append( dlgs );
        if(dlgs == null){
            dialogs = null;
        }
    }
    public static void clean()
    {
        /* 
         * Devuelve el cursor a la parte superior
         */
        String os = System.getProperty("os.name").toLowerCase();
        //Windows
        if (os.indexOf("win") >= 0){
            try{
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            catch(IOException e){
                //es muy noche
            }
            catch(InterruptedException e){
                //es muy noche              
            }
        }
        // Linux, Mac ANSI ESCAPES
        else{
            System.out.print("\033[2J\033[;H");
            System.out.flush();
        }
    }
    public boolean isOver()
    {
        return isOver;
    }
}