package lda;


public class LDA {

	public static String file;
	public static int thread;

	public LDA(String file,int thread)
	{
		LDA.file=file;
		LDA.thread=thread;
	//}

	//public static void main(String args[]){
		LDACmdOption option = new LDACmdOption();
		long heapSize = Runtime.getRuntime().totalMemory();
        System.out.println("Heap Size = " + heapSize);
        System.out.println("-------------------------STARTING LDA---------------------");
		//CmdLineParser parser = new CmdLineParser(option);
		option.dfile=file;
		option.modelName="model"+thread;
		try {
			/*if (args.length == 0){
				showHelp(parser);
				return;
			}*/

			//parser.parseArgument(args);

			//if (option.est || option.estc){
				Estimator estimator = new Estimator();
				estimator.init(option);
				System.out.println("done init");
				estimator.estimate();
				System.out.println(estimator.trnModel.K);
				/*for (int i = 0; i < estimator.trnModel.K; ++i){
					//phi: K * V
					System.out.println("-----------------------\ntopic" + i  + " : ");
					for (int j = 0; j < estimator.trnModel.V; ++j){
						System.out.println(estimator.trnModel.phi[i][j]);
					}
				}8?

			//}
			/*else if (option.inf){
				Inferencer inferencer = new Inferencer();
				inferencer.init(option);

				Model newModel = inferencer.inference();

				for (int i = 0; i < newModel.phi.length; ++i){
					//phi: K * V
					System.out.println("-----------------------\ntopic" + i  + " : ");
					for (int j = 0; j < 10; ++j){
						System.out.println(inferencer.globalDict.id2word.get(j) + "\t" + newModel.phi[i][j]);
					}
				}
			}*/
		}
		/*catch (CmdLineException cle){
			System.out.println("Command line error: " + cle.getMessage());
			showHelp(parser);
			return;
		}*/
		catch (Exception e){
			System.out.println("Error in main: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	/*public static void showHelp(CmdLineParser parser){
		System.out.println("LDA [options ...] [arguments...]");
		parser.printUsage(System.out);
	}*/

}
