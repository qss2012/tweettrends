import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import datastr.CountPerDay;
import datastr.Word;
import datastr.Aggregate;
import preprocessing.LDAInput;
import lda.LDA;

@SuppressWarnings("unused")
public class TwitterTrends {
	
	
	public static void main(String[] args) throws IOException {
		
		long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println("Heap Size = " + heapSize);
        String mainfile="src/resources/WallStqueries";
        //------------------------------------ INPUT PROCESSING STARTS-------------------------------------------
        try {
			new Input(mainfile);
		} catch (IOException e) {e.printStackTrace();} catch (ParseException e) {e.printStackTrace();}
        
        //------------------------------------ INPUT PROCESSING ENDS---------------------------------------------
        
        //------------------------------------ DATA STR FOR FINAL RESULTS & LDA STARTS---------------------------
        
        Aggregate ag=new Aggregate();
        ag.finalScores=new ArrayList<CountPerDay>();
        ag.forLDA=new HashMap<String,CountPerDay>();
        ag.LDAdata=new ArrayList<String>();
        
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
        Thread t1=new Thread1("src/resources/firstpart.txt",ag,out,out1);
        Thread t2=new Thread2("src/resources/secondpart.txt",ag,out,out1);
        Thread t3=new Thread3("src/resources/thirdpart.txt",ag,out,out1);
        Thread t4=new Thread4("src/resources/fourthpart.txt",ag,out,out1);
        
        t1.start();while(t1.isAlive()); 
        printLDAData(ag,1);ag.LDAdata.clear();//performLDA(1);
        
        t2.start();while(t2.isAlive());
        printLDAData(ag,2);ag.LDAdata.clear();//performLDA(2);
        
        t3.start();while(t3.isAlive());
        printLDAData(ag,3);ag.LDAdata.clear();//performLDA(3);
        
        t4.start();while(t4.isAlive());
        printLDAData(ag,4);ag.LDAdata.clear();//performLDA(4);
        //------------------------------------ RUNNING THREADS FOR EACH INPUT FILE ENDS--------------------------
        
        //------------------------------------ RUNNING THE LDA STARTS--------------------------------------------
        /*
         * Using the ag.finalScores arraylist, populated during the 4 threads,
         * get the terms which are important from topic modeling point of view and
         * put them in the HashMap.
         */
        printFinalScores(ag);
        System.out.println("Creating final HashMap...");
        Iterator<CountPerDay> itr=ag.finalScores.iterator();
		CountPerDay cpd;
		try 
		{
			out.flush();
		while(itr.hasNext())
		{
			cpd=itr.next();
			String word=cpd.word.word;
			if(!ag.forLDA.containsKey(cpd.word.word))
			{
				ag.forLDA.put(cpd.word.word, cpd);
				out.write(word);

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
        performLDA(2);performLDA(3);performLDA(4);
        //------------------------------------ RUNNING THE LDA ENDS----------------------------------------------
	}
	
	public static void performLDA(int thread)
	{
		System.out.println("--------------Perform LDA for Thread "+thread+"---------------------");
		new LDA("src/resources/forLDA"+thread,thread);
	}
	
	public static void printLDAData(Aggregate ag,int thread)
	{
		FileWriter fstream;
		try 
		{
			fstream = new FileWriter("src/resources/forLDA"+thread);
			BufferedWriter out = new BufferedWriter(fstream);		
			Iterator<String> stritr=ag.LDAdata.iterator();
			while(stritr.hasNext())
			{
				String temp=stritr.next();
				try {
					out.write(temp);
				} catch (IOException e) {e.printStackTrace();}
			}
		} catch (IOException e1) {e1.printStackTrace();}
	}
	
		
	public static void printFinalScores(Aggregate ag)
	{
		System.out.println("Storing finalscores array in a file......\nSize of ag.finalScores ="+ag.finalScores.size());

		try 
		{
			FileWriter fstream = new FileWriter("results/final/finalScores_by_Count_then_BurstScores");
			BufferedWriter out = new BufferedWriter(fstream);
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
				if(cpd.total_days>2)
					out.write("Word: "+cpd.word.word+"\t\t Date: "+td+"\tBurst Scores= "+cpd.burstScore+"\t\tCount= "+cpd.count+"\t\t\t"+cpd.total_days+"\n");
			}
		} catch (IOException e) {e.printStackTrace();}
		printFinalScores1(ag);
	}
	
	public static void printFinalScores1(Aggregate ag)
	{
		try 
		{
			FileWriter fstream = new FileWriter("results/final/finalScores_by_BurstScores_then_count");
			BufferedWriter out = new BufferedWriter(fstream);
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
				if(cpd.total_days>2)
					out.write("Word: "+cpd.word.word+"\t\t Date: "+td+"\tBurst Scores= "+cpd.burstScore+"\t\tCount= "+cpd.count+"\t\t\t"+cpd.total_days+"\n");
			}
		} catch (IOException e) {e.printStackTrace();}
	}

}

