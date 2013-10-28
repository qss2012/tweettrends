package lda;



public class LDACmdOption {

	//@Option(name="-est", usage="Specify whether we want to estimate model from scratch")
	public boolean est = true;

	//@Option(name="-estc", usage="Specify whether we want to continue the last estimation")
	public boolean estc = false;

	//@Option(name="-inf", usage="Specify whether we want to do inference")
	public boolean inf = true;

	//@Option(name="-dir", usage="Specify directory")
	public String dir = "results";

	//@Option(name="-dfile", usage="Specify data file")
	public String dfile = "forLDA";

	//@Option(name="-model", usage="Specify the model name")
	public String modelName = "moddel";

	//@Option(name="-alpha", usage="Specify alpha")
	public double alpha = -1.0;

	//@Option(name="-beta", usage="Specify beta")
	public double beta = -1.0;

	//@Option(name="-ntopics", usage="Specify the number of topics")
	public int K = 10;

	//@Option(name="-niters", usage="Specify the number of iterations")
	public int niters = 10;

	//@Option(name="-savestep", usage="Specify the number of steps to save the model since the last save")
	public int savestep = 100;

	//@Option(name="-twords", usage="Specify the number of most likely words to be printed for each topic")
	public int twords = 20;

	//@Option(name="-withrawdata", usage="Specify whether we include raw data in the input")
	public boolean withrawdata = false;

	//@Option(name="-wordmap", usage="Specify the wordmap file")
	public String wordMapFileName = "wordmap.txt";
}
