public class testKey
{
	public static void main(String[] args)
	{
		RawConsoleInput in = new RawConsoleInput();
		try{
		while(true)	
			System.out.print((char)in.read(true));
		}
		catch(Exception ex)
		{;}
		finally{}
	}
}