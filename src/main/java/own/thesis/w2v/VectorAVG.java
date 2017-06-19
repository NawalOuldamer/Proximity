/**
 * 
 */
package own.thesis.w2v;

import java.util.Iterator;
import java.util.List;

/**
 * @author ould
 *
 */
public class VectorAVG {

	/**
	 * @param args
	 */
	
	
	
	public double[] buildVectorAvg(List<double[]> list , int dimension){
		double[] vector_avg = new double [dimension];
		for (Iterator<double[]> iterator = list.iterator(); iterator.hasNext();) {
			double[] vector =  iterator.next();
			for (int i = 0; i < vector.length; i++) {
				vector_avg[i] = vector_avg[i] + vector[i];
			}
		}
		for (int i = 0; i < vector_avg.length; i++) {
			vector_avg[i] = vector_avg[i] / vector_avg.length;
		}
		
		return vector_avg;
	}

}
