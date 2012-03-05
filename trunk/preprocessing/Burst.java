package preprocessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import datastr.*;
import preprocessing.*;

@SuppressWarnings("unused")
public class Burst {
	
	public WordCandidateList wcl;
	public Dataset dataset;
	public int thread;
	public Aggregate ag;
	public BufferedWriter out;
	public BufferedWriter out1;
	
	public Burst(Dataset dataset,WordCandidateList wcl,int thread,Aggregate ag,BufferedWriter out,BufferedWriter out1)
	{
		//initialize the requirements
		this.ag=ag;
		this.out=out;
		this.out1=out1;
		this.thread=thread;
		this.dataset=dataset;
		this.wcl=wcl;
		
		// now use each word in dictionary to populate this wcl.list

		System.out.println("Populating List");


		/* Added all possible words in the corpus to the wcl.list
		 * now we need to scan through all the tweets and populate 
		 * the dayList ie wc.dayList
		 */


		/* We now have the all-word list (=wcl.list) containing each word and 
		 * the corresponding tweet(and date) it occurs in.
		 * Now we need to calculate the mean for each day[between dataset.minDate and maxDate] 
		 * for each word in the list by adding 1 to wc.mean_R_d where wc is obtained by
		 * iterating through thewcl.list arraylist   
		 */

		System.out.println("No of days = "+noofdays(dataset.minDate,dataset.maxDate));
		System.out.println("Size of wcl.list = "+wcl.list.size());
		System.out.println("Calculating Mean");
		
		calculateMean();
		
		/*
		 * Now we need to calculate the Standard Deviation independently for each term/word in wcl.list    
		 */
		
		System.out.println("Calculating StdDev");
		calculateSD();
		
		/*
		 * Now using the count, Mean and StdDev calculate the Burst score for each CountPerDay for each word in wcl.list
		 */
		
		System.out.println("Calculating Burst Score & preparing files for LDA");
		calculateBurstScore();
		System.out.println("Printing Burst Score");
		printBurstScores();
	}
	
	
	public void printBurstScores()
	{
		try
		{
			FileWriter fstream = new FileWriter("results/BurstScores"+thread);
			BufferedWriter out = new BufferedWriter(fstream);
			
			
			Collection<WordCandidate> c=wcl.list.values();
			Iterator<WordCandidate> itr=c.iterator();	
			WordCandidate wc;
			while(itr.hasNext())
			{
				wc=itr.next();
				out.write(wc.word.word+"   :   Mean: "+wc.mean_R_d+"  Std Dev = "+wc.stddev+"  total_no_of_tweets_appearingIn = "+wc.total_days+"\n Burst Scores = ");
				Iterator<CountPerDay> itrdl=wc.dayList.iterator();
				CountPerDay cpd;
				while(itrdl.hasNext())
				{
					cpd=itrdl.next();
					dataset.scoresArr.add(cpd);
					out.write("Count= "+cpd.count+" Score= "+cpd.burstScore+" || ");
				}
				out.write("\n");
			}
			
		} catch (IOException e) {e.printStackTrace();}
				
	}
	
	public void calculateBurstScore()
	{
		Collection<WordCandidate> c=wcl.list.values();
		Iterator<WordCandidate> itr=c.iterator();
		WordCandidate wc;
		double mean,stddev;
		while(itr.hasNext())//for each word in the list
		{
			//for this word, using the mean and stddev, calculate, for each entry in dayList, the Burst Score
			wc=itr.next();
			mean=wc.mean_R_d;
			stddev=wc.stddev;
			Iterator<CountPerDay> itrdaylist=wc.dayList.iterator();
			CountPerDay cpd;
			while(itrdaylist.hasNext())
			{
				cpd=itrdaylist.next();
				cpd.burstScore=(cpd.count-mean)/stddev;
				/*
				 * Based on the heuristics, add this cpd to the ag.finalScores arraylist
				 */
				//----------------------------------Updating ag.finalScoress & printing for LDA start----------------------------------
					if(cpd.count-mean>30) 
						{
							ag.finalScores.add(cpd);
							if(!ag.forLDA.containsKey(cpd.word.word))
							{
								ag.forLDA.put(cpd.word.word, cpd);
								try 
								{
									out.write(cpd.word.word+"\n");
									// for this word, write to out1 the relevant tweets in a single line
									Iterator<Tweet> twitr=dataset.Tweets.iterator();
									String temp="";int l=0;
									while(twitr.hasNext())
									{
										if(l>20) break;
										Tweet tw=twitr.next();
										if(tw.getText().contains(cpd.word.word)) {temp+=tw.getTest_no_sw().trim();l++;}//out1.write(tw.getText()+". ");
									}
									temp+="\n";
									ag.LDAdata.add(temp);
								} catch (IOException e) {e.printStackTrace();}
							}
						}
				//----------------------------------Updating ag.finalScoress & printing for LDA end----------------------------------
			}
		}
	}
	
