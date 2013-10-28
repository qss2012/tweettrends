import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import datastr.CountPerDay;
import datastr.Word;
import datastr.Aggregate;
import datastr.WordCandidateList;
import datastr.WordCandidate;
import preprocessing.LDAInput;
import lda.LDA;
import experiments.Retweets;

@SuppressWarnings("unused")
public class TwitterTrends {
	public static WordCandidateList wcl1,wcl2,wcl3,wcl4;
	public static Date minDate,maxDate;
	
	public static void main(String[] args) throws IOException, RowsExceededException, WriteException, ParseException {
		
		minDate=null;maxDate=null;
		long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println("Heap Size = " + heapSize);
               
        //String mainfile="src/resources/dataset1/specific.txt";
        String mainfile="src/resources/dataset3/events.txt";
        //String mainfile="src/resources/dataset/generic.txt";
        //String mainfile="src/resources/parsed_NZ_tw";
        //String mainfile="src/resources/wc1new";
        //String mainfile="src/resources/Gillard&Rudd";
        //String mainfile="src/resources/Canberra";
        //------------------------------------ INPUT PROCESSING STARTS-------------------------------------------
        /*try {
			//new Input(mainfile,minDate,maxDate);
        	//new Parse("src/resources/1.new.xml",minDate,maxDate);
        	new Parse("src/resources/gillard.xml",minDate,maxDate);
        	System.exit(0);
		} catch (IOException e) {e.printStackTrace();} catch (ParseException e) {e.printStackTrace();}
        */
        //------------------------------------ INPUT PROCESSING ENDS---------------------------------------------
        
        //------------------------------------ DATA STR FOR FINAL RESULTS & LDA STARTS---------------------------
        
        Aggregate ag=new Aggregate();
        ag.finalScores=new ArrayList<CountPerDay>();
        ag.forLDA=new HashMap<String,CountPerDay>();
        ag.LDAdata=new ArrayList<String>();
        wcl1=new WordCandidateList();
		wcl1.list=new HashMap<String,WordCandidate>();
		wcl2=new WordCandidateList();
		wcl2.list=new HashMap<String,WordCandidate>();
		wcl3=new WordCandidateList();
		wcl3.list=new HashMap<String,WordCandidate>();
		wcl4=new WordCandidateList();
		wcl4.list=new HashMap<String,WordCandidate>();
        
        //------------------------------------ DATA STR FOR FINAL RESULTS & LDA ENDS-----------------------------
        
        //------------------------------------ FORFILE PRINTING STARTS-------------------------------------------
        
        FileWriter fstream = new FileWriter("results/words4LDA.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		FileWriter fstream1 = new FileWriter("results/dummy");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter("results/LDAInputFile");
		BufferedWriter out2 = new BufferedWriter(fstream2);

        //------------------------------------ FORFILE PRINTING ENDS---------------------------------------------
        
        //------------------------------------ RUNNING THREADS FOR EACH INPUT FILE STARTS------------------------
        //Thread t1=new Thread1("src/resources/gentest1",ag,out,out1);
		//Thread t2=new Thread2("src/resources/gentest2",ag,out,out1);
		Thread t1=new Thread1(mainfile,ag,out,out1,wcl1);
        /*Thread t2=new Thread2("src/resources/secondpart.txt",ag,out,out1,wcl2);
        Thread t3=new Thread3("src/resources/thirdpart.txt",ag,out,out1,wcl3);
        Thread t4=new Thread4("src/resources/fourthpart.txt",ag,out,out1,wcl4);*/
		
        t1.start();while(t1.isAlive()); 
        
        printLDAData(ag,1);ag.LDAdata.clear();//performLDA(1);
        
        /*t2.start();while(t2.isAlive());
        printLDAData(ag,2);ag.LDAdata.clear();//performLDA(2);
        
        t3.start();while(t3.isAlive());
        printLDAData(ag,3);ag.LDAdata.clear();//performLDA(3);
        
        t4.start();while(t4.isAlive());
        printLDAData(ag,4);ag.LDAdata.clear();//performLDA(4);*/
        //------------------------------------ RUNNING THREADS FOR EACH INPUT FILE ENDS--------------------------
        
        //------------------------------------ RUNNING THE LDA STARTS--------------------------------------------
        /*
         * Using the ag.finalScores arraylist, populated during the 4 threads,
         * get the terms which are important from topic modeling point of view and
         * put them in the HashMap.
         */
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!");
        printFinalScores(ag);
        System.out.println("Creating final HashMap...");
        Iterator<CountPerDay> itr=ag.finalScores.iterator();
		CountPerDay cpd;
		try 
		{
			out.flush();
		while(itr.hasNext())
		{
			cpd=itr.next();//System.out.print("!");
			String word=cpd.word.word;
			if(!ag.forLDA.containsKey(cpd.word.word))
			{
				ag.forLDA.put(cpd.word.word, cpd);
				out.write(word);
				//System.out.print("~"+word+"~");
			}
		}
		}catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		System.out.println("The sizeof final HashMap = "+ag.forLDA.size()+"\n Storing final hashmap in a file");
		
		Iterator<String> stritr=ag.LDAdata.iterator();
		while(stritr.hasNext())
		{
			String temp=stritr.next();
			out2.write(temp);
		}
		
        /*
         * Now using the terms in the HashMap create a file for_LDA where each line will be
         * a document which will be the collection of all tweets containing that term.
         * Now perform LDA on this for_LDA file.
         */
		performLDA(1);
		/*performLDA(2);
		performLDA(3);
		performLDA(4);*/
        //------------------------------------ RUNNING THE LDA ENDS----------------------------------------------
		//new Retweets();
		//HashMap<String,WordCandidate> list=wcl1.getList();
		//ArrayList<String> topicTop10=new ArrayList<String>();
		//prepareForPlot(wcl1,topicTop10);
		
		//new Plot(list,topicTop10,minDate,maxDate);
		
	}
	
	public static void prepareForPlot(WordCandidateList wcl1,ArrayList<String> topicTop10)
	{		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("src/resources/topicTop10"));
		    String word = br.readLine();
		    while(word != null)
		    {
		    	word=word.trim();
		    	topicTop10.add(word);
		    	word = br.readLine();            	  
		    }
		    br.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
	}
	
