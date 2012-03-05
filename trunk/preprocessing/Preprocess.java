package preprocessing;

import datastr.Tweet;
import datastr.Word;
import datastr.Dataset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;
import java.util.*;


/*This class will load the tweets file from Resources folder and preprcess them.
 * Pre-processing include: Removal of stop words, removal of urls.
 * This is followed by creating a wordList for each tweet and storing it in the corresponding 
 * tweet class*/

public class Preprocess {
	
	public String filename;
	public Dataset dataset; 
	
	public Preprocess(String filename, Dataset dataset)
	{
		this.filename=filename;
		this.dataset=dataset;
		System.out.println("Preprocessing tweets");
		populateStopWords();
		fillTweets(filename,dataset);
	}
	
	public void populateStopWords()
	{		
            BufferedReader br;
			try {
				br = new BufferedReader(new FileReader("src/resources/stopwords.txt"));
			    String word = br.readLine();
			    while(word != null)
			    {
			    	word=word.trim();
			    	if(dataset.stopWords.containsKey(word)==false)
			    		dataset.stopWords.put(word, word);
			    	word = br.readLine();            	  
			    }
			    br.close();
				} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
            
         
		System.out.println("Size of StopWords Hash Table = "+dataset.stopWords.size());
	}
	
	public void fillTweets(String filename,Dataset dataset)
	{
		/*This function will take in the text from the filename and process each tweet and adds it to the Dataset class
		 * variable Tweets*/
		int totalWordCount=0;
		try{
            BufferedReader brd = new BufferedReader(new FileReader(filename));
            String line = brd.readLine();
            int first=0;
            int tweetNo=0;
            while(line != null)
            {
            	tweetNo++;            	
            	Tweet tw=new Tweet(line);
            	line=tw.getText();
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
            	
            	tw.setWordList(wrdlst);
            	dataset.Tweets.add(tw);
            	if(tweetNo==150000) {System.out.println("Total Word Count@150000= "+totalWordCount);}
            	line = brd.readLine();            	  
            }
            brd.close();
         }catch(Exception e){}
	}
	
	public String removeStopWords(String line)
	{
		try{
            BufferedReader br = new BufferedReader(new FileReader("src/resources/stopwords.txt"));
            String word = br.readLine();
            while(word != null)
            {
            	word=word.trim();
            	String temp=" "+word+" ";
            	word=temp;
            	while(line.contains(word))line=line.replaceAll(word," ");
            	word = br.readLine();            	  
            }
            br.close();
         }catch(Exception e){}
		return line;
	}
}
