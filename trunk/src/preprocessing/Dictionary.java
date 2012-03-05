package preprocessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
					//---------------------------------------------insert in Dictionary start---------------------------------------
					if(dataset.dictionary.containsKey(w.word)) 
					{
						//if(w.word.equals("brave")) System.out.print("_Dictionary Contains_");
						tempw=dataset.dictionary.get(w.word);
						tempw.count+=w.count;
						tempw.idf++;
					}
					else
					{
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
			while(itr.hasNext())
			{
				Word w=itr.next();
				if(w.idf>10)out.write(w.word+" idf value= "+w.idf+" total count = "+w.count+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
