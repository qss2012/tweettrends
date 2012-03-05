import java.io.BufferedWriter;

import datastr.Aggregate;

public class Thread3 extends Thread {
	
	public String file;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	
	
	public Thread3(String file,Aggregate ag,BufferedWriter out,BufferedWriter out1)
	{
		this.file=file;
		this.ag=ag;
		this.out=out;
		this.out1=out1;
	}
	
	public void run()
	{
		new Common(file,3,ag,out,out1);
	}	
	
}

