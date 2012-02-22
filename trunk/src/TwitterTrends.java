
import datastr.*;
import preprocessing.*;
import java.util.*;

@SuppressWarnings("unused")
public class TwitterTrends {
	
	public static Dataset dataset;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dataset=new Dataset();
		dataset.Tweets=new ArrayList<Tweet>();
		Preprocess prep=new Preprocess("WallStqueries",dataset);
		

	}

}
