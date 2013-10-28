import java.io.*;

public class PMI {
	
	public static void main(String args[]) throws IOException
	{
		//String filename="sample-data/topics/1assign_7_hashtag";
		//String filename="sample-data/topics/1more_full_hashtag";
		//String filename = "sample-data/topics/3more_full_hashtag";
		//String filename = "sample-data/topics/3hashtag";
		String filename = "sample-data/topics/1hashtag";
		String dataset="sample-data/forLDAdataset1/GfilEspecific.txt";
        //String dataset="sample-data/forLDAdataset2/GfilEgeneric.txt";
		//String dataset = "sample-data/forLDAdataset3/GfilEevents.txt";
		
		double avg = 1.43,sigma=0.0;
		
		BufferedReader brd = new BufferedReader(new FileReader(filename));
        String line=brd.readLine();
        int l,i,j,k,count=0;
        String array[][]=new String[10][10];
        int tcount[][]=new int[10][10];//topiccount
        
        for(i=0;i<10;i++)
        	for(j=0;j<10;j++)
        		tcount[i][j]=0;
        
        while(line!=null)
        {
        	count++;
        	line=line.trim();
        	String token[]=line.split(" ");
        	l=token.length;
        	//if(l!=10) System.out.println("There should be 10 topics in line number "+count+" as of now, l="+l);
        	for(i=0;i<10;i++)
        	{
        		if(token[i].length()>0) array[count-1][i]=token[i];
        	}
        	line=brd.readLine();
        }
        brd.close();
        //now we have all the topic words in the array
        //now we need to scan through the main dataset file and get the prob for each pair of topics
		int N=0;//totalnumber of tweets
        brd = new BufferedReader(new FileReader(dataset));
        line=brd.readLine();
        double pmi=0.0;
        int score[][][]=new int[10][10][10];
        for(i=0;i<10;i++)
        	for(j=0;j<10;j++)
        		for(k=0;k<10;k++)
        			score[i][j][k]=1;
        while(line!=null)
        {
        	N++;
        	if(N%10000==0) System.out.println(N);
        	line=line.trim();
        	//this is one particular tweet
        	for(i=0;i<10;i++)
        	{
        		for(j=0;j<10;j++)
        		{
        			if(line.contains(array[i][j])) tcount[i][j]++;
        			for(k=j+1;k<10;k++)
        			{
        				if(line.contains(array[i][j]) && line.contains(array[i][k])) score[i][j][k]++;
        			}
        		}
        	}
        	line=brd.readLine();
        }
        //calculating the pmi scores
        double temp=0.0;
        
        for(i=0;i<10;i++)
        {
        	for(j=0;j<10;j++)
        	{
        		for(k=j+1;k<10;k++)
        		{
        			//System.out.println(score[i][j][k]);
        			if(tcount[i][j]==0) tcount[i][j]=1;
        			if(tcount[i][k]==0) tcount[i][k]=1;
        			temp=((double)score[i][j][k]*(double)N)/((double)tcount[i][j]*(double)tcount[i][k]);
        			System.out.println(score[i][j][k]+"___ temp = "+temp);
        			pmi+=Math.log(temp);
        			double ttemp = Math.log(temp);
        			sigma+= (avg - ttemp)*(avg - ttemp);
        		}
        	}
        }
        pmi=pmi/(double)450;
        sigma = sigma/450.0;
        sigma= Math.sqrt(sigma);
        System.out.println("The PMI score = "+pmi);
        System.out.println("The sigma score = "+sigma);
        double plusminus = 1.96*sigma/(Math.sqrt(450.0));
        System.out.println("Plus Minus = "+plusminus);
	}

}

