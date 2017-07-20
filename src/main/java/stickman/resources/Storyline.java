package stickman.resources;

import java.util.*;

public class Storyline {
    private List<String> storyline;
    int last = 0;

    public Storyline(String path) {
        storyline = new ArrayList<>();
        try {
            Properties properties = ReadFile.loadProperties(path);
            for (String step : properties.getProperty("storyline").split(",")) {
                String key = properties.getProperty(step);
                if(key == null)
                    throw new RuntimeException("Cannot find level or scene named " + step);
                storyline.add(key);
            }
        } catch(Exception e) {
            throw new RuntimeException("Cannot parse storyline", e);
        }

    }

    public String getNext() {
        if(storyline.size() > 0)
            return storyline.remove(0);
        else
            return "";
    }
}
