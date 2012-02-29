package preprocessing;

import java.util.ArrayList;
import java.util.Iterator;

import datastr.Dataset;
import datastr.Tweet;
import datastr.Word;
/*This class will use the Tweets arraylist of Dataset
 * and build a dictionary of all words found in the Tweets for this dataset*/
public class Dictionary {
	
	@SuppressWarnings("unused")
	public Dictionary(Dataset dataset)
	{
		int i,j,k,l,m,n;
		int len=dataset.Tweets.size();
		Iterator<Tweet> itr=dataset.Tweets.iterator();
		int temp_idf=0;int twno=0;
		while(itr.hasNext())
		{
			//now deal with each tweet and insert the words in each tweet in Dictionary if its not present already
			temp_idf=0;twno++;
			Tweet tw=itr.next();
			ArrayList<Word> list=tw.getWordList();
			//System.out.println("Tweet no "+twno+" has "+list.size()+" no. of words");
			Iterator<Word> wrditr=list.iterator();
			Word tempw;
			//for each word in this particular tweet modify the dictionary accordingly
			while(wrditr.hasNext())
			{
				Word w=wrditr.next();
				//System.out.println("checking this word");
				if(dataset.dictionary.containsKey(w.word)) 
				{
					//System.out.println("Dictionary Contains");
					tempw=dataset.dictionary.get(w.word);
					tempw.count+=w.count;
					if(temp_idf==0) {tempw.idf++;temp_idf=1;}
				}
				else
				{
					dataset.dictionary.put(w.word, w);
				}
			}
		}
		System.out.println("Total Dict Count= "+dataset.dictionary.size());
	}

}
