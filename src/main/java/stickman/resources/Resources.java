package stickman.resources;

public class Resources
{
    public static Object lookup(String key) {
        String[] s = key.split("/");
        String type = s[0];
        String name = s.length > 1 ? s[1] : "";
        Object resource = null;

        if(type.equals("level"))
            resource = (Object) LoadLevel.load(name);
        else if(type.equals("scene"))
            resource = (Object) LoadScene.load(name);
        else if(type.equals("banner"))
            resource = (Object) ReadFile.loadImage("/" + key + ".txt");
        else if(type.equals("test"))
            resource = (Object) ReadFile.loadImage("/" + key + ".txt");
        else if(type.equals("player"))
            resource = (Object) LoadPlayer.load();
        return resource;
    }
}
