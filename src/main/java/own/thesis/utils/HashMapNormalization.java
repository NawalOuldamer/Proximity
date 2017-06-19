/**
 * 
 */
package own.thesis.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ould
 *
 */
public class HashMapNormalization {

	/**
	 * @param args
	 */
	
	/**
	 *Normalization
	 **/	
	
	public HashMap<String, Double> Normalize(HashMap<String, Double> New_Document_Model){
		double sum = New_Document_Model.values().stream().mapToDouble(Number::intValue).sum();
		for (Map.Entry<String,Double> entry : New_Document_Model.entrySet()) {
			New_Document_Model.put(entry.getKey(), entry.getValue()/sum);
		}
		return New_Document_Model;
	}
		
	
}
