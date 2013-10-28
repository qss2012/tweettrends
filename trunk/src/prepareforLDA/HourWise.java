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



public class HourWise {
	
	public HashMap<String,String> hours;
	
	public HourWise(ArrayList<Tweet> tweets,String filename) throws IOException
	{
		Iterator<Tweet> itr=tweets.iterator();
		Tweet t;
		System.out.println("Size of the dataset = "+tweets.size());
		//System.exit(0);
		FileWriter fstream = new FileWriter("src/resources/forLDA/hourwiseLDA"+filename);
		BufferedWriter outw = new BufferedWriter(fstream);
		
		int twcount=0;
		//now we need to collect tweets based on the Hour-scheme
		hours = new HashMap<String,String>();//key=hour, value=collection(as a single document) of tweets posted in this hour separated by a space
		while(itr.hasNext())
		{
			twcount++;
			if(twcount%50000==0) System.out.println("In HourWise.java...Tweet being processed= "+twcount);
			
			t=itr.next();//now we have the tweet in t
			if(hours.containsKey(t.getHour()))
			{
				//now get the tweet collection till now for this hour....add this new tweet to this collection and put back this new entry
				//in the HashMap
				String temp=hours.get(t.getHour());
				temp=t.getTest_no_sw()+" "+temp;
				hours.put(t.getHour(), temp);
			}
			else 
			{
				hours.put(t.getHour(),t.getTest_no_sw()+"\n");
				//System.out.println("Hour ="+t.getHour());
			}
		}
		System.out.println("~~~~~~~~~~~~Total no of Hours= "+hours.size());

		
		//now traverse this hashmap of hours to print tweets of the hours
		Collection<String> c=hours.values();
		Iterator<String> it=c.iterator();
		
		while(it.hasNext())//for each hour
		{
			//now for each hour print tweets of this hour
			outw.write(it.next());
		}
		outw.close();

	}
}
