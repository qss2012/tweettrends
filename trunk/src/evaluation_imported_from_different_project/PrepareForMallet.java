import java.io.*;

public class PrepareForMallet {
	
	public static void main(String args[]) throws IOException
	{
		//String mainfile="sample-data/forLDAdataset2/burstgeneric.txt";
		//String mainfile = "sample-data/more/hashtag_specific7_hash_preMALLET";
		//String mainfile = "sample-data/more/somemore_onethird_hashtagwiseLDAspecific";
		//String mainfile = "sample-data/more/hashtag_specific9_hash_preMALLET";
		//String mainfile = "sample-data/more/somemore_full_hashtagwiseLDAspecific";
		//String mainfile = "/home/rishabh/workspace/malletnew/sample-data/dataset3/hourwiseLDAevents.txt";
		//String mainfile = "/home/rishabh/workspace/malletnew/sample-data/dataset3/GfilEevents.txt";
		//String mainfile = "/home/rishabh/workspace/malletnew/sample-data/dataset3/hourwiseLDAevents.txt";
		String mainfile ="sample-data/forLDAdataset3/somemore_full_hashtagwiseLDAevents";
		
		BufferedReader brd = new BufferedReader(new FileReader(mainfile));
        String line = brd.readLine();
        
        //FileWriter fstream = new FileWriter("sample-data/forLDAdataset2/burstwiseLDAgenericMALLET.txt");
        //FileWriter fstream = new FileWriter("sample-data/more/somemore_onethird_hashtagwiseLDAspecificMALLET");
        //FileWriter fstream = new FileWriter("sample-data/more/hashtag_specific7_hash_forMALLET");
        //FileWriter fstream = new FileWriter("sample-data/more/hashtag_specific9_hash_forMALLET");
        //FileWriter fstream = new FileWriter("sample-data/more/somemore_full_hashtagwiseLDAspecificMALLET");
        //FileWriter fstream = new FileWriter("sample-data/forLDAdataset3/hour_LDA_events_MALLET.txt");
        //FileWriter fstream = new FileWriter("sample-data/forLDAdataset3/complete_LDA_events_MALLET.txt");
        FileWriter fstream = new FileWriter("sample-data/forLDAdataset3/somemore_full_hashtagwiseLDAevents_MALLET");
		BufferedWriter out = new BufferedWriter(fstream);
        
		int format=0;//1 for changing format...0 otherwise
		int count=0;
		String tweet="";
		
        while(line!=null)
        {
        	count++;
        	line.replaceAll("\n", "");
        	//if(line.length()<10) continue;
        	while(line.length()<10) line=brd.readLine();
        	if(format==1)
        	{
        		//System.out.println("line= _"+line);
				String token[] = line.split("\t");
				//if(token.length<4)  {System.out.print("aaaa");continue;}
				while(token.length<4)
				{
					line=brd.readLine();
					token=line.split("\t");
				}
				if(token[3].length()<5) {System.out.print("bbbb_"+token[3]);continue;}
				tweet=token[3].toLowerCase();
				out.write("X\tX\t"+tweet+"\n");
        	}
        	else out.write("X\tX\t"+line+"\n");
        	if(count%10000==0) 
        		System.out.println("Current count= "+count);
        	line=brd.readLine();
        }
        
        brd.close();
        out.close();
        
        System.out.println("No of documents read/written = "+count);
	}

}
