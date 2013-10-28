import java.io.*;

public class TopicsPerDoc {

	public static void main(String[] args) {
		float arr[][]=new float[100000][9];
		int count=0;
		
		BufferedReader br;
		try {
			//br = new BufferedReader(new FileReader("src/resources/prob/gillard&rudd_unpooled"));
			br = new BufferedReader(new FileReader("src/resources/prob/wc_hashtagwise"));
		    String line = br.readLine();
		    int length=0;
		    while(line != null)
		    {
		    	
		    	line=line.trim();
		    	String token[] = line.split("\t");
		    	length=token.length;
		    	//System.out.print("split length="+length);
		    	if(length!=9) {line = br.readLine();continue;}
		    	
		    	for(int i=0;i<9;i++)
		    	{
		    		arr[count][i]=Float.parseFloat(token[i]);
		    	}
		    	count++;
		    	if(count%10000==0) System.out.print(count+"_");
		    	line = br.readLine();            	  
		    }
		    br.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
			
			System.out.println("Count= "+count);
			//now we have the prob in the arr array.
			//now we need to calculate the 2^entropy score...
			
			float avgTopPerDoc[]=new float[count];
			float nonzero[]=new float[count];
			
			float totalnonzero=0;
			
			for(int i=0;i<count;i++)
			{
				float temp=0;
				for(int j=0;j<9;j++)
				{
					float t1=arr[i][j];
					if(arr[i][j]!=0) nonzero[i]++;
					if(t1<0.00001) t1=(float) 0.0001;
					float t2=(float) Math.log10(t1);
					//float t3=t2*t1;//entropy
					float t3=t2;//self-information
					t3*=-1;
					temp+=t3;
				}
				avgTopPerDoc[i]=temp;
				totalnonzero+=nonzero[i];
			}
			
			System.out.println("Done calculating the entropy....finding the 2^entropy now");
			
			float avg=0;
			for(int i=0;i<count;i++)
			{
				avgTopPerDoc[i]=(float) Math.pow(2,avgTopPerDoc[i]);
				avg+=avgTopPerDoc[i];
			}
			avg=avg/count;
			System.out.println("Avg no of topics per doc in this dataset = "+avg);
			
			float avgNonZero=totalnonzero/count;
			System.out.println("Avg no of non-0 Prob = "+avgNonZero);
	}

}