	public static void performLDA(int thread)
	{
		System.out.println("--------------Perform LDA for Thread "+thread+"---------------------");
		new LDA("src/resources/forLDA"+thread,thread);
	}
	
	public static void printLDAData(Aggregate ag,int thread)
	{
		//this ag.LDAdata was modified last by calculateBurstScore() in Burst.java
		FileWriter fstream;
		try 
		{
			fstream = new FileWriter("src/resources/forLDA"+thread);
			BufferedWriter out = new BufferedWriter(fstream);		
			
			System.out.println("Inside printLDAdata() in TwitterTrends.java....Size of ag.LDAdata= "+ag.LDAdata.size());
			//System.exit(0);
			int cc=0;
			Iterator<String> stritr=ag.LDAdata.iterator();
			while(stritr.hasNext())
			{
				cc++;
				String temp=stritr.next();
				try {
					out.write(temp);
				} catch (IOException e) {e.printStackTrace();}
			}
			out.close();
			System.out.println("Total no. of lines printed in forLDA1= "+cc);
		} catch (IOException e1) {e1.printStackTrace();}
	}
	
		
	public static void printFinalScores(Aggregate ag)
	{
		System.out.println("Storing finalscores array in a file......\nSize of ag.finalScores ="+ag.finalScores.size());

		try 
		{
			FileWriter fstream = new FileWriter("results/final/finalScores_by_Count_then_BurstScores.txt");
			BufferedWriter out11 = new BufferedWriter(fstream);
			Collections.sort(ag.finalScores, new Comparator<Object>(){
		        public int compare(Object o1, Object o2) {
                CountPerDay p1 = (CountPerDay) o1;
                CountPerDay p2 = (CountPerDay) o2;
                /*if(p1.burstScore==p2.burstScore) return 0;
                else if(p1.burstScore<p2.burstScore) return 1;*/
                if(p1.count==p2.count) return 0;
                else if(p1.count<p2.count) return 1;
                else return -1;
		        }
			});
		
			Iterator<CountPerDay> itr=ag.finalScores.iterator();
			CountPerDay cpd;
			while(itr.hasNext())
			{
				cpd=itr.next();
				SimpleDateFormat sdf = new SimpleDateFormat("dd: MMM : yyyy");
				String td=sdf.format(cpd.date); 
				//if(cpd.total_days>20 && cpd.burstScore>1)
				//if(cpd.total_days>2)
				//System.out.print("!");
				//System.out.println("Word: "+cpd.word.word+"\t\t Date: "+td+"\tBurst Scores= "+cpd.burstScore+"\t\tCount= "+cpd.count+"\tNoOfDays= "+cpd.total_days+"\n");
					out11.write("Word: "+cpd.word.word+"\t\t Date: "+td+"\tBurst Scores= "+cpd.burstScore+"\t\tCount= "+cpd.count+"\tNoOfDays= "+cpd.total_days+"\n");
					
			}
			out11.close();
		} catch (IOException e) {e.printStackTrace();}
		System.out.println();
		printFinalScores1(ag);
	}
	
	public static void printFinalScores1(Aggregate ag)
	{
		try 
		{
			FileWriter fstream22 = new FileWriter("results/final/fnlScr_by_BrstScr_den_count.txt");
			BufferedWriter out22 = new BufferedWriter(fstream22);
			Collections.sort(ag.finalScores, new Comparator<Object>(){
		        public int compare(Object o1, Object o2) {
                CountPerDay p1 = (CountPerDay) o1;
                CountPerDay p2 = (CountPerDay) o2;
                if(p1.burstScore==p2.burstScore) return 0;
                else if(p1.burstScore<p2.burstScore) return 1;
                else return -1;
		        }
			});
		
			Iterator<CountPerDay> itr=ag.finalScores.iterator();
			CountPerDay cpd;
			while(itr.hasNext())
			{
				cpd=itr.next();
				SimpleDateFormat sdf = new SimpleDateFormat("dd: MMM : yyyy");
				String td=sdf.format(cpd.date); 
				//if(cpd.total_days>20 && cpd.burstScore>1)
				//if(cpd.total_days>2)
				//System.out.print('@');
				//System.out.println("Word: "+cpd.word.word+"\t\t Date: "+td+"\tBurst Scores= "+cpd.burstScore+"\t\tCount= "+cpd.count+"\tNoOfDays= "+cpd.total_days+"\n");
					out22.write("Word: "+cpd.word.word+"\t\t Date: "+td+"\tBurst Scores= "+cpd.burstScore+"\t\tCount= "+cpd.count+"\tNoOfDays= "+cpd.total_days+"\n");
					
			}
			out22.close();
		} catch (IOException e) {e.printStackTrace();}
		System.out.println();
	}

}

