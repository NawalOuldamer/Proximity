package own.thesis.proximity.strategy.lda.matrix;

import java.util.HashMap;
import java.util.Map.Entry;

public class EstimationGeneralDocumentTopic {

	// we keep all document topics
	//the tag should be appear in topic

	private HashMap<String, Double> Document_Tag_Model = new HashMap<>(); // tags and probability
	private HashMap<String, Double> Document_Model = new HashMap<String, Double>();
	private HashMap<Integer, HashMap<String, Double>> Terms_Topics_Distributions = new HashMap<>(); 


	public EstimationGeneralDocumentTopic(HashMap<String, Double> Document_Tag_Model,HashMap<String, Double> Document_Model,HashMap<Integer, HashMap<String, Double>> Terms_Topics_Distributions){
		this.Document_Model = Document_Model;
		this.Document_Tag_Model = Document_Tag_Model;
		this.Terms_Topics_Distributions = Terms_Topics_Distributions;
	}

	public void estimateDocumentTopicPrior(	HashMap<Integer,Double> Document_Topic_Distribution){
		HashMap<String, Double> Proximity_Document_Prior = new HashMap<>();
		for (Entry<String, Double> entry : this.Document_Tag_Model.entrySet()) {
			String tag = entry.getKey();
			Double tag_pr = entry.getValue();
			int K = Terms_Topics_Distributions.size(); // number of topics
			double tag_weight = 0.d;
			for (int i = 0; i < K; i++) {
				// tag appear in document and topic
				if(Document_Model.containsKey(tag) && Terms_Topics_Distributions.get(i).containsKey(tag_pr)){
					tag_weight += ((Document_Model.get(tag_pr) * Document_Topic_Distribution.get(i) )+ Terms_Topics_Distributions.get(i).get(tag) + tag_pr)/3;
				}
				else {

					// the tag does not appear in document and appear in topic
					tag_weight += (Terms_Topics_Distributions.get(i).get(tag) + tag_pr)/3;
				}
			}
			Proximity_Document_Prior.put(tag, tag_weight);			
		}
	}

	public void estimateTagTopicPrior(HashMap<Integer,Double> Tag_Topic_Distribution){
		HashMap<String, Double> Proximity_Tag_Prior = new HashMap<>();
		for (Entry<String, Double> entry : this.Document_Tag_Model.entrySet()) {
			String tag = entry.getKey();
			Double tag_pr = entry.getValue();
			int K = Terms_Topics_Distributions.size(); // number of topics
			double tag_weight = 0.d;
			for (int i = 0; i < K; i++) {
				// tag appear in document and topic
				if(Document_Model.containsKey(tag) && Terms_Topics_Distributions.get(i).containsKey(tag_pr)){
					tag_weight += ( Document_Model.get(tag_pr) + Terms_Topics_Distributions.get(i).get(tag) + (tag_pr * Tag_Topic_Distribution.get(i)))/3;
				}
				else {
					// the tag does not appear in document and appear in topic
					tag_weight += (Terms_Topics_Distributions.get(i).get(tag) + (tag_pr * Tag_Topic_Distribution.get(i)))/3;
				}

			}
			Proximity_Tag_Prior.put(tag, tag_weight);	
		}
	}


	public void estimatePrior(HashMap<Integer,Double> Document_Topic_Distribution,HashMap<Integer,Double> Tag_Topic_Distribution ){
		HashMap<String, Double> Proximity_Prior = new HashMap<>();
		for (Entry<String, Double> entry : this.Document_Tag_Model.entrySet()) {
			String tag = entry.getKey();
			Double tag_pr = entry.getValue();
			int K = Terms_Topics_Distributions.size(); // number of topics
			double tag_weight = 0.d;
			for (int i = 0; i < K; i++) {
				// tag appear in document and topic
				if(Document_Model.containsKey(tag) && Terms_Topics_Distributions.get(i).containsKey(tag_pr)){
					tag_weight += ( (Document_Model.get(tag_pr) * Document_Topic_Distribution.get(i)) + Terms_Topics_Distributions.get(i).get(tag) + (tag_pr * Tag_Topic_Distribution.get(i)))/3;
				}
				else {
					// the tag does not appear in document and appear in topic
					tag_weight += (Terms_Topics_Distributions.get(i).get(tag) + (tag_pr * Tag_Topic_Distribution.get(i)))/3;
				}

			}
			Proximity_Prior.put(tag, tag_weight);	
		}
	}

	public void estimateNonPrior(){
		HashMap<String, Double> Proximity = new HashMap<>();
		for (Entry<String, Double> entry : this.Document_Tag_Model.entrySet()) {
			String tag = entry.getKey();
			Double tag_pr = entry.getValue();
			int K = Terms_Topics_Distributions.size(); // number of topics
			double tag_weight = 0.d;
			for (int i = 0; i < K; i++) {
				// tag appear in document and topic
				if(Document_Model.containsKey(tag) && Terms_Topics_Distributions.get(i).containsKey(tag_pr)){
					tag_weight += ( (Document_Model.get(tag_pr) ) + Terms_Topics_Distributions.get(i).get(tag) + tag_pr)/3;
				}
				else {
					// the tag does not appear in document and appear in topic
					tag_weight += (Terms_Topics_Distributions.get(i).get(tag))/3;
				}

			}
			Proximity.put(tag, tag_weight);	
		}
	}

}
