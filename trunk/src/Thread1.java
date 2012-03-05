import java.io.BufferedWriter;

import datastr.Aggregate;

public class Thread1 extends Thread {
	
	public String file;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	
	
	public Thread1(String file,Aggregate ag,BufferedWriter out,BufferedWriter out1)
	{
		this.file=file;
		this.ag=ag;
		this.out=out;
		this.out1=out1;
	}
	
	public void run()
	{
		//new Common("src/resources/firstpart.txt",1);
		new Common(file,1,ag,out,out1);
	}	
	
}

