package stickman.resources;

import java.util.*;
import stickman.entity.*;
import stickman.level.*;
import stickman.files.ReadFile;

public class LoadScene
{
    private static final String scenesPath = "/scene/";
    private static String pathToFolder;
    private static Properties properties;
    private static HashMap <Image, List<String>> dialogsPerScene;

    public static Scene load(String folderName) {
        pathToFolder = scenesPath + folderName;
        properties = ReadFile.loadProperties(pathToFolder + "/" + folderName + ".properties");
        dialogsPerScene = getDialogsPerScene();
        return new Scene(dialogsPerScene);
    }

    private static HashMap <Image, List<String>> getDialogsPerScene() {
        List<Image> scenes = loadScenes();
        List<String> dialogs;
        HashMap <Image, List<String>> dialogsPerScene = new HashMap<Image, List<String>>();

        Integer i = 1;
        int begin = 0;
        String key = "dialogs_scene"+ i.toString();
        while(properties.getProperty(key) != null) {
            int end = Integer.parseInt(properties.getProperty(key)) - 1;
            dialogs = loadDialogs(begin, end);
            dialogsPerScene.put(scenes.get(i - 1), dialogs);
            begin = end;
            key = "dialogs_scene" + (++i).toString();
        }
        return dialogsPerScene;
    }

    private static List<Image> loadScenes() {
        List<Image> scenes = new ArrayList<Image>();
        Integer i = 1;
        String sceneId = "scene" + i.toString();
        while(properties.getProperty(sceneId) != null) {
            String pathToScene = pathToFolder + "/" + properties.getProperty(sceneId);
            scenes.add(ReadFile.loadImage(pathToScene));
            i++;
            sceneId = "scene" + i.toString();
        }
        return scenes;
    }

    private static List<String> loadDialogs(int begin, int end) {
        List<String> dialogs = new ArrayList<String>();
        Integer i = begin;
        String dialogId = "dialog" + i.toString();
        while((properties.getProperty(dialogId) != null)) {
            dialogs.add(properties.getProperty(dialogId));
            i++;
            dialogId = "dialog" + i.toString();
        }
        return dialogs;
    }
}
