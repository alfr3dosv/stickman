package game.files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ReadFile
{
	//file
	private Properties settings = new Properties();
	private InputStream input = null;

	public char[][] loadText(String path)
	{
		char[][] resource;
		List<String> lines = new ArrayList();

		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			lines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println("ERR: Not found file");
			e.printStackTrace();
		}
		resource = new char[lines.size()][lines.get(0).toCharArray().length];
		for(int i=0; i< resource.length; i++)
		{
			String s = lines.remove(0);
			resource[i] = s.toCharArray();
		}
		return resource;
	}
	public static Properties loadProperties(String path)
	{
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(path);
			prop.load(input);
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (input != null) {
				try {
					input.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			return prop;
		}
	}
	public void loadSettings(String settingsPath)
	{
		settings( loadProperties(settingsPath) );
	} 
	//getters
	public Properties settings(){ return this.settings;}

	//setters
	public void settings(Properties sttgs){this.settings = sttgs;}
}