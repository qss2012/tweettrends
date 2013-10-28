import java.io.BufferedReader;
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date; 
import java.util.HashMap;
import java.util.Iterator;

import jxl.*; 
import jxl.write.*; 
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

import datastr.CountPerDay;
import datastr.WordCandidateList;
import datastr.WordCandidate;

public class Plot {
	
	public Plot(HashMap<String,WordCandidate> list, ArrayList<String> topicTop10,Date minDate, Date maxDate) throws IOException, RowsExceededException, WriteException, ParseException
	{
		
		WordCandidateList wcl=null;
		/*try {
			
			FileInputStream fileIn =new FileInputStream("src/resources/wcl1.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			wcl = (WordCandidateList) in.readObject();
			in.close();
			fileIn.close();
		} catch (ClassNotFoundException e1) {e1.printStackTrace();}
		*/
		
		
		
		Date array[]=new Date[1000]; int k=0;
		int array2[]=new int[1000];
		WritableWorkbook workbook = Workbook.createWorkbook(new File("results/final/output.xls"));
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		Label label = new Label(0, 0, "DATE"); 
		sheet.addCell(label); 
		int j=1;
		//Dates
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("src/resources/dates"));
		    String word = br.readLine();
		    	word=word.trim();
		    	
		    	final String start_date="EEE, dd MMMM yyyy HH:mm:ss Z";
		 		 SimpleDateFormat sf = new SimpleDateFormat(start_date);
		 		 sf.setLenient(true);
		 		 minDate= (Date) sf.parse(word);
		    	
		    	word = br.readLine();
		    	
		    	maxDate= (Date) sf.parse(word);
		    	
		    br.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
			//Dates
		Date tempdate=minDate;
		//System.out.println("haha--"+tempdate+" "+minDate+" "+maxDate);
		while(tempdate.compareTo(maxDate)<0)
		{
			array[k++]=tempdate;
			Label label2 = new Label(0, j, tempdate.toString()); 
			sheet.addCell(label2);
			Calendar cal = Calendar.getInstance();
			cal.setTime (tempdate);
			cal.add (Calendar.DATE, 1);
			tempdate=cal.getTime();
			j++;
		}
		
		int i=1;
		Iterator<String> it=topicTop10.iterator();
		HashMap<String, Integer> topics = new HashMap<String, Integer>();
		while(it.hasNext())
		{
			String str=it.next();
			topics.put(str, new Integer(i));
			Label label1 = new Label(i, 0, str); 
			sheet.addCell(label1);
			i++;
		}
		
		//Collection<WordCandidate> c=wcl.list.values();
		//Collection<WordCandidate> c=list.values();
		//Iterator<WordCandidate> itr=c.iterator();	
		//WordCandidate wc;
		/*while(itr.hasNext())
		{			
			wc=itr.next();
			String wrd=wc.word.word;
			if(topics.containsKey(wrd))
			{
				for(int jj=0;jj<k;jj++)
				{
					Date td=array[jj];
					ArrayList<CountPerDay> dayList=wc.dayList;
					Iterator<CountPerDay> itt=dayList.iterator();
					CountPerDay cpd;
					while(itt.hasNext())
					{
						cpd=itt.next();
						if(cpd.date.compareTo(td)==0)
						{
							array2[jj]=cpd.count;
						}
					}
				}
				System.out.println("For the word= "+wrd);
				for(int h=0;h<k;h++)
				{
					System.out.println("Date= "+array[h].toString()+" count= "+array2[h]);
				}
			}
			//System.out.print(wc.word.word+" ");
		}
		*/
		
		Number number = new Number(3, 4, 3.1459); 
		sheet.addCell(number);
		workbook.write(); 
		workbook.close();
	}

}
