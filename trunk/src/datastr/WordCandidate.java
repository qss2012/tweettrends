package datastr;

import java.util.ArrayList;

public class WordCandidate {
	public Word word;
	public ArrayList<CountPerDay> dayList;
	public double mean_R_d;
	public int total_days;//total_no_of_tweets_appearingIn
	public double stddev;
	
	public WordCandidate(Word word)
	{
		this.word=word;
		dayList=new ArrayList<CountPerDay>();
		this.mean_R_d=0;
		this.total_days=1;
	}
}
