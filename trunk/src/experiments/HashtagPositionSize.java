package experiments;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashtagPositionSize {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//String filename = "src/resources/forLDA/GfilEspecific.txt";
		//String filename = "/home/rishabh/workspace/malletnew/sample-data/forLDAdataset1/GfilEspecific.txt";
		String filename = "/home/rishabh/workspace/malletnew/sample-data/forLDAdataset2/GfilEgeneric.txt";
		
		BufferedReader brd = new BufferedReader(new FileReader(filename));
        String line=brd.readLine();
        int tcount=0, count = 0, mid=0, start=0, end=0;
        String tag = "";
        String REGEX = "(\\s|\\A)#(\\w+)";
        String INPUT = "";
        int avg_size_hashtag = 0;
        int twithtag=0;
        
        while(line!=null)
        {
        	tcount++;
        	line=line.trim();
        	INPUT = line;
		    Pattern p = Pattern.compile(REGEX);
		    Matcher m = p.matcher(INPUT); // get a matcher object
		    int temp=0;

		       while(m.find())
		       {
		    	   temp++;
		    	   count++;
		    	   tag=m.group();
		    	   avg_size_hashtag += tag.length();
		    	   if(line.startsWith(tag)) start++;
		    	   else if(line.endsWith(tag)) end++;
		    	   else mid++;
		       }
		       if(temp!=0) twithtag++;
		       temp=0;
        	line=brd.readLine();
        }
        
        double avglen = (double)avg_size_hashtag / (double) count;
        
        System.out.println("Tweet Count = "+tcount);
        System.out.println("Tweets with hashtags = "+twithtag);
        System.out.println("Hashtag Count = "+count);
        System.out.println("Start = "+start);
        System.out.println("Mid = "+mid);
        System.out.println("End = "+end);
        System.out.println("Avg Length of Hashtags = "+avglen);

	}

}
