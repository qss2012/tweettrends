package experiments;

import java.io.*;
import java.util.*;
import experiments.HTweet;

public class AssignHashtag implements Serializable{

	static HashMap<String,HTweet> hash;
	static HashMap<Integer,HTweet> nohash;
	
	public static void main(String[] args) throws IOException {
		
		hash = new HashMap<String,HTweet>();
		nohash = new HashMap<Integer,HTweet>();
		
		String file1 = "src/resources/forLDA/hashtagwiseLDAevents.txt";
		//load hashtag-string in the HashMap
		BufferedReader br = new BufferedReader(new FileReader(file1));
		String line = br.readLine();
		int ct=0;
		while(line!=null)
		{
			ct++;
			if(ct%50000==0) System.out.println("ct= "+ct);
			line=line.trim();
			String token[] = line.split("\t");
			if(token.length!=2) {line=br.readLine();continue;}
			hash.put(token[0],new HTweet(token[0],token[1]));
			line=br.readLine();
		}
		br.close();
		ct=0;
		//now read the NoHashtag file Line-By-Line
		String file2="src/resources/forLDA/NOhashtagLDAevents.txt";
		BufferedReader br1 = new BufferedReader(new FileReader(file2));
		line = br1.readLine();
		int twid=0;
		while(line!=null)
		{
			twid++;ct++;
			//put all these tweets which donot have hashtags in nohash hashmap 
			line=line.trim();
			nohash.put(new Integer(twid), new HTweet(null,line));
			line = br1.readLine();
			if(ct%50000==0) System.out.println("ct= "+ ct);
		}
		br1.close();
		System.out.println("Total nohash twid = "+twid+"_"+nohash.size());
		System.out.println("Total hash hahsmapsize = "+hash.size());
		
		//now for each tweet which doesn't have any hashtag calculate its cosine similarity with 
		// of the hash hashmap pooled tweet collection and assign hashtag with max score
		int num_nohash=nohash.size();
		Collection<HTweet> c = nohash.values();
		Iterator<HTweet> itr= c.iterator();
		HTweet ht;
		int count=0;
		int filtered=0;
		double average = 0;
		while(itr.hasNext())
		{
			count++;
			if(count%50000==0) System.out.println("count/total = "+count+"/"+num_nohash);
			ht=itr.next();
			double max=0.0;
			String possibletag="";
			//now for this HTweet ht we will calculate its cosine score with all hash ooled tweets
			Collection<HTweet> c1 = hash.values();
			Iterator<HTweet> it= c1.iterator();
			while(it.hasNext())
			{
				HTweet ht1=it.next();
				//now find the cosine similarity score between ht1 and ht
				double score=cosSimilarityBetweenFreqMaps(ht.wordmap,ht1.wordmap);
				
				if(max<score)
				{
					max=score;
					possibletag=ht1.tag;
				}
			}
			if(max>0.75)
			{
				filtered++;
				ht.tag=possibletag;
				//System.out.println("Max Score = "+ max + " Assigned Hashtag = "+possibletag + "__Tweet= _"+ht.text);
			}
		}
		System.out.println("Total #tweets = "+count + "#tweets with score>0.75 = "+filtered);
	}
	
	public static float getWordFreqFrom(String word, HashMap<String, Float> map) {
		  Float count = map.get(word);
		  if (count == null)
		   return 0f;
		  else
		   return count;
		 }
	
	public static float cosSimilarityBetweenFreqMaps(HashMap<String, Float> map1, HashMap<String, Float> map2) {
			  float d1 = 0f, d2 = 0f;
			  for (Float v : map1.values())
			   d1 += v * v;
			  for (Float v : map2.values())
			   d2 += v * v;
			  float denominator = (float) (Math.sqrt(d1) * Math.sqrt(d2));
			  float numerator = 0f;
			  if (map1.size() <= map2.size()) {
			   for (String key : map1.keySet()) {
			    numerator += map1.get(key) * getWordFreqFrom(key, map2);
			   }
			  } else {
			   for (String key : map2.keySet()) {
			    numerator += map2.get(key) * getWordFreqFrom(key, map1);
			   }
			  }
			  return numerator / denominator;
			 }
	
	static double computescore(HTweet h1,HTweet h2)
	{
		return 1.0;
	}

}
