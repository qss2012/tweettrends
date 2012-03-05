package datastr;

import datastr.Tweet;
import datastr.Word;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Dataset {
	
	public ArrayList<Tweet> Tweets;
	public HashMap<String,Word> dictionary;
	public Date minDate;
	public Date maxDate;
	public ArrayList<CountPerDay> scoresArr;//for all burst scores total_days is the no of days;count is the no of tweets in a particular day
	public HashMap<String,String> stopWords;

}
