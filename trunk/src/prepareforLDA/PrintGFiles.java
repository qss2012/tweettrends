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



public class PrintGFiles {
	
	public HashMap<String,String> hours;
	
	public PrintGFiles(ArrayList<Tweet> tweets,String filename) throws IOException
	{
		Iterator<Tweet> itr=tweets.iterator();
		Tweet t;
		
		FileWriter fstream = new FileWriter("src/resources/forLDA/GfilE"+filename);
		BufferedWriter outw = new BufferedWriter(fstream);
		
		int twcount=0;
		//now we need to collect tweets based on the Hour-scheme
		hours = new HashMap<String,String>();//key=hour, value=collection(as a single document) of tweets posted in this hour separated by a space
		while(itr.hasNext())
		{
			twcount++;
			t=itr.next();//now we have the tweet in t
			outw.write(t.getTest_no_sw()+"\n");
		}
		outw.close();
		//System.exit(0);
	}
}
