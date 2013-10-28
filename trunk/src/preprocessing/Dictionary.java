package preprocessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import datastr.*;
/*This class will use the Tweets arraylist of Dataset
 * and build a dictionary of all words found in the Tweets for this dataset*/
public class Dictionary {
	
		public int thread;
	@SuppressWarnings("unused")
	public Dictionary(Dataset dataset,WordCandidateList wcl,int thread)
	{
		int i,j,k,l,m,n;
		this.thread=thread;
		int len=dataset.Tweets.size();
		Iterator<Tweet> itr=dataset.Tweets.iterator();
		int tempidf=0;int twno=0;
		
		while(itr.hasNext())
		{
			//now deal with each tweet and insert the words in each tweet in Dictionary if its not present already
			tempidf=0;twno++;int flag=0;
			Tweet tw=itr.next();
			ArrayList<Word> list=tw.getWordList();
			Iterator<Word> wrditr=list.iterator();
			Word tempw;tempidf=0;
			//for each word in this particular tweet modify the dictionary & wcl.list accordingly
			
			while(wrditr.hasNext())
			{
				Word w=wrditr.next();
				w.word=w.word.trim();
				w.word=w.word.toLowerCase();
				if(w.word.length()>1)
				{	
					//if(w.word.equals("equipment")) System.out.println("outside");
					//---------------------------------------------insert in Dictionary start---------------------------------------
					if(dataset.dictionary.containsKey(w.word)) 
					{
						//if(w.word.equals("equipment")) System.out.println("inside yes");
						//if(w.word.equals("brave")) System.out.print("_Dictionary Contains_");
						tempw=dataset.dictionary.get(w.word);
						tempw.count+=w.count;
						//tempw.count++;
						//if(w.word.equals("equipment")) System.out.println("inside yes-count increased");
						
						tempw.idf++;
						
						dataset.dictionary.put(tempw.word, tempw);//added this on 6th June 2012
					}
					else
					{
						//if(w.word.equals("equipment")) System.out.println("inside else");
						dataset.dictionary.put(w.word, w);
					}
					//---------------------------------------------insert in Dictionary end---------------------------------------
				
					//---------------------------------------------insert in WordCandidateList start---------------------------------------
					if(wcl.list.containsKey(w.word))
					{
						WordCandidate wc= wcl.list.get(w.word);
						wc.total_days++;
						Iterator<CountPerDay> itrdl=wc.dayList.iterator();
						CountPerDay cpd;int flg=0;
						while(itrdl.hasNext())
						{
							cpd=itrdl.next();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							if(sdf.format(cpd.date).equals(sdf.format(tw.getDate()))) 
							{cpd.count++;flg=1;break;}
						}
						if(flg==0)
						{
							cpd=new CountPerDay(tw.getDate(),1,wc.word,wc.total_days);
							wc.dayList.add(cpd);
						}
						wcl.list.put(wc.word.word, wc);
					}
					else
					{
						WordCandidate wc=new WordCandidate(w);
						wcl.list.put(w.word,wc);
						CountPerDay cpd=new CountPerDay(tw.getDate(),1,wc.word,wc.total_days);
						wc.dayList.add(cpd);
					}
					
					//---------------------------------------------insert in WordCandidateList end---------------------------------------
					
				}
			}
		}
			System.out.println("Total Dictionary elements Count= "+dataset.dictionary.size());
			printIDF(dataset);
	}
	public void printIDF(Dataset dataset)
	{
		FileWriter fstream;
		try {
			fstream = new FileWriter("results/IDF Values"+thread);
			BufferedWriter out = new BufferedWriter(fstream);
			Collection<Word> c=dataset.dictionary.values();
			Iterator<Word> itr=c.iterator();
			ArrayList<Word> forSort=new ArrayList<Word>();
			while(itr.hasNext())
			{
				Word w=itr.next();
				forSort.add(w);
				//if(w.idf>10)
					out.write(w.word+" idf value= "+w.idf+" total count = "+w.count+"\n");
			}
			out.close();
			printSortByCount(forSort);
			printSortByIdf(forSort);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printSortByCount(ArrayList<Word> forSort)
	{
		Collections.sort(forSort, new Comparator<Object>(){
	        public int compare(Object o1, Object o2) {
            Word p1 = (Word) o1;
            Word p2 = (Word) o2;
            /*if(p1.burstScore==p2.burstScore) return 0;
            else if(p1.burstScore<p2.burstScore) return 1;*/
            if(p1.count==p2.count) return 0;
            else if(p1.count<p2.count) return 1;
            else return -1;
	        }
		});
		Iterator<Word> itr=forSort.iterator();
		try {
			FileWriter fstream;
			
			fstream = new FileWriter("results/final/sortByCount"+thread);
			BufferedWriter out = new BufferedWriter(fstream);
			while(itr.hasNext())
			{
				Word w=itr.next();
			
				out.write("Word= "+w.word+"\tCount= "+w.count+"\tidf= "+w.idf+"\n");
			
			}
			out.close();
		}catch (IOException e) {e.printStackTrace();}
	}
	
	public void printSortByIdf(ArrayList<Word> forSort)
	{
		Collections.sort(forSort, new Comparator<Object>(){
	        public int compare(Object o1, Object o2) {
            Word p1 = (Word) o1;
            Word p2 = (Word) o2;
            /*if(p1.burstScore==p2.burstScore) return 0;
            else if(p1.burstScore<p2.burstScore) return 1;*/
            if(p1.idf==p2.idf) return 0;
            else if(p1.idf<p2.idf) return 1;
            else return -1;
	        }
		});
		
		Iterator<Word> itr=forSort.iterator();
		
		FileWriter fstream;
		
		try {
			fstream = new FileWriter("results/final/sortByIdf"+thread);
			BufferedWriter out = new BufferedWriter(fstream);
			
			while(itr.hasNext())
			{
				Word w=itr.next();
				
				out.write("Word= "+w.word+"\tIDF= "+w.idf+"\tCount= "+w.count+"\n");
			
			}
			}catch (IOException e) {e.printStackTrace();}
	}
	
}

