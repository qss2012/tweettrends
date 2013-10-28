package prepareforLDA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SizeOfDoc {

	public static void main(String[] args) {
		BufferedReader br;
	    int length=0,count=0,max=0,up=0;
	    double avglen=0;
		try {
			//br = new BufferedReader(new FileReader("src/resources/forLDA/authorwiseLDAspecific.txt"));
			//String file = "src/resources/forLDA/GfilEspecific.txt";
			//String file = "src/resources/forLDA/GfilEevents.txt";
			String file = "/home/rishabh/workspace/twittertrends/src/resources/forLDA/hashtagwiseLDAevents.txt";
			br = new BufferedReader(new FileReader(file));
		    String line = br.readLine();
		    while(line != null)
		    {
		    	line=line.trim();
		    	String token[]=line.split(" ");
		    	length=token.length;
		    	if(max<length) max=length;
		    	//if(length>50) System.out.println(line);
		    	if(length>5000) up++;
		    	//System.out.println(line + "_____"+ length);
		    	count++;
		    	avglen+=length;
		    	line = br.readLine();            	  
		    }
		    br.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
			avglen=avglen/count;
			System.out.println("No of docs = "+count);
			System.out.println("Avg no of words per doc= "+avglen+ "MAX= "+max+" no of docs having >10000 words = "+up);
	}

}
