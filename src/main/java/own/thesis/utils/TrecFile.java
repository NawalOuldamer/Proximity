package own.thesis.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Nawal OULD-AMER
 *
 */
public class TrecFile {

	private String PATH_STORE_RESULTS;
	/**
	 * @param args
	 * @throws IOException 
	 * 
	 */
	public TrecFile(String PATH_STORE_RESULTS){
		this.PATH_STORE_RESULTS = PATH_STORE_RESULTS;
	}
	
	public void saveTrecFile(String queryID, List<Map.Entry<String, Double>> results,String model) throws IOException{
		FileWriter fw = new FileWriter(PATH_STORE_RESULTS+model,true);
		int count = 0;
		for (Iterator<Entry<String, Double>> iterator = results.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			fw.write(queryID+" "+"Q0"+" "+entry.getKey()+" "+count+" "+entry.getValue()+" "+model+" \n");	
			count++;
		}
		fw.close();
	}
}