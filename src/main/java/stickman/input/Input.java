
package stickman.input;
import stickman.entity.Entity;
import stickman.RawConsoleInput;
import java.io.*;

public class Input {
    private static int omitTheSameKeyCounter=0;
    private static RawConsoleInput in = new RawConsoleInput();;
    private static char lastKey;
    private static long elapsedTimeBetweenKeys;// contador de tiempo entre teclas
    private static final int WAIT_PER_KEY = 20;//intervalo de tiempo entre teclas
    private static final int TIMES_OMITTED_THE_SAME_KEY = 2;
    public static final char DEFAULT_KEY = 'f';
    public static boolean stop = false;

    public static char waitKeyPress() {
        char key = readUntilKeyPress();
        return key;
    }

    public static char getKey() {
        char key = DEFAULT_KEY;
        if( (System.currentTimeMillis() - elapsedTimeBetweenKeys) >
            WAIT_PER_KEY)
        {
            key = read();
            elapsedTimeBetweenKeys = System.currentTimeMillis();
        }
        return key;
    }

    private static boolean isTheSameKey(char newKey) {
        if( (lastKey != newKey) ||
           (omitTheSameKeyCounter > TIMES_OMITTED_THE_SAME_KEY) )
        {
            lastKey = newKey;
            omitTheSameKeyCounter = 0;
            return false;
        }
        else {
            omitTheSameKeyCounter++;
            return true;
        }
    }

    private static char read() {
        char key = DEFAULT_KEY;
        try {
            key = (char)in.read(false);
            if(isTheSameKey(key))
                key = DEFAULT_KEY;
        } catch(IOException ex) {
            stop = true;
            throw new RuntimeException("[Input] No puede leer las teclas");
        } finally {
            return key;
        }
    }

    private static char readUntilKeyPress() {
        char key = DEFAULT_KEY;
        try {
            key = (char)in.read(true);
        } catch(IOException ex) {
            stop = true;
            throw new RuntimeException("[Input] No puede leer las teclas");
        } finally {
            return key;
        }
    }
}
