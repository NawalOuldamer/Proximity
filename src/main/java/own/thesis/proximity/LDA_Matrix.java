package own.thesis.proximity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import own.thesis.proximity.strategy.lda.matrix.LDA_Matrix_Estimation;
import own.thesis.utils.SortList;

public class LDA_Matrix {


	private String MODEL_PROX;
	private String MATRIX_CONFIG;
	private String MATRIX_MODEL;
	private int K;
	private int TOP_K_DOC_TOPIC;
	private int TOP_K_TERMS_TOPICS;
	private HashMap<String,HashMap<String, Double> > DOCUMENTS_TAGS = new HashMap<>(); // document tags with their term frequency
	private HashMap<String,HashMap<String, Double> > DOCUMENTS_MODELS = new HashMap<>(); // document models (SLM, DIR,HIEM,BM25)
	private HashMap<Integer, HashMap<String, Double>> TOPICS_TERMS_DISRIBUTIONS = new HashMap<>(); // contains topics and terms distributions
	private HashMap<String, HashMap<Integer, Double>> DOCUMENTS_TOPICS_DISRIBUTIONS = new HashMap<>(); // contains documents and topics distributions
	private HashMap<String, HashMap<Integer, Double>> TAGS_TOPICS_DISRIBUTIONS = new HashMap<>(); // contains documents and topics distributions
	private String PATH_FILE_SAVE_MODELS;

	
	
