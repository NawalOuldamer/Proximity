/**
 * 
 */
package own.thesis.proximity;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.hadoop.util.hash.Hash;
import org.apache.log4j.Logger;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import own.thesis.proximity.strategy.lda.matrix.LDA_Matrix_Estimation;


/**
 * @author ould
 *
 */
public class RunProximity {

	private static String MODEL_PROX;
	private static String MATRIX_CONFIG;
	private static String MATRIX_MODEL;
	private static int K;
	private static int TOP_K_DOC_TOPIC;
	private static int TOP_K_TERMS_TOPICS;
	private static double SIM_THRESHOLD;
	private static String PATH_FILE_SAVE_MODELS;
	private static String WORD2VEC_MODEL_PATH ="./data/SBS/aol-dataset.100.cbow.w8.neg25.it25.model";
	private static String PATH_TAGS_MODELS = "./data/SBS/tags_models/sbs_document_tags_cleaned";
	private static String PATH_DOCUMENTS_MODELS ="./data/SBS/initial_document_models/SLM";
	private static String PATH_DOCUMENT_TOPICS_DISTRIBUTION ="./data/SBS/initial_document_models/SLM";
	private static String PATH_TERMES_TOPICS_DISTRIBUTION ="./data/SBS/initial_document_models/SLM";
	private static String PATH_TAGS_TOPICS_DISTRIBUTION;

	
	
	
	public static HashMap<String,HashMap<String, Double> > DOCUMENTS_TAGS = new HashMap<>(); // document tags with their term frequency
	public static HashMap<String,HashMap<String, Double> > DOCUMENTS_MODELS = new HashMap<>(); // document models (SLM, DIR,HIEM,BM25)
	private static HashMap<Integer, HashMap<String, Double>> TOPICS_TERMS_DISRIBUTIONS = new HashMap<>(); // contains topics and terms distributions
	private static HashMap<String, HashMap<Integer, Double>> DOCUMENTS_TOPICS_DISRIBUTIONS = new HashMap<>(); // contains documents and topics distributions
	private static HashMap<String, HashMap<Integer, Double>> TAGS_TOPICS_DISRIBUTIONS = new HashMap<>(); // contains documents and topics distributions
	public static WordVectors WORDS_VECTORS;

	
	private static final Logger logger = Logger.getLogger(RunProximity.class);

	/**
	 * @param args
	 */


