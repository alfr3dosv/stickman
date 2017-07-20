package stickman.resources;

import java.util.*;
import stickman.entity.*;
import stickman.level.*;
import stickman.enemie.*;

public class Resources
{
    public static Object lookup(String key) {
        String type = key.split("/")[0];
        String name = key.split("/")[0];
        Object resource = null;
        if(key.equals("level"))
            resource = (Object) LoadLevel.load(name);
        else if(key.equals("scene"))
            resource = (Object) LoadLevel.load(name);
        return resource;
    }
}