	private HashMap<String, Double> Proximity_Document_Prior = new HashMap<>();
	private HashMap<String, Double> Proximity_Tag_Prior = new HashMap<>();
	private HashMap<String, Double> Proximity_Prior = new HashMap<>();
	private HashMap<String, Double> Proximity = new HashMap<>();


	
	public LDA_Matrix(HashMap<String,HashMap<String, Double> > DOCUMENTS_TAGS,HashMap<String,HashMap<String, Double> > DOCUMENTS_MODELS,
			HashMap<Integer, HashMap<String, Double>> TOPICS_TERMS_DISRIBUTIONS, HashMap<String, HashMap<Integer, Double>> DOCUMENTS_TOPICS_DISRIBUTIONS,
			HashMap<String, HashMap<Integer, Double>> TAGS_TOPICS_DISRIBUTIONS, String MODEL_PROX, String MATRIX_CONFIG, String MATRIX_MODEL, int K, 
			int TOP_K_DOC_TOPIC, int TOP_K_TERMS_TOPICS, String PATH_FILE_SAVE_MODELS){
		this.MODEL_PROX = MODEL_PROX;
		this.MATRIX_CONFIG = MATRIX_CONFIG;
		this.MATRIX_MODEL = MATRIX_CONFIG;
		this.K = K;
		this.TOP_K_DOC_TOPIC = TOP_K_DOC_TOPIC;
		this.TOP_K_TERMS_TOPICS = TOP_K_TERMS_TOPICS;
		this.DOCUMENTS_TAGS = DOCUMENTS_TAGS;
		this.DOCUMENTS_MODELS = DOCUMENTS_MODELS;
		this.TOPICS_TERMS_DISRIBUTIONS = TOPICS_TERMS_DISRIBUTIONS;
		this.DOCUMENTS_TOPICS_DISRIBUTIONS = DOCUMENTS_TOPICS_DISRIBUTIONS;
		this.TAGS_TOPICS_DISRIBUTIONS = TAGS_TOPICS_DISRIBUTIONS;
		this.PATH_FILE_SAVE_MODELS  = PATH_FILE_SAVE_MODELS;

	}
	public void estimate() throws IOException{
		switch (MATRIX_MODEL) {
		case "ALL_TOPICS":{
			// for all documents 
			for (Entry<String,HashMap<String, Double>> entry : DOCUMENTS_TAGS.entrySet()) {
				String docID = entry.getKey();
				HashMap<String, Double> Document_Tag_Model = entry.getValue();
				HashMap<String, Double> Document_Model = DOCUMENTS_MODELS.get(docID);
				HashMap<Integer, Double> Document_Topic_Distribution = DOCUMENTS_TOPICS_DISRIBUTIONS.get(docID);
				HashMap<Integer, Double> Tag_Topic_Distribution = TAGS_TOPICS_DISRIBUTIONS.get(docID);
				LDA_Matrix_Estimation estimation = new LDA_Matrix_Estimation(Document_Tag_Model, TOPICS_TERMS_DISRIBUTIONS, Document_Model, Document_Topic_Distribution, Tag_Topic_Distribution);
				estimation.estimationGeneral(MATRIX_CONFIG);
				this.Proximity_Document_Prior = new HashMap<>();
				this.Proximity_Document_Prior = estimation.getProximity_Document_Prior();
				this.Proximity_Tag_Prior = new HashMap<>();
				this.Proximity_Tag_Prior = estimation.getProximity_Tag_Prior();
				this.Proximity_Prior = new HashMap<>();
				this.Proximity_Prior = estimation.getProximity();
				this.Proximity = new HashMap<>();
				this.Proximity = estimation.getProximity();
				
				
				// save results 
				SortList  sort = new SortList();
				saveProxomity_Models(sort.sort(this.Proximity_Document_Prior,this.Proximity_Document_Prior.size()), docID);
				saveProxomity_Models(sort.sort(this.Proximity_Tag_Prior,this.Proximity_Tag_Prior.size()), docID);
				saveProxomity_Models(sort.sort(this.Proximity_Prior,this.Proximity_Prior.size()), docID);
				saveProxomity_Models(sort.sort(this.Proximity,this.Proximity.size()), docID);

			}
			break;
		}


		case "TOP_TOPICS":{
			// for all documents 
			for (Entry<String,HashMap<String, Double>> entry : DOCUMENTS_TAGS.entrySet()) {
				String docID = entry.getKey();
				HashMap<String, Double> Document_Tag_Model = entry.getValue();
				HashMap<String, Double> Document_Model = DOCUMENTS_MODELS.get(docID);
				HashMap<Integer, Double> Document_Topic_Distribution = DOCUMENTS_TOPICS_DISRIBUTIONS.get(docID);
				HashMap<Integer, Double> Tag_Topic_Distribution = TAGS_TOPICS_DISRIBUTIONS.get(docID);
				LDA_Matrix_Estimation estimation = new LDA_Matrix_Estimation(Document_Tag_Model, TOPICS_TERMS_DISRIBUTIONS, Document_Model, Document_Topic_Distribution, Tag_Topic_Distribution);
				estimation.estimationSelected(MATRIX_CONFIG);	
				this.Proximity_Document_Prior = new HashMap<>();
				this.Proximity_Document_Prior = estimation.getProximity_Document_Prior();
				this.Proximity_Tag_Prior = new HashMap<>();
				this.Proximity_Tag_Prior = estimation.getProximity_Tag_Prior();
				this.Proximity_Prior = new HashMap<>();
				this.Proximity_Prior = estimation.getProximity();
				this.Proximity = new HashMap<>();
				this.Proximity = estimation.getProximity();
				
				// save results 
				SortList  sort = new SortList();
				saveProxomity_Models(sort.sort(this.Proximity_Document_Prior,this.Proximity_Document_Prior.size()), docID);
				saveProxomity_Models(sort.sort(this.Proximity_Tag_Prior,this.Proximity_Tag_Prior.size()), docID);
				saveProxomity_Models(sort.sort(this.Proximity_Prior,this.Proximity_Prior.size()), docID);
				saveProxomity_Models(sort.sort(this.Proximity,this.Proximity.size()), docID);
			}
			break;
		}

		default:
			break;
		}
		
		
		
	}

	public void saveProxomity_Models(List<Map.Entry<String, Double>> Model, String docID) throws IOException{
		if(!new File(this.PATH_FILE_SAVE_MODELS+"/"+MODEL_PROX).exists()){			
			new File(this.PATH_FILE_SAVE_MODELS+"/"+MODEL_PROX).mkdirs();
		}
		FileWriter file = new FileWriter(PATH_FILE_SAVE_MODELS+"/"+MODEL_PROX+"/MATRIX_MODEL_"+MATRIX_MODEL+"_MATRIX_CONFIG_"+MATRIX_CONFIG+"_K_"+K+"_TOP_K_DOC_TOPIC_"+TOP_K_DOC_TOPIC+"_TOP_K_TERMS_TOPICS_"+TOP_K_TERMS_TOPICS,true);
		file.write(docID+" ");
		for (Iterator<Entry<String, Double>> iterator = Model.iterator(); iterator.hasNext();) {
			Entry<String, Double> entry = iterator.next();
			file.write(entry.getKey()+":"+entry.getValue()+" ");				
		}
		file.write("\n");
		file.close();
	}
}
