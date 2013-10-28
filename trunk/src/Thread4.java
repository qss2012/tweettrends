import java.io.BufferedWriter;
import java.io.IOException;

import datastr.Aggregate;
import datastr.WordCandidateList;

public class Thread4 extends Thread {
	
	public String file;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	public WordCandidateList wcl;
	public Thread4(String file,Aggregate ag,BufferedWriter out,BufferedWriter out1,WordCandidateList wcl)
	{
		this.file=file;
		this.ag=ag;
		this.out=out;
		this.out1=out1;
		this.wcl=wcl;
	}
	
	public void run()
	{
		try {
			new Common(file,4,ag,out,out1,wcl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}

