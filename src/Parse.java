import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;

public class Parse {
	public String filename;
 
	public Parse(String filename,Date minDate, Date maxDate) throws IOException, ParseException {
		
		int count=0;
		//FileWriter fstream = new FileWriter("src/resources/parsed_NZ_tw");
	FileWriter fstream = new FileWriter("src/resources/parsed_gillard");
		BufferedWriter out = new BufferedWriter(fstream);
		String str1="",str2="";
		
	  try{
		//this.filename="src/resources/tweetnz.nf.xml";
		//File fXmlFile = new File("c:\\file.xml");
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("heading");
		System.out.println("-----------------------");
 
		for (int temp = 0; temp < nList.getLength(); temp++) {
 
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
		      Element eElement = (Element) nNode;

		      count++;
		      if(count%20000==0) System.out.println("Inside Parse.java"+count);
		      String a=getTagValue("author", eElement);
		      String b=getTagValue("time", eElement);
	          String c=getTagValue("text", eElement);
	          
	         final String start_date="yyyy-mm-dd HH:mm:ss";//"EEE, dd MMMM yyyy HH:mm:ss Z";
	 		 SimpleDateFormat sf = new SimpleDateFormat(start_date);
	 		 sf.setLenient(true);
	 		 Date d= (Date) sf.parse(b);
	 		 if(temp==0) minDate=maxDate=d;
	 		if(minDate.compareTo(d)>0) minDate=d;
    		if(maxDate.compareTo(d)<0) maxDate=d;
	 		 
	 		 SimpleDateFormat sdfDestination = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss Z");
	 	     String end_date = sdfDestination.format(d);
	 	     str1=sdfDestination.format(minDate);
	 	     str2=sdfDestination.format(maxDate);
	 	     //System.out.println(end_date);
	 	     out.write(a+"\t"+a+"\t"+end_date+"\t"+c+"\n");
		      //System.out.println("Salary : " + getTagValue("salary", eElement));
 
		   }
		}
		
		FileWriter fstream1 = new FileWriter("src/resources/dates");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		out1.write(str1+"\n"+str2+"\n");
		out1.close();
		System.out.println("here1= "+minDate+maxDate);
	  } catch (Exception e) {
		e.printStackTrace();
	  }
	  System.out.println("Count_in_Parse= "+count);
  }
 
  private static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
 
        Node nValue = (Node) nlList.item(0);
 
	return nValue.getNodeValue();
  }
 
}