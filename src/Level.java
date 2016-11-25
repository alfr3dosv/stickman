import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Level 
{
	List<char[][]> stages = new ArrayList<char[][]>();
	List<char[][]> assets = new ArrayList<char[][]>();
	public char[][] load(String path)
	{
		char[][] resource;
		List<String> lines = new ArrayList();

		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			lines = stream.collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println("ERR: Not found file");
			e.printStackTrace();
		}
		if(lines == null)
				System.out.println("ERR: Not found file");
		resource = new char[lines.size()][lines.get(0).toCharArray().length];
		for(int i=0; i< resource.length; i++)
		{
			String s = lines.remove(0);
			resource[i] = s.toCharArray();
		}
		return resource;
	}
	public void loadStage(String path)
	{
		stages.add(load(path));
	}

	public void loadAsset(String path)
	{
		assets.add(load(path));
	}
}