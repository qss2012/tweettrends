import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModifyEval {
	
	public static void main(String args[]) throws IOException
	{
		/*String mainfile="sample-data/dataset2/.txt";
		
		BufferedReader brd = new BufferedReader(new FileReader(mainfile));
        String line = brd.readLine();
        */
		/*
		BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset1/apple.txt"));
        BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset1/baseball.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset1/burgerking.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset1/cricket.txt"));
        BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset1/france.txt"));
        BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset1/mcdonalds.txt"));
        BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset1/microsoft.txt"));
        BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset1/obama.txt"));
        BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset1/sarkozy.txt"));
        BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset1/usa.txt"));
        
        FileWriter fstream0 = new FileWriter("sample-data/dataset1/appleEVAL.txt");
        FileWriter fstream1 = new FileWriter("sample-data/dataset1/baseballEVAL.txt");
        FileWriter fstream2 = new FileWriter("sample-data/dataset1/burgerkingEVAL.txt");
        FileWriter fstream3 = new FileWriter("sample-data/dataset1/cricketEVAL.txt");
        FileWriter fstream4 = new FileWriter("sample-data/dataset1/franceEVAL.txt");
        FileWriter fstream5 = new FileWriter("sample-data/dataset1/mcdonaldsEVAL.txt");
        FileWriter fstream6 = new FileWriter("sample-data/dataset1/microsoftEVAL.txt");
        FileWriter fstream7 = new FileWriter("sample-data/dataset1/obamaEVAL.txt");
        FileWriter fstream8 = new FileWriter("sample-data/dataset1/sarkozyEVAL.txt");
        FileWriter fstream9 = new FileWriter("sample-data/dataset1/usaEVAL.txt");
		*/
		/*
        BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset2/business.txt"));
        BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset2/design.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset2/family.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset2/food.txt"));
        BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset2/fun.txt"));
        BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset2/health.txt"));
        BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset2/movie.txt"));
        BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset2/music.txt"));
        BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset2/space.txt"));
        BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset2/sport.txt"));
		
        FileWriter fstream0 = new FileWriter("sample-data/dataset2/businessEVAL.txt");
        FileWriter fstream1 = new FileWriter("sample-data/dataset2/designEVAL.txt");
        FileWriter fstream2 = new FileWriter("sample-data/dataset2/familyEVAL.txt");
        FileWriter fstream3 = new FileWriter("sample-data/dataset2/foodEVAL.txt");
        FileWriter fstream4 = new FileWriter("sample-data/dataset2/funEVAL.txt");
        FileWriter fstream5 = new FileWriter("sample-data/dataset2/healthEVAL.txt");
        FileWriter fstream6 = new FileWriter("sample-data/dataset2/movieEVAL.txt");
        FileWriter fstream7 = new FileWriter("sample-data/dataset2/musicEVAL.txt");
        FileWriter fstream8 = new FileWriter("sample-data/dataset2/spaceEVAL.txt");
        FileWriter fstream9 = new FileWriter("sample-data/dataset2/sportEVAL.txt");
		*/
		
		BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset3/attack.txt"));
        BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset3/conference.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset3/Flight_447.txt"));
        BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset3/Iran_election.txt"));
        BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset3/jackson.txt"));
        BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset3/Lakers.txt"));
        BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset3/recession.txt"));
        BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset3/scandal.txt"));
        BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset3/swine_flu.txt"));
        BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset3/T20.txt"));
		
        FileWriter fstream0 = new FileWriter("sample-data/dataset3/attackEVAL.txt");
        FileWriter fstream1 = new FileWriter("sample-data/dataset3/conferenceEVAL.txt");
        FileWriter fstream2 = new FileWriter("sample-data/dataset3/Flight_447EVAL.txt");
        FileWriter fstream3 = new FileWriter("sample-data/dataset3/Iran_electionEVAL.txt");
        FileWriter fstream4 = new FileWriter("sample-data/dataset3/jacksonEVAL.txt");
        FileWriter fstream5 = new FileWriter("sample-data/dataset3/LakersEVAL.txt");
        FileWriter fstream6 = new FileWriter("sample-data/dataset3/recessionEVAL.txt");
        FileWriter fstream7 = new FileWriter("sample-data/dataset3/scandalEVAL.txt");
        FileWriter fstream8 = new FileWriter("sample-data/dataset3/swine_fluEVAL.txt");
        FileWriter fstream9 = new FileWriter("sample-data/dataset3/T20EVAL.txt");
		
        BufferedWriter out0 = new BufferedWriter(fstream0);
		BufferedWriter out1 = new BufferedWriter(fstream1);
		BufferedWriter out2 = new BufferedWriter(fstream2);
		BufferedWriter out3 = new BufferedWriter(fstream3);
		BufferedWriter out4 = new BufferedWriter(fstream4);
		BufferedWriter out5 = new BufferedWriter(fstream5);
		BufferedWriter out6 = new BufferedWriter(fstream6);
		BufferedWriter out7 = new BufferedWriter(fstream7);
		BufferedWriter out8 = new BufferedWriter(fstream8);
		BufferedWriter out9 = new BufferedWriter(fstream9);
		
		String line="";
		
		line=b0.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b0.readLine();continue;}
        	out0.write(token[3]+"\n");
        	line=b0.readLine();
        }
        b0.close();
        
        line=b1.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b1.readLine();continue;}
        	out1.write(token[3]+"\n");
        	line=b1.readLine();
        }
        b1.close();
        
        line=b2.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b2.readLine();continue;}
        	out2.write(token[3]+"\n");
        	line=b2.readLine();
        }
        b2.close();
        
        line=b3.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b3.readLine();continue;}
        	out3.write(token[3]+"\n");
        	line=b3.readLine();
        }
        b3.close();
        
        line=b4.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b4.readLine();continue;}
        	out4.write(token[3]+"\n");
        	line=b4.readLine();
        }
        b4.close();
        
        line=b5.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b5.readLine();continue;}
        	out5.write(token[3]+"\n");
        	line=b5.readLine();
        }
        b5.close();
        
        line=b6.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b6.readLine();continue;}
        	out6.write(token[3]+"\n");
        	line=b6.readLine();
        }
        b6.close();
        
        line=b7.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b7.readLine();continue;}
        	out7.write(token[3]+"\n");
        	line=b7.readLine();
        }
        b7.close();
        
        line=b8.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b8.readLine();continue;}
        	out8.write(token[3]+"\n");
        	line=b8.readLine();
        }
        b8.close();
        
        line=b9.readLine();
        while(line!=null)
        {
        	String token[]=line.split("\t");
        	if(token.length<4) {line=b9.readLine();continue;}
        	out9.write(token[3]+"\n");
        	line=b9.readLine();
        }
        b9.close();
        
        out0.close();
        out1.close();
        out2.close();
        out3.close();
        out4.close();
        out5.close();
        out6.close();
        out7.close();
        out8.close();
        out9.close();
	}
}
