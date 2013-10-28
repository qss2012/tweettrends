package experiments;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

import datastr.Bigram;
import datastr.Tweet;
import datastr.Dataset;


public class Collocations {
	
	//this class is called from preprocessing/Preprocess
	
	public HashMap<String, Integer> mapboth;
	public HashMap<String, Integer> mapfirst;
	public HashMap<String, Integer> mapsecond;
	public HashMap<String, Integer> mapscores;
	public HashMap<String,Bigram> byscore;
	public HashMap<String,Bigram> bycount;
	
	public ArrayList<Bigram> bigrams;
	public ArrayList<Bigram> common;
	
	public int N;
	
	public Collocations(Dataset dataset) throws IOException
	{
		System.out.println("Entering Collocations!");
		mapboth=new HashMap<String, Integer>();
		mapfirst=new HashMap<String, Integer>();
		mapsecond=new HashMap<String, Integer>();
		mapscores=new HashMap<String, Integer>();
		byscore=new HashMap<String,Bigram>();
		bycount=new HashMap<String,Bigram>();
		common=new ArrayList<Bigram>();
		bigrams=new ArrayList<Bigram>();
		N=0;
		
		ArrayList<Tweet> tweets=dataset.Tweets;
		Iterator<Tweet> itr= tweets.iterator();
		System.out.println("Size of tweets= "+tweets.size());
		Tweet t;
		int i,te=0;
		String temp="";
		
		while(itr.hasNext())
		{
			t=itr.next();
			String text=t.getText(),first="",second="";
			String token[] = text.split(" ");
			int l=token.length;
			for(i=0;i<l-1;i++)
			{
				N++;
				//handle both
				first=token[i];second=token[i+1];
				temp=first+"_"+second;
				if(first.length()<1 || second.length()<1) continue;
				if(mapboth.containsKey(temp))
				{
					te=mapboth.get(temp); te++;
					mapboth.put(temp, new Integer(te)); te=0;
				}
				else mapboth.put(temp, new Integer(1));
				
				//handle first
				if(mapfirst.containsKey(first))
				{
					te=mapfirst.get(first); te++;
					mapfirst.put(first, new Integer(te)); te=0;
				}
				else mapfirst.put(first, 1);
				
				//handle second
				if(mapsecond.containsKey(second))
				{
					te=mapsecond.get(second); te++;
					mapsecond.put(second, new Integer(te));	te=0;
				}
				else mapsecond.put(second, 1);
			}
		}
		
		System.out.println("The total no of bi-grams encountered= "+N);
		System.out.println("The total no of 'unique' bi-grams encountered= "+mapboth.size());
		System.out.println("The total no of 1st words in bi-grams encountered= "+mapfirst.size());
		System.out.println("The total no of 2nd words in bi-grams encountered= "+mapsecond.size());
		
		//now we have all information about the bi-grams...time to calculate the scores
		Collection<String> coll=mapboth.keySet();
		Iterator<String> it=coll.iterator();
		
		//Iterator itt = mapboth.entrySet().iterator();
		temp="";
		int a=0,b=0,c=0,d=0,tt=0;
		
	    //while (itt.hasNext())
	    //{
	        //Map.Entry pairs = (Map.Entry)itt.next();
	        //System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        //String w1w2=pairs.getKey();
		while(it.hasNext())
		{
			String w1w2=it.next();
			String w1=w1w2.substring(0, w1w2.indexOf('_'));
			String w2=w1w2.substring(w1w2.indexOf('_')+1,w1w2.length());
			//System.out.println(w1w2+"++"+w1+"++"+w2);
			int count=mapboth.get(w1w2);
			temp=w1w2;
			Bigram bg=new Bigram();
			bg.bigram=w1w2;
			bg.count=count;
			a=count;
			if(mapfirst.get(w1)!=null) b=mapfirst.get(w1)-a;
			else b=1;
			if(mapsecond.get(w2)!=null) c=mapsecond.get(w2)-a;
			else c=1;
			d=N-a-b-c;
			if(a==0) a=1;if(b==0) b=1;if(c==0) c=1;if(d==0) d=1;
			bg.score=getscore(a,b,c,d);
			bigrams.add(bg);
			tt++;
		}
		
		System.out.println("The final size of bigram arraylist= "+bigrams.size());
		System.out.println("Size comparison: "+mapboth.size()+"_"+bigrams.size()+"_"+tt);
		sortBigramsByScore();
		sortBigramsByCount();
		intersection();
		printintersectionCnS();
		printintersectionSnC();
		System.out.println("Exiting Collocations!");	
	}
	
