package datastr;


import datastr.Word;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("unused")
public class Tweet {
	
	private String user;
	private String name;
	private Date date;
	private String text;
	private String text_no_sw;
	private ArrayList<Word> wordList;
	
	
	public Tweet(String tweet) throws ParseException
	{
		 String token[] = tweet.split("\t");
		 this.user=token[0];
		 this.name=token[1];
		 token[2]=token[2].trim();
		 final String twitter_date="EEE, dd MMMM yyyy HH:mm:ss Z";
		 SimpleDateFormat sf = new SimpleDateFormat(twitter_date);
		 sf.setLenient(true);
		 this.date=sf.parse(token[2]);
		 this.text=token[3].toLowerCase();
		 cleanText();
	}

	
	//--------------------------------------------------------
	
	public String getUser()
	{
		return user;
	}
	public String getName()
	{
		return name;
	}
	public Date getDate()
	{
		return date;
	}
	public String getText()
	{
		return text;
	}
	public String getTest_no_sw()
	{
		return text_no_sw;
	}
	public ArrayList<Word> getWordList()
	{
		return wordList;
	}
	
	//--------------------------------------------------------
	
	public void setUser(String user)
	{
		this.user=user;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setDate(Date date)
	{
		this.date=date;
	}
	
	public void setText(String text)
	{
		this.text=text;
	}
	public void setText_no_sw(String text)
	{
		this.text_no_sw=text;
	}
	public void setWordList(ArrayList<Word> wordList)
	{
		this.wordList=wordList;
	}
	
	//--------------------------------------------------------
	
	public void cleanText()
	{
		text=text.replaceAll("'"," ");
		text=text.replaceAll(","," ");
		text=text.replaceAll(": "," ");
		text=text.replaceAll("!"," ");
		text=text.replaceAll("-"," ");
		text=text.replaceAll("#"," ");
		//text.replaceAll("?"," ");
		//text.replaceAll("("," ");
		//text.replaceAll(")"," ");
	}
	
}


