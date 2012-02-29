
import datastr.*;
import preprocessing.Preprocess;
import preprocessing.Dictionary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

@SuppressWarnings("unused")
public class TwitterTrends {
	
	public static Dataset dataset;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dataset=new Dataset();
		dataset.Tweets=new ArrayList<Tweet>();
		dataset.dictionary=new HashMap<String,Word>();
		System.out.println("Dataset created...ie...datastr ccreated");
		Preprocess prep=new Preprocess("WallStqueries",dataset);
		System.out.println("Done with preprocessing");
		System.out.println("The number of tweets scanned = "+dataset.Tweets.size());
		//Dictionary dict=new Dictionary(dataset);
		new Dictionary(dataset);		
		System.out.println("Total words in the dictionary = "+dataset.dictionary.size());
		int max=0;double max1=0;
		Collection<Word> c=dataset.dictionary.values();
		Iterator<Word> itr=c.iterator();
		Word tw,w1=null,w2 = null;
		try{
  		  System.out.println("Done");
  		  FileWriter fstream = new FileWriter("top1000.txt");
  		  BufferedWriter out = new BufferedWriter(fstream);
  		  FileWriter fstream1 = new FileWriter("forLDA");
		  BufferedWriter out1 = new BufferedWriter(fstream1);
  		  //out.write(text);
		  out1.write(dataset.Tweets.size()+"\n");
  		  Iterator<Tweet> i1=dataset.Tweets.iterator();
  		  while(i1.hasNext())
  		  {
  			  Tweet t=i1.next();
  			  Iterator<Word> i2=t.getWordList().iterator();
  			  while(i2.hasNext())
  			  {
  				  Word w=i2.next();
  				  //for(int i=0;i<w.count;i++)
  				  out1.write(w.word.trim().replaceAll(" ","")+" ");
  			  }
  			  out1.write("\n");
  		  }
		while(itr.hasNext()) 
		{
			tw=itr.next();
			if(tw.count>1000 && tw.idf>500)
			out.write(tw.word+" "+tw.count+" "+tw.idf+"\n");
			if(max<tw.count) {max=tw.count;w1=tw;}
			if(max1<tw.idf) {max1=tw.idf;w2=tw;}
		}
		out.close();
		}catch (Exception e){
	  		  System.err.println("Error: " + e.getMessage());
	  		  }
		//System.out.println("Max count = "+max+" for "+w1.word+" max idf= "+max1+" for "+w2.word);
	}

}
