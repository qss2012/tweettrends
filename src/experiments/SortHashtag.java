package experiments;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

public class SortHashtag {
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("src/resources/forLDA/hashtags_Gillard&Rudd"));
			FileWriter fstream = new FileWriter("src/resources/hashtags/sorted_hashtags_Gillard&Rudd");
			BufferedWriter out = new BufferedWriter(fstream);
			
			TreeMap<String, Integer> tm=new TreeMap<String, Integer>();
			
		    String word = br.readLine();
		    while(word != null)
		    {
		    	word=word.trim();
		    	tm.put(word,1);
		    	word = br.readLine();            	  
		    }
		    br.close();
		    Collection<String> c=tm.keySet();
			Iterator<String> it=c.iterator();
			
			while(it.hasNext())
			{
				out.write(it.next()+"\n");
			}
			out.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
	}

}
