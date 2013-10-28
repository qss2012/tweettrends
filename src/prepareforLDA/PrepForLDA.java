package prepareforLDA;

import java.io.IOException;
import java.util.ArrayList;

import datastr.Dataset;
import datastr.Tweet;

public class PrepForLDA {

	//this class is called from preprocessing/Preprocess
	public String filename;
	
	public PrepForLDA(String filename,Dataset dataset) throws IOException
	{
		
		//Note: filename is just the last part after src/resources/
		ArrayList<Tweet> tweets=dataset.Tweets;
		new HashTagWise(tweets,filename);
		//System.exit(0);
		new AuthorWise(tweets,filename);
		new PrintGFiles(tweets,filename);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!HERE");
		new HourWise(tweets,filename);
		
	}

}
