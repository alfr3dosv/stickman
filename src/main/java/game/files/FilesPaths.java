package game.files;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesPaths {
    private Properties storylineFile;
    private List<String> levelsFilesPaths;
    private List<String> scenesFilesPaths;

    public FilesPaths(String path) {
        storylineFile = readStorylineFile(path);
        levelsFilesPaths = readLevelsFilesPaths();
        scenesFilesPaths = readScenesFilesPaths();
    }

    private Properties readStorylineFile(String path) {
        Properties file = ReadFile.loadProperties(path);
        return file;
    }

    private List<String> readScenesFilesPaths() {
        List<String> filesPaths = new ArrayList<String>();
        for(int i = 1;
        storylineFile.getProperty("story"+Integer.toString(i)) != null; i++)
        {
            String path = storylineFile.getProperty("story"+Integer.toString(i));
            filesPaths.add(path);
        }
        return filesPaths;
    }

    private List<String> readLevelsFilesPaths() {
        List<String> filesPaths = new ArrayList<String>();
        for(int i = 1;
        storylineFile.getProperty("level"+Integer.toString(i)) != null; i++)
        {
            String path = storylineFile.getProperty("level"+Integer.toString(i));
            filesPaths.add(path);
        }
        return filesPaths;
    }

    public Properties getStorylineFile() {
        return storylineFile;
    }

    public List<String> getLevelsFilesPaths() {
        return levelsFilesPaths;
    }

    public List<String> getScenesFilesPaths() {
        return scenesFilesPaths;
    }
}
