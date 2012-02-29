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
	//private String time;
	private String text;
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
		 this.text=token[3];
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
	public void setWordList(ArrayList<Word> wordList)
	{
		this.wordList=wordList;
	}
	
	//--------------------------------------------------------
	
	String getValidTime(String time)
	{
		String temp="";
		time=time.replaceAll(",","");
		String token[] = time.split(" ");
		
		return time;
	}
	String getValidDate(String date)
	{
		String temp="";
		//System.out.println("   uhu   "+temp);
		date=date.replaceAll(",","");
		String token[] = date.split(" ");
		token[0]=token[0].trim();token[1]=token[1].trim();token[2]=token[2].trim();token[3]=token[3].trim();
		System.out.println("   uhu   "+temp);
		temp+=token[3].charAt(2);temp+=token[3].charAt(3);
		if(token[2].equalsIgnoreCase("january")) temp+="01";
		if(token[2].equalsIgnoreCase("february")) temp+="02";
		if(token[2].equalsIgnoreCase("march")) temp+="03";
		if(token[2].equalsIgnoreCase("april")) temp+="04";
		if(token[2].equalsIgnoreCase("may")) temp+="05";
		if(token[2].equalsIgnoreCase("june")) temp+="06";
		if(token[2].equalsIgnoreCase("july")) temp+="07";
		if(token[2].equalsIgnoreCase("august")) temp+="08";
		if(token[2].equalsIgnoreCase("september")) temp+="09";
		if(token[2].equalsIgnoreCase("october")) temp+="10";
		if(token[2].equalsIgnoreCase("november")) temp+="11";
		if(token[2].equalsIgnoreCase("december")) temp+="12";
		temp+=token[1];
		//System.out.println(temp);
		return temp;
	}

}

