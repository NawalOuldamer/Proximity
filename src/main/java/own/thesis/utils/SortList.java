package own.thesis.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortList {

	
	public SortList(){		
	}
	
	public List<Map.Entry<String, Double>> sort(HashMap<String, Double> map, Integer topkList){
		List<Entry<String, Double>> list = sortByValues(map,true);  // sort by high value of term weight			
		List<Map.Entry<String, Double>> topK = getTopK(topkList, list);	
		return topK;
	}
	
	public List<Map.Entry<String, Double>> sortByValues(Map<String, Double> unsortMap, final boolean order) {
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});
		return list;
	}
	
	public List<Map.Entry<String, Double>> getTopK(Integer k, List<Map.Entry<String, Double>> map) {
		HashMap<String, Double> m = new HashMap<String, Double>();
		for (Iterator<Entry<String, Double>> iterator = map.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			m.put(entry.getKey(), entry.getValue());
		}
		List<Map.Entry<String, Double>> sorted = sortByValues(m, false);
		k = k < sorted.size() ? k : sorted.size();
		return sorted.subList(0, k);
	} 


}
