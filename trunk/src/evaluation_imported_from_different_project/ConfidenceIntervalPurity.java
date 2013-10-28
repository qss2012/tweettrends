
public class ConfidenceIntervalPurity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] purity= {22888,13856,28859,28562,32157,18745,17066,14448,24888,18049};
		double avg = 21951.8;
		int i;
		double sigma=0.0;
		for(i=0;i<10;i++)
		{
			sigma += ((purity[i]-avg)*(purity[i]-avg));
		}
		sigma=sigma/10;
		sigma = Math.sqrt(sigma);
		System.out.println("sigma= "+sigma);
		sigma = 1.96*sigma;
		sigma = sigma/Math.sqrt(10);
		System.out.println("plusminus= "+sigma);
	}

}
