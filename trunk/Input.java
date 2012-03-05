import java.io.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import datastr.Tweet;

public class Input {
	
	public String filename;
	
	@SuppressWarnings("unused")
	//public static void main() throws IOException, ParseException
	public Input(String filename) throws IOException, ParseException
	{
		this.filename=filename;
		Date minDate,maxDate;
		System.out.println("Hey There!");
		BufferedReader brd = new BufferedReader(new FileReader("src/resources/"+filename));
        String singleline = brd.readLine();
        Tweet t=new Tweet(singleline);
        minDate=maxDate=t.getDate();int tot=0;
        while(singleline != null)
        {
        	Tweet tw=new Tweet(singleline);tot++;
        	if(minDate.compareTo(tw.getDate())>0) minDate=tw.getDate();
    		if(maxDate.compareTo(tw.getDate())<0) maxDate=tw.getDate();
    		singleline=brd.readLine();
        }
        int days=noofdays(minDate,maxDate);
        int first,second,third,fourth;
        first=days/4;second=first+days/4;third=second+days/4;fourth=first+days;
        int count=0;
        Date tempdate=minDate,firstd,secondd,thirdd;
        firstd=secondd=thirdd=minDate;
        while(tempdate.compareTo(maxDate)<0)
		{
        	count++;			
			Calendar cal = Calendar.getInstance();
			cal.setTime (tempdate);
			cal.add (Calendar.DATE, 1);
			tempdate=cal.getTime();
			if(count==first) firstd=tempdate;
			if(count==second) secondd=tempdate;
			if(count==third) thirdd=tempdate;
		}
        System.out.println(firstd+" "+secondd+" "+thirdd);
        int outfile=1;
        FileWriter fstream1 = new FileWriter("src/resources/firstpart.txt");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		FileWriter fstream2 = new FileWriter("src/resources/secondpart.txt");
		BufferedWriter out2 = new BufferedWriter(fstream2);
		FileWriter fstream3 = new FileWriter("src/resources/thirdpart.txt");
		BufferedWriter out3 = new BufferedWriter(fstream3);
		FileWriter fstream4 = new FileWriter("src/resources/fourthpart.txt");
		BufferedWriter out4 = new BufferedWriter(fstream4);
        BufferedReader br = new BufferedReader(new FileReader("src/resources/"+filename));
        String  line= br.readLine();int c1=0,c2=0,c3=0,c4=0;
        while(line!=null)
        {
        	Tweet tw=new Tweet(line);
        	Date d=tw.getDate();
			if(d.compareTo(firstd)<0) outfile=1;
			else if(d.compareTo(secondd)<0 && d.compareTo(firstd)>0) outfile=2;
			else if(d.compareTo(thirdd)<0 && d.compareTo(secondd)>0) outfile=3;
			else outfile=4;
			if(outfile==1)
			{
				if(c1<tot/4)
				{
					out1.write(line+"\n");c1++;
				}
				else
				{
					if(c2<tot/4) {out2.write(line+"\n");c2++;}
					else if(c3<tot/4) {out3.write(line+"\n");c3++;}
					else {out4.write(line+"\n");c4++;}
				}
			}
			if(outfile==2)
			{
				if(c2<tot/4)
				{
					out2.write(line+"\n");c2++;
				}
				else
				{
					if(c3<tot/4) {out3.write(line+"\n");c3++;}
					else if(c4<tot/4) {out4.write(line+"\n");c4++;}
					else {out1.write(line+"\n");c1++;}
				}
			}
			if(outfile==3)
			{
				if(c3<tot/4)
				{
					out3.write(line+"\n");c3++;
				}
				else
				{
					if(c4<tot/4) {out4.write(line+"\n");c4++;}
					else if(c1<tot/4) {out1.write(line+"\n");c1++;}
					else {out2.write(line+"\n");c2++;}
				}
			}
			if(outfile==4)
			{
				if(c4<tot/4)
				{
					out4.write(line+"\n");c4++;
				}
				else
				{
					if(c3<tot/4) {out3.write(line+"\n");c3++;}
					else if(c2<tot/4) {out2.write(line+"\n");c2++;}
					else {out1.write(line+"\n");c1++;}
				}
			}
			line=br.readLine();
		}
	}
	
	public static int noofdays(Date start, Date end)
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

}

