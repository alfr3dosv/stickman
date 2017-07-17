package stickman.files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ReadFile
{
	public static char[][] loadText(String path) {
		char[][] resource;
		List<String> lines = new ArrayList();
		try ( BufferedReader in = new BufferedReader(
			  new InputStreamReader(getInputStream(path))))
		{
			String line;
			while ((line = in.readLine()) != null)
				lines.add(line);
			in.close();
		} catch (IOException e) {
			System.out.println("ERR: Not found file");
			e.printStackTrace();
		}
		resource = new char[lines.size()][lines.get(0).toCharArray().length];
		for(int i=0; i< resource.length; i++) {
			String s = lines.remove(0);
			resource[i] = s.toCharArray();
		}
		return resource;
	}

	public static Properties loadProperties(String path) {
		Properties prop = new Properties();
		try( InputStream is = getInputStream(path)) {
			prop.load(getInputStream(path));
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		finally {
			return prop;
		}
	}

	static InputStream getInputStream(String path) {
		return ReadFile.class.getClass().getResourceAsStream(path);
	}
}
