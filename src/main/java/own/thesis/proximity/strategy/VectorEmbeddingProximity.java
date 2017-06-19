/**
 * 
 */
package own.thesis.proximity.strategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import own.thesis.w2v.VectorSimilarity;

/**
 * @author ould
 *
 */
public class VectorEmbeddingProximity {

	/**
	 * @param args
	 */
	

	private HashMap<String, Double> Document_Tag = new HashMap<>(); // tags and frequencies
	private HashMap<String, Double> Document_Model = new HashMap<String, Double>();
	private WordVectors WORDS_VECTORS;
	private HashMap<String, Double> Proximity = new HashMap<>();
	private double sim_threshold = 0.d;

	public VectorEmbeddingProximity(double sim_threshold, HashMap<String, Double> Document_Model, HashMap<String, Double> Document_Tag,WordVectors WORDS_VECTORS){
		this.Document_Tag = Document_Tag;
		this.WORDS_VECTORS  = WORDS_VECTORS;
		this.Document_Model = Document_Model;
		this.sim_threshold = sim_threshold;
	}
	
	/**
	 * w_tg = for all terms in document sim(tg,t) * p(t,d)
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public HashMap<String, Double>  estimate() throws NumberFormatException, IOException{
		for (Entry<String, Double> entry : this.Document_Tag.entrySet()) {
			String tag = entry.getKey();
			double score_tag = 0.d;
			for (Entry<String, Double> entry1 : Document_Model.entrySet()) {
				String term = entry1.getKey();
				double term_pr = entry1.getValue();
				VectorSimilarity vecSim = new VectorSimilarity(WORDS_VECTORS);
				double sim = vecSim.estimateNormalizeVectorSimilarity(tag, term);
				if(sim>=sim_threshold){
					score_tag += sim * term_pr;
				}
			}
			if(score_tag!=0)
				Proximity.put(tag, score_tag);			
		}
		return this.Proximity;
	}


	public HashMap<String, Double>  normalization(){
		double sum = this.Proximity.values().stream().reduce(0.0, Double::sum);
		for (Entry<String, Double> entry : this.Proximity.entrySet()) {
			this.Proximity.put(entry.getKey(), entry.getValue()/sum);
		}
		return this.Proximity;
	}

	public HashMap<String, Double> getProximity() {
		return Proximity;
	}
}