	void intersection() throws IOException
	{
		FileWriter fstream = new FileWriter("results/final/intersectionBigramScoreandCount.txt");
		BufferedWriter outw = new BufferedWriter(fstream);
		
		Collection<String> c=byscore.keySet();
		Iterator<String> it=c.iterator();
		while(it.hasNext())
		{
			String temp=it.next();
			if(bycount.containsKey(temp)) 
			{
				outw.write("Bigram: "+temp+"\tscore= "+byscore.get(temp).score+"\tcount= "+byscore.get(temp).count+"\n");
				common.add(byscore.get(temp));
			}
		}
		outw.close();
	}

	
	void printintersectionCnS() throws IOException
	{
		Collections.sort(common, new Comparator<Bigram>(){
			  public int compare(Bigram s1, Bigram s2) {
				  int res=0;
			    if(s1.count<s2.count) res= 1;
			    if(s1.count>s2.count) res= -1;
			    if(s1.count==s2.count) res= 0;
			    return res;
			  }
			});
		FileWriter fstream = new FileWriter("results/final/inetrsectnCount.txt");
		BufferedWriter outw = new BufferedWriter(fstream);
		Iterator<Bigram>it=common.iterator();
		Bigram bg;
		while(it.hasNext())
		{
			bg=it.next();
			outw.write("Bigram= "+bg.bigram+"\tScore= "+bg.score+"\t count= "+bg.count+"\n");
		}
		outw.close();		
	}
	
	void printintersectionSnC() throws IOException
	{
		Collections.sort(common, new Comparator<Bigram>(){
			  public int compare(Bigram s1, Bigram s2) {
				  int res=0;
			    if(s1.score<s2.score) res= 1;
			    if(s1.score>s2.score) res= -1;
			    if(s1.score==s2.score) res= 0;
			    return res;
			  }
			});
		FileWriter fstream = new FileWriter("results/final/inetrsectnScore.txt");
		BufferedWriter outw = new BufferedWriter(fstream);
		Iterator<Bigram>it=common.iterator();
		Bigram bg;
		while(it.hasNext())
		{
			bg=it.next();
			outw.write("Bigram= "+bg.bigram+"\tScore= "+bg.score+"\t count= "+bg.count+"\n");
		}
		outw.close();		
	}
	
	
	void sortBigramsByScore() throws IOException
	{
		Collections.sort(bigrams, new Comparator<Bigram>(){
			  public int compare(Bigram s1, Bigram s2) {
				  int res=0;
			    if(s1.score<s2.score) res= 1;
			    if(s1.score>s2.score) res= -1;
			    if(s1.score==s2.score) res= 0;
			    return res;
			  }
			});
		FileWriter fstream = new FileWriter("results/final/sortedBigramsByScore.txt");
		BufferedWriter outw = new BufferedWriter(fstream);
		Iterator<Bigram>it=bigrams.iterator();
		Bigram bg;
		int count=0;
		while(it.hasNext())
		{
			bg=it.next();count++;
			byscore.put(bg.bigram,bg);
			outw.write("Bigram= "+bg.bigram+"\tScore= "+bg.score+"\t count= "+bg.count+"\n");
			if(count>1000) break;
		}
		outw.close();		
	}
	
	void sortBigramsByCount() throws IOException
	{
		Collections.sort(bigrams, new Comparator<Bigram>(){
			  public int compare(Bigram s1, Bigram s2) {
				  int res=0;
			    if(s1.count<s2.count) res= 1;
			    if(s1.count>s2.count) res= -1;
			    if(s1.count==s2.count) res= 0;
			    return res;
			  }
			});
		FileWriter fstream = new FileWriter("results/final/sortedBigramsByCount.txt");
		BufferedWriter outw = new BufferedWriter(fstream);
		Iterator<Bigram>it=bigrams.iterator();
		Bigram bg;
		int count=0;
		while(it.hasNext())
		{
			bg=it.next();count++;
			bycount.put(bg.bigram,bg);
			outw.write("Bigram= "+bg.bigram+"\tScore= "+bg.score+"\t count= "+bg.count+"\n");
			if(count>1000) break;
		}
		outw.close();		
	}
	
	double getscore(int a,int b,int c,int d)
	{
		double res=0;
		double t1=(a*d);
		double t2=(b*c);
		double t3=t1/t2;
		double t4=Math.log(t3);
		//System.out.println(a+"_"+b+"_"+c+"_"+d);
		double t5=(1/a)+(1/b)+(1/c)+(1/d);
		double t6=Math.sqrt(t5);
		double t7=3.29*t6;
		res=t4-t7;
		return res;
	}
	
}

