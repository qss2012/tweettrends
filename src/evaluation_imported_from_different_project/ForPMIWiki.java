import java.io.*;


public class ForPMIWiki {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String file = "/home/rishabh/workspace/malletnew/sample-data/topics/forPMI";
		BufferedReader brd = new BufferedReader(new FileReader(file));
        String line=brd.readLine();
        
        FileWriter fstream = new FileWriter("/home/rishabh/workspace/malletnew/sample-data/topics/forPMI_final");
		BufferedWriter out = new BufferedWriter(fstream);

        while(line!=null)
        {
        	String token[] = line.split(" ");
        	for(int i=0;i<token.length;i++)
        	{
        		String t = token[i].trim();
        		out.write(t+"\n");
        	}
        	line = brd.readLine();
        }
	}

}
