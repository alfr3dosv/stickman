package stickman.resources;

import java.util.*;
import stickman.entity.*;
import stickman.level.*;
import stickman.enemie.*;

public class Resources
{
    public static Object lookup(String key) {
        String type = key.split("/")[0];
        String name = key.split("/")[1];
        Object resource = null;
        if(type.equals("level"))
            resource = (Object) LoadLevel.load(name);
        else if(type.equals("scene"))
            resource = (Object) LoadScene.load(name);
        return resource;
    }
}
