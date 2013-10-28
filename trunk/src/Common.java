import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import preprocessing.Preprocess;
import preprocessing.Dictionary;
import preprocessing.Burst;
import datastr.CountPerDay;
import datastr.Dataset;
import datastr.Aggregate;
import datastr.WordCandidateList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import datastr.Tweet;
import datastr.Word;


public class Common {
	
	public static Dataset dataset;
	public static WordCandidateList wcl;
	public static int thread;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	
	@SuppressWarnings("unused")
	public Common(String file,int threadno,Aggregate ag,BufferedWriter out,BufferedWriter out1,WordCandidateList wcl) throws IOException
	{
		thread=threadno;
		this.ag=ag;
		this.out=out;
		this.out1=out1;
		System.out.println("---------------------------Starting thread "+thread+"------------------------");
		dataset=new Dataset();
		dataset.Tweets=new ArrayList<Tweet>();
		dataset.dictionary=new HashMap<String,Word>();
		dataset.scoresArr=new ArrayList<CountPerDay>();
		dataset.stopWords=new HashMap<String,String>();
		//wcl=new WordCandidateList();
		//wcl.list=new HashMap<String,WordCandidate>();
		Common.wcl=wcl;
		System.out.println("Dataset created...ie...datastr created");
		Preprocess prep=new Preprocess(file,dataset);
		System.out.println("Done with preprocessing");
		System.out.println("The number of tweets scanned = "+dataset.Tweets.size());
		
		//Dictionary dict=new Dictionary(dataset);
		new Dictionary(dataset,wcl,thread);		
		
		System.out.println("Total words in the dictionary = "+dataset.dictionary.size());
		int max=0;double max1=0;
		Collection<Word> c=dataset.dictionary.values();
		Iterator<Word> itr=c.iterator();
		Word tw,w1=null,w2 = null;
		try{
  		  //System.out.println("Done");
  		  FileWriter fstream = new FileWriter("results/top1000"+thread);
  		  BufferedWriter outw = new BufferedWriter(fstream);
  		  FileWriter fstream1 = new FileWriter("results/forLDA"+thread);
		  BufferedWriter outw1 = new BufferedWriter(fstream1);
  		  //out.write(text);
		  outw1.write(dataset.Tweets.size()+"\n");
  		  Iterator<Tweet> i1=dataset.Tweets.iterator();
  		  while(i1.hasNext())
  		  {
  			  Tweet t=i1.next();
  			  Iterator<Word> i2=t.getWordList().iterator();
  			  while(i2.hasNext())
  			  {
  				  Word w=i2.next();
  				  //for(int i=0;i<w.count;i++)
  				  outw1.write(w.word.trim().replaceAll(" ","")+" ");
  			  }
  			  outw1.write("\n");
  		  }
		while(itr.hasNext()) 
		{
			tw=itr.next();
			if(tw.count>10 && tw.idf>5)
			outw.write(tw.word+" "+tw.count+" "+tw.idf+"\n");
			if(max<tw.count) {max=tw.count;w1=tw;}
			if(max1<tw.idf) {max1=tw.idf;w2=tw;}
		}
		outw.close();
		}catch (Exception e){System.err.println("Error: " + e.getMessage());}
		//System.out.println("Max count = "+max+" for "+w1.word+" max idf= "+max1+" for "+w2.word);
		
		
		new Burst(dataset,wcl,thread,ag,out,out1);
		/*FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("src/resources/wcl"+thread+".ser");
			ObjectOutputStream outer = new ObjectOutputStream(fileOut);
			outer.writeObject(wcl);
			outer.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		System.out.println("Out of Burst Class");
		System.out.println("size of burst scores = "+dataset.scoresArr.size());
		System.out.println("Calculating top burst scores");
		topBurstScores();
		System.out.println("----------------OVER------Thread Over: "+thread+"----------------");
	}
	
	
	public static void topBurstScores()
	{
		FileWriter fstream;
		try 
		{
			fstream = new FileWriter("results/TopBurstScores"+thread);
			BufferedWriter out = new BufferedWriter(fstream);
			Collections.sort(dataset.scoresArr, new Comparator<Object>(){
		        public int compare(Object o1, Object o2) {
                CountPerDay p1 = (CountPerDay) o1;
                CountPerDay p2 = (CountPerDay) o2;
                if(p1.burstScore==p2.burstScore) return 0;
                else if(p1.burstScore<p2.burstScore) return 1;
                else return -1;
		        }
			});
		
			Iterator<CountPerDay> itr=dataset.scoresArr.iterator();
			CountPerDay cpd;
			while(itr.hasNext())
			{
				System.out.print(".");
				cpd=itr.next();
				SimpleDateFormat sdf = new SimpleDateFormat("dd: MMM : yyyy");
				String td=sdf.format(cpd.date); 
				//if(cpd.total_days>20 && cpd.burstScore>1)
					out.write(cpd.word.word+"\t\t"+cpd.burstScore+"\t\tCount= "+cpd.count+"       Date: "+td+"\t\t\t\t"+cpd.total_days+"\n");
				
			}
		} catch (IOException e) {e.printStackTrace();}
		System.out.println();
	}

}
