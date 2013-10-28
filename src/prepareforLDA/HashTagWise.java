package prepareforLDA;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;

import datastr.Tweet;

//NOTE: Not yet modified for Threads yet



public class HashTagWise {
	
	public HashMap<String,String> hashtag;
	
	public HashTagWise(ArrayList<Tweet> tweets,String filename) throws IOException
	{
		Iterator<Tweet> itr=tweets.iterator();
		Tweet t;
		
		//FileWriter fstream = new FileWriter("src/resources/forLDA/hashtag+hour_wiseLDA"+filename);
		FileWriter fstream = new FileWriter("src/resources/forLDA/hashtagwiseLDA"+filename);
		BufferedWriter outw = new BufferedWriter(fstream);
		
		FileWriter fstream1 = new FileWriter("src/resources/forLDA/hashtags_"+filename);
		BufferedWriter outw1 = new BufferedWriter(fstream1);
		
		FileWriter fstream2 = new FileWriter("src/resources/forLDA/NOhashtagLDA"+filename);
		BufferedWriter outw2 = new BufferedWriter(fstream2);
		
		int twcount=0;
		
		//now we need to collect tweets based on the Hour-scheme
		hashtag = new HashMap<String,String>();//key=hour, value=collection(as a single document) of tweets posted in this hour separated by a space
		while(itr.hasNext())
		{
			twcount++;
			if(twcount%50000==0) System.out.println("In HashTagWise.java...Tweet being processed= "+twcount);
			t=itr.next();//now we have the tweet in t
			String text=t.getText();

		    String REGEX = "(\\s|\\A)#(\\w+)";
		    String INPUT = text;
		       Pattern p = Pattern.compile(REGEX);
		       Matcher m = p.matcher(INPUT); // get a matcher object
		       int count = 0;

		       while(m.find())
		       {
		    	   count++;
		    	   String tag=m.group();
		    	   //String timedtag=tag+"_"+t.getDate()+"_"+t.getHour();
		    	   //tag=timedtag;//uncomment this fortag+time
		    	   tag=tag.trim();
		    	   if(hashtag.containsKey(tag))
		    	   {
		    		   //now get the tweet collection till now for this tag....add this new tweet to this collection and put back this new entry
		    		   //in the HashMap
		    		   String temp=hashtag.get(tag);
		    		   temp=t.getTest_no_sw()+" "+temp;
		    		   hashtag.put(tag, temp);
		    	   }
		    	   else
		    	   {
		    		   hashtag.put(tag,t.getTest_no_sw()+"\n");
		    		   outw1.write(tag+"\n");
		    		   //System.out.println("Hashtag= "+tag);
		    	   }
		       }
		       if(count==0)
		       {
		    	   outw2.write(text+"\n");
		       }
		}
		outw2.close();
		System.out.println("~~~~~~~~~~~~Total no of HashTags= "+hashtag.size());

		
		//now traverse this hashmap of hours to print tweets of the hours
		//Collection<String> c=hashtag.values();
		Collection<String> c=hashtag.keySet();
		Iterator<String> it=c.iterator();
		String tt="";
		while(it.hasNext())//for each hour
		{
			//now for each hour print tweets of this hashtag
			tt=it.next();
			//outw.write(tt.substring(tt.indexOf('_'),tt.length())+"\t");
			//outw.write(tt+"\t");
			outw.write(hashtag.get(tt));
		}
		outw.close();

	}
}
