package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import datastr.Aggregate;
import datastr.CountPerDay;
import datastr.Tweet;

public class LDAInput {
	
	public LDAInput(Aggregate ag,String mainfile)
	{
		/*
		 * For each word in the HashMap print all tweets containing that word as a single document in
		 * a file forLDA in src/resources folder
		 */
		try
		{
			FileWriter fstream = new FileWriter("results/LDAInputFinal");
			BufferedWriter out = new BufferedWriter(fstream);
			BufferedReader brd; 
			Iterator<Entry<String, CountPerDay>> itr=ag.forLDA.entrySet().iterator();
			while (itr.hasNext()) 
			{
				Entry<String, CountPerDay> pairs = itr.next();
		        CountPerDay cpd=(CountPerDay) pairs.getValue();
				brd = new BufferedReader(new FileReader(mainfile));
		        //brd = new BufferedReader(new FileReader("src/resources/gentest1"));
				String line = brd.readLine();
		        while(line != null)
				{       	
					Tweet tw=new Tweet(line);
					line=tw.getText();
					if(line.contains(cpd.word.word)) out.write(line+".");
					line=brd.readLine();
				}
		        out.write("\n");
			}
			
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (ParseException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
	}

}
