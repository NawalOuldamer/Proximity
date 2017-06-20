package own.thesis.proximity.strategy.lda.matrix;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class LDA_Matrix_Estimation {

	private HashMap<String, Double> Document_Tag_Model = new HashMap<>(); // tags and probability
	private HashMap<Integer, HashMap<String, Double>> TOPICS_TERMS_DISRIBUTIONS = new HashMap<>(); // contains only top k terms for each topic
	private HashMap<String, Double> Document_Model = new HashMap<String, Double>();
	private HashMap<Integer, Double> Document_Topic_Distribution = new HashMap<>(); // LDA Document Topic Distribution
	private HashMap<Integer, Double> Tag_Topic_Distribution = new HashMap<>(); // LDA Tag Topic Distribution

	
	
	
	private HashMap<String, Double> Proximity_Document_Prior = new HashMap<>();
	private HashMap<String, Double> Proximity_Tag_Prior = new HashMap<>();
	private HashMap<String, Double> Proximity_Prior = new HashMap<>();
	private HashMap<String, Double> Proximity = new HashMap<>();

	
	public LDA_Matrix_Estimation(HashMap<String, Double> Document_Tag_Model,HashMap<Integer, HashMap<String, Double>> TOPICS_TERMS_DISRIBUTIONS ,HashMap<String, Double> Document_Model,
			HashMap<Integer, Double> Document_Topic_Distribution, HashMap<Integer, Double> Tag_Topic_Distribution){
		this.Document_Model = Document_Model;
		this.Document_Tag_Model = Document_Tag_Model;
		this.TOPICS_TERMS_DISRIBUTIONS = TOPICS_TERMS_DISRIBUTIONS;
		this.Tag_Topic_Distribution = Tag_Topic_Distribution;
		this.Document_Topic_Distribution = Document_Topic_Distribution;
		
	}

	/**
	 * The document prior means we take account for the topic of document
	 * TopKTopicTerms : we take only on top k terms in topics (top 20)
	 * This method take account of document topic  (d,z)
	 * for each tag, we estimate it weight using the matrix 
	 * for all document topics w_t = w_t,z + (w_t,d * d,k)+ w_t,ds  
	 */

	
	/**
	 * All topics are used
	 * @param config
	 */
	public void estimationGeneral(String MATRIX_CONFIG){
		switch (MATRIX_CONFIG) {
		case "TOP_TERMS":{
			// remove tags does not appears in topic vocabulary
			HashMap<String, Double> new_document_tag_model = new HashMap<>();
			new_document_tag_model =  getMapTopKTopicTerms();
			EstimationGeneralDocumentTopic estimation_general = new EstimationGeneralDocumentTopic(new_document_tag_model, Document_Model, TOPICS_TERMS_DISRIBUTIONS);
			estimation_general.estimateDocumentTopicPrior(Document_Topic_Distribution);
			estimation_general.estimateTagTopicPrior(Tag_Topic_Distribution);
			estimation_general.estimatePrior(Document_Topic_Distribution, Tag_Topic_Distribution);
			estimation_general.estimateNonPrior();
			
			this.Proximity_Document_Prior = estimation_general.getProximity_Document_Prior();
			this.Proximity_Tag_Prior = estimation_general.getProximity_Tag_Prior();
			this.Proximity_Prior = estimation_general.getProximity();
			this.Proximity = estimation_general.getProximity();
			break;			
		}

		case "ALL_TERMES":{
			EstimationGeneralDocumentTopic estimation_general = new EstimationGeneralDocumentTopic(Document_Tag_Model, Document_Model, TOPICS_TERMS_DISRIBUTIONS);
			estimation_general.estimateDocumentTopicPrior(Document_Topic_Distribution);
			estimation_general.estimateTagTopicPrior(Tag_Topic_Distribution);
			estimation_general.estimatePrior(Document_Topic_Distribution, Tag_Topic_Distribution);
			estimation_general.estimateNonPrior();
			
			this.Proximity_Document_Prior = estimation_general.getProximity_Document_Prior();
			this.Proximity_Tag_Prior = estimation_general.getProximity_Tag_Prior();
			this.Proximity_Prior = estimation_general.getProximity();
			this.Proximity = estimation_general.getProximity();
			break;			
		}

		default:
			break;
		}

	}
	
	/**
	 * Only topk k document topic are used
	 * @param config
	 */
	public void estimationSelected(String MATRIX_CONFIG){
		switch (MATRIX_CONFIG) {
		case "TOP_TERMS":{
			// remove tags does not appears in topic vocabulary
			HashMap<String, Double> new_document_tag_model = new HashMap<>();
			new_document_tag_model =  getMapTopKTopicTerms();
			Set<Integer> Top_Topics = new HashSet<>();
			Top_Topics = getTopKTopicDocument();
			EstimationSelectedDocumentTopic estimation_selected = new EstimationSelectedDocumentTopic(Top_Topics, new_document_tag_model, Document_Model, TOPICS_TERMS_DISRIBUTIONS);
			estimation_selected.estimateDocumentTopicPrior(Document_Topic_Distribution);
			estimation_selected.estimateTagTopicPrior(Tag_Topic_Distribution);
			estimation_selected.estimatePrior(Document_Topic_Distribution, Tag_Topic_Distribution);
			estimation_selected.estimateNonPrior();
			
			this.Proximity_Document_Prior = estimation_selected.getProximity_Document_Prior();
			this.Proximity_Tag_Prior = estimation_selected.getProximity_Tag_Prior();
			this.Proximity_Prior = estimation_selected.getProximity();
			this.Proximity = estimation_selected.getProximity();
			break;			
		}

		case "ALL_TERMES":{
			Set<Integer> Top_Topics = new HashSet<>();
			Top_Topics = getTopKTopicDocument();
			EstimationSelectedDocumentTopic estimation_selected = new EstimationSelectedDocumentTopic(Top_Topics, Document_Tag_Model, Document_Model, TOPICS_TERMS_DISRIBUTIONS);
			estimation_selected.estimateDocumentTopicPrior(Document_Topic_Distribution);
			estimation_selected.estimateTagTopicPrior(Tag_Topic_Distribution);
			estimation_selected.estimatePrior(Document_Topic_Distribution, Tag_Topic_Distribution);
			estimation_selected.estimateNonPrior();
			
			this.Proximity_Document_Prior = estimation_selected.getProximity_Document_Prior();
			this.Proximity_Tag_Prior = estimation_selected.getProximity_Tag_Prior();
			this.Proximity_Prior = estimation_selected.getProximity();
			this.Proximity = estimation_selected.getProximity();
			break;			
		}

		default:
			break;
		}

	}
	

	public HashMap<String, Double> getProximity_Document_Prior() {
		return Proximity_Document_Prior;
	}

	public HashMap<String, Double> getProximity_Tag_Prior() {
		return Proximity_Tag_Prior;
	}

	public HashMap<String, Double> getProximity_Prior() {
		return Proximity_Prior;
	}

	public HashMap<String, Double> getProximity() {
		return Proximity;
	}

	/**
	 * This function remove tags does not appears in top k topics Terms
	 */
	public HashMap<String, Double> getMapTopKTopicTerms(){
		// get all terms in topics
		HashSet<String> TopicTermsVocabulary = new HashSet<>();
		Collection<HashMap<String, Double>> map =  TOPICS_TERMS_DISRIBUTIONS.values();
		for (Iterator<HashMap<String, Double>> iterator = map.iterator(); iterator.hasNext();) {
			HashMap<String, Double> hashMap = iterator.next();
			TopicTermsVocabulary.addAll(hashMap.keySet());			
		}

		// remove tags does not appears in topic vocabulary
		HashMap<String, Double> new_document_tag_model = new HashMap<>();
		for (Entry<String, Double> entry : this.Document_Tag_Model.entrySet()) {
			if(TopicTermsVocabulary.contains(entry.getKey())){
				new_document_tag_model.put(entry.getKey(), entry.getValue());
			}
		}

		return new_document_tag_model;		
	}
	
	public Set<Integer> getTopKTopicDocument(){
		Set<Integer> Top_Topics = new HashSet<>();
		Top_Topics =  this.Document_Topic_Distribution.keySet();
		return Top_Topics;		
	}
}
