package stickman.core.loader;

public class Loader {
    private static LoadLevel loadLevel = new LoadLevel();
    private static LoadScene loadScene = new LoadScene();
    private static LoadPlayer loadPlayer = new LoadPlayer();

    public static Object lookup(String key) {
        String[] s = key.split("/");
        String type = s[0];
        String name = s.length > 1 ? s[1] : "";
        Object resource = null;

        if (type.equals("level"))
            resource = (Object) loadLevel.load(name);
        else if (type.equals("scene"))
            resource = (Object) loadScene.load(name);
        else if (type.equals("banner"))
            resource = (Object) ReadFile.loadImage("/" + key + ".txt");
        else if (type.equals("test"))
            resource = (Object) ReadFile.loadImage("/" + key + ".txt");
        else if (type.equals("player"))
            resource = (Object) loadPlayer.load();
        return resource;
    }
}