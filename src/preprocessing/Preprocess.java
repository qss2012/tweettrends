package preprocessing;

import datastr.Tweet;
import datastr.Word;
import datastr.Dataset;
import prepareforLDA.PrepForLDA;
import experiments.Collocations;
import experiments.MMR;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/*This class will load the tweets file from Resources folder and preprcess them.
 * Pre-processing include: Removal of stop words, removal of urls.
 * This is followed by creating a wordList for each tweet and storing it in the corresponding 
 * tweet class*/

@SuppressWarnings("unused")
public class Preprocess {
	
	public String filename;
	public Dataset dataset;
	public String stop[];
	public int stopcount;
	
	public Preprocess(String filename, Dataset dataset) throws IOException
	{
		this.stop=new String[1000];
		this.filename=filename;
		String file=filename.substring(filename.lastIndexOf('/')+1,filename.length());
		//System.out.println("Inside preprocess...filename=_"+file+"_");System.exit(0);
		this.dataset=dataset;
		System.out.println("Preprocessing tweets");
		populateStopWords();
		fillTweets(filename,dataset);
		//new Collocations(dataset);
		//new MMR(dataset);
		//
		System.out.println("FIlename = "+filename);
		System.out.println("Size of dataset from Preprocess = "+dataset.Tweets.size());
		//System.exit(0);
		new PrepForLDA(file,dataset);//for different LDA schemes
	}
	
	public void populateStopWords()
	{		
			BufferedReader br;
			try {
				stopcount=0;
				br = new BufferedReader(new FileReader("src/resources/stopwords.txt"));
				String word = br.readLine();
				while(word != null)
				{
					word=word.trim();
					
					if(dataset.stopWords.containsKey(word)==false)
					{
						dataset.stopWords.put(word, word);
						stop[stopcount++]=word;
					}
					word = br.readLine();            	  
				}
				br.close();
				} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
			
		 
		System.out.println("Size of StopWords Hash Table = "+dataset.stopWords.size()+" value of stopcount= "+stopcount);
	}
	
	public void fillTweets(String filename,Dataset dataset)
	{
		/*This function will take in the text from the filename and process each tweet and adds it to the Dataset class
		 * variable Tweets*/
		int totalWordCount=0;
		try{
			FileWriter fstream = new FileWriter("src/resources/retweets.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			BufferedReader brd = new BufferedReader(new FileReader(filename));
			String line = brd.readLine();
			int ttt=0;
			while(line!=null)
			{
				ttt++;
				line = brd.readLine();
			}
			System.out.println("ttt = "+ttt);
			ttt=0;
			brd = new BufferedReader(new FileReader(filename));
			line = brd.readLine();
			int first=0,rtcount=0;
			int tweetNo=0;
			while(line != null)
			{
				ttt++;
				tweetNo++;
				String tok[]=line.split("\t");
				if(tok.length<3) {line=brd.readLine();continue;}
				Tweet tw=new Tweet(line);
				line=tw.getText();
				
				//dealing retweets starts
				
				if(line.startsWith("rt ")) {out.write(line+"\n");rtcount++;}
				//dealing retweets ends
				
				if(first==0) {dataset.maxDate=tw.getDate();dataset.minDate=tw.getDate();first=1;}
				else
				{
					if(dataset.minDate.compareTo(tw.getDate())>0)dataset.minDate=tw.getDate();
					if(dataset.maxDate.compareTo(tw.getDate())<0) dataset.maxDate=tw.getDate();
				}
				
				line = line.replaceAll("\\s*RT\\s*@\\w+:\\s*","");  //removes "RT @other_user:"
				line = line.replaceAll("\\s*@\\w+\\s*","");  //removes "@other_user"
				line = line.replaceAll("https?:[^\\s]*","");  //removes "http://foo" "https://bar"
				line = line.toLowerCase();
				line = removeStopWords(line);

				tw.setText_no_sw(line);
				line = line.trim();
				
				ArrayList<Word> wrdlst=new ArrayList<Word>();
				String token[] = line.split(" ");//extracting words from the that particular tweet
				int l=token.length;totalWordCount+=l;
				
				for(int i=0;i<l;i++)
				{
					//if(token[i].equals("the")) System.out.println(tw.getText());
					token[i]=token[i].trim();
					Iterator<Word> it=wrdlst.iterator();
					int flag=0;
					while(it.hasNext())
					{
						Word wt=(Word) it.next();
						if(wt.word.equals(token[i])) {wt.count++;flag=1;break;}
					}
					if(flag==0)
					{
						Word w=new Word(token[i],1);
						w.idf=1;
						wrdlst.add(w);
					}
				}
				/*if(line.contains("equipment"))
				{
				System.out.println("Tweet= "+line);
				System.out.print("Word List= __");
				Iterator<Word> it=wrdlst.iterator();
				while(it.hasNext()) {Word wt=(Word) it.next(); System.out.print(wt.word+" "+wt.count+" ");}
				System.out.println("__");
				}*/
				
				tw.setWordList(wrdlst);
				dataset.Tweets.add(tw);
				if(tweetNo%5000==0) 
				{
					System.out.print(tweetNo+"_"+ttt+" !_! ");
					if(tweetNo%50000==0)
						System.out.println("\nInside fillTweets() in Preprocess.java. Total Word Count= "+totalWordCount+" TweetNo= "+tweetNo);
				}
				
				line = brd.readLine();            	  
			}
			System.out.println("ttt = "+ttt);
			//System.exit(0);
			out.close();
			brd.close();
			System.out.println("Total no of tweets= "+tweetNo+" Total no of ReTweets= "+rtcount);
			System.out.println("Dates for this dataset: "+dataset.minDate+"__"+dataset.maxDate);
		 }catch(Exception e){System.out.println("ERROR IN PREPROCESS CATCHED"+e.getMessage());e.printStackTrace();}
	}
	
	public String removeStopWords(String line)
	{
		try{
			//BufferedReader br = new BufferedReader(new FileReader("src/resources/stopwords.txt"));
			//String word = br.readLine();
			int i=0;
			String word;
			for(i=0;i<stopcount;i++)
			{
			//while(word != null)
			//{
				//word=word.trim();
				word=stop[i];
				String temp=" "+word+" ";
				word=temp;
				while(line.contains(word))line=line.replaceAll(word," ");
				//word = br.readLine();            	  
			}
			//br.close();
		 }catch(Exception e){}
		return line;
	}
}
