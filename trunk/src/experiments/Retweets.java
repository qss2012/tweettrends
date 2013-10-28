package experiments;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Retweets {
	
	public HashMap<String,Integer> rt;
	
	public Retweets()
	{
		try
		{
			BufferedReader brd = new BufferedReader(new FileReader("src/resources/retweets.txt"));
			String line = brd.readLine();
			System.out.println("********************"+line);
			while(line!=null)
			{
				line=line.trim();
				System.out.println("********************"+line);
				if(rt.containsKey(line))
				{
					int t=rt.get(line);
					t++;
					rt.put(line, new Integer(t));
				}
				else
				{
					rt.put(line, new Integer(1));
				}
				line=brd.readLine();
			}
			brd.close();
			System.out.println("Size of HashMap for retweets= "+rt.size());
			System.exit(0);
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
        
	}

}
