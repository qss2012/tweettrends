

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class PrepareForEval {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String line="";
        String filename="sample-data/forLDAdataset1/GfilEspecific.txt";
        
        FileWriter fstream = new FileWriter(filename);
		BufferedWriter out = new BufferedWriter(fstream);
        int ID=0;
      //populate the g hashmaps
		
        HashMap<Integer,String> g0=new HashMap<Integer,String>();
        HashMap<Integer,String> g1=new HashMap<Integer,String>();
        HashMap<Integer,String> g2=new HashMap<Integer,String>();
        HashMap<Integer,String> g3=new HashMap<Integer,String>();
        HashMap<Integer,String> g4=new HashMap<Integer,String>();
        HashMap<Integer,String> g5=new HashMap<Integer,String>();
        HashMap<Integer,String> g6=new HashMap<Integer,String>();
        HashMap<Integer,String> g7=new HashMap<Integer,String>();
        HashMap<Integer,String> g8=new HashMap<Integer,String>();
        HashMap<Integer,String> g9=new HashMap<Integer,String>();
       
        BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset1/appleEVAL.txt"));
        BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset1/baseballEVAL.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset1/burgerkingEVAL.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset1/cricketEVAL.txt"));
        BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset1/franceEVAL.txt"));
        BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset1/mcdonaldsEVAL.txt"));
        BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset1/microsoftEVAL.txt"));
        BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset1/obamaEVAL.txt"));
        BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset1/sarkozyEVAL.txt"));
        BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset1/usaEVAL.txt"));
        
        /*
        BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset2/businessEVAL.txt"));
        BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset2/designEVAL.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset2/familyEVAL.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset2/foodEVAL.txt"));
        BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset2/funEVAL.txt"));
        BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset2/healthEVAL.txt"));
        BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset2/movieEVAL.txt"));
        BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset2/musicEVAL.txt"));
        BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset2/spaceEVAL.txt"));
        BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset2/sportEVAL.txt"));
        */
        ArrayList<String> everything=new ArrayList<String>();
        line=b0.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g0.put(new Integer(ID),line);
        	ID++;
        	out.write(line+"\n");
        	line=b0.readLine();
        }
        b0.close();
        
        line=b1.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g1.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b1.readLine();
        }
        b1.close();
        
        line=b2.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g2.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b2.readLine();
        }
        b2.close();
        
        line=b3.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g3.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b3.readLine();
        }
        b3.close();
        
        line=b4.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g4.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b4.readLine();
        }
        b4.close();
        
        line=b5.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g5.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b5.readLine();
        }
        b5.close();
        
        line=b6.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g6.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b6.readLine();
        }
        b6.close();
        
        line=b7.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g7.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b7.readLine();
        }
        b7.close();
        
        line=b8.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g8.put(new Integer(ID),line);
        	out.write(line+"\n");
        	ID++;
        	line=b8.readLine();
        }
        b8.close();
        
        line=b9.readLine();
        while(line!=null)
        {
        	//if(line.length()<2) continue;
        	g9.put(new Integer(ID),line);
        	ID++;
        	out.write(line+"\n");
        	line=b9.readLine();
        }
        b9.close();
        

	}

}
