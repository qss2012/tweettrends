package preprocessing;

import datastr.Tweet;
import datastr.Word;
import datastr.Dataset;

import java.io.BufferedReader;
import java.io.FileReader;
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
		System.out.println("Inside Preprocess");
		fillTweets(filename);
	}
	
	public void fillTweets(String filename)
	{
		/*This function will take in the text from the filename and process each tweet and adds it to the Dataset class
		 * variable Tweets*/
		System.out.println("Inside fillTweets "+filename);
		try{
            BufferedReader brd = new BufferedReader(new FileReader("src/resources/"+filename));
            String line = brd.readLine();
            int tweetNo=0;
            while(line != null)
            {
            	tweetNo++;
            	System.out.println("Scanning tweetNo= "+tweetNo);
            	
            	Tweet tw=new Tweet(line);
            	line=tw.getText();
            	
            	// remove stop words
            	line=removeStopWords(line);
            	
            	//remove urls
            	//line=removeurls(line);
            	line=line.trim();
            	System.out.println("here0");
            	//while(line.contains("  ")) line.replaceAll("  "," ");
            	System.out.println("here1");
            	ArrayList<Word> wrdlst=new ArrayList<Word>();
            	System.out.println("here2");
            	String token[] = line.split(" ");//extracting words from the that particular tweet
            	int l=token.length;
            	System.out.println("Tokenized the text"+l);
            	for(int i=0;i<l;i++)
            	{
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
            			wrdlst.add(w);
            		}
            	}
            	tw.setWordList(wrdlst);
            	dataset.Tweets.add(tw);
            	System.out.println("added");
            	if(tweetNo==50)
            	{
            		Iterator<Word> it=tw.getWordList().iterator();
            		while(it.hasNext())
            		{
            			Word w=it.next();
            			System.out.println(w.word+"  "+w.count);
            		}
            		break;
            	}
            	line = brd.readLine();            	  
            }
            brd.close();
         }catch(Exception e){}
	}
	
	public String removeStopWords(String line)
	{
		System.out.println("Inside removeStopWords"+line);
		try{
            BufferedReader br = new BufferedReader(new FileReader("resources/stopwords.txt"));
            String word = br.readLine();
            while(word != null)
            {
            	String temp=" "+word+" ";
            	word=temp;
            	line=line.replaceAll(word," ");
            	word = br.readLine();            	  
            }
            br.close();
         }catch(Exception e){}
		return line;
	}
	
	public String removeurls(String line)
	{
		System.out.println("Inside removeurls");
		//String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String regex = "https";
		Matcher files=Pattern.compile(regex,Pattern.DOTALL).matcher(line);
        String output = files.replaceAll(" ");        
		return output;
	}
	

}