	public void calculateSD()
	{
		Collection<WordCandidate> c=wcl.list.values();
		Iterator<WordCandidate> itr=c.iterator();
		
		//Iterator<WordCandidate> itr=wcl.list.iterator();
		WordCandidate wc;
		double mean;
		while(itr.hasNext())//for each word in the list
		{
			wc=itr.next();//now find SD for this particular wc
			mean=wc.mean_R_d;
			Iterator<CountPerDay> itrdaylist=wc.dayList.iterator();
			CountPerDay cpd;
			double temp=0;
			while(itrdaylist.hasNext())
			{
				cpd=itrdaylist.next();
				temp+=(cpd.count-mean)*(cpd.count-mean);
			}
			wc.stddev=temp/noofdays(dataset.minDate,dataset.maxDate);
			wc.stddev=Math.sqrt(wc.stddev);
		}
	}
	
	public void calculateMean()
	{
		Date tempdate=dataset.minDate;
		int n=noofdays(dataset.minDate,dataset.maxDate);
		int cc=0;
		{
			/*
			 * Now for this date, scan through all words in the wcl.list list
			 * and for each word sctweets...find all tweets having this date
			 *   
			 */
			
			Collection<WordCandidate> c=wcl.list.values();
			Iterator<WordCandidate> itr=c.iterator();
			
			WordCandidate wc;
			while(itr.hasNext())//for each word in the list
			{
				wc=itr.next();
				wc.mean_R_d=(double)wc.total_days/(double)n;
				cc++;				
			}
		}
	}
	
	public int noofdays(Date start, Date end)
	{
		int ans=0;
		Date tempdate=start;
		while(tempdate.compareTo(end)<0)
		{
			ans++;
			Calendar cal = Calendar.getInstance();
			cal.setTime (tempdate);
			cal.add (Calendar.DATE, 1);
			tempdate=cal.getTime();
		}
		return ans;
	}
	
	/*public void populateDayList()
	{
		Iterator<Tweet> tweetItr=dataset.Tweets.iterator();
		Tweet temptw;
		while(tweetItr.hasNext())//for each tweet
		{
			temptw=tweetItr.next();
			String tweet=temptw.getText();
			tweet=tweet.toLowerCase();
			Date tweetdate=temptw.getDate();
			//for each word in the wcl.list of all words, see if that word appears in this tweet
			Iterator<WordCandidate> itr=wcl.list.iterator();
			while(itr.hasNext())//for each word in the list
			{
				WordCandidate wc=itr.next();
				//for this WordCandidate, see if this date has been added already in dayList
				if(tweet.contains(wc.word.word)) //add a CountPerDay in the dayList if this word present in this tweet
				{
					//iterate through the dayList to see if tweetdate is present or not
					wc.total_days++;
					Iterator<CountPerDay> daylistitr = wc.dayList.iterator();
					int flag=0;
					//System.out.print("FOr the word "+wc.word.word+"      ");
					while(daylistitr.hasNext())
					{
						//System.out.println(wc.word.word+"inside while");
						CountPerDay cpd=daylistitr.next();
						//if(cpd.date.compareTo(tweetdate)==0) {cpd.count++;flag=1;break;}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						if(sdf.format(cpd.date).equals(sdf.format(tweetdate))) {cpd.count++;flag=1;break;}
					}
					if(flag==0)//if this date is not present then add a CountPerDay
					{
						//System.out.println(wc.word.word+" inside flag=0");
						CountPerDay cpd=new CountPerDay(tweetdate,1);
						wc.dayList.add(cpd);
					}
				}
			}
		}
	}*/
	
	/*public void populateList()
	{
		Collection<Word> c=dataset.dictionary.values();
		Iterator<Word> itr=c.iterator();
		Word tw;int size=0;
		while(itr.hasNext()) //iterate over each word of the dictionary
		{
			tw=itr.next();
			if(tw.count>1000)
			{
				WordCandidate wc=new WordCandidate(tw);
				wcl.list.add(wc);size++;
			}
		}
		System.out.println("Size of the wcl.list = "+size);
	}*/

}

