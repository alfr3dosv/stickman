package stickman.files;

import java.util.List;
import java.util.HashMap;
import stickman.files.ReadFile;
import java.lang.StringBuilder;

public class Banner {
    private static HashMap<String, char[][]> resources = new HashMap<String, char[][]>();
    public static void print(String name) {
        String key = getKeyByName(name);
        if(!resources.containsKey(key)) {
            read(name);
        }
        char[][] banner = resources.get(key);
        StringBuilder text = new StringBuilder();
        for(char[] line : banner) {
                text.append(line);
                text.append("\n");
        }
        System.out.println(text);
    }

    public static void read(String name) {
        String key = getKeyByName(name);
        char[][] banner = ReadFile.loadText(key);
        if(banner == null) {
            System.out.println("KEY " + key);
            System.out.println("BANNER IS NULL");
        }
        resources.put(key, banner);
    }

    private static String getKeyByName(String name) {
        return "/banners/" + name + ".txt";
    }
}
