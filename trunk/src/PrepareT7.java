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


@SuppressWarnings("unused")
public class PrepareT7 {
	
	public static void main(String args[]) throws IOException, ParseException
	{
		/*
		FileWriter fstream1 = new FileWriter("src/resources/dataset1/obama.txt");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter("src/resources/dataset1/sarkozy.txt");
		BufferedWriter out2 = new BufferedWriter(fstream2);
		FileWriter fstream3 = new FileWriter("src/resources/dataset1/baseball.txt");
		BufferedWriter out3 = new BufferedWriter(fstream3);
		FileWriter fstream4 = new FileWriter("src/resources/dataset1/cricket.txt");
		BufferedWriter out4 = new BufferedWriter(fstream4);
		FileWriter fstream5 = new FileWriter("src/resources/dataset1/mcdonalds.txt");
		BufferedWriter out5 = new BufferedWriter(fstream5);
		FileWriter fstream6 = new FileWriter("src/resources/dataset1/burgerking.txt");
		BufferedWriter out6 = new BufferedWriter(fstream6);
		FileWriter fstream7 = new FileWriter("src/resources/dataset1/apple.txt");
		BufferedWriter out7 = new BufferedWriter(fstream7);
		FileWriter fstream8 = new FileWriter("src/resources/dataset1/microsoft.txt");
		BufferedWriter out8 = new BufferedWriter(fstream8);
		FileWriter fstream9 = new FileWriter("src/resources/dataset1/usa.txt");
		BufferedWriter out9 = new BufferedWriter(fstream9);
		FileWriter fstream10 = new FileWriter("src/resources/dataset1/france.txt");
		BufferedWriter out10 = new BufferedWriter(fstream10);
		*/
		/*
		FileWriter fstream1 = new FileWriter("src/resources/dataset/music.txt");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter("src/resources/dataset/business.txt");
		BufferedWriter out2 = new BufferedWriter(fstream2);
		FileWriter fstream3 = new FileWriter("src/resources/dataset/movie.txt");
		BufferedWriter out3 = new BufferedWriter(fstream3);
		FileWriter fstream4 = new FileWriter("src/resources/dataset/design.txt");
		BufferedWriter out4 = new BufferedWriter(fstream4);
		FileWriter fstream5 = new FileWriter("src/resources/dataset/food.txt");
		BufferedWriter out5 = new BufferedWriter(fstream5);
		FileWriter fstream6 = new FileWriter("src/resources/dataset/fun.txt");
		BufferedWriter out6 = new BufferedWriter(fstream6);
		FileWriter fstream7 = new FileWriter("src/resources/dataset/health.txt");
		BufferedWriter out7 = new BufferedWriter(fstream7);
		FileWriter fstream8 = new FileWriter("src/resources/dataset/family.txt");
		BufferedWriter out8 = new BufferedWriter(fstream8);
		FileWriter fstream9 = new FileWriter("src/resources/dataset/sport.txt");
		BufferedWriter out9 = new BufferedWriter(fstream9);
		FileWriter fstream10 = new FileWriter("src/resources/dataset/space.txt");
		BufferedWriter out10 = new BufferedWriter(fstream10);
		*/
		
		FileWriter fstream1 = new FileWriter("src/resources/dataset3/jackson.txt");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter("src/resources/dataset3/conference.txt");
		BufferedWriter out2 = new BufferedWriter(fstream2);
		FileWriter fstream3 = new FileWriter("src/resources/dataset3/recession.txt");
		BufferedWriter out3 = new BufferedWriter(fstream3);
		FileWriter fstream4 = new FileWriter("src/resources/dataset3/swine_flu.txt");
		BufferedWriter out4 = new BufferedWriter(fstream4);
		FileWriter fstream5 = new FileWriter("src/resources/dataset3/T20.txt");
		BufferedWriter out5 = new BufferedWriter(fstream5);
		FileWriter fstream6 = new FileWriter("src/resources/dataset3/Iran_election.txt");
		BufferedWriter out6 = new BufferedWriter(fstream6);
		FileWriter fstream7 = new FileWriter("src/resources/dataset3/scandal.txt");
		BufferedWriter out7 = new BufferedWriter(fstream7);
		FileWriter fstream8 = new FileWriter("src/resources/dataset3/attack.txt");
		BufferedWriter out8 = new BufferedWriter(fstream8);
		FileWriter fstream9 = new FileWriter("src/resources/dataset3/Flight_447.txt");
		BufferedWriter out9 = new BufferedWriter(fstream9);
		FileWriter fstream10 = new FileWriter("src/resources/dataset3/Lakers.txt");
		BufferedWriter out10 = new BufferedWriter(fstream10);
		
		String mainfile="src/resources/month/tweets2009-06.txt";
		int count=0;
		BufferedReader br;
		try {
			
				String temp="";
				final String twitter_date="yyyy-mm-dd HH:mm:ss";//"EEE, dd MMMM yyyy HH:mm:ss Z";
				SimpleDateFormat sf = new SimpleDateFormat(twitter_date);
				sf.setLenient(true);
				Date d;
			
				br = new BufferedReader(new FileReader(mainfile));
				String line = br.readLine();
				line=br.readLine();
			 
				int c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
				c1=c2=c3=c4=c5=c6=c7=c8=c9=c10=0;
				
				while(line != null)
				{
					count++;
					//if(count>50000) break;
					if(count%50000==0)System.out.println(count);
					//System.out.println("line= _"+line);
					int ww=0;
					System.out.println("ww = "+ww+"_"+c1+"_"+c2+"_"+c3+"_"+c4+"_"+c5+"_"+c6+"_"+c7+"_"+c8+"_"+c9+"_"+c10);
					if(line.length()>1)
					{
						//System.out.println("line= _"+line);
						//if(line.contains("RT: Men in civilian clothes attacked Day hospital")) {line = br.readLine(); continue;}
						//if(line.contains("I'm not goin to hate! Congrads lakers!")) {line = br.readLine(); continue;}
						//if(line.contains("Congrads lakers!")) {line = br.readLine(); continue;}
						String token[] = line.split("\t");
						if(token.length<2) continue;
						//time
						if(token[1].length() > 20) {line = br.readLine(); continue;}
						try
						{
							d=sf.parse(token[1]);
						}
						catch (ParseException e) {line = br.readLine(); continue;};
						SimpleDateFormat sdfDestination = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss Z");
						String date = sdfDestination.format(d);
						//user
						line = br.readLine();
						//System.out.println("line= _"+line);
						String token1[] = line.split("\t");
						if(token1.length<2) continue;;
						String user=token1[1];
						//tweet text
						line = br.readLine();
						//System.out.println("line= _"+line);
						String token2[] = line.split("\t");
						if(token2.length<2) continue;;
						String tweet=token2[1].toLowerCase();
						temp="";
						//combine them all
						temp=user+"\t"+user+"\t"+date+"\t"+tweet+"\n";
						//System.out.println("temp= _"+temp);
						int te=0;
						if(tweet.contains("jackson")) {if(c1>15000) continue; out1.write(temp);te=1;c1++;}
						if(tweet.contains("conference")) {if(c2>15000) continue; out2.write(temp);te=1;c2++;}
						if(tweet.contains("recession")) {if(c3>15000) continue; out3.write(temp);te=1;c3++;}
						if(tweet.contains("wine flu")) {if(c4>15000) continue; out4.write(temp);te=1;c4++;}
						if(tweet.contains("t20")) {if(c5>15000) continue; out5.write(temp);te=1;c5++;}
						if(tweet.contains("iran election")) {if(c6>15000) continue; out6.write(temp);te=1;c6++;}
						if(tweet.contains("scandal")) {if(c7>15000) continue; out7.write(temp);te=1;c7++;}
						if(tweet.contains("attack")) {if(c8>15000) continue; out8.write(temp);te=1;c8++;}
						if(tweet.contains("light 447")) {if(c9>15000) continue; out9.write(temp);te=1;c9++;}
						if(tweet.contains("lakers")) {if(c10>15000) continue; out10.write(temp);te=1;c10++;}
						//if (te==1) System.out.println("count= "+count+" tweet= "+temp);
						if(te==1)
						{
							ww++;
							if(ww%100 ==0 ) System.out.println("ww = "+ww+"_"+c1+"_"+c2+"_"+c3+"_"+c4+"_"+c5+"_"+c6+"_"+c7+"_"+c8+"_"+c9+"_"+c10);
						}
							
						temp="";te=0;
					}
					line = br.readLine();            	  
				}
			br.close();
		} 
		//catch (ParseException e) {System.out.println("Error!");} 
		catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		
	}

}
