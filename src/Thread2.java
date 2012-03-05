import java.io.BufferedWriter;

import datastr.Aggregate;

public class Thread2 extends Thread {

	public String file;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	
	
	public Thread2(String file,Aggregate ag,BufferedWriter out,BufferedWriter out1)
	{
		this.file=file;
		this.ag=ag;
		this.out=out;
		this.out1=out1;
	}
	
	public void run()
	{
		new Common(file,2,ag,out,out1);
	}	
}

