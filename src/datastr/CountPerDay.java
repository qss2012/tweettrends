package datastr;

import java.util.Date;

public class CountPerDay {
	public Date date;
	public Word word;
	public int count;
	public int total_days;//parents's variable: total_no_of_tweets_appearingIn
	public double burstScore;
	
	public CountPerDay(Date date,int count,Word word,int total_days)
	{
		this.date=date;
		this.count=count;
		this.word=word;
		this.total_days=total_days;
	}
}
