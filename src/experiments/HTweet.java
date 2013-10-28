package experiments;

import java.io.Serializable;
import java.util.*;

public class HTweet implements Serializable{
	
	public	String text;
	public HashMap<String,Float> wordmap;
	public String tag;
	
	public HTweet(String tag,String text)
	{
		this.tag=tag;
		this.text=text;
		wordmap = new HashMap<String,Float>();
		populateMap();
		normalize();
	}
	void populateMap()
	{
		String token[] = text.split(" ");
		int l= token.length;
		int i;
		for(i=0;i<l;i++)
		{
			if(wordmap.containsKey(token[i]))
			{
				float f = wordmap.get(token[i]);
				f+=1;
				wordmap.put(token[i],f);
			}
			else
			{
				wordmap.put(token[i],new Float(1.0));
			}
		}
	}
	void normalize()
	{
		int l=wordmap.size();
		Collection<String> c = wordmap.keySet();
		Iterator<String> itr= c.iterator();
		while(itr.hasNext())
		{
			String t=itr.next();
			float f=wordmap.get(t);
			f=(float)f/(float)l;
			wordmap.put(t,f);
		}
	}
}
