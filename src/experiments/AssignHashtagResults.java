package experiments;

import java.io.*;
import java.util.*;
import experiments.HTweet;

public class AssignHashtagResults implements Serializable{
	
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("src/resources/more/generic/hashtag_generic_hash"));
		HashMap<String,HTweet> hash = (HashMap<String,HTweet>) input.readObject();
		input.close();
		
		System.out.println("Done reading the first hashmap");
		
		ObjectInputStream input1 = new ObjectInputStream(new FileInputStream("src/resources/more/generic/hashtag_generic_NOhash"));
		HashMap<Integer,HTweet> nohash = (HashMap<Integer,HTweet>) input1.readObject();
		input1.close();
		
		System.out.println("Done reading both the hashmaps");
		
		Collection<Integer> c = nohash.keySet();
		Iterator<Integer> itr =c.iterator();
		System.out.println("Sizeofcollection = "+c.size());
		int temp;
		HTweet ht,ht1;
		String tag="",text="";
		int modified=0,count=0;
		
		while(itr.hasNext())
		{
			count++;
			if(count%10000==0) System.out.println(count);
			temp=itr.next();
			ht = nohash.get(temp);
			if(ht.tag!=null)
			{
				tag= ht.tag;
				if(!hash.containsKey(tag)) System.out.println("FATAL ERROR -- SOMETHING IS WRONG");
				else
				{
					ht1 = hash.get(tag);
					text = ht1.text;
					text+= (nohash.get(temp).text);
					ht1.text = text;
					hash.put(tag,ht1);
					modified++;
				}
			}
		}
		System.out.println("No of tweets in hash modified = "+modified);
	}

}
