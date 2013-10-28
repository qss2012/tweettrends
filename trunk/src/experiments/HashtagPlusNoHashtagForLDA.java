package experiments;

import java.io.*;

public class HashtagPlusNoHashtagForLDA {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//String file="generic";
		String file = "events";
		
		BufferedReader br = new BufferedReader(new FileReader("src/resources/forLDA/hashtagwiseLDA"+file+".txt"));
		String line = br.readLine();
		
		BufferedReader br1 = new BufferedReader(new FileReader("src/resources/forLDA/NOhashtagLDA"+file+".txt"));
		String line1 = br1.readLine();
		
		FileWriter fstream = new FileWriter("src/resources/forLDA/somemore_full_hashtagwiseLDA"+file);
		BufferedWriter outw = new BufferedWriter(fstream);
		
		int tot=0;
		
		while(line!=null)
		{
			outw.write(line+"\n");
			tot++;
			line=br.readLine();
		}
		br.close();
		int count=0;
		while(line1!=null)
		{
			count++;
			line1=br1.readLine();
		}
		br1.close();
		int limit = count/1;
		br1 = new BufferedReader(new FileReader("src/resources/forLDA/NOhashtagLDA"+file+".txt"));
		line1 = br1.readLine();
		int cc=0;
		while(line1!=null)
		{
			cc++;
			if(cc>=limit) break;
			outw.write(line1+"\n");
			tot++;
			line1=br1.readLine();
		}
		br1.close();
		outw.close();
		System.out.println("Final size of NOhashtagwiseLDA"+file+".txt => tot = "+tot);
	}

}
