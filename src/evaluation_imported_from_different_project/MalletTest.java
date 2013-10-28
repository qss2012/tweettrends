

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.topics.MarginalProbEstimator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class MalletTest {
	private static final String[] INPUT_FILES = {
		//        "sample-data\\data_nbn.txt"
		//        "sample-data\\data_wc_smh.txt",
		//        "sample-data/data_ap.txt",
		//        "sample-data\\data_gillard.txt",
		//        "sample-data\\data_nips.txt",
		    	"sample-data/forLDAdataset1/dataset1specificAuthorMALLET.txt",
		//    	"sample-data/forLDAdataset1/dataset1specificHourlyMALLET.txt",
		//    	"sample-data/forLDAdataset1/dataset1specificHashTagMALLET.txt",
		//    	"sample-data/forLDAdataset1/dataset1specificBurstMALLET.txt",
		//    	"sample-data/forLDAdataset1/dataset1specificMALLET.txt",
		//    	"sample-data/forLDAdataset2/dataset2genericMALLET.txt",
		//    	"sample-data/forLDAdataset2/hashtagwiseLDAgenericMALLET.txt",
		//    	"sample-data/forLDAdataset1/dataset1specificHashTagMALLET.txt"
		//    	"sample-data/forLDAdataset2/burstwiseLDAgenericMALLET.txt"
		//    	"sample-data/more/somemore_hashtagwiseLDAspecificMALLET.txt"
		//    	"sample-data/more/somemore_hashtagwiseLDAgenericMALLET.txt"
		//    	"sample-data/more/hashtag_generic_hash_forMALLET"
		//    	"sample-data/more/somemore_hashtagwiseLDAspecificMALLET.txt"
		//    	"sample-data/more/somemore_onethird_hashtagwiseLDAgenericMALLET"
		//    	"sample-data/more/hashtag_generic6_hash_forMALLET"
		//    	"sample-data/more/somemore_full_hashtagwiseLDAgenericMALLET"
		//    	"sample-data/more/hashtag_specific7_hash_forMALLET"
		//    	"/home/rishabh/workspace/malletnew/sample-data/forLDAdataset3/complete_LDA_events_MALLET.txt"
		//    	"sample-data/forLDAdataset3/hour_LDA_events_MALLET.txt"
		//    	"sample-data/forLDAdataset3/somemore_full_hashtagwiseLDAevents_MALLET"
		//"data.txt"
	};

	private static final String STOP_WORDS_FILE = "stoplists/en.txt";
	private static final int NUM_TOPICS = 10;
	private static final int NUM_ITERATIONS = 400;
	private static final int NUM_THREADS = 1;

	private static final int OPTIMIZE_INTERVAL = 0;
	private static final int BURN_IN_PERIOD = 0;

	private static final double ALPHA = 50.0;
	private static final double BETA = 0.01;

	public static void main(String[] args) throws IOException {
		System.out.println(NUM_ITERATIONS + " Iterations; " 
		+ NUM_TOPICS + " topics; optimize interval = " + OPTIMIZE_INTERVAL 
		+ "; burn in period = " + BURN_IN_PERIOD
		+ "; initial alpha = " + (new DecimalFormat("##.#####")).format(ALPHA/NUM_TOPICS) + "; "
		+ "initial beta = " + BETA);
		System.out.println("starting...");

		for(int inputIdx = 0; inputIdx < INPUT_FILES.length; inputIdx++) {
			System.out.println("Processing file " + INPUT_FILES[inputIdx]);
			System.out.println("[Topic No.]  [Topic Proportion]  [Alpha]  [Topic Words]");
			List<Pipe> pipeList = new ArrayList<Pipe>();
			pipeList.add(new CharSequenceLowercase());
			pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
			pipeList.add(new TokenSequenceRemoveStopwords(new File(STOP_WORDS_FILE), "UTF-8", false, false, false));
			pipeList.add(new TokenSequence2FeatureSequence());

			InstanceList instances = new InstanceList(new SerialPipes(pipeList));            
			Reader fileReader = new InputStreamReader(new FileInputStream(new File(INPUT_FILES[inputIdx])));
			instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(.*)$"), 1, 0, 0));

			ParallelTopicModel model = new ParallelTopicModel(NUM_TOPICS, ALPHA, BETA);
			model.setRandomSeed(412321);
			model.addInstances(instances);
			model.setNumThreads(NUM_THREADS);
			model.setNumIterations(NUM_ITERATIONS);
			model.setTopicDisplay(NUM_ITERATIONS, 15);
			model.setBurninPeriod(BURN_IN_PERIOD);
			model.setOptimizeInterval(OPTIMIZE_INTERVAL);
			
			

			long start = System.currentTimeMillis();
			model.estimate();
			long end = System.currentTimeMillis();
			System.out.println("Topic Modelling finished in " + ((end-start)/1000) + " seconds.");
//			Object [][]res = model.getTopWords(10);
//			for(int i=0;i<10;i++)
//			{
//				for(int j=0;j<10;j++)
//				{
//					System.out.print(res[i][j]+"\t");
//				}
//				System.out.println();
//			}
			            
			            double[] alpha = model.getAlpha();
			            System.out.print("Optimized Alpha:  ");
			            for(int i = 0; i < alpha.length; i++) {
			                System.out.print(alpha[i] + ", ");
			            }
			            System.out.println("\nOptimized Beta:  " + model.getBeta());
			            fileReader.close();
			            System.out.println("----------------------------------------------------------------------------------------");
			            
			            
			            //--------------------------------------------------------------------------------------------------------------------
			            
			            //write inference code here using "model" variable
			            
			            
			            String line="";
			            String filename = "sample-data/forLDAdataset1/GfilEspecific.txt";
			            //String filename = "sample-data/forLDAdataset2/GfilEgeneric.txt";
			            //String filename = "sample-data/forLDAdataset2/Generic-temp-Gfile.txt";
			            //String filename = "sample-data/forLDAdataset3/GfilEevents.txt";
			            //String filename = "sample-data/forLDAdataset3/event_dataset";
			            
			            /*FileWriter fstream = new FileWriter(filename);
			    		BufferedWriter out = new BufferedWriter(fstream);*/
			            int ID=0;
			            
			            System.out.println("INFERENCING NOW.....");
			            
			            
			            InstanceList testing = new InstanceList(instances.getPipe());
			            BufferedReader brd = new BufferedReader(new FileReader(filename));
			            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 FIlename = "+filename);
			            line=brd.readLine();
			            int twcount=0,i=0;
			            //HashMap<Integer,String> twid=new HashMap<Integer,String>();
			            String twid[]=new String[1000000];
			            while(line!=null)
			            {
			            	//if(!twid.containsKey(line)) twid.put(new Integer(twcount),line);
			            	twid[twcount]=line;
			            	testing.addThruPipe(new Instance(line, null, "test instance", null));
			            	line=brd.readLine();twcount++;
			            }
			            brd.close();
			            System.out.println("Added instances...going todo inference now...");
			            TopicInferencer inferencer = model.getInferencer();
			            System.out.println("Inference done...now going to populate t_is");
			            
			            
			            //testing the evaluation module
			            System.out.println("-------------------------------------------------------------------------------");
			            System.out.println("EVALUATION module...");
			            
						/*PrintStream outputStream = System.out;
						PrintStream docProbabilityStream = null;
			            String inputFile = "/home/rishabh/workspace/mallet-2.0.7/data/hashtagwiseLDAgenericMALLET1.mallet";
			            */
			            //MarginalProbEstimator evaluator = MarginalProbEstimator.read(new File(evaluatorFilename));
			            //MarginalProbEstimator evaluator = new MarginalProbEstimator(model.numTopics,model.getAlpha(),model.alphaSum,model.getBeta(),model.typeTopicCounts,model.tokensPerTopic);
			            //InstanceList instances1 = InstanceList.load (new File(inputFile));
			            
			            //(int numTopics,double[] alpha,double alphaSum,double beta,int[][] typeTopicCounts,int[] tokensPerTopic)
			            
			            /*double totalLogLikelihood;
			            totalLogLikelihood = evaluator.evaluateLeftToRight(instances, 10, false, docProbabilityStream);
			            System.out.println("Total Log Likelihood = " + totalLogLikelihood);
			            
			            int num_instances = instances.size();
			            System.out.println("No of instances = "+num_instances);
			            double avgLogLikelihood = totalLogLikelihood /num_instances;
			            System.out.println("Average Log Likelihood = " + avgLogLikelihood);
			            */
			            
			            //System.exit(0);
			            
			            //-------------------------------------------------------------------------------
			            //-------------------------------------------------------------------------------
			            //-------------------------------------------------------------------------------
			            //-------------------------------------------------------------------------------
			            //-------------------------------------------------------------------------------
			            
			            /*HashMap<String,Integer> t0=new HashMap<String,Integer>();
			            HashMap<String,Integer> t1=new HashMap<String,Integer>();
			            HashMap<String,Integer> t2=new HashMap<String,Integer>();
			            HashMap<String,Integer> t3=new HashMap<String,Integer>();
			            HashMap<String,Integer> t4=new HashMap<String,Integer>();
			            HashMap<String,Integer> t5=new HashMap<String,Integer>();
			            HashMap<String,Integer> t6=new HashMap<String,Integer>();
			            HashMap<String,Integer> t7=new HashMap<String,Integer>();
			            HashMap<String,Integer> t8=new HashMap<String,Integer>();
			            HashMap<String,Integer> t9=new HashMap<String,Integer>();*/
			            ArrayList<String> t0=new ArrayList<String>();
			            ArrayList<String> t1=new ArrayList<String>();
			            ArrayList<String> t2=new ArrayList<String>();
			            ArrayList<String> t3=new ArrayList<String>();
			            ArrayList<String> t4=new ArrayList<String>();
			            ArrayList<String> t5=new ArrayList<String>();
			            ArrayList<String> t6=new ArrayList<String>();
			            ArrayList<String> t7=new ArrayList<String>();
			            ArrayList<String> t8=new ArrayList<String>();
			            ArrayList<String> t9=new ArrayList<String>();
			            
			            double[] testProb;
			            for(i=0;i<twcount;i++)
			            {
			            	testProb = inferencer.getSampledDistribution(testing.get(i), 10, 1, 5);
			            	double max=0,maxtopic=0;
			            	for(int j=0;j<10;j++)
			            	{
			            		if(testProb[j]>max){max=testProb[j];maxtopic=j;}
			            	}
			            	line=twid[i];
			            	line = line.trim();
			            	/*if(i%1000 == 0) 
			            		System.out.println(twid[i]+" maxtopic = "+maxtopic);*/
			            	if(maxtopic==0) t0.add(line);
			            	if(maxtopic==1) t1.add(line);
			            	if(maxtopic==2) t2.add(line);
			            	if(maxtopic==3) t3.add(line);
			            	if(maxtopic==4) t4.add(line);
			            	if(maxtopic==5) t5.add(line);
			            	if(maxtopic==6) t6.add(line);
			            	if(maxtopic==7) t7.add(line);
			            	if(maxtopic==8) t8.add(line);
			            	if(maxtopic==9) t9.add(line);
			            }
			            
			            System.out.println("twcount= "+twcount);
			            System.out.println("Size of maps = "+t0.size());
			            System.out.println("Size of maps = "+t1.size());
			            System.out.println("Size of maps = "+t2.size());
			            System.out.println("Size of maps = "+t3.size());
			            System.out.println("Size of maps = "+t4.size());
			            System.out.println("Size of maps = "+t5.size());
			            System.out.println("Size of maps = "+t6.size());
			            System.out.println("Size of maps = "+t7.size());
			            System.out.println("Size of maps = "+t8.size());
			            System.out.println("Size of maps = "+t9.size());
			            
			            
			          //populate the g hashmaps
			    		
			            HashMap<String,Integer> g0=new HashMap<String,Integer>();
			            HashMap<String,Integer> g1=new HashMap<String,Integer>();
			            HashMap<String,Integer> g2=new HashMap<String,Integer>();
			            HashMap<String,Integer> g3=new HashMap<String,Integer>();
			            HashMap<String,Integer> g4=new HashMap<String,Integer>();
			            HashMap<String,Integer> g5=new HashMap<String,Integer>();
			            HashMap<String,Integer> g6=new HashMap<String,Integer>();
			            HashMap<String,Integer> g7=new HashMap<String,Integer>();
			            HashMap<String,Integer> g8=new HashMap<String,Integer>();
			            HashMap<String,Integer> g9=new HashMap<String,Integer>();
			            
			            BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset1/appleEVAL.txt"));
			            BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset1/baseballEVAL.txt"));
			            BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset1/burgerkingEVAL.txt"));
			            BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset1/cricketEVAL.txt"));
			            BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset1/franceEVAL.txt"));
			            BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset1/mcdonaldsEVAL.txt"));
			            BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset1/microsoftEVAL.txt"));
			            BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset1/obamaEVAL.txt"));
			            BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset1/sarkozyEVAL.txt"));
			            BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset1/usaEVAL.txt"));
			            
			            /*
			            BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset2/businessEVAL.txt"));
			            BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset2/designEVAL.txt"));
			            BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset2/familyEVAL.txt"));
			            BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset2/foodEVAL.txt"));
			            BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset2/funEVAL.txt"));
			            BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset2/healthEVAL.txt"));
			            BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset2/movieEVAL.txt"));
			            BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset2/musicEVAL.txt"));
			            BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset2/spaceEVAL.txt"));
			            BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset2/sportEVAL.txt"));
			            */
			            /*
			    		BufferedReader b0 = new BufferedReader(new FileReader("sample-data/dataset3/attackEVAL.txt"));
			            BufferedReader b1 = new BufferedReader(new FileReader("sample-data/dataset3/conferenceEVAL.txt"));
			            BufferedReader b2 = new BufferedReader(new FileReader("sample-data/dataset3/Flight_447EVAL.txt"));
			            BufferedReader b3 = new BufferedReader(new FileReader("sample-data/dataset3/Iran_electionEVAL.txt"));
			            BufferedReader b4 = new BufferedReader(new FileReader("sample-data/dataset3/jacksonEVAL.txt"));
			            BufferedReader b5 = new BufferedReader(new FileReader("sample-data/dataset3/LakersEVAL.txt"));
			            BufferedReader b6 = new BufferedReader(new FileReader("sample-data/dataset3/recessionEVAL.txt"));
			            BufferedReader b7 = new BufferedReader(new FileReader("sample-data/dataset3/scandalEVAL.txt"));
			            BufferedReader b8 = new BufferedReader(new FileReader("sample-data/dataset3/swine_fluEVAL.txt"));
			            BufferedReader b9 = new BufferedReader(new FileReader("sample-data/dataset3/T20EVAL.txt"));
			            */
			            //ArrayList<String> everything=new ArrayList<String>();
			            line=b0.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g0.put(line,new Integer(ID));
			            	ID++;
			            	 
			            	line=b0.readLine();
			            }
			            b0.close();
			            System.out.println("Size of g0-maps: "+g0.size());
			            
			            line=b1.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g1.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b1.readLine();
			            }
			            b1.close();
			            System.out.println("Size of g1-maps: "+g1.size());
			            
			            line=b2.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g2.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b2.readLine();
			            }
			            b2.close();
			            System.out.println("Size of g2-maps: "+g2.size());
			            
			            line=b3.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g3.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b3.readLine();
			            }
			            b3.close();
			            System.out.println("Size of g3-maps: "+g3.size());
			            
			            line=b4.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g4.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b4.readLine();
			            }
			            b4.close();
			            System.out.println("Size of g4-maps: "+g4.size());
			            
			            line=b5.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g5.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b5.readLine();
			            }
			            b5.close();
			            System.out.println("Size of g5-maps: "+g5.size());
			            
			            line=b6.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g6.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b6.readLine();
			            }
			            b6.close();
			            System.out.println("Size of g6-maps: "+g6.size());
			            
			            line=b7.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g7.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b7.readLine();
			            }
			            b7.close();
			            System.out.println("Size of g7-maps: "+g7.size());
			            
			            line=b8.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g8.put(line,new Integer(ID));
			            	 
			            	ID++;
			            	line=b8.readLine();
			            }
			            b8.close();
			            System.out.println("Size of g8-maps: "+g8.size());
			            
			            line=b9.readLine();
			            while(line!=null)
			            {
			            	//if(line.length()<2) continue;
			            	g9.put(line,new Integer(ID));
			            	ID++;
			            	 
			            	line=b9.readLine();
			            }
			            b9.close();
			            System.out.println("Size of g9-maps: "+g9.size());
			            
			            
			            System.out.println("Size of g-maps: "+g0.size());
			            System.out.println("Size of g-maps: "+g1.size());
			            System.out.println("Size of g-maps: "+g2.size());
			            System.out.println("Size of g-maps: "+g3.size());
			            System.out.println("Size of g-maps: "+g4.size());
			            System.out.println("Size of g-maps: "+g5.size());
			            System.out.println("Size of g-maps: "+g6.size());
			            System.out.println("Size of g-maps: "+g7.size());
			            System.out.println("Size of g-maps: "+g8.size());
			            System.out.println("Size of g-maps: "+g9.size());
			            int total=g0.size()+g1.size()+g2.size()+g3.size()+g4.size()+g5.size()+g6.size()+g7.size()+g8.size()+g9.size();
			            System.out.println("Total size by Gmaps,from all 10 to GfilEspecific= "+total);
			            int N=total;
			
			            
			
			            
			            
			            //System.out.println("0\t" + testProbabilities[0]);
			            //-----------------------------------------------------------------------------------------
			            //now get the purity scores using t0-t9 and g0-g9
			            int ANS=0;
			            int max=0,j=0;
			            int c[][]=new int[10][10];
			            int sizet[]=new int[10];
			            int sizeg[]=new int[10];
			            sizet[0]=t0.size();
			            sizet[1]=t1.size();
			            sizet[2]=t2.size();
			            sizet[3]=t3.size();
			            sizet[4]=t4.size();
			            sizet[5]=t5.size();
			            sizet[6]=t6.size();
			            sizet[7]=t7.size();
			            sizet[8]=t8.size();
			            sizet[9]=t9.size();
			            sizeg[0]=g0.size();
			            sizeg[1]=g1.size();
			            sizeg[2]=g2.size();
			            sizeg[3]=g3.size();
			            sizeg[4]=g4.size();
			            sizeg[5]=g5.size();
			            sizeg[6]=g6.size();
			            sizeg[7]=g7.size();
			            sizeg[8]=g8.size();
			            sizeg[9]=g9.size();
			            Collection<String> cc;
			            Iterator<String> it;
			            int tt;
			            for(i=0;i<10;i++)
			            	for(j=0;j<10;j++)
			            		c[i][j]=0;
			            //cc=t0. keySet();
			            tt=0;
			    		it=t0.iterator();
			    		String line1;
			    		while(it.hasNext())
			    		{
			    			tt++;
			    			if(tt%1000==0)System.out.print(".");
			    			line1=it.next();
			    			line1= line1.trim();
			    			if(g0.containsKey(line1))c[0][0]++;
			    			if(g1.containsKey(line1))c[0][1]++;
			    			if(g2.containsKey(line1))c[0][2]++;
			    			if(g3.containsKey(line1))c[0][3]++;
			    			if(g4.containsKey(line1))c[0][4]++;
			    			if(g5.containsKey(line1))c[0][5]++;
			    			if(g6.containsKey(line1))c[0][6]++;
			    			if(g7.containsKey(line1))c[0][7]++;
			    			if(g8.containsKey(line1))c[0][8]++;
			    			if(g9.containsKey(line1))c[0][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[0][i]>max) max=c[0][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t1.keySet();
			    		it=t1.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[1][0]++;
			    			if(g1.containsKey(line1))c[1][1]++;
			    			if(g2.containsKey(line1))c[1][2]++;
			    			if(g3.containsKey(line1))c[1][3]++;
			    			if(g4.containsKey(line1))c[1][4]++;
			    			if(g5.containsKey(line1))c[1][5]++;
			    			if(g6.containsKey(line1))c[1][6]++;
			    			if(g7.containsKey(line1))c[1][7]++;
			    			if(g8.containsKey(line1))c[1][8]++;
			    			if(g9.containsKey(line1))c[1][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[1][i]>max) max=c[1][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t2.keySet();
			    		it=t2.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[2][0]++;
			    			if(g1.containsKey(line1))c[2][1]++;
			    			if(g2.containsKey(line1))c[2][2]++;
			    			if(g3.containsKey(line1))c[2][3]++;
			    			if(g4.containsKey(line1))c[2][4]++;
			    			if(g5.containsKey(line1))c[2][5]++;
			    			if(g6.containsKey(line1))c[2][6]++;
			    			if(g7.containsKey(line1))c[2][7]++;
			    			if(g8.containsKey(line1))c[2][8]++;
			    			if(g9.containsKey(line1))c[2][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[2][i]>max) max=c[2][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t3.keySet();
			    		it=t3.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[3][0]++;
			    			if(g1.containsKey(line1))c[3][1]++;
			    			if(g2.containsKey(line1))c[3][2]++;
			    			if(g3.containsKey(line1))c[3][3]++;
			    			if(g4.containsKey(line1))c[3][4]++;
			    			if(g5.containsKey(line1))c[3][5]++;
			    			if(g6.containsKey(line1))c[3][6]++;
			    			if(g7.containsKey(line1))c[3][7]++;
			    			if(g8.containsKey(line1))c[3][8]++;
			    			if(g9.containsKey(line1))c[3][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[3][i]>max) max=c[3][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t4.keySet();
			    		it=t4.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[4][0]++;
			    			if(g1.containsKey(line1))c[4][1]++;
			    			if(g2.containsKey(line1))c[4][2]++;
			    			if(g3.containsKey(line1))c[4][3]++;
			    			if(g4.containsKey(line1))c[4][4]++;
			    			if(g5.containsKey(line1))c[4][5]++;
			    			if(g6.containsKey(line1))c[4][6]++;
			    			if(g7.containsKey(line1))c[4][7]++;
			    			if(g8.containsKey(line1))c[4][8]++;
			    			if(g9.containsKey(line1))c[4][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[4][i]>max) max=c[4][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t5.keySet();
			    		it=t5.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[5][0]++;
			    			if(g1.containsKey(line1))c[5][1]++;
			    			if(g2.containsKey(line1))c[5][2]++;
			    			if(g3.containsKey(line1))c[5][3]++;
			    			if(g4.containsKey(line1))c[5][4]++;
			    			if(g5.containsKey(line1))c[5][5]++;
			    			if(g6.containsKey(line1))c[5][6]++;
			    			if(g7.containsKey(line1))c[5][7]++;
			    			if(g8.containsKey(line1))c[5][8]++;
			    			if(g9.containsKey(line1))c[5][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[5][i]>max) max=c[5][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t6.keySet();
			    		it=t6.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[6][0]++;
			    			if(g1.containsKey(line1))c[6][1]++;
			    			if(g2.containsKey(line1))c[6][2]++;
			    			if(g3.containsKey(line1))c[6][3]++;
			    			if(g4.containsKey(line1))c[6][4]++;
			    			if(g5.containsKey(line1))c[6][5]++;
			    			if(g6.containsKey(line1))c[6][6]++;
			    			if(g7.containsKey(line1))c[6][7]++;
			    			if(g8.containsKey(line1))c[6][8]++;
			    			if(g9.containsKey(line1))c[6][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[6][i]>max) max=c[6][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t7.keySet();
			    		it=t7.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[7][0]++;
			    			if(g1.containsKey(line1))c[7][1]++;
			    			if(g2.containsKey(line1))c[7][2]++;
			    			if(g3.containsKey(line1))c[7][3]++;
			    			if(g4.containsKey(line1))c[7][4]++;
			    			if(g5.containsKey(line1))c[7][5]++;
			    			if(g6.containsKey(line1))c[7][6]++;
			    			if(g7.containsKey(line1))c[7][7]++;
			    			if(g8.containsKey(line1))c[7][8]++;
			    			if(g9.containsKey(line1))c[7][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[7][i]>max) max=c[7][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t8.keySet();
			    		it=t8.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1))c[8][0]++;
			    			if(g1.containsKey(line1))c[8][1]++;
			    			if(g2.containsKey(line1))c[8][2]++;
			    			if(g3.containsKey(line1))c[8][3]++;
			    			if(g4.containsKey(line1))c[8][4]++;
			    			if(g5.containsKey(line1))c[8][5]++;
			    			if(g6.containsKey(line1))c[8][6]++;
			    			if(g7.containsKey(line1))c[8][7]++;
			    			if(g8.containsKey(line1))c[8][8]++;
			    			if(g9.containsKey(line1))c[8][9]++;
			    		}
			    		max=0;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[8][i]>max) max=c[8][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            //for(i=0;i<10;i++) c[i]=0;
			            //cc=t9.keySet();
			    		it=t9.iterator();
			    		while(it.hasNext())
			    		{
			    			line1=it.next();
			    			if(g0.containsKey(line1)){ c[9][0]++;}
			    			if(g1.containsKey(line1)){ c[9][1]++;}
			    			if(g2.containsKey(line1)){ c[9][2]++;}
			    			if(g3.containsKey(line1)){ c[9][3]++;}
			    			if(g4.containsKey(line1)){ c[9][4]++;}
			    			if(g5.containsKey(line1)){ c[9][5]++;}
			    			if(g6.containsKey(line1)){ c[9][6]++;}
			    			if(g7.containsKey(line1)){ c[9][7]++;}
			    			if(g8.containsKey(line1)){ c[9][8]++;}
			    			if(g9.containsKey(line1)){ c[9][9]++;}
			    		}
			    		max=0;
			    		double avg = 0.604;
			    		for(i=0;i<10;i++)
			    		{
			    			if(c[9][i]>max) max=c[9][i];
			    		}
			            ANS+=max;
			            System.out.println("Ans = "+ANS +" max= "+max);
			            
			            System.out.println("N= "+N);
			            System.out.println("ANS= "+ANS);
			            double purity=(double)ANS/N;
			            System.out.println("-------------------------------Purity= "+purity);
			            //Calculating the NMI scores
			            for(i=0;i<10;i++)
			            	System.out.print(sizet[i]+"_");
			            System.out.println();
			            for(i=0;i<10;i++)
			            	System.out.print(sizeg[i]+"_");
			            System.out.println();
//			            System.out.println("Calculating the NMI Scores...");
//			            //calculating I(T;G)
//			            double temp=0,temp1=0,valI=0,ht=0,hg=0,nmi=0;
//			            for(i=0;i<10;i++)
//			            {
//			            	for(j=0;j<10;j++)
//			            	{
//			            		if(c[i][j]==0) c[i][j]=1;
//			            		temp=0.0;
//			            		//if(c[i][j]<0) {System.out.print("Whoa1!");break;}
//			            		temp=(double)(c[i][j])*(double)N;
//			            		//if(temp<0) {System.out.print("Whoa!");break;}
//			            		System.out.print("C[][]*N= "+temp);
//			            		temp1=(double)temp/((double)((double)sizet[i]*(double)sizeg[j]));
//			            		
//			            		System.out.print(" C[][]*N / sizet*sizeg= "+temp1);
//			            		temp=Math.log(temp1);
//			            		System.out.print(" LOG < C[][]*N / sizet*sizeg > = "+temp);
//			                	//System.out.println("c[i][j]= "+c[i][j]+" temp1 = "+temp1+" temp=log(temp1) = "+temp);
//			
//			            		//if(temp<0.0001) temp=0.0001;
//			            		temp1=(double)temp*(double)c[i][j]/(double)N;
//			            		//System.out.print(" C[][]*LOG C[][]*N / sizet*sizeg   / n= "+temp1);
//			            		valI+=temp1;
//			            		//System.out.println(" Current valI = "+valI);
//			            	}
//			            }
//			            System.out.println("The value of I(T;G) = "+valI);
//			            System.out.println("Calculating the value of H(T) and H(G)");
//			            for(i=0;i<10;i++)
//			            {
//			            	temp=(double)sizet[i]/(double)N;
//			            	
//			            	temp1=Math.log(temp);
//			            	//System.out.println("temp = "+temp+" temp1=log(temp) = "+temp1);
//			            	//if(temp1<0.0001) temp1=0.0001;
//			            	temp=(double)temp1*(double)sizet[i]/(double)N;
//			            	ht+=temp;
//			            	
//			            	temp=(double)sizeg[i]/(double)N;
//			            	temp1=Math.log(temp);
//			            	//if(temp1<0.0001) temp1=0.0001;
//			            	temp=(double)temp1*(double)sizeg[i]/(double)N;
//			            	hg+=temp;
//			            }
//			            System.out.println("The value of H(T) = "+ht+" and the value of H(G) = "+hg);
//			            nmi=(double)2*(double)valI/(double)(ht*hg);
//			            System.out.println("-------------------------------The value of NMI Score = "+nmi);
			            //----------------------------------------------------------------------------------------------------
			        }
			        

			System.out.println("DONE!");
		}
	}

//}