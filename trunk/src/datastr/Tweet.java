package datastr;


import datastr.Word;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Tweet {
	
	private String user;
	private String name;
	private String date;
	private String time;
	private String text;
	private ArrayList<Word> wordList;
	
	public Tweet(String tweet)
	{
		 String token[] = tweet.split("\t");
		 this.user=token[0];
		 this.name=token[1];
		 //this.date=getValidDate(token[2]);
		 //this.time=getValidTime(token[2]);
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
	public String getDate()
	{
		return date;
	}
	public String getTime()
	{
		return time;
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
	public void setDate(String date)
	{
		this.date=date;
	}
	public void setTime(String time)
	{
		this.time=time;
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
		date=date.replaceAll(",","");
		String token[] = time.split(" ");
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
		return temp;
	}

}

