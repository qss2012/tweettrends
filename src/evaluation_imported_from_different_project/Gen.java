
import java.io.*;

public class Gen {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("sample-data/forLDAdataset2/temp-Gfile.txt"));
		String line = br.readLine();
		
		FileWriter fstream = new FileWriter("sample-data/forLDAdataset2/Generic-temp-Gfile.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		
		int ct=0;
		while(line!=null)
		{
			line = line.trim();
			String token[]=line.split("\t");
			if(token.length!=3) {line=br.readLine();continue;}
			out.write(token[2]+"\n");
			line=br.readLine();
		}
		br.close();
		out.close();


	}

}
