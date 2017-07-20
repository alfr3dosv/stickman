package stickman.files;

import java.util.*;
import stickman.level.*;
import stickman.files.ReadFile;

public class Storyline {
    private List<String> storyline;
    int last = 0;

    public Storyline(String path) {
        try {
            Properties properties = ReadFile.loadProperties(path);
            for (String step : properties.getProperty("storyline").split(",")) {
                String folderName = properties.getProperty(step);
                if (step.contains("level")) {
                    storyline.add(last++, "level/" + folderName);
                } else if (step.contains("scene")) {
                    storyline.add(last++, "scene/" + folderName);
                }
            }
        } catch(Exception e) {
            throw new RuntimeException("Cannot parse storyline", e);
        }

    }

    private String getFullPat(String fileName) {
        return "/assets/" + fileName + "/" + fileName + ".properties";
    }

    public Object getNext() {
        if(storyline.size() > 0)
            return storyline.remove(0);
        else
            return null;
    }
}
