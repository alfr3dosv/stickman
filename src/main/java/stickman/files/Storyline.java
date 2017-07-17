package stickman.files;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;
import stickman.level.*;

public class Storyline {
    private Properties storylineFile;
    private List<Object> storyline = new ArrayList<Object>();

    public Storyline(String path) {
        storylineFile = ReadFile.loadProperties(path);
        for (String step:storylineFile.getProperty("storyline").split(",")) {
            String fileName = storylineFile.getProperty(step);
            if(step.contains("level")) {
                storyline.add(new Level(getFullPath(fileName)));
            }
            else if(step.contains("story")) {
                storyline.add(new Scene(getFullPath(fileName)));
            }
        }
    }

    private String getFullPath(String fileName) {
        return "/assets/" + fileName + "/" + fileName + ".properties";
    }

    public Object getNext() {
        if(storyline.size() > 0)
            return storyline.remove(0);
        else
            return null;
    }
}
