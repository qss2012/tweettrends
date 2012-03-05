import java.io.BufferedWriter;

import datastr.Aggregate;

public class Thread4 extends Thread {
	
	public String file;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	
	public Thread4(String file,Aggregate ag,BufferedWriter out,BufferedWriter out1)
	{
		this.file=file;
		this.ag=ag;
		this.out=out;
		this.out1=out1;
	}
	
	public void run()
	{
		new Common(file,4,ag,out,out1);
	}	
	
}