	public static void usage(){
		System.out.println("	--build-proximity-model [LM,LDA,LDA_MATRIX,TM]  : Proxmity based on LM, LDA, TM(Translation Model with sim), LDA_MATRIX:");
		System.out.println("	--sim-threshold [double] : sim threshold keep only tag that has sim(tag,terms)> sim threashold ");
		System.out.println("	--matrix-config [ALL_TERMES, TOP_TERMS]  : ALL :consider all terms inf topics , TOP: keep only top k terms in topics");
		System.out.println("	--matrix-model [ALL_TOPICS, TOP_TOPICS] : GENERAL : keep all topics for a document, SELECTION: only topk k topics document");
		System.out.println("	--k-topics [int] : number of LDA topics  ");
		System.out.println("	--matrix-top-k-document-topic [int]  :  top k topic for each document");
		System.out.println("	--matrix-top-k-terms-topic [int] : top k terms for topics (considers only topk terms for each topic (by default 20))");
		System.out.println("	--input-path-save-results-file [FILENAME] : path to save results ");
		System.out.println("	--input-path-tags-models-file [FILENAME]  : path to document tag models (DIR, HIEM, SLM, BM25) ");
		System.out.println("	--input-path-document-models-file [FILENAME]  : path to document models (DIR, HIEM, SLM, BM25)");
		System.out.println("	--input-path-document-topics-file [FILENAME] : path to documents topic distribution from LDA  ");
		System.out.println("	--input-path-terms-topics-file [FILENAME] : path to terms topic distribution from LDA ");
		System.out.println("	--input-w2v-model [FILENAME] : path to word2vec model");

	}
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length == 0 || args[0].equals("--help") ) {
			System.out.print("Usage: " ); usage();
		}
		else {
			for (int i = 0; i < args.length; i = i+2) {
				if(args[i].equals("--build-proximity-model")){
					MODEL_PROX = args[i+1];
				}
				if(args[i].equals("--sim-threshold")){
					SIM_THRESHOLD = Double.parseDouble(args[i+1]);
				}

				if(args[i].equals("--matrix-config")){
					MATRIX_CONFIG = args[i+1];
				}
				if(args[i].equals("--matrix-model")){
					MATRIX_MODEL = args[i+1];
				}
				if(args[i].equals("--k-topics")){
					K= Integer.parseInt(args[i+1]);
				}
				if(args[i].equals("--matrix-top-k-document-topic")){
					TOP_K_DOC_TOPIC = Integer.parseInt(args[i+1]);
				}				
				if(args[i].equals("--matrix-top-k-terms-topic")){
					TOP_K_TERMS_TOPICS = Integer.parseInt(args[i+1]);
				}
				if(args[i].equals("--input-path-save-results-file")){
					PATH_FILE_SAVE_MODELS = args[i+1];
				}				
				if(args[i].equals("--input-path-tags-models-file")){
					PATH_TAGS_MODELS = args[i+1];
				}
				if(args[i].equals("--input-path-document-models-file")){
					PATH_DOCUMENTS_MODELS = args[i+1];
				}
				if(args[i].equals("--input-w2v-model")){
					WORD2VEC_MODEL_PATH = args[i+1];
				}
				if(args[i].equals("--input-path-terms-topics-file")){
					PATH_TERMES_TOPICS_DISTRIBUTION = args[i+1];
				}
				if(args[i].equals("--input-path-document-topics-file")){
					PATH_DOCUMENT_TOPICS_DISTRIBUTION = args[i+1];
				}
			}

			/**
			 * Print Configuration
			 */

			switch (MODEL_PROX) {
			case "LM":{
				logger.info("Model build with parameters:");
				logger.info("MODEL_PROX: "+ MODEL_PROX);
				if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_DOCUMENTS_MODELS.isEmpty()){
					logger.info("Missing Value:");
					logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
					logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
					logger.info("PATH_DOCUMENTS_MODELS: "+ PATH_DOCUMENTS_MODELS);
					break;
				}
				else {
					// estimate proximity based on LM Model
				}
				break;
			}
			case "LDA":{
				logger.info("Model build with parameters:");
				logger.info("MODEL_PROX: "+ MODEL_PROX);
				if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_TERMES_TOPICS_DISTRIBUTION.isEmpty()  || PATH_DOCUMENT_TOPICS_DISTRIBUTION.isEmpty() || (K == 0)){
					logger.info("Missing Value:");
					logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
					logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
					logger.info("PATH_DOCUMENT_TOPICS_DISTRIBUTION: "+PATH_DOCUMENT_TOPICS_DISTRIBUTION);
					logger.info("PATH_TERMES_TOPICS_DISTRIBUTION: "+PATH_TERMES_TOPICS_DISTRIBUTION);
					logger.info("K: "+ K );
					break;
				}
				else {
					// estimate proximity based on Classical LDA
				}
				break;
			}
			case "TM":{
				logger.info("Model build with parameters:");
				logger.info("MODEL_PROX: "+ MODEL_PROX);
				if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_DOCUMENTS_MODELS.isEmpty() || SIM_THRESHOLD < 0.0 || WORD2VEC_MODEL_PATH.isEmpty()){
					logger.info("Missing Value:");
					logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
					logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
					logger.info("SIM_THRESHOLD: "+SIM_THRESHOLD);
					logger.info("PATH_DOCUMENTS_MODELS: "+ PATH_DOCUMENTS_MODELS);
					logger.info("WORD2VEC_MODEL_PATH: "+WORD2VEC_MODEL_PATH);

					break;
				}
				else {
					// estimate proximity based on Translation model
				}
				break;
			}
			case "LDA_MATRIX":{
				logger.info("Model build with parameters:");
				logger.info("MODEL_PROX: "+ MODEL_PROX);
				if(MATRIX_MODEL.equals("ALL_TOPICS")){ // general model
					if(MATRIX_CONFIG.equalsIgnoreCase("ALL_TERMS")){
						if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_TERMES_TOPICS_DISTRIBUTION.isEmpty()  
								|| PATH_DOCUMENT_TOPICS_DISTRIBUTION.isEmpty() || (K == 0)){
							logger.info("Missing Value:");
							logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
							logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
							logger.info("PATH_DOCUMENT_TOPICS_DISTRIBUTION: "+PATH_DOCUMENT_TOPICS_DISTRIBUTION);
							logger.info("PATH_TERMES_TOPICS_DISTRIBUTION: "+PATH_TERMES_TOPICS_DISTRIBUTION);
							logger.info("K: "+ K );
							logger.info("MATRIX_CONFIG: "+MATRIX_CONFIG);
							logger.info("MATRIX_MODEL: "+MATRIX_MODEL);
							break;
						}
						else {
							// estimate proximity GENERAL with ALL TERMS
							logger.info("Estimate Model ALL_TOPICS and ALL_TERMS");
							init_LDA_MATRIX_PROXIMITY();
						}
					}
					else {
						if(MATRIX_CONFIG.equalsIgnoreCase("TOP_TERMS")){
							if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_TERMES_TOPICS_DISTRIBUTION.isEmpty()  
									|| PATH_DOCUMENT_TOPICS_DISTRIBUTION.isEmpty() || (K == 0) || TOP_K_TERMS_TOPICS == 0){
								logger.info("Missing Value:");
								logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
								logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
								logger.info("PATH_DOCUMENT_TOPICS_DISTRIBUTION: "+PATH_DOCUMENT_TOPICS_DISTRIBUTION);
								logger.info("PATH_TERMES_TOPICS_DISTRIBUTION: "+PATH_TERMES_TOPICS_DISTRIBUTION);
								logger.info("K: "+ K );
								logger.info("MATRIX_CONFIG: "+MATRIX_CONFIG);
								logger.info("MATRIX_MODEL: "+MATRIX_MODEL);
								logger.info("TOP_K_TERMS_TOPICS: "+TOP_K_TERMS_TOPICS);
								break;
							}
							else {
								// estimate proximity GENERAL with TOP_TERMS
								logger.info("Estimate Model ALL_TOPICS and TOP_TERMS");
								init_LDA_MATRIX_PROXIMITY();
							}
						}
					}
				}
				else {
					if(MATRIX_MODEL.equals("TOP_TOPICS")){
						if(MATRIX_CONFIG.equalsIgnoreCase("ALL_TERMS")){
							if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_TERMES_TOPICS_DISTRIBUTION.isEmpty()  
									|| PATH_DOCUMENT_TOPICS_DISTRIBUTION.isEmpty() || (K == 0)){
								logger.info("Missing Value:");
								logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
								logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
								logger.info("PATH_DOCUMENT_TOPICS_DISTRIBUTION: "+PATH_DOCUMENT_TOPICS_DISTRIBUTION);
								logger.info("PATH_TERMES_TOPICS_DISTRIBUTION: "+PATH_TERMES_TOPICS_DISTRIBUTION);
								logger.info("K: "+ K );
								logger.info("MATRIX_CONFIG: "+MATRIX_CONFIG);
								logger.info("MATRIX_MODEL: "+MATRIX_MODEL);
								break;
							}
							else {
								// estimate proximity SELECTED Model with ALL_TERMS
								logger.info("Estimate Model TOP_TOPICS and ALL_TERMS");
								init_LDA_MATRIX_PROXIMITY();
							}
						}
						else {
							if(MATRIX_CONFIG.equalsIgnoreCase("TOP_TERMS")){
								if(PATH_FILE_SAVE_MODELS.isEmpty() || PATH_TAGS_MODELS.isEmpty() || PATH_TERMES_TOPICS_DISTRIBUTION.isEmpty()  
										|| PATH_DOCUMENT_TOPICS_DISTRIBUTION.isEmpty() || (K == 0) || TOP_K_TERMS_TOPICS == 0 || TOP_K_DOC_TOPIC == 0){
									logger.info("Missing Value:");
									logger.info("PATH_FILE_SAVE_MODELS: "+ PATH_FILE_SAVE_MODELS);
									logger.info("PATH_TAGS_MODELS: "+ PATH_TAGS_MODELS);
									logger.info("PATH_DOCUMENT_TOPICS_DISTRIBUTION: "+PATH_DOCUMENT_TOPICS_DISTRIBUTION);
									logger.info("PATH_TERMES_TOPICS_DISTRIBUTION: "+PATH_TERMES_TOPICS_DISTRIBUTION);
									logger.info("K: "+ K );
									logger.info("MATRIX_CONFIG: "+MATRIX_CONFIG);
									logger.info("MATRIX_MODEL: "+MATRIX_MODEL);
									logger.info("TOP_K_TERMS_TOPICS: "+TOP_K_TERMS_TOPICS);
									logger.info("TOP_K_DOC_TOPIC: "+TOP_K_DOC_TOPIC);
									break;
								}
								else {
									// estimate proximity SELECTED Model with TOP_TERMES
									logger.info("Estimate Model TOP_TOPICS and TOP_TERMS");
									init_LDA_MATRIX_PROXIMITY();
								}
							}
						}
					}
				}
				break;
			}

			default:
				break;
			}
		}

	}

	public static void init_LDA_MATRIX_PROXIMITY() throws FileNotFoundException{
		InitProximity init = new InitProximity(PATH_TAGS_TOPICS_DISTRIBUTION, PATH_TAGS_MODELS, PATH_DOCUMENTS_MODELS, PATH_DOCUMENT_TOPICS_DISTRIBUTION, PATH_TERMES_TOPICS_DISTRIBUTION);
		DOCUMENTS_MODELS = init.loadDocumentModels();
		DOCUMENTS_TAGS = init.loadTagsModels();
		DOCUMENTS_TOPICS_DISRIBUTIONS = init.loadDocumentsTopicsDistributions();
		TAGS_TOPICS_DISRIBUTIONS = init.loadTagsTopicsDistributions();
		TOPICS_TERMS_DISRIBUTIONS = init.loadTopicsTermsDistributions();	
	}

	public static void init_LDA_PROXIMITY() throws FileNotFoundException{
		InitProximity init = new InitProximity(PATH_TAGS_MODELS,PATH_DOCUMENT_TOPICS_DISTRIBUTION, PATH_TERMES_TOPICS_DISTRIBUTION);
		init.loadTagsModels();
		init.loadDocumentsTopicsDistributions();
		init.loadTopicsTermsDistributions();		
	}

	public static void init_LM_PROXIMITY() throws FileNotFoundException{
		InitProximity init = new InitProximity(PATH_TAGS_MODELS, PATH_DOCUMENTS_MODELS);
		init.loadDocumentModels();
		init.loadTagsModels();
	}
	public static void init_TM_PROXIMITY() throws IOException{
		InitProximity init = new InitProximity(WORD2VEC_MODEL_PATH, PATH_TAGS_MODELS, PATH_DOCUMENTS_MODELS);
		init.loadDocumentModels();
		init.loadTagsModels();
		init.loadW2VModel();
	}
}
