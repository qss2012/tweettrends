package prepareforLDA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import datastr.Tweet;

//NOTE: Not yet modified for Threads yet

public class AuthorWise {
	
	public HashMap<String,String> authors;
	
	public AuthorWise(ArrayList<Tweet> tweets,String filename) throws IOException
	{
		System.out.println("Inside AuthorWise now...");
		Iterator<Tweet> itr=tweets.iterator();
		Tweet t;
		
		FileWriter fstream = new FileWriter("src/resources/forLDA/authorwiseLDA"+filename);
		BufferedWriter outw = new BufferedWriter(fstream);
		
		String temp="";
		int authorcount=0,twcount=0;
		//now we need to collect tweets based on the Author-scheme
		authors = new HashMap<String,String>();
		while(itr.hasNext())
		{
			twcount++;
			if(twcount%50000==0) System.out.println("In AuthorWise.java...Tweet being processed= "+twcount+" no of authorsryt now= "+authorcount);
			t=itr.next();//now we have the tweet in t
			if(!authors.containsKey(t.getUser()))
				{
				authors.put(t.getUser(),t.getTest_no_sw()+"\n");
				authorcount++;
				}
			else
			{
				temp=authors.get(t.getUser());
				temp=t.getTest_no_sw()+" "+temp;
				authors.put(t.getUser(),temp);
			}
		}
		System.out.println("~~~~~~~~~~~~Total no of Authors= "+authors.size());
		//now traverse this hashmap of authors to collect tweets of the authors
		/*Collection<String> c=authors.values();
		Iterator<String> it=c.iterator();
		String author="";
		
		while(it.hasNext())//for each author
		{
			//now for each author find tweets by this author in the arraylist<tweet> tweets
			author=it.next();
			String text4thisAuthor="";
			itr=tweets.iterator();
			
			while(itr.hasNext())//for each tweet
			{
				t=itr.next();
				if(t.getUser().equals(author)) {text4thisAuthor+=t.getTest_no_sw()+" ";}
			}
			
			text4thisAuthor+="\n";
			outw.write(text4thisAuthor);
			text4thisAuthor="";
		}*/
		Collection<String> c=authors.values();
		Iterator<String> it=c.iterator();
		while(it.hasNext())
		{
			outw.write(it.next());
		}
		outw.close();
	}
}
