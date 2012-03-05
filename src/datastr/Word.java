package datastr;

public class Word {
	
	public String word;
	public int count;
	public double idf;
	
	public Word(String word, int count)
	{
		this.word=word;
		this.count=count;
		this.idf=0;
	}

}
